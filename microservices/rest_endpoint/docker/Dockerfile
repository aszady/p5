#FROM rest_builder
FROM phusion/baseimage
#RUN mkdir /etc/service/rest_endpoint
#ADD rest.sh /etc/service/rest_endpoint/run
#RUN chmod +x /etc/service/rest_endpoint/run

COPY rest_endpoint.tar.gz /home/root/
RUN tar -C /home/root -xzvf /home/root/rest_endpoint.tar.gz
EXPOSE 4000
ENV PORT 4000
#CMD ["/run.sh"]
CMD ["/sbin/my_init"]
#CMD ["/bin/rest_endpoint", "start"]
