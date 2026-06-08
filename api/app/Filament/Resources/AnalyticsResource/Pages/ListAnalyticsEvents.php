<?php

namespace App\Filament\Resources\AnalyticsResource\Pages;

use App\Filament\Resources\AnalyticsResource;
use Filament\Resources\Pages\ListRecords;

class ListAnalyticsEvents extends ListRecords
{
    protected static string $resource = AnalyticsResource::class;

    protected function getHeaderActions(): array
    {
        return [];
    }
}
