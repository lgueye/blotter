Blotter project

Rationale
===

The project has several goals, among which:

- challenge design choices
- be a baseline for future discussions
- act as a proof of concept
- quickly bootstrap the project


Features
===

- Ability to ingest messages from JMS sources (target sources might be http)
    - messages structures need to be known: they act as a contract from the source
    - different messages can share the same queue. they are de-serialized then categorized by the processors

- Ability to normalize messages
    - messages are normalized in a common structure
    - their details are serialized as string in a details field

- Ability to persist messages
    - normalized messages are persisted in a mysql db
    - relevant fields are indexed for search

- Ability to notify
    - notification messages produced from normalized message then sent to a kafka queue

- Ability to search through the API
    - a very naive search has been implemented
    - kafka groups are not yet supported

Missing Features
===

- ingestion
    - change jms by http
    - de-serialize xml messages
    - ingest real messages

- normalization
    - investigate final form of messages
    - normalize details (FX from AVQ will not share the same model as FX from SMT)

- persistence
    - support complex types (BigDecimal, etc)
    - review indexes
    - investigate final DDL

- notification
    - support kafka groups 

- API
    - design API
    - create results model
    - test API

- Cross cutting
    - error management
    - log strategy
    - unit tests
    - http tests (server, client)
    - persistence tests
    - TLS support
    
Design
===

The design was solely driven by scenarios following the design for testability principle.

The E2E project is the entry point for anything you might ask yourself (what is the purpose of this). the idea is to follow the scenarios

- e2e: uses cucumber, cucumber for spring and cucumber for java8
- build-tools: a little project to reformat java source files during the build

- ingestion: to simulate message production via JMS an activemq broker is launched during e2e. It is encapsulated in a spring-boot app to leverage all the benefit associated to spring factory lifecycle management: the broker will gracefully stop at the end of the tests
    - producer: the e2e project is a message producer
    - consumer: the api-server is a message consumer
    - model: shared by both e2e and api-server (it's the sources contract).

- notification: to simulate message notifications a kafka server (see [EmbeddedKafkaBroker](https://github.com/spring-projects/spring-kafka/blob/master/spring-kafka-test/src/main/java/org/springframework/kafka/test/EmbeddedKafkaBroker.java)) is launched during e2e. It is encapsulated in a spring-boot app to leverage all the benefit associated to spring factory lifecycle management: the server will gracefully stop at the end of the tests
    - producer: the api-server is a notification producer
    - consumer: the e2e is a notification consumer
    - model: shared by both e2e and api-server (it's the notification contract).
        
- persistence: to simulate DB interaction an embedded mysql server (see [wix-embedded-mysql](https://github.com/wix/wix-embedded-mysql)) is launched during e2e.
    - client: the api-server is a db client
    - migrations: the e2e uses the migrations to simulate DB changes (no automatic generation, written by hand on purpose)

- api-server: main deliverable
    - consumer: the e2e is an API consumer (searches by criteria)
    - model: used by store client and e2e
    - `visitor` pattern: used to decouple various message structures from main processing
    - `stateless processors` for high testability and decoupling the `read -> process -> write -> notify` sequence :
        - any data required for the processing is fetched before the processing
        - makes very apparent what is produced in the context of this processing 

Run
===

The project is self contained to minimize the interactions with other teams

- build: `mvn clean install`
