<?php
/**
 * Created by PhpStorm.
 * User: hanna
 * Date: 12.12.2016
 * Time: 13:10
 */

namespace humhub\modules\bcs\transformers\search;


use humhub\modules\bcs\transformers\messages\AbstractTransformer;

class SearchResultTransformer extends AbstractTransformer
{

    /**
     * @param mixed $data
     * @return array
     */
    public function transform($data)
    {
        return [
            'message' => $data->message,
        ];

    }
}