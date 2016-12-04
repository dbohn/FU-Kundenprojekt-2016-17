<?php

use humhub\modules\admin\widgets\AdminMenu;
use humhub\modules\user\models\User;

return [
    'id' => 'bcs',
    'class' => 'humhub\modules\bcs\Module',
    'namespace' => 'humhub\modules\bcs',
    'events' => [
        [
            'class' => AdminMenu::className(),
            'event' => AdminMenu::EVENT_INIT,
            'callback' => ['humhub\modules\bcs\Events', 'onAdminMenuInit']
        ],
        [
            'class' => User::className(),
            'event' => User::EVENT_BEFORE_DELETE,
            'callback' => ['humhub\modules\bcs\Events', 'onUserDelete']
        ]
    ]
];
