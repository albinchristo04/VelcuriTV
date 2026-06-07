<?php

namespace App\Http\Controllers;

use App\Models\AppVersion;
use Illuminate\Http\JsonResponse;
use Illuminate\Http\Request;

class UpdateController extends Controller
{
    public function check(Request $request): JsonResponse
    {
        $currentVersionCode = (int) $request->query('version_code', 0);
        $latest = AppVersion::latestActive()->first();

        if (!$latest) {
            return response()->json(['update_available' => false]);
        }

        return response()->json([
            'latest_version'   => $latest->version,
            'version_code'     => $latest->version_code,
            'force_update'     => $latest->force_update,
            'update_available' => $latest->version_code > $currentVersionCode,
            'download_url'     => $latest->download_url,
            'release_notes'    => $latest->release_notes,
        ]);
    }
}
