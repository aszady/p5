FROM rest_builder

ENV REST_PATH "/usr/lib/tide"

ADD tide_src/ /tide_src
ADD files/prod.secret.exs /tide_src/config/prod.secret.exs
ADD files/config.exs /tide_src/config/config.exs
ADD files/prod.exs /tide_src/config/prod.exs

RUN cd tide_src \
    && mix local.hex --force \
    && mix local.rebar --force \
    && mix compile \
    && sed -i -e "s%SECRET_KEY_BASE%\"$(mix phoenix.gen.secret)\"%" config/prod.secret.exs \
    && cat config/prod.secret.exs \
    && mix release.clean \
    && npm install \
    && node_modules/brunch/bin/brunch build \
    && MIX_ENV=prod mix phoenix.digest \
    && MIX_ENV=prod mix compile \
    && MIX_ENV=prod mix release --env=prod \
    && cd / \
    && find tide_src/rel/tide/releases/  -regex "^.*/[0-9]+\.[0-9]+\.[0-9]+/tide.tar.gz" -type f -exec mv {} / \; \
    && mkdir -p $TIDE_PATH \
    && tar xvf tide.tar.gz -C $TIDE_PATH

RUN mkdir -p /var/lib/tide/uploads
RUN mkdir /etc/service/tide
ADD files/tide.sh /etc/service/tide/run
RUN chmod +x /etc/service/tide/run
ADD files/run.sh /run.sh
RUN chmod +x /run.sh

EXPOSE 8080

VOLUME ["/var/lib/tide"]

CMD ["/run.sh"]
