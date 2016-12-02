<?php

namespace humhub\modules\bcs\repositories;

use humhub\modules\bcs\models\UserBcs;
use humhub\modules\user\models\User;

class UserRepository
{

    /**
     * @param $bcsId
     *
     * @return User
     */
    public function findByBcsId($bcsId)
    {
        $userBcs = UserBcs::find()->where([
            'bcs_id' => $bcsId
        ])->one();

        if (!$userBcs) {
            return null;
        }

        return User::findIdentity($userBcs->user_id);
    }
}