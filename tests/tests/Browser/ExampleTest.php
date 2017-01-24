<?php

namespace Tests\Browser;

use Laravel\Dusk\Browser;
use Tests\DuskTestCase;
use Laravel\Dusk\Chrome;
use Illuminate\Foundation\Testing\DatabaseMigrations;

class ExampleTest extends DuskTestCase
{

    /**
     * A basic browser test example.
     *
     * @return void
     */
    public function testBasicExample()
    {
        Browser::$baseUrl = 'http://humhub.local:8082';

        $this->browse(function ($browser) {
            $browser->visit('/')
                ->assertSee('HumHub');
        });
    }
}
