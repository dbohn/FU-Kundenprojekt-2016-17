<?php

namespace humhub\modules\bcs\controllers;

use humhub\modules\bcs\repositories\SpacesRepository;
use humhub\modules\bcs\repositories\UserRepository;
use humhub\modules\space\models\Space;
use Yii;

class SpacesController extends ApiController
{

    /**
     * @var SpacesRepository
     */
    protected $spaces;

    /**
     * @var UserRepository
     */
    protected $users;

    public function init()
    {
        $this->spaces = new SpacesRepository();
        $this->users = new UserRepository();

        parent::init();
    }

    public function actionIndex()
    {
        $this->forceGetRequest();

        if ($error = $this->forceBcsAuthentication()) {
            return $error;
        }

        $spaces = $this->spaces->all();

        return $this->responseSuccess(array_map(function ($space) {
            /**
             * @var $space Space
             */

            return [
                'id' => $space->id,
                'guid' => $space->guid,
                'name' => $space->name,
                'desc' => $space->description,
            ];
        }, $spaces));
    }

    /**
     * @return \yii\web\Response
     */
    public function actionAvatar()
    {
        $this->forceGetRequest();

        /** @var \humhub\components\Request $request */
        $request = \Yii::$app->request;

        $spaceId = $request->get('space_id');

        $space = $this->spaces->get($spaceId);

        return $this->redirect($space->getProfileImage()->getUrl());
    }

    public function actionCreate()
    {
        $this->forcePostRequest();

        if ($error = $this->forceBcsAuthentication()) {
            return $error;
        }

        /** @var \humhub\components\Request $request */
        $request = \Yii::$app->request;

        $guid = $request->post('guid');
        $name = $request->post('name');
        $description = $request->post('description');

        // The creator is the admin user of this space
        $creator = $this->users->findByBcsId($request->post('user_id'));

        $space = $this->spaces->create($guid, $name, $creator, $description);

        return $this->responseSuccess([
            'id' => $space->id,
            'guid' => $space->guid,
            'name' => $space->name,
            'desc' => $space->description
        ]);
    }
}
