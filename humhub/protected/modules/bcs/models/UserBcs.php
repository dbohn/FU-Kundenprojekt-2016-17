<?php

namespace humhub\modules\bcs\models;

use humhub\components\ActiveRecord;
use humhub\modules\user\models\User;

class UserBcs extends ActiveRecord
{

    protected static $bcsIdCache = [];

    /**
     * Returns the bcs id for given user
     *
     * @param User|int $user
     * @return string
     */
    public static function idFor($user)
    {
        if (!($user instanceof User)) {
            if (!isset(static::$bcsIdCache[$user])) {
                static::$bcsIdCache[$user] = User::findIdentity($user);
            }

            $user = static::$bcsIdCache[$user];
        }

        return $user->guid;
    }
}