<?php

namespace humhub\modules\bcs\controllers;

use humhub\modules\admin\components\Controller;

class SettingsController extends Controller
{

    public function init()
    {
        $this->appendPageTitle('BCS');
        return parent::init();
    }


    public function actionIndex()
    {
        return $this->render('/settings/index');
    }
}