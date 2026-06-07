<?php

namespace App\Filament\Widgets;

use App\Models\ActivationCode;
use App\Models\Device;
use Filament\Widgets\StatsOverviewWidget as BaseWidget;
use Filament\Widgets\StatsOverviewWidget\Stat;

class StatsOverviewWidget extends BaseWidget
{
    protected function getStats(): array
    {
        return [
            Stat::make('Active Codes', ActivationCode::where('status', 'active')->count())
                ->description('Total active subscriptions')
                ->color('success'),

            Stat::make('New Activations Today',
                ActivationCode::where('status', 'active')
                    ->whereDate('updated_at', today())
                    ->count()
            )
                ->description('Codes activated today')
                ->color('primary'),

            Stat::make('Active Devices',
                Device::where('last_seen', '>=', now()->subHours(24))
                    ->where('is_blocked', false)
                    ->count()
            )
                ->description('Devices seen in last 24h')
                ->color('info'),

            Stat::make('Expiring This Week',
                ActivationCode::where('status', 'active')
                    ->whereNotNull('expiry_date')
                    ->where('expiry_date', '<=', now()->addDays(7))
                    ->count()
            )
                ->description('Subscriptions expiring in 7 days')
                ->color('warning'),
        ];
    }
}
