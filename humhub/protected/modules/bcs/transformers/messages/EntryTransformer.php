<?php

namespace humhub\modules\bcs\transformers\messages;

use humhub\modules\bcs\models\UserBcs;
use humhub\modules\mail\models\MessageEntry;

class EntryTransformer extends AbstractTransformer
{

    /**
     * @param MessageEntry $data
     * @return array
     */
    public function transform($data)
    {
        return [
            'id' => $data->id,
            'user_id' => UserBcs::idFor($data->user_id),
            'user_social_id' => $data->user_id,
            // TODO: include files... $data->file_id
            'content' => $data->content,
            'created_at' => $data->created_at,
            'created_by' => UserBcs::idFor($data->created_by),
            'updated_at' => $data->updated_at,
            'updated_by' => UserBcs::idFor($data->updated_by),
        ];
    }
}