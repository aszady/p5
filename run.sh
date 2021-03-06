#!/bin/sh
set -x

./stop.sh

if [ ! "$AIRLY_TOKEN" ]; then
  echo "Please specify AIRLY_TOKEN env var."
  exit 1
fi

for CONTAINER_NAME in eureka obliczajka yacs updater rest\
    marasm airly icm mongo cas; do
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
sleep 20
run_p5container obliczajka -p 4421
run_p5container yacs -p 4200
run_p5container updater -p 5001
run_p5container rest -p 4000:4000
run_p5container marasm -p 4413
run_p5container airly -e AIRLY_TOKEN -p 4411
run_p5container icm -p 4412
run_p5container cas -p 5002
docker ps

docker exec -it rest ./home/root/bin/rest_endpoint foreground
