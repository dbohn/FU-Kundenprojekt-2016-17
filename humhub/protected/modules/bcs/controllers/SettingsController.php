<?php

namespace humhub\modules\bcs\controllers;

use humhub\modules\admin\components\Controller;
use humhub\modules\bcs\models\BcsToken;
use humhub\modules\bcs\repositories\BcsUserRepository;
use humhub\modules\user\models\Group;
use yii\db\Connection;

class SettingsController extends Controller
{

    public $dsn = "";
    public $username = "";
    public $password = "";
    public $charset = "utf-8";

    public function init()
    {
        $this->appendPageTitle('BCS');
        return parent::init();
    }


    /**
     * Action to display all bcs tokens
     *
     * @return string
     */
    public function actionIndex()
    {
        $tokens = BcsToken::find()->all();

        $connection = $this->getConnection();

        $repository = new BcsUserRepository($connection);

        $bcsGroups = $repository->getGroups();

        $connection->close();

        $groups = [];
        foreach ($bcsGroups as $bcsGroup) {
            $groups[] = (object)[
                'bcs' => (object)$bcsGroup,
                'humhub' => Group::findOne(['name' => $bcsGroup['name']]),
            ];
        }

        return $this->render('/settings/index', compact('tokens', 'groups'));
    }


    /**
     * Action to generate a new bcs super token
     *
     * @return \yii\web\Response
     */
    public function actionAdd()
    {
        $token = new BcsToken();

        $token->setAttribute('token', $this->random_str(60));

        $token->setAttribute('comment', 'BCS Super Token');

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

    /**
     * Connect to the Postgres Database
     * @return Connection
     */
    protected function getConnection()
    {
        //TODO: this should not stay here!

        return new Connection([
            'dsn' => 'pgsql:host=database;port=5432;dbname=humhub',
            'username' => 'humhub',
            'password' => '1234',
            'charset' => 'utf8',
        ]);
    }

    public function actionSync()
    {
        /** @var \humhub\components\Request $request */
        $request = \Yii::$app->request;

        $groupName = $request->get('group');

        $group = new Group();

        $group->setAttribute('name', $groupName);

        $group->setAttribute('description', 'BCS auto sync');

        $group->save();

        return $this->redirect('/bcs/settings/index');
    }
}
