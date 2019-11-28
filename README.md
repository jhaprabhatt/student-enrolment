### About
A basic Spring boot application to showcase different HTTP methods usage for Student Enrolment

### Requirements for Execution

- Maven
- Java 8
- Docker

### Details
- Multi maven module for modularization 
- Swagger for API documentation
- Gateway, Config Server etc hasnt been used because we only have one service
- Spring Data JPA using H2
- Basic cucumber for BDD
- The module has been tested on Windows 10 machine

### TODO
- Cloud Setup and Containerization (AWS/Kubernetes)

## Running Application as Docker Image on Local Machine
Run run.sh. It will download the code and build it locally. A docker image is started automatically

```shell script
curl -o run.sh 'https://raw.githubusercontent.com/jhaprabhatt/student-enrolment/master/run.sh'
chmod +x run.sh
./run.sh
```

## Swagger API Documentation

http://localhost:8080/swagger-ui.html


