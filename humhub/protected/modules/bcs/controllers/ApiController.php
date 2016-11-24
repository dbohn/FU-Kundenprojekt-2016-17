<?php

namespace humhub\modules\bcs\controllers;

use humhub\components\Controller;
use Yii;

class ApiController extends Controller
{

    public function actionUsers()
    {
        $this->forcePostRequest();

        $this->forceBcsAuthentication();

        // read request body for input data

        // create user registration
        // Note:
        //    $enablePasswordForm = true;
        //    $enableEmailField = false;
    }

    /**
     * Authenticates BCS via header
     */
    private function forceBcsAuthentication()
    {
        // authenticate BSC via token header

        // TODO: read current token from database or config file...
        if (Yii::$app->request->headers->get('bcs-super-token') != 'krass_sicher') {
            print "Unauthorized request!";
            die;
        }
    }
}