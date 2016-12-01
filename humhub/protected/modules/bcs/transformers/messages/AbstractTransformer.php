<?php

namespace humhub\modules\bcs\transformers\messages;

abstract class AbstractTransformer
{

    /**
     * @param mixed $data
     * @return array
     */
    abstract public function transform($data);

    /**
     * @param array $items
     * @param AbstractTransformer $transformer
     * @return array
     */
    protected function transformCollection($items, AbstractTransformer $transformer)
    {
        return array_filter(array_map(function ($item) use ($transformer) {
            return $transformer->transform($item);
        }, $items));
    }
}