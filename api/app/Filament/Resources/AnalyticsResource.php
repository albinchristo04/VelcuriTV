<?php

namespace App\Filament\Resources;

use App\Filament\Resources\AnalyticsResource\Pages;
use App\Models\AnalyticsEvent;
use Filament\Forms\Form;
use Filament\Resources\Resource;
use Filament\Tables;
use Filament\Tables\Table;
use Filament\Tables\Filters\SelectFilter;
use Filament\Tables\Filters\Filter;
use Illuminate\Database\Eloquent\Builder;
use Filament\Forms\Components\DatePicker;

class AnalyticsResource extends Resource
{
    protected static ?string $model = AnalyticsEvent::class;
    protected static ?string $navigationIcon = 'heroicon-o-chart-bar';
    protected static ?string $navigationLabel = 'Analytics';
    protected static ?int $navigationSort = 4;

    // Read-only: no create/edit
    public static function form(Form $form): Form
    {
        return $form->schema([]);
    }

    public static function table(Table $table): Table
    {
        return $table
            ->defaultSort('created_at', 'desc')
            ->columns([
                Tables\Columns\TextColumn::make('event_type')
                    ->badge()
                    ->searchable()
                    ->sortable(),
                Tables\Columns\TextColumn::make('extension_name')
                    ->label('Extension')
                    ->placeholder('—')
                    ->searchable(),
                Tables\Columns\TextColumn::make('country')
                    ->label('Country')
                    ->placeholder('—')
                    ->sortable(),
                Tables\Columns\TextColumn::make('app_version')
                    ->label('App Version')
                    ->placeholder('—'),
                Tables\Columns\TextColumn::make('device.device_model')
                    ->label('Device')
                    ->placeholder('—')
                    ->searchable(),
                Tables\Columns\TextColumn::make('created_at')
                    ->label('Time')
                    ->dateTime()
                    ->sortable(),
            ])
            ->filters([
                SelectFilter::make('event_type')
                    ->options(fn () => AnalyticsEvent::query()
                        ->distinct()
                        ->orderBy('event_type')
                        ->pluck('event_type', 'event_type')
                        ->toArray()
                    )
                    ->label('Event Type'),

                SelectFilter::make('country')
                    ->options(fn () => AnalyticsEvent::query()
                        ->whereNotNull('country')
                        ->distinct()
                        ->orderBy('country')
                        ->pluck('country', 'country')
                        ->toArray()
                    )
                    ->label('Country'),

                Filter::make('created_at')
                    ->label('Date Range')
                    ->form([
                        DatePicker::make('from')->label('From'),
                        DatePicker::make('until')->label('Until'),
                    ])
                    ->query(function (Builder $query, array $data) {
                        return $query
                            ->when($data['from'], fn ($q, $date) => $q->whereDate('created_at', '>=', $date))
                            ->when($data['until'], fn ($q, $date) => $q->whereDate('created_at', '<=', $date));
                    }),
            ])
            ->actions([])
            ->bulkActions([]);
    }

    public static function canCreate(): bool
    {
        return false;
    }

    public static function getPages(): array
    {
        return [
            'index' => Pages\ListAnalyticsEvents::route('/'),
        ];
    }
}
