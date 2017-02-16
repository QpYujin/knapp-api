FROM tomcat:8.5.11-jre8
MAINTAINER Yu Jin Kok <yujin@qpair.io>

# Remove tomcat default root app and copy our rest app deploy as ROOT
RUN ["rm", "-rf", "/usr/local/tomcat/webapps/ROOT"]
ADD target/api-1.0.0-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war

CMD ["catalina.sh", "run"]

EXPOSE 8080


