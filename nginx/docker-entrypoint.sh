#!/bin/sh
service nginx start

consul-template -consul "$CONSUL_HOST" -template "/templates/services.conf.ctmpl:/etc/nginx/conf.d/serivces.conf:service nginx reload"
