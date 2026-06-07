<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    public function up(): void
    {
        Schema::create('activation_codes', function (Blueprint $table) {
            $table->id();
            $table->string('code', 20)->unique(); // VEL-XXXXXX
            $table->enum('plan', ['trial', 'monthly', 'quarterly', 'semiannual', 'annual', 'lifetime']);
            $table->enum('status', ['unused', 'active', 'expired', 'suspended', 'cancelled'])->default('unused');
            $table->timestamp('expiry_date')->nullable(); // null = lifetime
            $table->unsignedTinyInteger('devices_allowed')->default(1);
            $table->unsignedTinyInteger('devices_used')->default(0);
            $table->text('notes')->nullable();
            $table->foreignId('created_by')->nullable()->constrained('users')->nullOnDelete();
            $table->timestamps();
        });
    }

    public function down(): void
    {
        Schema::dropIfExists('activation_codes');
    }
};
