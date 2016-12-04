<?php

namespace humhub\modules\bcs;

use humhub\modules\bcs\models\UserBcs;
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
            'url' => Url::to(['/bcs/settings/index']),
            'icon' => '<i class="fa fa-cog"></i>',
            'isActive' => (Yii::$app->controller->module && Yii::$app->controller->module->id == 'bcs'),
            'sortOrder' => 300,
        ));
    }

    public static function onUserDelete($event)
    {
        foreach (UserBcs::findAll(['user_id' => $event->sender->id]) as $userBcs) {
            $userBcs->delete();
        }
    }
}
