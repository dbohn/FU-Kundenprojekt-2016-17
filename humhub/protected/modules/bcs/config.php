<?php

use humhub\components\console\Application;
use humhub\modules\admin\widgets\AdminMenu;
use humhub\modules\user\authclient\BaseClient;
use humhub\modules\user\models\User;

return [
    'id' => 'bcs',
    'class' => 'humhub\modules\bcs\Module',
    'isCoreModule' => true,
    'namespace' => 'humhub\modules\bcs',
    'events' => [
        [
            'class' => AdminMenu::className(),
            'event' => AdminMenu::EVENT_INIT,
            'callback' => ['humhub\modules\bcs\Events', 'onAdminMenuInit'],
        ],
        [
            'class' => User::className(),
            'event' => User::EVENT_BEFORE_DELETE,
            'callback' => ['humhub\modules\bcs\Events', 'onUserDelete'],
        ],
        [
            'class' => BaseClient::class,
            'event' => BaseClient::EVENT_CREATE_USER,
            'callback' => ['humhub\modules\bcs\Events', 'onUserCreate'],
        ],
        [
            'class' => BaseClient::class,
            'event' => BaseClient::EVENT_UPDATE_USER,
            'callback' => ['humhub\modules\bcs\Events', 'onUserUpdate'],
        ],
        [
            'class' => Application::class,
            'event' => Application::EVENT_ON_INIT,
            'callback' => ['humhub\modules\bcs\Events', 'onConsoleInit'],
        ],
    ],
    'urlManagerRules' => [
    ],
];
