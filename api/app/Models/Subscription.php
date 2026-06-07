<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Relations\BelongsTo;

class Subscription extends Model
{
    protected $fillable = ['activation_id', 'plan', 'start_date', 'end_date', 'status'];

    protected $casts = [
        'start_date' => 'datetime',
        'end_date' => 'datetime',
    ];

    public function activationCode(): BelongsTo
    {
        return $this->belongsTo(ActivationCode::class, 'activation_id');
    }

    public function isActive(): bool
    {
        return $this->status === 'active';
    }
}
