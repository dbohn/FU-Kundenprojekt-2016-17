<?php
/**
 * Created by PhpStorm.
 * User: hanna
 * Date: 14.01.2017
 * Time: 11:41
 */

namespace modules\bcs\transformers\search;


class SpaceTransformer
{

    /**
     * @param mixed $data
     * @return array
     */
    public function transform($data)
    {
        return [
            'id' =>  $data->id,
            'name' => $data->name,
            'description' => $data->description,
        ];

    }


    private function host()
    {
        return \Yii::$app->settings->get('baseUrl');
    }

}