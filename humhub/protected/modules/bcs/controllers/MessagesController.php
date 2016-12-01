<?php

namespace humhub\modules\bcs\controllers;

use humhub\modules\bcs\models\UserBcs;
use humhub\modules\bcs\repositories\UserRepository;
use humhub\modules\bcs\transformers\messages\MessageTransformer;
use humhub\modules\mail\models\Message;

class MessagesController extends ApiController
{

    /**
     * @var UserRepository
     */
    protected $userRepository;

    /**
     * @var MessageTransformer
     */
    protected $messageTransformer;

    public function init()
    {
        $this->userRepository = new UserRepository();
        $this->messageTransformer = new MessageTransformer();

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

        $userMessages = $this->userRepository->getMessages($user);

        return $this->responseSuccess(array_map(function ($userMessage) {
            /** @var Message $message */
            $message = $userMessage->getMessage()->one();

            return $this->messageTransformer->transform($message);
        }, $userMessages));
    }
}