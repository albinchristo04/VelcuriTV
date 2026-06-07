<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    public function up(): void
    {
        Schema::create('devices', function (Blueprint $table) {
            $table->id();
            $table->foreignId('activation_id')->constrained('activation_codes')->onDelete('cascade');
            $table->string('device_id', 64);
            $table->string('device_model', 100)->nullable();
            $table->string('android_version', 20)->nullable();
            $table->string('app_version', 20)->nullable();
            $table->string('ip_address', 45)->nullable();
            $table->string('country', 10)->nullable();
            $table->timestamp('last_seen')->nullable();
            $table->boolean('is_blocked')->default(false);
            $table->timestamps();
            $table->unique(['activation_id', 'device_id']);
        });
    }

    public function down(): void
    {
        Schema::dropIfExists('devices');
    }
};
