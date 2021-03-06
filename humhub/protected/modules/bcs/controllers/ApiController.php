<?php

namespace humhub\modules\bcs\controllers;

use humhub\components\Controller;
use humhub\modules\bcs\models\BcsToken;
use humhub\modules\user\models\User;
use Yii;
use yii\web\Response;

abstract class ApiController extends Controller
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
     * Authenticates BCS via header
     * Returns a 401 error response if invalid
     *
     * @return Response|null
     */
    protected function forceBcsAuthentication()
    {
        $tokens = BcsToken::find()->all();

        $validApiTokens = array_map(function ($model) {
            return $model->token;
        }, $tokens);

        // TODO: remove this line!!!11!1!!1!
        $validApiTokens[] = 'IVa9aWe6rFneYtEpJEXiVTS4gKmBuoXvPLVxpnRaE2xubLzeV4Pbn8QK284v';

        $requestToken = Yii::$app->request->headers->get('x-bcs-super-token');

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
     * Creates a json formatted api message
     *
     * @param string $message message to display
     * @param integer $code http code to set
     * @param string $status status to set (e.g. "error")
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
     * @param integer $code http code to resolve
     * @return string
     */
    protected function tryFetchHttpMessage($code)
    {
        return Response::$httpStatuses[$code] ?? 'Unknown error';
    }

    /**
     * Exit if http method is not GET
     */
    protected function forceGetRequest()
    {
        if (\Yii::$app->request->method != 'GET') {
            print "Invalid method!";
            die();
        }
    }

    /**
     * login a user by given guid
     *
     * @param string $id user guid
     * @param int $duration duration for login in seconds
     * @return bool
     */
    protected function loginBcsUser($id, $duration = 0)
    {
        $user = User::findByGuid($id);

        if (!$user) {
            return false;
        }

        Yii::$app->user->login($user, $duration);

        return true;
    }
}