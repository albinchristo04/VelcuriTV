<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    public function up(): void
    {
        Schema::create('analytics_events', function (Blueprint $table) {
            $table->id();
            $table->foreignId('device_id')->nullable()->constrained('devices')->nullOnDelete();
            $table->string('event_type', 50);
            $table->string('extension_name', 100)->nullable();
            $table->string('country', 10)->nullable();
            $table->string('app_version', 20)->nullable();
            $table->json('meta')->nullable();
            $table->timestamp('created_at')->useCurrent();
            $table->index(['event_type', 'created_at']);
        });
    }

    public function down(): void
    {
        Schema::dropIfExists('analytics_events');
    }
};
