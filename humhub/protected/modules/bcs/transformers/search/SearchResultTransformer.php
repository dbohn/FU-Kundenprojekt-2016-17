<?php
/**
 * Created by PhpStorm.
 * User: hanna
 * Date: 12.12.2016
 * Time: 13:10
 */

namespace humhub\modules\bcs\transformers\search;


use humhub\modules\bcs\transformers\messages\AbstractTransformer;
use humhub\modules\content\components\ContentContainerActiveRecord;
use humhub\modules\space\models\Space;
use humhub\modules\user\models\User;
use yii\helpers\Url;

class SearchResultTransformer extends AbstractTransformer
{

    /**
     * @param mixed $data
     * @return array
     */
    public function transform($data)
    {
        $classParts = explode("\\", get_class($data));
        $class = array_pop($classParts);
        $url = "";
        if ($data instanceof ContentContainerActiveRecord) {
            $url = $this->host() . $data->getUrl();
        }

        if ($data instanceof Space) {
            $message = $data->name;

        } else if ($data instanceof User) {
            $message = $data->username;
        } else {
            $message = $data->message;
        }
        return [
            'message' => $message,
            'type' => $class,
            'url' => $url,
        ];

    }



    private function host(){
        return \Yii::$app->settings->get('baseUrl');
    }

}