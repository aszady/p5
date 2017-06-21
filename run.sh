#!/bin/sh
set -x

./stop.sh

if [ ! "$AIRLY_TOKEN" ]; then
  echo "Please specify AIRLY_TOKEN env var."
  exit 1
fi

for CONTAINER_NAME in eureka obliczajka database updater rest\
    translator_marasm translator_airly translator_icm mongo; do
  docker rm $CONTAINER_NAME
done

run_container() {
  NAME="$1"
  IMG_NAME="$2"
  DOCKER_PARAMS="$3"
  EP_PARAMS="$4"
  shift;shift
  docker run -idt --net p5 -h $NAME --name $NAME $DOCKER_PARAMS $IMG_NAME $EP_PARAMS
}

run_p5container() {
  NAME="$1"
  shift
  run_container $NAME p5_$NAME "$*" ""
}

run_container mongo mongo "" "--smallfiles"
run_p5container eureka -p 5042
sleep 10
run_p5container obliczajka -p 4421
run_p5container database -p 4200
run_p5container updater -p 5001
run_p5container rest -p 4000:4000
run_p5container translator_marasm -p 4413
run_p5container translator_airly -e AIRLY_TOKEN -p 4411
run_p5container translator_icm -p 4412

docker ps

docker exec -it rest ./home/root/bin/rest_endpoint foreground
