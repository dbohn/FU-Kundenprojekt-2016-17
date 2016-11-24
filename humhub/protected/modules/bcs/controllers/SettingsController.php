<?php

namespace humhub\modules\bcs\controllers;

use humhub\components\Controller;

class SettingsController extends Controller
{

    public function init()
    {
        $this->appendPageTitle('BCS Settings');
        $this->subLayout = '@bcs/views/layouts/main';

        return parent::init();
    }


    public function actionIndex()
    {
        return $this->render('/api/index');
    }
}