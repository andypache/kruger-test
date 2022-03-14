# Vaccination

This application was generated using spring initializr, you can find documentation and help at [https://start.spring.io/](https://start.spring.io/).

## Project Structure

Openjdk 17 is required for generation and recommended for development. `pom.xml` is always generated for management project dependencies.

`/docker/*` for create environment production.

`/Dockerfile/*` for create java spring environment production.

`/kruger-vaccination.postman_collection/*` for example of how to consume the apis

`/src/*` structure follows default Java structure.

- `.java` - Sources

- - `.vaccination` - Root folder for the project
- - - `.config`    - Contains all configuration and the properties
- - - `.domain`    - Contains all class for tables representation database
- - - `.error`     -  Utilities error personalized
- - - `.interceptor` - Handle error personalized
- - - `.repository` - Contains all repositories for database communication
- - - `.service`    - Contains interfaces and implementations with all the business logic needed by the system
- - - `.utility`    - General utilities 
- - - `.web`        - Contains all routes exposing REST services

- - `.resources` - Configuration yml file
- - - `.config` - Properties application
- - - `.messages` - Internationalization of messages

### OAuth 2.0

The security settings in `src/main/resources/config/application.yml` are configured for this image.

```yaml
security:
  oauth2:
    resource:
      token-info-uri: http://localhost:9080/vaccination/oauth/check_token
      userInfoUri: http://localhost:9080/vaccination/user/current
      token-type: Bearer
    authorization:
      check-token-access: isAuthenticated()
    client:
      clientId: vaccination-wepapp
      clientSecret: v@cc1n@t1onWep@pp2022..*
      accessTokenUri: http://localhost:9080/vaccination/oauth/token
      userAuthorizationUri: http://localhost:9080/vaccination/oauth/authorize
```
In the same application exist client and server OAuth 2.0

## Development

Before you can build this project, you must install and configure the following dependencies on your machine:

1. [openjdk17][]: We use Java to run a development web server and build the project.
2. [postgresql][]: We use database for persist information.
3. [user database][]: Create role usr_vac and the password Uv@cc1n@t10n...
4. [database][]: Create database vaccination.
5. [grant database][]: Grant permissions to the user in the database.
6. [url database][]: If necessary change the ip of the database.

After verify the file properties

```yaml
spring:
  devtools:
    restart:
      enabled: true
  jackson:
    serialization:
      indent-output: true
  datasource:
    type: com.zaxxer.hikari.HikariDataSource
    url: jdbc:postgresql://localhost:5432/vaccination
    username: usr_vac
    password: Uv@cc1n@t10n..
    hikari:
      poolName: Hikari
      auto-commit: true
```
The project considers the migration of data, so liquibase is used internally, it creates the tables and the information necessary to start the application, when it is executed for the first time a user will be created for the administration and entry of the apis

If you open project in IDE don't forget to activate the profile in the configuration files

For probe into this environment run the next command from root folder [/vaccination](/vaccination)

To facilitate and automate the deployment of the application, we have created an orchestration with docker-compose, it raises two containers that represent a postgres database and the application.

1. For create and verify **.jar

```shell
./mvnw -N io.takari:maven:wrapper install -Dspring.profiles.active=dev -DskipTests
```

2. For run java **.jar in development

```shell
java -Dspring.profiles.active="dev" -jar target/vaccination-0.0.1-SNAPSHOT.jar
```

## Production using docker to simplify deploy

To facilitate and automate the deployment of the application, we have created an orchestration with docker-compose, it raises two containers that represent a postgres database and the application.

It should be taken into account that it is not necessary to install the database, but you may have problems with ports 5432 in case the host machine has a postgres engine running, if this is the case, inactivate it.

For create docker deploy run the next command from root folder [/vaccination](/vaccination)

1. For to start a postgresql database and application, run:

```
docker-compose --file docker/docker-compose.yml up -d --build --scale vaccination-server=1
```

2. To stop it and remove the container, run:

```
docker-compose --file docker/docker-compose.yml down
```

3. To start and create the container, run:

```
docker-compose --file docker/docker-compose.yml up -d
```