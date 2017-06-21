#!/bin/bash
set -x


buildimg() {
  IMG_PATH="$2"
  NAME="$1"
  pushd $IMG_PATH
  
  docker rmi -f $NAME
  docker build -t $NAME .
  
  popd
}

# Python – light and easy
buildimg p5_translator_airly microservices/translators/airly
buildimg p5_translator_icm microservices/translators/icm

# Erlang – nice and clean
cd microservices/rest_endpoint/
./build.sh
cd $OLDPWD
# Java – ...
mvn package

cp microservices/translators/marasm/{target/translator-marasm-0.1.2.jar,src/main/docker}
buildimg p5_translator_marasm microservices/translators/marasm/src/main/docker

for MNAME in eureka updater database; do
  cp microservices/$MNAME/{target/$MNAME-0.0.1-SNAPSHOT.jar,src/main/docker}
  buildimg p5_$MNAME microservices/$MNAME/src/main/docker
done

cp microservices/obliczajkas/model1/{target/obliczajkas-model1-0.1.0.jar,src/main/docker}
buildimg p5_obliczajka microservices/obliczajkas/model1/src/main/docker
