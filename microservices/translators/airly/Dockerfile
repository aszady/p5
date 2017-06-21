FROM python:3
ADD service.py /opt/airly/service.py
ADD airly_client.py /opt/airly/airly_client.py
RUN pip3 install tornado haversine
RUN apt install git
WORKDIR /tmp
RUN git clone https://github.com/aszady/python-eureka.git
WORKDIR /tmp/python-eureka
RUN ls
RUN python3 setup.py install
WORKDIR /
CMD python3 /opt/airly/service.py --host `cat /etc/hosts | tail -n1 | awk '{print $1}'` --eureka_host eureka --airly_token=$AIRLY_TOKEN
