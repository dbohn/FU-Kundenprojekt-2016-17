FROM php:7-fpm

ARG workdir="/var/www/html"

RUN apt-get update && apt-get install -y \
    libfreetype6-dev \
        libjpeg62-turbo-dev \
        libmcrypt-dev \
        libpng12-dev \
        libicu-dev \
        libpq-dev \
        zlib1g zlib1g-dev zlib1g-dbg zlibc \
    && docker-php-ext-install -j$(nproc) iconv mcrypt \
    && docker-php-ext-configure gd --with-freetype-dir=/usr/include/ --with-jpeg-dir=/usr/include/ \
    && docker-php-ext-install -j$(nproc) gd \
    && docker-php-ext-install intl pdo mysqli pdo_mysql pdo_pgsql mbstring exif zip \
    && php -r "readfile('http://getcomposer.org/installer');" | php -- --install-dir=/usr/bin/ --filename=composer

WORKDIR "$workdir"
