package com.quarkus.developers.services.messaging.jms;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quarkus.developers.services.messaging.events.ResourceQuotaInfo;
import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.jms.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@ApplicationScoped
@RequiredArgsConstructor
public class RequestedQuotasJmsListener implements Runnable {
    static final String DEFAULT_QUEUE_NAME = "my_default_queue";

    final ConnectionFactory connectionFactory;
    final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private final ObjectMapper objectMapper;

    @ConfigProperty(name = "receivers.jms.queues.request-quota", defaultValue = DEFAULT_QUEUE_NAME)
    private String queueName;

    @ConfigProperty(name = "receiver.jms.initial-delay.seconds", defaultValue = "0")
    long initialDelay;

    @ConfigProperty(name = "receiver.jms.delay.seconds", defaultValue = "5")
    long delay;

    void onStart(@Observes StartupEvent ev) {
        log.debug("Starting JMS scheduler with InitialDelay [{}] and Delay [{}]", initialDelay, delay);
        scheduler.scheduleWithFixedDelay(this, initialDelay, delay, TimeUnit.SECONDS);
    }

    void onStop(@Observes ShutdownEvent ev) {
        log.debug("Gracefully shutdown JMS scheduler");
        scheduler.shutdown();
    }

    @Override
    public void run() {
        final String queueName;

        if(StringUtils.isBlank(getQueueName())) {
            log.warn("No QueueName set! Will be used the default one: {}", DEFAULT_QUEUE_NAME);
            queueName = DEFAULT_QUEUE_NAME;
        } else {
            queueName = getQueueName().trim();
        }

        log.info("Start listening queue {}", queueName);

        try (JMSContext context = connectionFactory.createContext(JMSContext.AUTO_ACKNOWLEDGE)) {
            JMSConsumer consumer = context.createConsumer(context.createQueue(queueName));
            while (true) {
                Message message = consumer.receive();

                if (message == null) {
                    log.info("No other messages found");
                    return;
                } else {
                    onMessage(message);
                }
            }
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }

    public void onMessage(Message message) throws JMSException {
        String body = message.getBody(String.class);
        try {
            ResourceQuotaInfo event = objectMapper.readValue(body, ResourceQuotaInfo.class);
            log.info("Received event {}", event);
        } catch (JsonProcessingException e) {
            throw new JMSException("The received message is not a ResourceQuotaInfo ", "500", e);
        }
    }

    String getQueueName() {
        return queueName;
    }
}
