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
        if ($user instanceof User) {
            $user = $user->id;
        }

        if (!isset(static::$bcsIdCache[$user])) {
            static::$bcsIdCache[$user] = static::find()->where([
                'user_id' => $user
            ])->one()->bcs_id;
        }

        return static::$bcsIdCache[$user];
    }
}
