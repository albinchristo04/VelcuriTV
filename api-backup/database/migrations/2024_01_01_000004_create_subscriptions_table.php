<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    public function up(): void
    {
        Schema::create('subscriptions', function (Blueprint $table) {
            $table->id();
            $table->foreignId('activation_id')->constrained('activation_codes')->onDelete('cascade');
            $table->enum('plan', ['trial', 'monthly', 'quarterly', 'semiannual', 'annual', 'lifetime']);
            $table->timestamp('start_date');
            $table->timestamp('end_date')->nullable();
            $table->enum('status', ['active', 'expired', 'suspended', 'cancelled'])->default('active');
            $table->timestamps();
        });
    }

    public function down(): void
    {
        Schema::dropIfExists('subscriptions');
    }
};
