package com.github.lidj22;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.messaging.support.MessageBuilder;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;

import io.awspring.cloud.messaging.core.QueueMessagingTemplate;
import io.awspring.cloud.messaging.listener.annotation.SqsListener;

@SpringBootApplication
public class Application 
{

    private final AmazonSQSAsync amazonSQSAsync = AmazonSQSAsyncClientBuilder.standard().build();
    private final QueueMessagingTemplate queueMessagingTemplate = new QueueMessagingTemplate(amazonSQSAsync);

    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

    @Autowired
    private Environment environment;

    /**
     * Relay the message received from the SQS request queue and send it to the response queue.
     * @param messageBody
     */
    private void processMessage(String messageBody) {
        LOGGER.info("Received message: {}", messageBody);
        queueMessagingTemplate.send(environment.getProperty("response.queue.url"), MessageBuilder.withPayload(messageBody).build());
        LOGGER.info("Sent message: {}", messageBody);
    }

    @SqsListener(value = "${request.queue.url}")
    public void queueListener(String messageBody) {
        processMessage(messageBody);
    }

    public static void main( String[] args ) {
        SpringApplication.run(Application.class, args); 
    }

}
