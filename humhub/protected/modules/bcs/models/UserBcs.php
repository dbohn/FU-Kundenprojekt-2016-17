<?php

namespace humhub\modules\bcs\models;

use humhub\components\ActiveRecord;
use humhub\modules\user\models\User;

class UserBcs extends ActiveRecord
{

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

        return static::find()->where([
            'user_id' => $user
        ])->one()->bcs_id;
    }
}