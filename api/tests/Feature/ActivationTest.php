<?php

namespace Tests\Feature;

use App\Models\ActivationCode;
use App\Models\Device;
use Illuminate\Foundation\Testing\RefreshDatabase;
use Tests\TestCase;

class ActivationTest extends TestCase
{
    use RefreshDatabase;

    public function test_valid_unused_code_returns_token()
    {
        $code = ActivationCode::factory()->create(['status' => 'unused', 'plan' => 'monthly']);

        $response = $this->postJson('/api/activate', [
            'code' => $code->code,
            'device_id' => 'device123',
        ]);

        $response->assertStatus(200)
                 ->assertJsonStructure(['success', 'token', 'status', 'plan', 'expiry', 'devices_allowed', 'devices_used'])
                 ->assertJsonPath('success', true)
                 ->assertJsonPath('status', 'active');
                 
        $this->assertEquals(1, Device::count());
    }

    public function test_same_code_and_device_reactivation_works()
    {
        $code = ActivationCode::factory()->create(['status' => 'active', 'plan' => 'monthly', 'devices_used' => 1, 'expiry_date' => now()->addDays(30)]);
        $device = Device::factory()->create(['activation_id' => $code->id, 'device_id' => 'device123']);

        $response = $this->postJson('/api/activate', [
            'code' => $code->code,
            'device_id' => 'device123',
        ]);

        $response->assertStatus(200)
                 ->assertJsonPath('success', true);
                 
        $this->assertEquals(1, Device::count()); // Should not create a new device
    }

    public function test_expired_code_returns_403()
    {
        $code = ActivationCode::factory()->create(['status' => 'active', 'plan' => 'monthly', 'expiry_date' => now()->subDay()]);

        $response = $this->postJson('/api/activate', [
            'code' => $code->code,
            'device_id' => 'device123',
        ]);

        $response->assertStatus(403)
                 ->assertJsonPath('error', 'expired');
    }

    public function test_suspended_code_returns_403()
    {
        $code = ActivationCode::factory()->create(['status' => 'suspended']);

        $response = $this->postJson('/api/activate', [
            'code' => $code->code,
            'device_id' => 'device123',
        ]);

        $response->assertStatus(403)
                 ->assertJsonPath('error', 'suspended');
    }

    public function test_devices_full_returns_403()
    {
        $code = ActivationCode::factory()->create(['status' => 'active', 'devices_allowed' => 1, 'devices_used' => 1, 'expiry_date' => now()->addDays(30)]);
        
        $response = $this->postJson('/api/activate', [
            'code' => $code->code,
            'device_id' => 'new_device_456',
        ]);

        $response->assertStatus(403)
                 ->assertJsonPath('error', 'device_limit_reached');
    }

    public function test_invalid_code_returns_404()
    {
        $response = $this->postJson('/api/activate', [
            'code' => 'INVALID-CODE',
            'device_id' => 'device123',
        ]);

        $response->assertStatus(404)
                 ->assertJsonPath('error', 'invalid_code');
    }

    public function test_ping_endpoint_returns_200()
    {
        $response = $this->getJson('/api/ping');
        $response->assertStatus(200)
                 ->assertJsonStructure(['status', 'timestamp']);
    }
}
