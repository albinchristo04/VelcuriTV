<?php

namespace App\Filament\Resources;

use App\Filament\Resources\AppVersionResource\Pages;
use App\Models\AppVersion;
use Filament\Forms;
use Filament\Forms\Form;
use Filament\Resources\Resource;
use Filament\Tables;
use Filament\Tables\Table;

class AppVersionResource extends Resource
{
    protected static ?string $model = AppVersion::class;
    protected static ?string $navigationIcon = 'heroicon-o-arrow-up-circle';
    protected static ?string $navigationLabel = 'App Versions';

    public static function form(Form $form): Form
    {
        return $form->schema([
            Forms\Components\TextInput::make('version')->required(),
            Forms\Components\TextInput::make('version_code')->numeric()->required(),
            Forms\Components\TextInput::make('download_url')->url()->required(),
            Forms\Components\Toggle::make('force_update')->label('Force Update'),
            Forms\Components\Textarea::make('release_notes')->nullable(),
            Forms\Components\Toggle::make('is_active')->label('Active')->default(true),
        ]);
    }

    public static function table(Table $table): Table
    {
        return $table
            ->columns([
                Tables\Columns\TextColumn::make('version')->sortable(),
                Tables\Columns\TextColumn::make('version_code')->sortable(),
                Tables\Columns\IconColumn::make('force_update')->boolean()->label('Force Update'),
                Tables\Columns\IconColumn::make('is_active')->boolean()->label('Active'),
                Tables\Columns\TextColumn::make('created_at')->date()->sortable(),
            ])
            ->actions([
                Tables\Actions\EditAction::make(),
                Tables\Actions\DeleteAction::make(),
            ]);
    }

    public static function getRelations(): array
    {
        return [];
    }

    public static function getPages(): array
    {
        return [
            'index'  => Pages\ListAppVersions::route('/'),
            'create' => Pages\CreateAppVersion::route('/create'),
            'edit'   => Pages\EditAppVersion::route('/{record}/edit'),
        ];
    }
}
