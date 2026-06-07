<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Builder;

class AppVersion extends Model
{
    protected $fillable = ['version', 'version_code', 'download_url', 'force_update', 'release_notes', 'is_active'];

    protected $casts = [
        'force_update' => 'boolean',
        'is_active' => 'boolean',
        'version_code' => 'integer',
    ];

    public function scopeLatestActive(Builder $query): Builder
    {
        return $query->where('is_active', true)->orderByDesc('version_code');
    }
}
