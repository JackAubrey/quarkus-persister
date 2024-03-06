package com.quarkus.developers.services.messaging.consumers;

import com.quarkus.common.data.events.ClusterResourceEvent;
import com.quarkus.developers.services.persistance.PersistService;
import io.quarkus.arc.properties.IfBuildProperty;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;

import java.util.concurrent.CompletionStage;

@Slf4j
@ApplicationScoped
@IfBuildProperty(name = "messaging.consume-from.jms", stringValue = "true")
public class JmsConsumer {
    private final PersistService service;

    public JmsConsumer(PersistService service) {
        this.service = service;
    }

    @Incoming("jmsQueue")
    CompletionStage<Void> consume(Message<ClusterResourceEvent> message) {
        log.info("JMS || Received message with payload {}", message.getPayload());
        service.persist(message.getPayload());
        return message.ack();
    }
}
