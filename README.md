# Mizudo-app
Aplicação para gerenciamento de membros da associação Mizu-do de Karatê.

Aqui a playlist no Youtube onde estamos desenvolvendo o projeto:

<a href="http://www.youtube.com/watch?feature=player_embedded&v=eHrogm7-vsA&list=PLkN-PjC-cnJdad9OYRrRJBUnKedOSxbG1" target="_blank"><img src="https://img.youtube.com/vi/eHrogm7-vsA/0.jpg" width="240" height="180" border="10" /></a>


<a href="https://www.youtube.com/watch?v=eHrogm7-vsA&list=PLkN-PjC-cnJdad9OYRrRJBUnKedOSxbG1" target="_blank">https://www.youtube.com/watch?v=eHrogm7-vsA&list=PLkN-PjC-cnJdad9OYRrRJBUnKedOSxbG1</a>
  
## Tecnologias utilizadas:
 - Java 17
 - Quarkus
  - JAX-RS
  - Autenticação com JWT
  - JPA
 - Angular 13
 - Ionic v6  
 - PostgreSQL

## Hospedagem
  - Heroku

## Casos de Uso da aplicação

  ![Caso de Uso](https://www.plantuml.com/plantuml/proxy?cache=no&src=https://github.com/arrudalabs/mizudo-app/raw/main/usecases.puml)

## Detalhes da aplicação

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Packaging and running the application

The application can be packaged using:
```shell script
./mvnw package
```
It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:
```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using:
```shell script
./mvnw package -Pnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using:
```shell script
./mvnw package -Pnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/mizudo-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.

## Provided Code

### RESTEasy Reactive

Easily start your Reactive RESTful Web Services

[Related guide section...](https://quarkus.io/guides/getting-started-reactive#reactive-jax-rs-resources)
