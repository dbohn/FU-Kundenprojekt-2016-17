<?php

namespace humhub\modules\bcs\repositories;

use humhub\modules\space\models\Space;

class SpacesRepository
{

    public function all()
    {
        return Space::find()->all();
    }
}
