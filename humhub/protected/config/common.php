<?php

return [
    'components' => [
        'urlManager' => [
            'showScriptName' => false,
            'enablePrettyUrl' => true,
        ],

        'authClientCollection' => [
            'class' => 'humhub\modules\user\authclient\Collection',
            'clients' => [
                'bcs' => [
                    'class' => \humhub\modules\bcs\auth\BcsAuthClient::class,
                    'dsn' => 'pgsql:host=database;port=5432;dbname=humhub',
                    'username' => 'humhub',
                    'password' => '1234',
                    'charset' => 'utf8',
                ]
            ]
        ],
    ]
];
