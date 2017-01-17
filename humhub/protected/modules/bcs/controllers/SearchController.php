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
    public function actionSearch()
    {

      //  if ($error = $this->forceBcsAuthentication()) {
       //     return $error;
        //}

        $request = Yii::$app->request;

        $bcsId = $request->get("bcs_id");
        
        $this->loginBcsUser($bcsId);

        $query = $request->get('query');
        $limitedToSpace = $request->get("space");
        //var_dump($limitedToSpace);


       // if ($limitedToSpace == "All"){
            $searchResultSet = Yii::$app->search->find($query, ['checkPermissions' => false]);
        //}
        //else{

            //hier als option limitedToSpace übergeben
           // $limitSpaceGuids = Yii::$app->request->get('limitSpaceGuids', "");
           // $limitSpaces = array();
           // if ($limitSpaceGuids !== "") {
             // foreach (explode(",", $limitSpaceGuids) as $guid) {
            //    $guid = trim($guid);
            //       if ($guid != "") {
             //     $space = Space::findOne(['guid' => trim($guid)]);
             //           if ($space !== null) {
              //              $limitSpaces[] = $space;
               //         }
                 // }
               // }
          //  }

           // var_dump($limitedToSpace);

           // $array = [
           //     "space" => $limitedToSpace,
           // ];
           // $searchResultSet = Yii::$app->search->find($query, ['checkPermissions' => false,'limitSpaceGuids' => $array]);
        //}

        $results = $searchResultSet->getResultInstances();


        $searchTransformer = new SearchResultTransformer();

        return $this->responseSuccess($searchTransformer->transformCollection($results, $searchTransformer));
    }

}