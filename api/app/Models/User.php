<?php

namespace App\Models;

use Illuminate\Foundation\Auth\User as Authenticatable;
use Illuminate\Database\Eloquent\Relations\HasMany;
use Laravel\Sanctum\HasApiTokens;
use Filament\Models\Contracts\FilamentUser;
use Filament\Panel;

class User extends Authenticatable implements FilamentUser
{
    use HasApiTokens;

    protected $fillable = ['name', 'email', 'password', 'role', 'status'];

    protected $hidden = ['password'];

    protected $casts = ['password' => 'hashed'];

    public function canAccessPanel(Panel $panel): bool
    {
        return $this->role === 'admin' && $this->status === 'active';
    }

    public function activationCodes(): HasMany
    {
        return $this->hasMany(ActivationCode::class, 'created_by');
    }
}
