# Spring SQS Listener (JDK 11)

A simple Spring Boot application that runs an AWS SQS request/response consumer, with JDK 11. The Application consumes from a request queue, and relays those messages to a response queue.

**Requirements**: JDK 11, Maven 3.

Set up appropriate AWS configurations with `aws configure` if necessary.

Install the application.
```sh
mvn clean install
```

Configure request and response queue environment variables in `REQUEST_QUEUE_URL` and `RESPONSE_QUEUE_URL` respectively.

Run the application:
```sh
java -jar target/spring-sqs-listener-*.jar
```
