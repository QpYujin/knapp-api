FROM nginx:latest
MAINTAINER Trinh Phuoc Thai <tphuocthai@gmail.com>

ENV CONSUL_HOST consul:8500

RUN apt-get update
RUN apt-get install -y unzip

ADD https://releases.hashicorp.com/consul-template/0.15.0/consul-template_0.15.0_linux_amd64.zip /tmp/
RUN unzip /tmp/consul-template_0.15.0_linux_amd64.zip -d /usr/bin

# Remove default config
RUN rm -rf /etc/nginx/conf.d/*

# Cleanup
RUN rm /tmp/consul-template_0.15.0_linux_amd64.zip &&\
  apt-get remove --purge -y unzip &&\
  apt-get autoremove -y &&\
  apt-get clean &&\
  apt-get autoclean &&\
  rm -rf /var/lib/apt/lists/* &&\
  rm -rf /usr/share/man/*

ADD templates /templates
ADD docker-entrypoint.sh /docker-entrypoint.sh

RUN chmod +x /docker-entrypoint.sh

CMD ["/docker-entrypoint.sh"]

EXPOSE 80
