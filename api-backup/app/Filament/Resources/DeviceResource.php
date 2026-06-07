<?php

namespace App\Filament\Resources;

use App\Filament\Resources\DeviceResource\Pages;
use App\Models\Device;
use Filament\Forms;
use Filament\Forms\Form;
use Filament\Resources\Resource;
use Filament\Tables;
use Filament\Tables\Table;
use Filament\Tables\Actions\Action;

class DeviceResource extends Resource
{
    protected static ?string $model = Device::class;
    protected static ?string $navigationIcon = 'heroicon-o-device-phone-mobile';
    protected static ?string $navigationLabel = 'Devices';

    public static function form(Form $form): Form
    {
        return $form->schema([
            Forms\Components\TextInput::make('device_model')->disabled(),
            Forms\Components\TextInput::make('android_version')->disabled(),
            Forms\Components\TextInput::make('app_version')->disabled(),
            Forms\Components\Toggle::make('is_blocked')->label('Blocked'),
        ]);
    }

    public static function table(Table $table): Table
    {
        return $table
            ->columns([
                Tables\Columns\TextColumn::make('activationCode.code')
                    ->label('Activation Code')
                    ->searchable()
                    ->url(fn ($record) => ActivationCodeResource::getUrl('edit', ['record' => $record->activation_id])),
                Tables\Columns\TextColumn::make('device_model')->label('Model')->searchable(),
                Tables\Columns\TextColumn::make('android_version')->label('Android'),
                Tables\Columns\TextColumn::make('app_version')->label('App Version'),
                Tables\Columns\TextColumn::make('country'),
                Tables\Columns\TextColumn::make('last_seen')->since()->sortable(),
                Tables\Columns\IconColumn::make('is_blocked')->label('Blocked')->boolean(),
            ])
            ->actions([
                Action::make('toggleBlock')
                    ->label(fn ($record) => $record->is_blocked ? 'Unblock' : 'Block')
                    ->icon('heroicon-o-shield-exclamation')
                    ->action(fn ($record) => $record->update(['is_blocked' => !$record->is_blocked])),
                Action::make('remove')
                    ->label('Remove')
                    ->icon('heroicon-o-trash')
                    ->color('danger')
                    ->requiresConfirmation()
                    ->action(function ($record) {
                        $record->activationCode->decrement('devices_used');
                        $record->delete();
                    }),
            ]);
    }

    public static function getRelations(): array
    {
        return [];
    }

    public static function getPages(): array
    {
        return [
            'index' => Pages\ListDevices::route('/'),
        ];
    }
}
