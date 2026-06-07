<?php

namespace App\Http\Controllers;

use App\Models\ActivationCode;
use App\Models\Device;
use App\Models\Subscription;
use Carbon\Carbon;
use Illuminate\Http\JsonResponse;
use Illuminate\Http\Request;

class ActivationController extends Controller
{
    public function activate(Request $request): JsonResponse
    {
        $request->validate([
            'code'            => 'required|string|max:20',
            'device_id'       => 'required|string|max:64',
            'device_model'    => 'nullable|string|max:100',
            'android_version' => 'nullable|string|max:20',
            'app_version'     => 'nullable|string|max:20',
        ]);

        $code = ActivationCode::where('code', strtoupper($request->code))->first();

        if (!$code) {
            return response()->json(['success' => false, 'error' => 'invalid_code', 'message' => 'Invalid activation code.'], 404);
        }

        // Check suspended/cancelled first
        if (in_array($code->status, ['suspended', 'cancelled'])) {
            return response()->json(['success' => false, 'error' => $code->status, 'message' => 'Account ' . $code->status . '.'], 403);
        }

        // Check expiry (and update status)
        if ($code->isExpired()) {
            $code->update(['status' => 'expired']);
            return response()->json(['success' => false, 'error' => 'expired', 'message' => 'Subscription expired.'], 403);
        }

        // Find existing device registration
        $device = Device::where('activation_id', $code->id)
            ->where('device_id', $request->device_id)
            ->first();

        if (!$device) {
            // New device — check slot availability
            if (!$code->canAddDevice()) {
                return response()->json(['success' => false, 'error' => 'device_limit_reached', 'message' => 'Device limit reached.'], 403);
            }

            $device = Device::create([
                'activation_id'   => $code->id,
                'device_id'       => $request->device_id,
                'device_model'    => $request->device_model,
                'android_version' => $request->android_version,
                'app_version'     => $request->app_version,
                'ip_address'      => $request->ip(),
                'last_seen'       => now(),
            ]);

            $code->increment('devices_used');

            // Activate code on first use
            if ($code->status === 'unused') {
                $expiryDate = $this->calculateExpiry($code->plan);
                $code->update(['status' => 'active', 'expiry_date' => $expiryDate]);
                Subscription::create([
                    'activation_id' => $code->id,
                    'plan'          => $code->plan,
                    'start_date'    => now(),
                    'end_date'      => $expiryDate,
                    'status'        => 'active',
                ]);
                $code->refresh();
            }
        } else {
            // Existing device — check if blocked
            if ($device->is_blocked) {
                return response()->json(['success' => false, 'error' => 'device_blocked', 'message' => 'This device is blocked.'], 403);
            }
            // Update device info on re-activation
            $device->update([
                'app_version' => $request->app_version,
                'last_seen'   => now(),
            ]);
        }

        // Issue Sanctum token scoped to this device
        $tokenName = "device:{$device->id}";
        // Revoke previous tokens for this device
        $code->tokens()->where('name', $tokenName)->delete();
        $token = $code->createToken($tokenName);

        return response()->json([
            'success'          => true,
            'token'            => $token->plainTextToken,
            'status'           => $code->status,
            'plan'             => $code->plan,
            'expiry'           => $code->expiry_date?->toIso8601String(),
            'devices_allowed'  => $code->devices_allowed,
            'devices_used'     => $code->devices_used,
        ]);
    }

    public function heartbeat(Request $request): JsonResponse
    {
        /** @var ActivationCode $activationCode */
        $activationCode = $request->user();

        if (!$activationCode) {
            return response()->json(['status' => 'unauthorized'], 401);
        }

        // Re-check expiry
        if ($activationCode->isExpired()) {
            $activationCode->update(['status' => 'expired']);
            return response()->json(['status' => 'expired', 'expiry' => $activationCode->expiry_date?->toIso8601String()], 403);
        }

        return response()->json([
            'status' => $activationCode->status,
            'expiry' => $activationCode->expiry_date?->toIso8601String(),
        ]);
    }

    private function calculateExpiry(string $plan): ?Carbon
    {
        return match ($plan) {
            'trial'      => now()->addDays(7),
            'monthly'    => now()->addDays(30),
            'quarterly'  => now()->addDays(90),
            'semiannual' => now()->addDays(180),
            'annual'     => now()->addDays(365),
            'lifetime'   => null,
            default      => now()->addDays(30),
        };
    }
}
