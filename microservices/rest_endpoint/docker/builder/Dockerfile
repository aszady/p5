FROM phusion/baseimage

ENV REST_BUILD_DEPS "python-pip \
                     python-dev \
                     libssl-dev \
                     libffi-dev \
                     python-wheel \
                     build-essential \
                     python-setuptools \
                     software-properties-common"
ENV REST_RUNTIME_DEPS "git \
                       python \
                       curl \
                       elixir=1.3.3-1 \
                       npm=3.5.2-0ubuntu4 \
                       esl-erlang=1:18.3.4"


RUN curl -O https://packages.erlang-solutions.com/erlang-solutions_1.0_all.deb \
    && dpkg -i erlang-solutions_1.0_all.deb \
    && apt-key adv --keyserver hkp://p80.pool.sks-keyservers.net:80 --recv-keys 58118E89F3A912897C070ADBF76221572C52609D \
    && echo "deb https://apt.dockerproject.org/repo ubuntu-xenial main" >> /etc/apt/sources.list.d/docker.list \
    && apt-get update \
    && apt-get install -y --no-install-recommends $REST_BUILD_DEPS $REST_RUNTIME_DEPS \
    && curl -sL https://deb.nodesource.com/setup_6.x | bash \
    && apt-get install -y nodejs \
    && update-alternatives --install /usr/bin/node node /usr/bin/nodejs 99
