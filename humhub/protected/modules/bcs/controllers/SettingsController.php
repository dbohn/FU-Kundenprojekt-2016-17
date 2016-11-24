<?php

namespace humhub\modules\bcs\controllers;

use humhub\modules\admin\components\Controller;
use humhub\modules\bcs\models\BcsToken;

class SettingsController extends Controller
{

    public function init()
    {
        $this->appendPageTitle('BCS');
        return parent::init();
    }


    public function actionIndex()
    {
        $tokens = BcsToken::find()->all();

        return $this->render('/settings/index', compact('tokens'));
    }


    public function actionAdd()
    {
        $token = new BcsToken();

        $token->setAttribute('token', $this->random_str(60));

        $token->setAttribute('comment', 'BCS Token for test: humhub.local');

        $token->save();

        return $this->redirect('/bcs/settings/index');
    }

    /**
     * Generate a random string, using a cryptographically secure
     * pseudorandom number generator (random_int)
     *
     * For PHP 7, random_int is a PHP core function
     * For PHP 5.x, depends on https://github.com/paragonie/random_compat
     *
     * @param int $length How many characters do we want?
     * @param string $keyspace A string of all possible characters
     *                         to select from
     * @return string
     *
     * @see http://stackoverflow.com/questions/4356289/php-random-string-generator/31107425#31107425
     */
    protected function random_str($length, $keyspace = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ')
    {
        $str = '';
        $max = mb_strlen($keyspace, '8bit') - 1;
        for ($i = 0; $i < $length; ++$i) {
            $str .= $keyspace[random_int(0, $max)];
        }
        return $str;
    }
}