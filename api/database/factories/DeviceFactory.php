<?php

namespace Database\Factories;

use App\Models\Device;
use App\Models\ActivationCode;
use Illuminate\Database\Eloquent\Factories\Factory;

/**
 * @extends \Illuminate\Database\Eloquent\Factories\Factory<\App\Models\Device>
 */
class DeviceFactory extends Factory
{
    protected $model = Device::class;

    /**
     * Define the model's default state.
     *
     * @return array<string, mixed>
     */
    public function definition(): array
    {
        return [
            'activation_id' => ActivationCode::factory(),
            'device_id' => fake()->uuid(),
            'device_model' => 'Pixel ' . fake()->numberBetween(4, 8),
            'android_version' => (string) fake()->numberBetween(10, 14),
            'app_version' => '1.0.0',
            'ip_address' => fake()->ipv4(),
            'country' => 'US',
            'last_seen' => now(),
            'is_blocked' => false,
        ];
    }
}
