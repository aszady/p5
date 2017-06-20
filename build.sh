#!/bin/sh
docker stop eureka
docker rm eureka
docker stop obliczajka
docker rm obliczajka
docker stop database
docker rm database
docker stop updater
docker rm updater
docker stop rest
docker rm rest
docker stop translator_marasm
docker rm translator_marasm
docker stop mongo
docker rm mongo

docker run -dt --net p5 -h mongo --name mongo mongo
docker run -dt -p 5042 --net p5 -h eureka --name eureka p5_eureka
docker run -dt -p 4021 -p 4421 --net p5 --name obliczajka p5_obliczajka
docker run -dt -p 4200 --net p5 --name database p5_database
docker run -dt -p 5001 --net p5 --name updater p5_updater
docker run -dt -p 4000:4000 --net p5 --name rest p5_rest
docker run -dt -p 4413 --net p5 --name translator_marasm p5_translator_marasm
docker exec -it rest ./home/root/bin/rest_endpoint foreground
