if [ ! -f /humhub/protected/config/dynamic.php ]; then
    echo "Starting installation..."
    cp /dynamic.php /humhub/protected/config/dynamic.php
    php /wait-for-database.php
    php ./protected/yii bcs/install
fi

php-fpm