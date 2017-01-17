<?php

namespace humhub\modules\bcs\repositories;

use humhub\modules\space\models\Space;
use humhub\modules\user\models\User;

class SpacesRepository
{

    public function all()
    {
        return Space::find()->all();
    }

    /**
     * @param        $name
     * @param string $description
     * @param User   $originator
     * @param string $color
     * @param int    $visibility
     * @param int    $joinPolicy
     *
     * @return Space
     */
    public function create($name, User $originator, $description = "", $color="#27ae60", $visibility = Space::VISIBILITY_NONE, $joinPolicy = Space::JOIN_POLICY_NONE)
    {
        $model = new Space();

        $model->scenario = 'create';
        $model->visibility = $visibility;
        $model->join_policy = $joinPolicy;

        $model->name = $name;
        $model->description = $description;
        $model->color = $color;

        $model->created_by = $originator->id;

        $model->save();

        return $model;
    }
}
