<?php

namespace humhub\modules\bcs\controllers;

use humhub\modules\bcs\models\UserBcs;
use humhub\modules\user\models\forms\Registration;
use Yii;
use yii\web\Response;

class UsersController extends ApiController
{

    /**
     * Api endpoint to create a user
     * Method: POST
     *
     * @return Response
     */
    public function actionAdd()
    {
        $this->forcePostRequest();

        if ($error = $this->forceBcsAuthentication()) {
            return $error;
        }

        /** @var \humhub\components\Request $request */
        $request = Yii::$app->request;

        $username = $request->post('username');
        $password = $request->post('password');
        $email = $request->post('email');
        $bcsId = $request->post('bcs_id');

        // TODO: bcs_id has to be unique

        if (empty($username) ||
            empty($password) ||
            empty($email) ||
            empty($bcsId)
        ) {
            return $this->responseError("Username, password, e-mail and bcs id are required fields", 400);
        }

        // fake user fields
        $_POST['User']['username'] = $username;
        $_POST['User']['email'] = $email;

        // fake password post fields
        $_POST['Password']['newPassword'] = $_POST['Password']['newPasswordConfirm'] = $password;

        if ($request->post('first_name')) {
            $_POST['Profile']['firstname'] = $request->post('first_name');
        }

        if ($request->post('last_name')) {
            $_POST['Profile']['lastname'] = $request->post('last_name');
        }

        // reassign POST data to request
        $request->setBodyParams($_POST);

        $registration = new Registration();

        $registration->enableEmailField = true;
        $registration->enableUserApproval = false;
        $registration->enablePasswordForm = true;

        // Fill models with POST data
        $registration->submitted();

        if (!$registration->validate()) {
            return $this->responseError('User validation failed', 400);
        }

        if (!$registration->register()) {
            return $this->responseError('User registration failed', 400);
        }

        $userBcs = new UserBcs();

        $userBcs->setAttribute('user_id', $registration->getUser()->id);
        $userBcs->setAttribute('bcs_id', $request->post('bcs_id'));

        $userBcs->save();

        return $this->responseSuccess('User registration successful');
    }
}