<?php

namespace humhub\modules\bcs;

use Yii;
use yii\helpers\Url;

class Events extends \yii\base\Object
{

    /**
     * @param $event
     */
    public static function onAdminMenuInit($event)
    {
        if (Yii::$app->user->isGuest) {
            return;
        }

        $event->sender->addItem(array(
            'label' => Yii::t('BcsModule.base', 'BCS'),
            'url' => Url::to(['/bcs/api/index']),
            'icon' => '<i class="fa fa-cog"></i>',
            'isActive' => (Yii::$app->controller->module && Yii::$app->controller->module->id == 'bcs'),
            'sortOrder' => 300,
        ));
    }

}
