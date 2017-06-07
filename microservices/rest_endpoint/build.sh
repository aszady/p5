./node_modules/brunch/bin/brunch b -p && MIX_ENV=prod mix do phoenix.digest, release --env=prod

docker build -t p5_rest rel/docker
