<?php
/**
 * Created by PhpStorm.
 * User: hanna
 * Date: 12.12.2016
 * Time: 12:56
 */

namespace humhub\modules\bcs\controllers;


use humhub\modules\bcs\controllers\ApiController;
use humhub\modules\bcs\transformers\search\SearchResultTransformer;
use Yii;

class SearchController extends ApiController
{
    public function actionSearch(){

        /*if ($error = $this->forceBcsAuthentication()) {
            return $error;
        }*/

        $request = Yii::$app->request;

        $query = $request->get('query');

        $searchResultSet = Yii::$app->search->find($query, ['checkPermissions' => false]);

        $results = $searchResultSet->getResultInstances();

        //var_dump($results); die();

        $searchTransformer = new SearchResultTransformer();

        return $this->responseSuccess($searchTransformer->transformCollection($results, $searchTransformer));
    }

}