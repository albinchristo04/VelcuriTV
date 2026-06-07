<?php

use App\Http\Controllers\ActivationController;
use App\Http\Controllers\AnalyticsController;
use App\Http\Controllers\UpdateController;
use Illuminate\Cache\RateLimiting\Limit;
use Illuminate\Foundation\Application;
use Illuminate\Foundation\Configuration\Exceptions;
use Illuminate\Foundation\Configuration\Middleware;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\RateLimiter;

return Application::configure(basePath: dirname(__DIR__))
    ->withRouting(
        web: __DIR__ . '/../routes/web.php',
        api: __DIR__ . '/../routes/api.php',
        commands: __DIR__ . '/../routes/console.php',
        health: '/up',
    )
    ->withMiddleware(function (Middleware $middleware) {
        //
    })
    ->withExceptions(function (Exceptions $exceptions) {
        //
    })
    ->booted(function () {
        // Rate limits
        RateLimiter::for('activation', function (Request $request) {
            return Limit::perMinute(5)->by($request->ip())->response(function () {
                return response()->json(
                    ['success' => false, 'error' => 'rate_limited', 'message' => 'Too many attempts.'],
                    429
                );
            });
        });

        RateLimiter::for('update', function (Request $request) {
            return Limit::perMinute(60)->by($request->ip());
        });

        RateLimiter::for('api', function (Request $request) {
            return Limit::perMinute(120)->by($request->bearerToken() ?? $request->ip());
        });
    })
    ->create();
