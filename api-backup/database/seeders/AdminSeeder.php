<?php

namespace Database\Seeders;

use App\Models\User;
use Illuminate\Database\Seeder;
use Illuminate\Support\Facades\Hash;

class AdminSeeder extends Seeder
{
    public function run(): void
    {
        User::create([
            'name'     => 'Velcuri Admin',
            'email'    => 'admin@velcuri.io',
            'password' => Hash::make('VelcuriAdmin2025!'),
            'role'     => 'admin',
            'status'   => 'active',
        ]);
    }
}
