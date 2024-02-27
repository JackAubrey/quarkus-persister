package com.quarkus.developers.services.messaging.jms;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quarkus.developers.entities.ResourceQuotaInfoEntity;
import com.quarkus.developers.mappers.ResourceQuotaMapper;
import com.quarkus.developers.repositories.ResourceQuotaInfoRepo;
import com.quarkus.developers.services.messaging.events.ResourceQuotaInfoEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@Slf4j
@ApplicationScoped
public class RequestedQuotasJmsListener extends AbstractJmsReceiver {
    @ConfigProperty(name = "receivers.jms.queues.request-quota", defaultValue = DEFAULT_QUEUE_NAME)
    private String queueName;

    final ResourceQuotaInfoRepo resourceQuotaInfoRepo;
    final ResourceQuotaMapper mapper;

    @Inject
    public RequestedQuotasJmsListener(
            ConnectionFactory connectionFactory,
            ObjectMapper objectMapper,
            ResourceQuotaInfoRepo resourceQuotaInfoRepo,
            ResourceQuotaMapper mapper) {
        this.setConnectionFactory(connectionFactory);
        this.setObjectMapper(objectMapper);
        this.resourceQuotaInfoRepo = resourceQuotaInfoRepo;
        this.mapper = mapper;
    }

    @Transactional
    @Override
    public void onMessage(Message message) throws JMSException {
        String body = message.getBody(String.class);
        try {
            ResourceQuotaInfoEvent event = objectMapper.readValue(body, ResourceQuotaInfoEvent.class);
            log.info(" NOT WORKS ---->> Received event {}", event);
            ResourceQuotaInfoEntity entity = mapper.eventToEntity(event);
            resourceQuotaInfoRepo.persist(entity);
            log.info(" NOT WORKS ---->> entity persisted {}", entity);
        } catch (JsonProcessingException e) {
            throw new JMSException("The received message is not a ResourceQuotaInfo ", "500", e);
        } catch (Exception e) {
            log.error("An error occurred: {}", e.getLocalizedMessage(), e);
            throw new JMSException("Generic error ", "500", e);
        }
    }

    @Override
    public String getQueueName() {
        return queueName;
    }
}
