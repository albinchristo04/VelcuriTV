<?php

namespace App\Filament\Resources;

use App\Filament\Resources\SubscriptionResource\Pages;
use App\Models\Subscription;
use Filament\Forms;
use Filament\Forms\Form;
use Filament\Resources\Resource;
use Filament\Tables;
use Filament\Tables\Table;
use Filament\Tables\Actions\Action;

class SubscriptionResource extends Resource
{
    protected static ?string $model = Subscription::class;
    protected static ?string $navigationIcon = 'heroicon-o-credit-card';
    protected static ?string $navigationLabel = 'Subscriptions';

    public static function form(Form $form): Form
    {
        return $form->schema([
            Forms\Components\Select::make('plan')
                ->options([
                    'trial'      => 'Trial',
                    'monthly'    => 'Monthly',
                    'quarterly'  => 'Quarterly',
                    'semiannual' => 'Semi-Annual',
                    'annual'     => 'Annual',
                    'lifetime'   => 'Lifetime',
                ])->required(),
            Forms\Components\DateTimePicker::make('start_date')->required(),
            Forms\Components\DateTimePicker::make('end_date')->nullable(),
            Forms\Components\Select::make('status')
                ->options([
                    'active'    => 'Active',
                    'expired'   => 'Expired',
                    'suspended' => 'Suspended',
                    'cancelled' => 'Cancelled',
                ])->required(),
        ]);
    }

    public static function table(Table $table): Table
    {
        return $table
            ->columns([
                Tables\Columns\TextColumn::make('activationCode.code')
                    ->label('Activation Code')
                    ->searchable(),
                Tables\Columns\TextColumn::make('plan'),
                Tables\Columns\TextColumn::make('start_date')->date()->sortable(),
                Tables\Columns\TextColumn::make('end_date')->date()->sortable(),
                Tables\Columns\BadgeColumn::make('status')
                    ->colors([
                        'success' => 'active',
                        'danger'  => 'expired',
                        'warning' => 'suspended',
                        'secondary' => 'cancelled',
                    ]),
            ])
            ->actions([
                Action::make('extend30')
                    ->label('+30 Days')
                    ->action(fn ($record) => $record->update(['end_date' => ($record->end_date ?? now())->addDays(30)])),
                Action::make('extend90')
                    ->label('+90 Days')
                    ->action(fn ($record) => $record->update(['end_date' => ($record->end_date ?? now())->addDays(90)])),
                Action::make('extend365')
                    ->label('+365 Days')
                    ->action(fn ($record) => $record->update(['end_date' => ($record->end_date ?? now())->addDays(365)])),
                Action::make('expireNow')
                    ->label('Expire Now')
                    ->color('danger')
                    ->requiresConfirmation()
                    ->action(fn ($record) => $record->update(['status' => 'expired', 'end_date' => now()])),
            ]);
    }

    public static function getRelations(): array
    {
        return [];
    }

    public static function getPages(): array
    {
        return [
            'index' => Pages\ListSubscriptions::route('/'),
        ];
    }
}
