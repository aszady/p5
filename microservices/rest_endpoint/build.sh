./node_modules/brunch/bin/brunch b -p && MIX_ENV=prod mix do phoenix.digest, release --env=prod
cp  _build/prod/rel/rest_endpoint/releases/0.0.1/rest_endpoint.tar.gz docker/
docker build -t p5_rest docker
