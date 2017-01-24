<?php

namespace Tests\Browser;

use Laravel\Dusk\Browser;
use Tests\DuskTestCase;
use Illuminate\Foundation\Testing\DatabaseMigrations;

class ApiTest extends DuskTestCase
{

    protected $validBcsToken = 'IVa9aWe6rFneYtEpJEXiVTS4gKmBuoXvPLVxpnRaE2xubLzeV4Pbn8QK284v';

    /**
     * A Dusk test example.
     *
     * @return void
     */
    public function testBcsSettings()
    {
        $this->browse(function (Browser $browser) {
            // login as administrator

            // go to /bcs/settings

            // check for a valid token

            // add a token

            /*$browser->visit('/bcs/settings')
                ->waitFor('#asdf', 10)
                ->assertSee('success');*/
        });
    }
}
