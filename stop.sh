#!/bin/sh
set -x

for CONTAINER_NAME in eureka obliczajka yacs updater rest\
    marasm airly icm mongo cas; do
  docker stop $CONTAINER_NAME -t 0
done
