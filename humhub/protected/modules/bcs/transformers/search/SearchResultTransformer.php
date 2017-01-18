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
        $id = null;
        if ($data instanceof ContentContainerActiveRecord) {
            $url = $this->host() . $data->getUrl();
        }
        if ($data instanceof Space) {
            $id = $data->guid;
            $message = $data->name;
            if (empty($data->description)) {
                $attributes = "Keine Space-Beschreibung vorhanden";
            } else {
                $attributes = $data->description;
            }
        } else if ($data instanceof User) {
            $id = $data->guid;
            $message = $data->username;
            if (empty($data->tags)) {
                $attributes = "";
            } else {
                $attributes = $data->tags;
            }
        } else {
            //$id = $data->guid;
            $message = $data->message;
            $user = User::findOne($data->created_by);
            $id = $user->guid;
            $url = $this->host() . "/u/" . $user->username;
            $attributes = $data->created_at . " " . $user->username;
        }
        return [
            'id' => $id,
            'message' => $message,
            'type' => $class,
            'url' => $url,
            'attributes' => $attributes,
        ];

    }


    private function host()
    {
        return \Yii::$app->settings->get('baseUrl');
    }

}