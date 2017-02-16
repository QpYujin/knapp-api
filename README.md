# knapp-api

This is just a basic integration test for knapp-api

## Knapp-API endpoints

1. POST {{ip:port}}/knapp/qtype
	{
		"name":"something",
		"description":"some words",
		"comments":"some words"
	}
2. GET {{ip:port}}/knapp/qtype ==> Returns all created Qtype
3. GET {{ip:port}}/knapp/qtype/{{id}} ==> Returns Qtype with specified ID
4. PUT {{ip:port}}/knapp/qtype/{{id}} ==> Edits Qtype with specified id with new database
5. DELETE {{ip:port}}/knapp/qtype/{{id}} ==> Remove Qtype with specified ID from database
		
## To rebuild image after changes to code (DO NOT RUN THIS WHEN THERE IS NO CHANGES TO CODE):

1. Install docker. Following [this guide](https://docs.docker.com/engine/installation/)
2. Cd into knapp-api/docker-mysql-image folder.
3. Run server using `docker-compose up` NOTE: Whenever repackaging knapp-api, a server is required to run test to complete package.
4. Return back to root, package app using `mvn clean package`
5. Rebuild image with `docker-compose build` or `docker build`
6. Run `docker stop `docker ps -a -q` to stop database container.
7. Run `docker-compose rm -vf` to remove all containers and all volume and containers within container to ensure clean slate.

## Steps to run with build image

1. Install docker. Following [this guide](https://docs.docker.com/engine/installation/)
1. Run app using `docker-compose up` from root 

## Docker compose creates 5 services:

1. Database container which use mysql image with 1 database named 'knappdb' created
2. Web application container built based on tomcat:8.5 image and deploy the war file as ROOT app
3. Nginx + consul template as load balancer serving on port 80
4. Consul server
5. Registrator

## Testing

1. Open mysql server with connection to 192.168.99.100:3306
2. Run mvn test in command line from root of file
3. Run following command in mysql server within database: `SELECT DATABASE knapptest; SELECT * FROM knapp_question_type;`

## Knapp API testing

1. Import `knapp-api-postman.json` to your postman collection
2. Create new environment which has `url` key with the value: `http://docker-ip`
3. Exec each pre-defined requests to see result
4. Check mysql server to see results are there

## Stopping all containers

1. Run `docker stop `docker ps -a -q``
2. Run `docker-compose rm -vf` to ensure containers are fully removed with all files deleted
