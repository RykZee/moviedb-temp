# moviedb-temp

## Requirements
- Java (I used version 17)
- Maven
- Docker

## Installation/Setup

Make sure you're standing in the root of the project when running these commands:
`docker pull mysql:latest`

`docker run --rm --name moviedb -v ./src/main/resources:/docker-entrypoint-initdb.d -e MYSQL_ROOT_PASSWORD=password123 -p 3306:3306 -d mysql:latest`

This sets the MySQL database up to run in a docker container listening on port 3306 and it will also initialize the database with test data.
Run the spring boot application and check your browser `localhost:9090/moviedb/movies`

In order to create movies or actors, you can use something like postman or a simple curl command for instance:
`curl -X POST localhost:9090/moviedb/movies -H 'Content-Type: application/json' -d '{"name":"Titanic","year":1997}' -i`
