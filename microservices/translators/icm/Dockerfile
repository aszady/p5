FROM python:3
ADD service.py /opt/icm/service.py

RUN pip3 install tornado

RUN apt install git

WORKDIR /tmp
RUN git clone https://github.com/aszady/icmparser.git
WORKDIR /tmp/icmparser
RUN python3 setup.py install

WORKDIR /tmp
RUN git clone https://github.com/aszady/python-eureka.git
WORKDIR /tmp/python-eureka
RUN ls
RUN python3 setup.py install

WORKDIR /
CMD python3 /opt/icm/service.py --host `cat /etc/hosts | tail -n1 | awk '{print $1}'` --eureka_host eureka
