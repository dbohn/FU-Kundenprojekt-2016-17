<?php

namespace humhub\modules\bcs\controllers;

use humhub\modules\bcs\repositories\GroupsRepository;
use humhub\modules\user\models\Group;

class GroupsController extends ApiController
{

    /**
     * @var GroupsRepository
     */
    protected $groups;

    public function init()
    {
        $this->groups = new GroupsRepository();

        parent::init();
    }

    public function actionIndex()
    {
        $this->forceGetRequest();

        if ($error = $this->forceBcsAuthentication()) {
            return $error;
        }

        $groups = $this->groups->all();

        return $this->responseSuccess(array_map(function ($group) {
            /**
             * @var $group Group
             */

            return [
                'id' => $group->id,
                'name' => $group->name,
                'desc' => $group->description,
            ];
        }, $groups));
    }
}
