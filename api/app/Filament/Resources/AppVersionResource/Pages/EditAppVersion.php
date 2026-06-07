<?php

namespace App\Filament\Resources\AppVersionResource\Pages;

use App\Filament\Resources\AppVersionResource;
use App\Models\AppVersion;
use Filament\Resources\Pages\EditRecord;

class EditAppVersion extends EditRecord
{
    protected static string $resource = AppVersionResource::class;

    protected function afterSave(): void
    {
        if ($this->record->is_active) {
            AppVersion::where('id', '!=', $this->record->id)->update(['is_active' => false]);
        }
    }
}