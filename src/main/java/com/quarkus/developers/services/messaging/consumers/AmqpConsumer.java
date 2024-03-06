package com.quarkus.developers.services.messaging.consumers;

import com.quarkus.common.data.events.ClusterResourceEvent;
import com.quarkus.developers.services.persistance.PersistService;
import io.quarkus.arc.properties.IfBuildProperty;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Acknowledgment;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;

import java.util.concurrent.CompletionStage;

@Slf4j
@ApplicationScoped
@IfBuildProperty(name = "messaging.consume-from.amqp", stringValue = "true")
public class AmqpConsumer {
    private final PersistService service;

    public AmqpConsumer(PersistService service) {
        this.service = service;
    }

    @Incoming("amqpAddress")
    @Acknowledgment(Acknowledgment.Strategy.POST_PROCESSING)
    CompletionStage<Void> consume(Message<JsonObject> message) {
        log.info("AMQP || Received message with payload {}", message.getPayload());
        service.persist(message.getPayload().mapTo(ClusterResourceEvent.class));
        return message.ack();
    }
}
