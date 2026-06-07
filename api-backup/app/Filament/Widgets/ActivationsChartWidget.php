<?php

namespace App\Filament\Widgets;

use App\Models\ActivationCode;
use Filament\Widgets\ChartWidget;
use Illuminate\Support\Carbon;

class ActivationsChartWidget extends ChartWidget
{
    protected static ?string $heading = 'Activations (Last 30 Days)';

    protected function getData(): array
    {
        $data = collect(range(29, 0))->map(function (int $daysAgo) {
            $date = Carbon::today()->subDays($daysAgo);
            return [
                'date'  => $date->format('M j'),
                'count' => ActivationCode::whereDate('updated_at', $date)
                    ->where('status', 'active')
                    ->count(),
            ];
        });

        return [
            'datasets' => [
                [
                    'label'           => 'Activations',
                    'data'            => $data->pluck('count')->toArray(),
                    'backgroundColor' => '#6C3FE8',
                    'borderColor'     => '#6C3FE8',
                ],
            ],
            'labels' => $data->pluck('date')->toArray(),
        ];
    }

    protected function getType(): string
    {
        return 'line';
    }
}
