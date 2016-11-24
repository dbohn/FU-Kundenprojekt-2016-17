<?php

namespace humhub\modules\bcs\controllers;

use humhub\components\Controller;
use humhub\components\Request;
use humhub\modules\user\models\forms\Registration;
use Yii;
use yii\web\Response;

class ApiController extends Controller
{

    /**
     * Initializes the api controller
     *
     * Disables CSRF-Protection for Api-Calls
     */
    public function init()
    {
        $this->enableCsrfValidation = false;

        parent::init();
    }

    /**
     * Api endpoint to create a user
     * Method: POST
     *
     * @return Response
     */
    public function actionUsers()
    {
        $this->forcePostRequest();

        if ($error = $this->forceBcsAuthentication()) {
            return $error;
        }

        // read request body for input data
        /** @var Request $request */
        $request = Yii::$app->request;

        $username = $request->post('username');
        $password = $request->post('password');
        $email = $request->post('email');

        if (empty($username) ||
            empty($password) ||
            empty($email)
        ) {
            return $this->responseError("Username, password and e-mail are required fields", 400);
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

        return $this->responseSuccess('User registration successful');
    }

    /**
     * Authenticates BCS via header
     * Returns a 401 error response if invalid
     *
     * @return Response|null
     */
    private function forceBcsAuthentication()
    {
        // TODO: read current tokens from database or config file...
        $validApiTokens = [
            'a3Jhc3Nfc2ljaGVy'
        ];

        $requestToken = Yii::$app->request->headers->get('bcs-super-token');

        if (!in_array($requestToken, $validApiTokens)) {
            return $this->responseError(null, 401);
        }

        return null;
    }

    /**
     * Creates a json formatted api success message
     *
     * @param string|null $message
     * @param int $code
     * @return Response
     */
    protected function responseSuccess($message = null, $code = 200)
    {
        return $this->responseJson($message, $code, 'success');
    }

    /**
     * Creates a json formatted api error message
     *
     * @param string|null $message
     * @param int $code
     * @return Response
     */
    protected function responseError($message = null, $code = 400)
    {
        return $this->responseJson($message, $code, 'error');
    }

    /**
     * @param $message
     * @param $code
     * @param $status
     * @return Response
     */
    protected function responseJson($message, $code, $status)
    {
        $response = new Response();

        $response->setStatusCode($code);

        $response->format = Response::FORMAT_JSON;

        $response->data = [
            'status' => $status,
            'code' => $code,
            'message' => $message ?? $this->tryFetchHttpMessage($code),
        ];

        return $response;
    }

    /**
     * Excepts a http status code and returns the specific error message
     *
     * @param $code
     * @return string
     */
    protected function tryFetchHttpMessage($code)
    {
        return Response::$httpStatuses[$code] ?? 'Unknown error';
    }
}