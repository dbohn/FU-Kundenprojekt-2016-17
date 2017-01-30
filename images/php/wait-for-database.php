<?php

echo "Waiting for database...\n";

$dsn = 'mysql:host=mysql;dbname=humhub';
$username = 'humhub';
$password = '1234';

$connection = false;

do {
    echo "Try connecting...\n";
    try {
        $connection = new PDO($dsn, $username, $password);
    } catch (Exception $e) {
        echo "Connection failed: " . $e->getMessage() . "\n";
        $connection = false;
        sleep(5);
    }
} while (!$connection);

echo "Database ready!\n";
