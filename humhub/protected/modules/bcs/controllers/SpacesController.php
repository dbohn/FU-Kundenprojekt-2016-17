<?php

namespace humhub\modules\bcs\controllers;

use humhub\modules\bcs\repositories\SpacesRepository;
use humhub\modules\space\models\Space;

class SpacesController extends ApiController
{

    /**
     * @var SpacesRepository
     */
    protected $spaces;

    public function init()
    {
        $this->spaces = new SpacesRepository();

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
                'name' => $space->name,
                'desc' => $space->description,
            ];
        }, $spaces));
    }
}
