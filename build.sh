#!/bin/sh
set -x

for CONTAINER_NAME in eureka obliczajka database updater rest\
    translator_marasm translator_airly translator_icm mongo; do
  docker stop $CONTAINER_NAME
  docker rm $CONTAINER_NAME
done

docker run -dt --net p5 -h mongo --name mongo mongo
docker run -dt -p 5042 --net p5 -h eureka --name eureka p5_eureka
docker run -dt -p 4021 -p 4421 --net p5 --name obliczajka p5_obliczajka
docker run -dt -p 4200 --net p5 --name database p5_database
docker run -dt -p 5001 --net p5 --name updater p5_updater
docker run -dt -p 4000:4000 --net p5 --name rest p5_rest
docker run -dt -p 4413 --net p5 --name translator_marasm p5_translator_marasm
docker run -dt --net p5 --name translator_airly p5_translator_airly
docker run -dt --net p5 --name translator_icm p5_translator_icm
docker exec -it rest ./home/root/bin/rest_endpoint foreground
