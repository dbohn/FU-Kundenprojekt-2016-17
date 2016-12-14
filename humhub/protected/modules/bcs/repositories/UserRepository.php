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
        return User::findByGuid($bcsId);
    }
}