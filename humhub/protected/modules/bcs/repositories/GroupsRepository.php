<?php

namespace humhub\modules\bcs\repositories;

use humhub\modules\user\models\Group;

class GroupsRepository
{

    public function all()
    {
        return Group::find()->all();
    }
}
