<?php

namespace Database\Factories;

use App\Models\ActivationCode;
use Illuminate\Database\Eloquent\Factories\Factory;

/**
 * @extends \Illuminate\Database\Eloquent\Factories\Factory<\App\Models\ActivationCode>
 */
class ActivationCodeFactory extends Factory
{
    protected $model = ActivationCode::class;

    /**
     * Define the model's default state.
     *
     * @return array<string, mixed>
     */
    public function definition(): array
    {
        return [
            'code' => ActivationCode::generateCode(),
            'plan' => 'monthly',
            'status' => 'unused',
            'devices_allowed' => 3,
            'devices_used' => 0,
            'expiry_date' => null,
            'notes' => null,
            'created_by' => null, // Can be overridden in tests
        ];
    }
}
