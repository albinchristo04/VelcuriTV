<?php

namespace App\Filament\Resources\AppVersionResource\Pages;

use App\Filament\Resources\AppVersionResource;
use App\Models\AppVersion;
use Filament\Resources\Pages\CreateRecord;

class CreateAppVersion extends CreateRecord
{
    protected static string $resource = AppVersionResource::class;

    protected function afterCreate(): void
    {
        // When marking a new version active, deactivate all others
        if ($this->record->is_active) {
            AppVersion::where('id', '!=', $this->record->id)->update(['is_active' => false]);
        }
    }
}