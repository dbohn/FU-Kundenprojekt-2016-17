<?php

namespace humhub\modules\bcs\transformers\messages;

use humhub\modules\bcs\models\UserBcs;
use humhub\modules\user\models\User;

class UserTransformer extends AbstractTransformer
{

    /**
     * @param User $data
     * @return array
     */
    public function transform($data)
    {
        return [
            'id' => UserBcs::idFor($data->id),
            'social_id' => $data->id,
            'username' => $data->username,
            'displayname' => $data->getDisplayName(),
            'email' => $data->email,
            'tags' => $data->tags,
            'language' => $data->language,
            'time_zone' => $data->time_zone,
            'last_login' => $data->last_login,
            'visibility' => $data->visibility,
        ];
    }
}