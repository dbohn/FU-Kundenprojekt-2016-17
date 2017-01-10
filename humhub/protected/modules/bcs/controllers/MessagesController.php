<?php

namespace humhub\modules\bcs\controllers;

use humhub\modules\bcs\repositories\MessageRepository;
use humhub\modules\bcs\repositories\UserRepository;
use humhub\modules\bcs\transformers\messages\EntryTransformer;
use humhub\modules\bcs\transformers\messages\MessageTransformer;
use humhub\modules\bcs\transformers\messages\UserTransformer;
use humhub\modules\mail\models\forms\CreateMessage;
use humhub\modules\mail\models\Message;
use humhub\modules\mail\models\MessageEntry;
use humhub\modules\mail\models\UserMessage;

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

    public function actionCreate()
    {
        /** @var \humhub\components\Request $request */
        $request = \Yii::$app->request;

        $userGuid = $request->post('user_id');
        $user = $this->userRepository->findByBcsId($userGuid);

        $model = new CreateMessage();

        //$model->recipient = $user->guid;
        //var_dump($model->load($request->post(), ''));

        //var_dump($user);die();

        if ($model->load($request->post(), '') && $model->validate()) {
            // Create new Message
            $message = new Message();
            $message->title = $model->title;
            $message->created_by = $user->id;
            $message->save();

            // Attach Message Entry
            $messageEntry = new MessageEntry();
            $messageEntry->message_id = $message->id;
            $messageEntry->user_id = $user->id;
            $messageEntry->content = $model->message;
            $messageEntry->save();

            // Attach also Recipients
            foreach ($model->getRecipients() as $recipient) {
                $userMessage = new UserMessage();
                $userMessage->message_id = $message->id;
                $userMessage->user_id = $recipient->id;
                $userMessage->save();
            }

            // Inform recipients (We need to add all before)
            foreach ($model->getRecipients() as $recipient) {
                try {
                    $message->notify($recipient);
                } catch(\Exception $e) {
                    Yii::error('Could not send notification e-mail to: '. $recipient->username.". Error:". $e->getMessage());
                }
            }

            // Attach User Message
            $userMessage = new UserMessage();
            $userMessage->message_id = $message->id;
            $userMessage->user_id = $user->id;
            $userMessage->is_originator = 1;
            $userMessage->last_viewed = new \yii\db\Expression('NOW()');
            $userMessage->save();

            return $this->responseSuccess([
                'message' => $this->messageTransformer->transform($message),
                'entries' => [
                    $this->entryTransformer->transform($messageEntry)
                ],
                'users' => $this->userTransformer->transformCollection(
                    $message->getUsers()->all(),
                    $this->userTransformer
                ),
            ]);
        }

        //var_dump($model->validate(), $model->getErrors());

    }
}
