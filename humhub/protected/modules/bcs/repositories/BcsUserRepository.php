<?php

namespace humhub\modules\bcs\repositories;

use yii\db\Connection;

class BcsUserRepository
{

    /**
     * @var Connection
     */
    private $postgres;

    public function __construct(Connection $postgres)
    {
        $this->postgres = $postgres;
    }

    /**
     * @param $username
     * @return array|false
     */
    public function getUserByUsername($username)
    {
        return $this->postgres
            ->createCommand("SELECT * FROM users WHERE username=:username")
            ->bindParam(":username", $username)
            ->queryOne();
    }
}
