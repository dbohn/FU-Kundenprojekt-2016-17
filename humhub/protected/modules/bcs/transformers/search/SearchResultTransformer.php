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
            $attributes = $data->description;
        } else if ($data instanceof User) {
            //Tags darf nicht leer sein TODO: Abfangen
            $message = $data->username;
            $attributes = $data->tags;
        } else {
            $message = $data->message;
            $attributes = $data->created_at;
        }
        return [
            'message' => $message,
            'type' => $class,
            'url' => $url,
            'attributes' => $attributes,
        ];

    }



    private function host(){
        return \Yii::$app->settings->get('baseUrl');
    }

}