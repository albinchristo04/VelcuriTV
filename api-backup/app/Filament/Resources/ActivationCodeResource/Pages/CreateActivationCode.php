<?php

namespace App\Filament\Resources\ActivationCodeResource\Pages;

use App\Filament\Resources\ActivationCodeResource;
use App\Models\ActivationCode;
use Filament\Resources\Pages\CreateRecord;

class CreateActivationCode extends CreateRecord
{
    protected static string $resource = ActivationCodeResource::class;

    protected function mutateFormDataBeforeCreate(array $data): array
    {
        $data['code'] = ActivationCode::generateCode();
        $data['created_by'] = auth()->id();
        return $data;
    }
}
