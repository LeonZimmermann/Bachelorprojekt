# Bachelorprojekt

## Running the application in docker

1. Execute `./gradlew build -x test` to build the application
2. Execute `docker build --build-arg JAR_FILE=build/libs/\*.jar -t bachelorprojekt .`
to create the docker image
3. Execute `docker-compose up`

The application should now be running. The application includes a pgadmin docker container that
can be accessed from http://localhost:5050/, as well as a Swagger UI that can be accessed from
http://localhost:8080/swagger-ui/index.html. Check wither of the addresses to make sure that the application
is actually running.

## Postman

You can automatically import the API for the application with the following steps:
1. Start the application and open Postman
2. Go to Import > Link
3. Type http://localhost:8080/api-docs
4. Import the API

Once you imported the applications API, you can find it under the API tab and use the predefined
requests to interact with the application.

## Using the application

The application has two use-cases. On the one hand, instructors are able to supply an ontology
to the application, in order to generate a new database that corresponds to that ontology. The 
ontology, the DatabaseScheme and the SQL-Queries to generate the tables of the database can be saved
into the system, so that they can be loaded at a different time.

On the other hand, a student can generate an assignment for a specified DatabaseScheme to practice his
SQL abililities.

### As an instructor

Before a student can generate and solve assignments, the application needs to be supplied an
ontology. The application will process the ontology and create a database that corresponds to the
entities and relations in the ontology.

Endpoint: TODO

### As a student

Once a DatabaseScheme has been created and added to the application, a student can select
a specific DatabaseScheme and generate an assignment for this DatabaseScheme. The Assignment
includes a text that specifies what the students SQL should look like, and a valid SQL-Query
solution, which the students answer will be compared to.

First things first, the DBMS needs to be setup. 

## Further Development

Rebuild and restart application: `./gradlew clean build -x test && docker build 
--build-arg JAR_FILE=build/libs/\*.jar -t bachelorprojekt . && docker-compose up`
