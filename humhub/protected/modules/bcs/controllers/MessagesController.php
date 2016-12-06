<?php

namespace humhub\modules\bcs\controllers;

use humhub\modules\bcs\repositories\MessageRepository;
use humhub\modules\bcs\repositories\UserRepository;
use humhub\modules\bcs\transformers\messages\EntryTransformer;
use humhub\modules\bcs\transformers\messages\MessageTransformer;
use humhub\modules\bcs\transformers\messages\UserTransformer;
use humhub\modules\mail\models\Message;
use humhub\modules\mail\models\MessageEntry;

class MessagesController extends ApiController
{

    /**
     * @var UserRepository
     */
    protected $userRepository;

    /**
     * @var MessageRepository
     */
    protected $messageRepository;

    /**
     * @var MessageTransformer
     */
    protected $messageTransformer;

    /**
     * @var EntryTransformer
     */
    protected $entryTransformer;

    /**
     * @var UserTransformer
     */
    protected $userTransformer;

    public function init()
    {
        $this->userRepository = new UserRepository();
        $this->messageRepository = new MessageRepository();

        $this->messageTransformer = new MessageTransformer();
        $this->entryTransformer = new EntryTransformer();
        $this->userTransformer = new UserTransformer();

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

        $userMessages = $this->messageRepository->allFromUser($user, $request->get('since'));

        return $this->responseSuccess(array_map(function ($userMessage) {
            /** @var Message $message */
            $message = $userMessage->getMessage()->one();

            return [
                'message' => $this->messageTransformer->transform($message),
                'last_entry' => $this->entryTransformer->transform($message->getLastEntry()),
                'users' => $this->userTransformer->transformCollection(
                    $message->getUsers()->all(),
                    $this->userTransformer
                ),
            ];
        }, $userMessages));
    }

    public function actionShow()
    {
        /** @var \humhub\components\Request $request */
        $request = \Yii::$app->request;

        $this->forceGetRequest();

        if ($error = $this->forceBcsAuthentication()) {
            return $error;
        }

        $bcsId = $request->get('bcs_id');

        if (!($user = $this->userRepository->findByBcsId($bcsId))) {
            return $this->responseError('User not found by bcs id ' . $bcsId, 404);
        }

        if (!($message = $this->messageRepository->fromUser($user, $request->get('message')))) {
            return $this->responseError('message not found', 404);
        }

        $entries = $message->getEntries();

        if (($since = $request->get('since')) !== null) {
            $entries->andWhere('message_entry.created_at >= :since', array(':since' => $since));
        }

        return $this->responseSuccess([
            'message' => $this->messageTransformer->transform($message),
            'entries' => $this->entryTransformer->transformCollection(
                $entries->all(),
                $this->entryTransformer
            ),
            'users' => $this->userTransformer->transformCollection(
                $message->getUsers()->all(),
                $this->userTransformer
            ),
        ]);
    }

    public function actionAdd()
    {
        /** @var \humhub\components\Request $request */
        $request = \Yii::$app->request;

        $this->forcePostRequest();

        if ($error = $this->forceBcsAuthentication()) {
            return $error;
        }

        if (!($user = $this->userRepository->findByBcsId($request->post('bcs_id')))) {
            return $this->responseError('User not found by bcs id ' . $request->post('bcs_id'), 422);
        }

        if (!($message = $this->messageRepository->fromUser($user, $request->post('message')))) {
            return $this->responseError('message not found', 422);
        }

        if (!($content = $request->post('content'))) {
            return $this->responseError('message body not found', 422);
        }

        $messageEntry = new MessageEntry();

        $messageEntry->message_id = $message->id;
        $messageEntry->user_id = $user->id;
        $messageEntry->content = $content;

        $messageEntry->save();
        $messageEntry->notify();

        return $this->responseSuccess('reply successful');
    }
}