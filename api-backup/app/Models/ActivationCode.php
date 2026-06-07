<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Relations\HasMany;
use Illuminate\Database\Eloquent\Relations\HasOne;
use Illuminate\Database\Eloquent\Relations\BelongsTo;
use Laravel\Sanctum\HasApiTokens;
use Carbon\Carbon;
use Illuminate\Support\Str;

class ActivationCode extends Model
{
    use HasApiTokens;

    protected $fillable = [
        'code', 'plan', 'status', 'expiry_date', 'devices_allowed', 'devices_used', 'notes', 'created_by'
    ];

    protected $casts = [
        'expiry_date' => 'datetime',
        'devices_allowed' => 'integer',
        'devices_used' => 'integer',
    ];

    public function devices(): HasMany
    {
        return $this->hasMany(Device::class, 'activation_id');
    }

    public function subscription(): HasOne
    {
        return $this->hasOne(Subscription::class, 'activation_id');
    }

    public function createdBy(): BelongsTo
    {
        return $this->belongsTo(User::class, 'created_by');
    }

    public function isExpired(): bool
    {
        if ($this->plan === 'lifetime') return false;
        if (is_null($this->expiry_date)) return false;
        return $this->expiry_date->isPast();
    }

    public function canAddDevice(): bool
    {
        return $this->devices_used < $this->devices_allowed;
    }

    public static function generateCode(): string
    {
        do {
            $code = 'VEL-' . strtoupper(Str::random(6));
        } while (static::where('code', $code)->exists());
        return $code;
    }

    public function scopeActive($query)
    {
        return $query->where('status', 'active');
    }

    public function scopeExpired($query)
    {
        return $query->where('status', 'expired')
            ->orWhere(function ($q) {
                $q->whereNotNull('expiry_date')
                    ->where('expiry_date', '<', now())
                    ->where('plan', '!=', 'lifetime');
            });
    }
}
