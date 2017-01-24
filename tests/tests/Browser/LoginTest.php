<?php

namespace Tests\Browser;

use Laravel\Dusk\Browser;
use Tests\DuskTestCase;

class LoginTest extends DuskTestCase
{

    protected $testUser = [
        'email' => 'test@test.com',
        'password' => 'test',
    ];

    /**
     * A Dusk test example.
     *
     * @return void
     */
    public function testExample()
    {
        $this->browse(function (Browser $browser) {
            $browser->visit('/user/auth/login')
                ->type('#login_username', $this->testUser['email'])
                ->type('#login_password', $this->testUser['password'])
                ->press('Einloggen')
                ->waitFor('#wallStream', 5)
                ->assertPathIs('/');
        });
    }
}
