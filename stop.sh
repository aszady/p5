#!/bin/sh
set -x

for CONTAINER_NAME in eureka obliczajka database updater rest\
    translator_marasm translator_airly translator_icm mongo; do
  docker stop $CONTAINER_NAME
done
