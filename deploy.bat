@echo off

:: Create possibly missing assets directory
if not exist ".\humhub\assets" mkdir .\humhub\assets

SET me=%~n0
:: present working directory
SET dir=%~dp0

SET mvn_docker_version=3.3.9-jdk-8
SET compose_file=docker-compose.deploy.yml

:: Build Demonstrator
docker run -it --rm --name build-demonstrator -v %dir%:/usr/src/mymaven -w /usr/src/mymaven/Demonstrator maven:%mvn_docker_version% mvn clean package

:: Move built Demonstrator.war to container build
move build\Demonstrator.war images\wildfly_deploy\Demonstrator.war

:: Build containers
docker-compose --file %compose_file% build --force-rm

:: Remove .war file
del images/wildfly_deploy/Demonstrator.war

:: Start containers
docker-compose --file %compose_file% up -d

docker-compose logs -f