<?php

namespace App\Filament\Resources\AppVersionResource\Pages;

use App\Filament\Resources\AppVersionResource;
use Filament\Resources\Pages\ListRecords;

class ListAppVersions extends ListRecords
{
    protected static string $resource = AppVersionResource::class;
}