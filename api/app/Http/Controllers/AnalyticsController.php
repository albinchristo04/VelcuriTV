<?php

namespace App\Http\Controllers;

use App\Models\Device;
use Illuminate\Http\JsonResponse;
use Illuminate\Http\Request;

class AnalyticsController extends Controller
{
    public function store(Request $request): JsonResponse
    {
        $request->validate([
            'event_type'     => 'required|string|max:50',
            'extension_name' => 'nullable|string|max:100',
            'country'        => 'nullable|string|max:10',
            'app_version'    => 'nullable|string|max:20',
            'meta'           => 'nullable|array',
        ]);

        // Find device from token
        $activationCode = $request->user();
        $deviceId = null;

        if ($activationCode) {
            $tokenName = $activationCode->currentAccessToken()->name ?? '';
            if (preg_match('/^device:(\d+)$/', $tokenName, $matches)) {
                $deviceId = (int) $matches[1];
            }
        }

        \App\Models\AnalyticsEvent::create([
            'device_id'      => $deviceId,
            'event_type'     => $request->event_type,
            'extension_name' => $request->extension_name,
            'country'        => $request->country,
            'app_version'    => $request->app_version,
            'meta'           => $request->meta ? json_encode($request->meta) : null,
        ]);

        return response()->json(['success' => true]);
    }
}
