<?php
/**
 * Created by PhpStorm.
 * User: hanna
 * Date: 12.12.2016
 * Time: 13:10
 */

namespace humhub\modules\bcs\transformers\search;


use humhub\modules\bcs\transformers\messages\AbstractTransformer;
use humhub\modules\space\models\Space;
use humhub\modules\user\models\User;

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
        if ($data instanceof Space) {
            $message = $data->name;

        } else if ($data instanceof User){
            $message = $data->username;
        }
        else {
            $message = $data->message;
        }
        return [
            'message' => $message,
            'type' => $class,
        ];

    }
}