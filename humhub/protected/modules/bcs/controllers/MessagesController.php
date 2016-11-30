<?php

namespace humhub\modules\bcs\controllers;

use humhub\modules\bcs\models\UserBcs;
use humhub\modules\bcs\repositories\UserRepository;
use humhub\modules\mail\models\Message;
use humhub\modules\mail\models\UserMessage;

class MessagesController extends ApiController
{

    /**
     * @var UserRepository
     */
    protected $userRepository;

    public function init()
    {
        $this->userRepository = new UserRepository();

        parent::init();
    }

    public function actionIndex()
    {
        /** @var \humhub\components\Request $request */
        $request = \Yii::$app->request;

        $this->forceGetRequest();

        if ($error = $this->forceBcsAuthentication()) {
            return $error;
        }

        $bcsId = $request->get('bcs_id');

        if (!($user = $this->userRepository->findByBcsId($bcsId))) {
            return $this->responseError('User not found bcs id ' . $bcsId, 404);
        }

        $messages = $this->userRepository->getMessages($user);

        $responseMessages = [];

        /** @var UserMessage $userMessage */
        foreach ($messages as $userMessage) {
            /** @var Message $message */
            $message = $userMessage->getMessage()->one();

            $responseMessages[] = [
                'message' => $message,
                'entries' => $message->getEntries()->all(),
                'users' => $message->getUsers()->all(),
                'bcs_map' => $this->mapUserToBcs($message->getUsers()->all()),
            ];
        }

        return $this->responseSuccess($responseMessages);
    }

    private function mapUserToBcs($users)
    {
        return array_map(function ($user) {
            return [
                'bcs_id' => UserBcs::idFor($user),
                'user_id' => $user->id,
            ];
        }, $users);
    }
}