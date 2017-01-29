<?php return array(
    'components' => array(
        'db' => array(
            'class' => 'yii\\db\\Connection',
            'dsn' => 'mysql:host=mysql;dbname=humhub',
            'username' => 'humhub',
            'password' => '1234',
            'charset' => 'utf8mb4',
        ),
        'user' => array(
        ),
        'mailer' => array(
            'transport' => array(
                'class' => 'Swift_MailTransport',
            ),
            'view' => array(
                'theme' => array(
                    'name' => 'BCSHumHub',
                    'basePath' => '/humhub/themes/BCSHumHub',
                    'publishResources' => false,
                ),
            ),
        ),
        'cache' => array(
            'class' => 'yii\\caching\\ApcCache',
            'keyPrefix' => 'humhub',
        ),
        'view' => array(
            'theme' => array(
                'name' => 'BCSHumHub',
                'basePath' => '/humhub/themes/BCSHumHub',
                'publishResources' => false,
            ),
        ),
        'formatter' => array(
            'defaultTimeZone' => 'Europe/Berlin',
        ),
        'formatterApp' => array(
            'defaultTimeZone' => 'Europe/Berlin',
            'timeZone' => 'Europe/Berlin',
        ),
    ),
    'params' => array(
        'installer' => array(
            'db' => array(
                'installer_hostname' => 'mysql',
                'installer_database' => 'humhub',
            ),
        ),
        'config_created_at' => 1485309542,
        'horImageScrollOnMobile' => '1',
        'databaseInstalled' => false,
        'installed' => false,
    ),
    'name' => 'HumHub',
    'language' => 'de',
    'timeZone' => 'Europe/Berlin',
);
