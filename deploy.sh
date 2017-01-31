#!/bin/sh

MVN_DOCKER_VERSION=3.3.9-jdk-8
DEMONSTRATOR_DOCKER_USER=1000
HUMHUB_DOCKER_USER=33
COMPOSE_FILE=docker-compose.deploy.yml

# Create possibly missing assets directory
if [ ! -d ./humhub/assets ]; then
    echo "Creating missing directory humhub/assets..."
    mkdir -p ./humhub/assets
    echo "[Done]\n"
fi

# Get the OS Architecture
os=$(uname -s)

# Set HumHub Directory Permissions
if [ $os = "Linux" ]; then
    echo "Setting directory permissions for humhub..."
    chmod -R 777 ./humhub
    echo "[Done]\n"

    echo "Setting directory permissions for maven build..."
    chmod -R 777 ./build
    echo "[Done]\n"
fi

# Build Demonstrator
docker run -it --rm --name build-demonstrator -v "$PWD":/usr/src/mymaven -w /usr/src/mymaven/Demonstrator maven:$MVN_DOCKER_VERSION mvn clean package

# Move built Demonstrator.war to container build
mv build/Demonstrator.war images/wildfly_deploy/Demonstrator.war

# Build containers
docker-compose --file $COMPOSE_FILE build --force-rm

# Remove .war file
rm -f images/wildfly_deploy/Demonstrator.war

# Start containers
docker-compose --file $COMPOSE_FILE up -d

docker-compose logs -f
