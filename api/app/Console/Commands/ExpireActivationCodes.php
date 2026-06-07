<?php

namespace App\Console\Commands;

use App\Models\ActivationCode;
use App\Models\Subscription;
use Illuminate\Console\Command;

class ExpireActivationCodes extends Command
{
    /**
     * The name and signature of the console command.
     *
     * @var string
     */
    protected $signature = 'velcuri:expire-codes';

    /**
     * The console command description.
     *
     * @var string
     */
    protected $description = 'Checks and expires activation codes that have passed their expiry date.';

    /**
     * Execute the console command.
     */
    public function handle()
    {
        $expiredCodes = ActivationCode::where('status', 'active')
            ->whereNotNull('expiry_date')
            ->where('expiry_date', '<', now())
            ->where('plan', '!=', 'lifetime')
            ->get();

        $count = $expiredCodes->count();

        foreach ($expiredCodes as $code) {
            $code->update(['status' => 'expired']);
            Subscription::where('activation_id', $code->id)
                ->where('status', 'active')
                ->update(['status' => 'expired']);
        }

        $this->info("Successfully expired {$count} activation codes.");
    }
}
