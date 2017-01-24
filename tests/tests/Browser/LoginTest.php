<?php

namespace Tests\Browser;

use Laravel\Dusk\Browser;
use Tests\DuskTestCase;

class LoginTest extends DuskTestCase
{

    /**
     * A Dusk test example.
     *
     * @return void
     */
    public function testExample()
    {
        Browser::$baseUrl = 'http://humhub.local:8082';

        $bcsEmail = 'test@test.com';
        $bcsPassword = 'test';

        $this->browse(function (Browser $browser) use ($bcsEmail, $bcsPassword) {
            $browser->visit('/user/auth/login')
                ->type('#login_username', $bcsEmail)
                ->type('#login_password', $bcsPassword)
                ->press('Einloggen')
                ->waitFor('#wallStream', 5)
                ->assertPathIs('/');
        });
    }
}
