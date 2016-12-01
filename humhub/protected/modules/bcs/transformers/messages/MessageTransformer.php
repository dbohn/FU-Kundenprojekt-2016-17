<?php

namespace humhub\modules\bcs\transformers\messages;

use humhub\modules\bcs\models\UserBcs;
use humhub\modules\mail\models\Message;

class MessageTransformer extends AbstractTransformer
{

    /**
     * Transforms a message object into a corresponding associative array
     *
     * @param Message $data
     * @return array
     */
    public function transform($data)
    {
        return [
            'id' => $data->id,
            'title' => $data->title,
            'created_at' => $data->created_at,
            'created_by' => UserBcs::idFor($data->created_by),
            'updated_at' => $data->updated_at,
            'updated_by' => UserBcs::idFor($data->updated_by),
            'messageEntries' => $this->transformCollection($data->getEntries()->all(), new EntryTransformer()),
            'users' => $this->transformCollection($data->getUsers()->all(), new UserTransformer()),
        ];
    }
}