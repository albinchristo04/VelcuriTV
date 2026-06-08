<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Relations\BelongsTo;

class Device extends Model
{
    use HasFactory;

    protected $fillable = [
        'activation_id', 'device_id', 'device_model', 'android_version',
        'app_version', 'ip_address', 'country', 'last_seen', 'is_blocked'
    ];

    protected $casts = [
        'last_seen' => 'datetime',
        'is_blocked' => 'boolean',
    ];

    public function activationCode(): BelongsTo
    {
        return $this->belongsTo(ActivationCode::class, 'activation_id');
    }
}
