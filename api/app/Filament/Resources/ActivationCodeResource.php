<?php

namespace App\Filament\Resources;

use App\Filament\Resources\ActivationCodeResource\Pages;
use App\Models\ActivationCode;
use Filament\Forms;
use Filament\Forms\Form;
use Filament\Resources\Resource;
use Filament\Tables;
use Filament\Tables\Table;
use Filament\Tables\Actions\Action;
use Filament\Tables\Actions\BulkActionGroup;
use Filament\Tables\Actions\DeleteBulkAction;
use Illuminate\Support\Collection;
use Symfony\Component\HttpFoundation\StreamedResponse;

class ActivationCodeResource extends Resource
{
    protected static ?string $model = ActivationCode::class;
    protected static ?string $navigationIcon = 'heroicon-o-key';
    protected static ?string $navigationLabel = 'Activation Codes';

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
            Forms\Components\Select::make('status')
                ->options([
                    'unused'    => 'Unused',
                    'active'    => 'Active',
                    'expired'   => 'Expired',
                    'suspended' => 'Suspended',
                    'cancelled' => 'Cancelled',
                ])->required(),
            Forms\Components\Select::make('devices_allowed')
                ->options([1 => '1', 3 => '3', 5 => '5'])->default(1)->required(),
            Forms\Components\DateTimePicker::make('expiry_date')->nullable(),
            Forms\Components\Textarea::make('notes')->nullable(),
        ]);
    }

    public static function table(Table $table): Table
    {
        return $table
            ->columns([
                Tables\Columns\TextColumn::make('code')
                    ->copyable()
                    ->badge()
                    ->searchable(),
                Tables\Columns\BadgeColumn::make('plan')
                    ->colors([
                        'gray'    => 'trial',
                        'primary' => 'monthly',
                        'indigo'  => 'quarterly',
                        'purple'  => 'semiannual',
                        'success' => 'annual',
                        'warning' => 'lifetime',
                    ]),
                Tables\Columns\BadgeColumn::make('status')
                    ->colors([
                        'gray'    => 'unused',
                        'success' => 'active',
                        'danger'  => 'expired',
                        'warning' => 'suspended',
                        'secondary' => 'cancelled',
                    ]),
                Tables\Columns\TextColumn::make('devices')
                    ->label('Devices')
                    ->getStateUsing(fn ($record) => "{$record->devices_used}/{$record->devices_allowed}"),
                Tables\Columns\TextColumn::make('expiry_date')
                    ->label('Expiry')
                    ->getStateUsing(fn ($record) => $record->expiry_date
                        ? $record->expiry_date->diffForHumans()
                        : 'Lifetime')
                    ->sortable(),
                Tables\Columns\TextColumn::make('created_at')->date()->sortable(),
            ])
            ->filters([
                Tables\Filters\SelectFilter::make('status')
                    ->options([
                        'unused'    => 'Unused',
                        'active'    => 'Active',
                        'expired'   => 'Expired',
                        'suspended' => 'Suspended',
                        'cancelled' => 'Cancelled',
                    ]),
                Tables\Filters\SelectFilter::make('plan')
                    ->options([
                        'trial'      => 'Trial',
                        'monthly'    => 'Monthly',
                        'quarterly'  => 'Quarterly',
                        'semiannual' => 'Semi-Annual',
                        'annual'     => 'Annual',
                        'lifetime'   => 'Lifetime',
                    ]),
            ])
            ->headerActions([
                Action::make('generateCodes')
                    ->label('Generate Codes')
                    ->icon('heroicon-o-plus')
                    ->form([
                        Forms\Components\Select::make('plan')
                            ->options([
                                'trial'      => 'Trial',
                                'monthly'    => 'Monthly',
                                'quarterly'  => 'Quarterly',
                                'semiannual' => 'Semi-Annual',
                                'annual'     => 'Annual',
                                'lifetime'   => 'Lifetime',
                            ])->required(),
                        Forms\Components\TextInput::make('quantity')
                            ->numeric()->default(1)->minValue(1)->maxValue(500)->required(),
                        Forms\Components\Select::make('devices_allowed')
                            ->options([1 => '1', 3 => '3', 5 => '5'])->default(1)->required(),
                        Forms\Components\DateTimePicker::make('expiry_date')
                            ->label('Custom Expiry (optional)')->nullable(),
                    ])
                    ->action(function (array $data): StreamedResponse {
                        $codes = collect();
                        for ($i = 0; $i < $data['quantity']; $i++) {
                            $code = ActivationCode::create([
                                'code'            => ActivationCode::generateCode(),
                                'plan'            => $data['plan'],
                                'status'          => 'unused',
                                'devices_allowed' => $data['devices_allowed'],
                                'expiry_date'     => $data['expiry_date'] ?? null,
                                'created_by'      => auth()->id(),
                            ]);
                            $codes->push($code);
                        }

                        return response()->streamDownload(function () use ($codes) {
                            $handle = fopen('php://output', 'w');
                            fputcsv($handle, ['Code', 'Plan', 'Devices Allowed', 'Expiry']);
                            foreach ($codes as $code) {
                                fputcsv($handle, [
                                    $code->code,
                                    $code->plan,
                                    $code->devices_allowed,
                                    $code->expiry_date?->toDateTimeString() ?? 'Lifetime',
                                ]);
                            }
                            fclose($handle);
                        }, 'activation-codes-' . now()->format('Y-m-d') . '.csv');
                    }),
            ])
            ->actions([
                Action::make('viewDevices')
                    ->label('Devices')
                    ->icon('heroicon-o-device-phone-mobile')
                    ->url(fn ($record) => DeviceResource::getUrl('index', ['tableFilters[activation_id][value]' => $record->id])),
                Action::make('toggleSuspend')
                    ->label(fn ($record) => $record->status === 'suspended' ? 'Unsuspend' : 'Suspend')
                    ->icon('heroicon-o-pause')
                    ->action(function ($record) {
                        $record->update([
                            'status' => $record->status === 'suspended' ? 'active' : 'suspended',
                        ]);
                    }),
                Action::make('resetDevices')
                    ->label('Reset Devices')
                    ->icon('heroicon-o-arrow-path')
                    ->requiresConfirmation()
                    ->action(function ($record) {
                        $record->devices()->delete();
                        $record->update(['devices_used' => 0]);
                    }),
                Tables\Actions\DeleteAction::make(),
            ])
            ->bulkActions([
                BulkActionGroup::make([
                    Tables\Actions\BulkAction::make('suspendSelected')
                        ->label('Suspend Selected')
                        ->action(fn (Collection $records) => $records->each->update(['status' => 'suspended'])),
                    DeleteBulkAction::make(),
                    Tables\Actions\BulkAction::make('exportCsv')
                        ->label('Export CSV')
                        ->action(fn (Collection $records) => response()->streamDownload(function () use ($records) {
                            $handle = fopen('php://output', 'w');
                            fputcsv($handle, ['Code', 'Plan', 'Status', 'Devices Used', 'Devices Allowed', 'Expiry']);
                            foreach ($records as $r) {
                                fputcsv($handle, [$r->code, $r->plan, $r->status, $r->devices_used, $r->devices_allowed, $r->expiry_date?->toDateTimeString() ?? 'Lifetime']);
                            }
                            fclose($handle);
                        }, 'export-' . now()->format('Y-m-d') . '.csv')),
                ]),
            ]);
    }

    public static function getRelations(): array
    {
        return [];
    }

    public static function getPages(): array
    {
        return [
            'index' => Pages\ListActivationCodes::route('/'),
            'create' => Pages\CreateActivationCode::route('/create'),
            'edit' => Pages\EditActivationCode::route('/{record}/edit'),
        ];
    }
}
