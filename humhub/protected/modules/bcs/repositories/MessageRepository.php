<?php

namespace humhub\modules\bcs\repositories;

use humhub\modules\mail\models\Message;
use humhub\modules\mail\models\UserMessage;
use humhub\modules\user\models\User;

class MessageRepository
{


    /**
     * @param User $user
     * @param string|null $since
     * @return UserMessage[]
     */
    public function allFromUser(User $user, $since = null)
    {
        $query = UserMessage::find();

        $query->joinWith('message');

        $query->where(['user_message.user_id' => $user->id]);

        if ($since !== null) {
            $query->andWhere('message.updated_at >= :since', array(':since' => $since));
        }

        $query->orderBy('message.updated_at DESC');

        return $query->all();
    }


    /**
     * Returns the Message Model by given Id
     * Also an access check will be performed.
     *
     * If insufficient privileges or not found null will be returned.
     *
     * @param User $user
     * @param int $id
     * @return Message|null
     */
    public function fromUser(User $user, $id)
    {
        $message = Message::findOne(['id' => $id]);

        if ($message != null) {

            $userMessage = UserMessage::findOne([
                'user_id' => $user->id,
                'message_id' => $message->id
            ]);

            if ($userMessage != null) {
                return $message;
            }
        }

        return null;
    }
}