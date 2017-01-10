<?php

namespace humhub\modules\bcs\controllers;

use humhub\modules\bcs\repositories\UserRepository;
use humhub\modules\bcs\transformers\messages\UserTransformer;

class UserController extends ApiController
{

    /**
     * @var UserRepository
     */
    protected $userRepository;
    /**
     * @var UserTransformer
     */
    protected $userTransformer;

    public function init()
    {
        $this->userRepository = new UserRepository();
        $this->userTransformer = new UserTransformer();

        parent::init();
    }

    public function actionFriends()
    {
        $this->forceGetRequest();

        if ($error = $this->forceBcsAuthentication()) {
            return $error;
        }

        /** @var \humhub\components\Request $request */
        $request = \Yii::$app->request;

        $bcsId = $request->get('user_id');

        $user = $this->userRepository->findByBcsId($bcsId);

        $friends = $user->getFriends()->all();

        return $this->responseSuccess([
            'me' => $this->userTransformer->transform($user),
            'friends' => $this->userTransformer->transformCollection(
                $friends,
                $this->userTransformer
            )
        ]);
    }

    public function actionDelete()
    {
        $this->forcePostRequest();

        if ($error = $this->forceBcsAuthentication()) {
            return $error;
        }

        /** @var \humhub\components\Request $request */
        $request = \Yii::$app->request;

        //var_dump("Hallo");die();
        $bcsId = $request->post('user_id');

        $user = $this->userRepository->findByBcsId($bcsId);

        if ($user !== null) {
            $this->userRepository->deleteUser($user);
            return $this->responseSuccess($this->userTransformer->transform($user));
        } else {
            return $this->responseError("User Not Found", 404);
        }

    }
}
