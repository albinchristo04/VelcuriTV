<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Relations\BelongsTo;

class AnalyticsEvent extends Model
{
    public $timestamps = false;

    protected $fillable = [
        'device_id', 'event_type', 'extension_name', 'country', 'app_version', 'meta'
    ];

    protected $casts = [
        'created_at' => 'datetime',
        'meta'       => 'array',
    ];

    public function device(): BelongsTo
    {
        return $this->belongsTo(Device::class);
    }
}
