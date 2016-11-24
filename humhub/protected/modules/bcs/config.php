<?php

use humhub\modules\admin\widgets\AdminMenu;

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
    ]
];
?>