if [ ! -f /humhub/protected/config/dynamic.php ]; then
    echo "Starting installation..."
    cp /dynamic.php /humhub/protected/config/dynamic.php
    sleep 20
    php ./protected/yii bcs/install
fi

php-fpm