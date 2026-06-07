<?php

namespace Tests\Feature;

use App\Models\AppVersion;
use Illuminate\Foundation\Testing\RefreshDatabase;
use Tests\TestCase;

class UpdateTest extends TestCase
{
    use RefreshDatabase;

    public function test_update_returns_true_for_old_version()
    {
        AppVersion::create([
            'version' => '1.1.0',
            'version_code' => 110,
            'download_url' => 'https://download.velcuri.io/test.apk',
            'force_update' => false,
            'is_active' => true,
        ]);

        $response = $this->getJson('/api/update?version_code=100');

        $response->assertStatus(200)
                 ->assertJsonPath('update_available', true)
                 ->assertJsonPath('version_code', 110);
    }

    public function test_update_returns_false_for_latest_version()
    {
        AppVersion::create([
            'version' => '1.1.0',
            'version_code' => 110,
            'download_url' => 'https://download.velcuri.io/test.apk',
            'force_update' => false,
            'is_active' => true,
        ]);

        $response = $this->getJson('/api/update?version_code=110');

        $response->assertStatus(200)
                 ->assertJsonPath('update_available', false);
    }
}
