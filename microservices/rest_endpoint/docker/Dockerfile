FROM rest_builder

RUN mkdir /etc/service/rest_endpoint
ADD rest.sh /etc/service/rest_endpoint/run
RUN chmod +x /etc/service/rest_endpoint/run

COPY rest_endpoint.tar.gz /usr/lib/
RUN ls /usr/lib
RUN tar -xzvf /usr/lib/rest_endpoint.tar.gz 
EXPOSE 4000
ENV PORT 4000
#CMD ["/run.sh"]
CMD ["/sbin/my_init"]
#CMD ["/bin/rest_endpoint", "start"]
