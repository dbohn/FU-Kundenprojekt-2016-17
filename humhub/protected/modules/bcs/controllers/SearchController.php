<?php
/**
 * Created by PhpStorm.
 * User: hanna
 * Date: 12.12.2016
 * Time: 12:56
 */

namespace humhub\modules\bcs\controllers;


use humhub\components\Request;
use humhub\modules\bcs\transformers\search\SearchResultTransformer;
use humhub\modules\space\models\Space;
use Yii;

class SearchController extends ApiController
{

    /**
     * Endpoint for BCS-Search
     *
     * @return \yii\web\Response
     */
    public function actionSearch()
    {
        /** @var Request $request */
        $request = Yii::$app->request;

        $bcsId = $request->get("bcs_id");

        $this->loginBcsUser($bcsId);

        $query = $request->get('query');
        $limitedToSpace = $request->get("space");

        $options = ['checkPermissions' => true];

        if ($limitedToSpace !== null && $limitedToSpace !== 'All') {
            $space = Space::findOne(['guid' => $limitedToSpace]);

            $options['limitSpaces'] = [$space];
        }

        $searchResultSet = Yii::$app->search->find($query, $options);

        $results = $searchResultSet->getResultInstances();

        $searchTransformer = new SearchResultTransformer();

        return $this->responseSuccess($searchTransformer->transformCollection($results, $searchTransformer));
    }

}
