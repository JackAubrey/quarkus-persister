package com.quarkus.developers.services.messaging.jms;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.event.Observes;
import jakarta.jms.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
abstract class AbstractJmsReceiver implements MessagingReceiver, Runnable {
    final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    ConnectionFactory connectionFactory;

    ObjectMapper objectMapper;

    @ConfigProperty(name = "receiver.jms.initial-delay.seconds", defaultValue = "0")
    long initialDelay;

    @ConfigProperty(name = "receiver.jms.delay.seconds", defaultValue = "5")
    long delay;

    void setConnectionFactory(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    void onStart(@Observes StartupEvent ev) {
        log.debug(" NOT WORKS ---->> Starting JMS scheduler with InitialDelay [{}] and Delay [{}]", initialDelay, delay);
        scheduler.scheduleWithFixedDelay(this, initialDelay, delay, TimeUnit.SECONDS);
    }

    void onStop(@Observes ShutdownEvent ev) {
        log.debug(" NOT WORKS ---->> Gracefully shutdown JMS scheduler");
        scheduler.shutdown();
    }

    @Override
    public void run() {
        final String queueName;

        if(StringUtils.isBlank(getQueueName())) {
            log.warn(" NOT WORKS ---->> No QueueName set! Will be used the default one: {}", DEFAULT_QUEUE_NAME);
            queueName = DEFAULT_QUEUE_NAME;
        } else {
            queueName = getQueueName().trim();
        }

        log.info(" NOT WORKS ---->> Start listening queue {}", queueName);

        try (JMSContext context = connectionFactory.createContext(Session.AUTO_ACKNOWLEDGE)) {
            log.debug("Creating consumer of queue {}", queueName);
            JMSConsumer consumer = context.createConsumer(context.createQueue(queueName));

            while (true) {
                log.debug("Going to consume queue {}", queueName);
                Message message = consumer.receive();

                if (message == null) {
                    return;
                } else {
                    onMessage(message);
                }
            }
        } catch (JMSException e) {
            log.error("Error occurred during sending message {}", e.getLocalizedMessage(), e);
            throw new MessagingReceiverException(e);
        } catch (JMSRuntimeException e) {
            log.error("Error occurred during connection {}", e.getLocalizedMessage(), e);
            throw e;
        }
    }
}
