# Personalized-Data-API
This assignment is a close simulation of our day-to-day work at NIQ Activate and is  meant to evaluate your design and coding skills.
#Dockerisation,How to find?
--Steps to find the Docker--
1-created folder,named docker-compose
2-created Dockerfile and configured it as well whose configuration is below
   configuartion:
            -->FROM openjdk:21
               WORKDIR /usr/src/bootapp
               COPY  . /usr/src/bootapp/
               CMD [ "java", "-jar","PersonalizedDataAPI-0.0.1-SNAPSHOT.jar" ] 
3-Inside boot directory i have added my jar file whose name is "PersonalizedDataAPI-0.0.1-SNAPSHOT.jar"

4-In docker-compose directory(outside boot directory) i have added docker-compose.yml file  whose configuration are as below
 version: '3'
 services:
 dbservice:
 image: mysql
 environment:
 - MYSQL_ROOT_PASSWORD=Rockstone@123$
 - MYSQL_USER=root
 volumes:
 - ./data:/var/lib/mysql
 ports:
 - "3306:3306"-->local server data pprts to manipulate and retrieve data 
 networks:
 - bootapp

appservice:
build: ./boot
depends_on:
- dbservice
environment:
- spring.datasource.url=jdbc:mysql://dbservice:3306/personalized_data_api?createDatabaseIfNotExist=true
- spring.datasource.username=root
- spring.datasource.password=Rockstone@123$
- spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
- spring.jpa.hibernate.ddl-auto=update
ports:
- "8081:8080" ->>appilication running port number
networks:
- bootapp

networks:
bootapp:-->stablising the connection between the two services one is mySql and second one is spring Boot Application

