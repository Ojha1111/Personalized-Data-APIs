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
////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////
database Design
+---------------------+       +------------------+       +---------------+
|   Product Metadata  |       |    Shelf Item    |       |    Shopper    |
|       Table         |       |      Table       |       |     Table     |
+---------------------+       +------------------+       +---------------+
| productId (PK)      | 1----1| id (PK)          |       | shopperId (PK)|
| category            |       | productId        |-------|               |
| brand               |       | relevancyScore   |       |               |
|                     |       | product_metadata_id(FK)| |               |
+---------------------+       +------------------+       +---------------+
|                        |                           |
|                        |                           |
|                        |                           |
|                        |                           |
|                        |                           |
|                        |                           |
v                        v                           v
+---------------------+ 1---*   +---------------------+      +------------------+
| ProductMetadataDTO |         |    ShelfItemDTO     |      |   ShopperDTO     |
|     (Data Transfer  |         |   (Data Transfer    |      |  (Data Transfer  |
|        Object)      |         |        Object)       |      |      Object)     |
+---------------------+         +---------------------+      +------------------+
|                          |                             |
|                          |                             |
|                          |                             |
|                          |                             |
|                          |                             |
|                          |                             |
v                          v                             v
+----------------------+     +----------------------+     +----------------------+
| ProductMetadataService|     |    ShelfItemService   |     |    ShopperService    |
|      (Service Layer)  |     |   (Service Layer)     |     |   (Service Layer)    |
+----------------------+     +----------------------+     +----------------------+
|                          |                             |
|                          |                             |
|                          |                             |
|                          |                             |
|                          |                             |
|                          |                             |
v                          v                             v
+-----------------------+     +------------------------+    +-----------------------+
| ProductMetadataRepository|  |  ShelfItemRepository    |  |   ShopperRepository    |
|     (Data Access Layer)  |  |   (Data Access Layer)   |  |   (Data Access Layer)  |
+-------------------------+  +------------------------+  +------------------------+

Tables represent the database tables (product_metadata, shelf_item, shopper) along with their respective fields.
DTOs represent Data Transfer Objects used to transfer data between layers of the application.
Services represent the service layer responsible for encapsulating business logic.
Repositories represent the data access layer responsible for interacting with the database.
The diagram illustrates how data flows between different layers of your Spring Boot application, starting from the database tables, passing through DTOs, services, and repositories, ultimately serving the HTTP requests handled by your controllers.


Workflow:
Storing Shopper Data:

Controller receives data, validates it, delegates to service, service converts DTOs to entities, saves to database.
Retrieving Shopper Data:

Controller receives request, extracts shopperId, delegates to service, service retrieves data from database, converts entities to DTOs, returns response.
Product Metadata Operations:

Similar workflow applies to endpoints related to product metadata.
Fetching Products by Shopper with Filters:

Controller receives request, extracts parameters, delegates to service, service retrieves filtered products from database, returns response.
This structure ensures that your Spring Boot application interacts effectively with the database, maintaining data integrity and supporting the required operations efficiently.
Entity Structure:
ProductMetadataEntity:

Fields:
productId (String, Primary Key)
category (String)
brand (String)
ShelfItemEntity:

Fields:
id (Long, Primary Key, Auto-generated)
productId (String)
relevancyScore (double)
productMetadata (One-to-One relationship with ProductMetadataEntity)
ShopperEntity:

Fields:
shopperId (String, Primary Key)
shelf (One-to-Many relationship with ShelfItemEntity)
Relationships:
One-to-One Relationship:

ShelfItemEntity.productMetadata -> ProductMetadataEntity (via productMetadata_id foreign key)
One-to-Many Relationship:

ShopperEntity.shelf -> List of ShelfItemEntity (via shopper_id foreign key in ShelfItemEntity)
Database Tables:
product_metadata:

Columns: productId (Primary Key), category, brand
shelf_item:

Columns: id (Primary Key), productId, relevancyScore, product_metadata_id (Foreign Key referencing product_metadata.productId)
shopper:

Columns: shopperId (Primary Key)
Utilization in Spring Boot:
Repositories:



ProductMetadataRepository: CRUD operations for product metadata.
ShelfItemRepository: CRUD operations for shelf items.
ShopperRepository: CRUD operations for shoppers.
Services:

ProductMetadataService: Business logic related to product metadata.
ShopperService: Business logic related to shoppers and their shelf items.
Controllers:

ShopperController: Handles HTTP requests related to shoppers, interacts with ShopperService.
DTOs:

ProductMetadataDTO: Data transfer object for product metadata.
ShelfItemDTO: Data transfer object for shelf items.
ShopperDTO: Data transfer object for shoppers.