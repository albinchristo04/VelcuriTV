<?php

use App\Http\Controllers\ActivationController;
use App\Http\Controllers\AnalyticsController;
use App\Http\Controllers\UpdateController;
use Illuminate\Support\Facades\Route;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
*/

// Public endpoints
Route::middleware('throttle:activation')->post('/activate', [ActivationController::class, 'activate']);
Route::middleware('throttle:update')->get('/update', [UpdateController::class, 'check']);
Route::get('/ping', fn() => response()->json(['status' => 'ok', 'timestamp' => now()->toIso8601String()]));

// Authenticated endpoints (device token required)
Route::middleware('auth:sanctum')->group(function () {
    Route::post('/heartbeat', [ActivationController::class, 'heartbeat']);
    Route::post('/analytics/event', [AnalyticsController::class, 'store']);
});
