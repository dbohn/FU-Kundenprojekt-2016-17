<?php

namespace humhub\modules\bcs\auth;

use humhub\modules\bcs\repositories\BcsUserRepository;
use humhub\modules\user\authclient\BaseFormAuth;
use humhub\modules\user\authclient\interfaces\PrimaryClient;
use yii\db\Connection;

/**
 * Class BcsAuthClient
 *
 * This class handles authentication against the
 * Demonstrator Postgres Database
 * The database connection and usage of this plugin
 * is configured in /protected/config/common.php
 *
 * @package humhub\modules\bcs\auth
 */
class BcsAuthClient extends BaseFormAuth implements PrimaryClient
{

    /**
     * Any user derived from the bcs dataset
     * should not be subject to approval by a
     * humhub admin.
     *
     * @var bool
     */
    public $byPassApproval = true;

    public $dsn = "";
    public $username = "";
    public $password = "";
    public $charset = "utf-8";

    /**
     * This string is used to identify the auth method
     * in the database
     * @return string
     */
    public function getId()
    {
        return 'bcs';
    }

    /**
     * This method is called as soon as a user tries to log in
     * and no other previous authentication method worked
     * In here we check the provided username and password
     * and validate these credentials against the postgres
     * database.
     * If the user is matched and does not exist in HumHub,
     * it is created and the user info is mapped to the internal user.
     *
     * @return bool
     */
    public function auth()
    {
        $username = $this->login->username;
        $password = $this->login->password;

        $connection = $this->getConnection();

        $repository = new BcsUserRepository($connection);

        $passwordChecker = new PasswordChecker();

        $result = $repository->getUserByUsername($username);

        $connection->close();

        // No user found?
        if (!$result) {
            return false;
        }

        if ($passwordChecker->validatePassword($result, $password)) {
            // Map the attributes of the bcs user to the humhub user
            $this->setUserAttributes($result);

            return true;
        }

        return false;
    }

    /**
     * Store the bcs id (which is a string id) in the guid field
     * @return string
     */
    public function getUserTableIdAttribute()
    {
        return 'guid';
    }

    protected function defaultName()
    {
        return 'bcs';
    }

    protected function defaultTitle()
    {
        return 'BCS';
    }

    /**
     * Connect to the Postgres Database
     * @return Connection
     */
    protected function getConnection()
    {
        return new Connection([
            'dsn' => $this->dsn,
            'username' => $this->username,
            'password' => $this->password,
            'charset' => $this->charset,
        ]);
    }

    /**
     * The returned map tells HumHub, which fields of the
     * BCS user entry should be mapped to which HumHub attribute
     * @return array
     */
    protected function defaultNormalizeUserAttributeMap()
    {
        return [
            'guid' => 'id',
            'username' => 'username',
            'email' => 'email',
            'firstname' => function ($attributes) {
                $nameParts = preg_split('/,\\s*/', $attributes['full_name']);
                return (count($nameParts) > 0) ? $nameParts[1] : '';
            },
            'lastname' => function ($attributes) {
                $nameParts = preg_split('/,\\s*/', $attributes['full_name']);
                return $nameParts[0];
            }
        ];
    }
}
