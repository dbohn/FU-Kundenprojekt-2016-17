<?php

namespace humhub\modules\bcs\repositories;

use humhub\modules\bcs\models\UserBcs;
use humhub\modules\mail\models\UserMessage;
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

    /**
     * @param User $user
     *
     * @return array
     */
    public function getMessages(User $user)
    {
        $query = UserMessage::find();

        $query->joinWith('message');
        $query->where(['user_message.user_id' => $user->id]);
        $query->orderBy('message.updated_at DESC');

        return $query->all();
    }

}