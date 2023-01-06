# Bachelorprojekt

## Running the application in docker

1. Execute `docker build --build-arg JAR_FILE=build/libs/\*.jar -t bachelorprojekt .`
to create the docker image
2. Execute `docker-compose up`

## Using the application

### Generate a new DatabaseScheme

Before a student can generate and solve assignments, the application needs to be supplied an
ontology. The application will process the ontology and create a database that corresponds to the
entities and the relations in the ontology.

Endpoint: TODO

## Swagger

TODO

## Postman

TODO

## Running tests

TODO
