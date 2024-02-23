package com.quarkus.developers.services.messaging.jms;

//@Slf4j
//@ApplicationScoped
public class RequestedQuotasJmsListenerNotWorks /*extends AbstractJmsReceiver*/ {
    /*private final ObjectMapper objectMapper;
    @ConfigProperty(name = "receivers.jms.queues.request-quota", defaultValue = DEFAULT_QUEUE_NAME)
    private String queueName;

    public RequestedQuotasJmsListener(ConnectionFactory connectionFactory, ObjectMapper objectMapper) {
        super(connectionFactory);
        this.objectMapper = objectMapper;
    }

    @Override
    public void onMessage(Message message) throws JMSException {
        String body = message.getBody(String.class);
        try {
            ResourceQuotaInfo event = objectMapper.readValue(body, ResourceQuotaInfo.class);
            log.info("Received event {}", event);
        } catch (JsonProcessingException e) {
            throw new JMSException("The received message is not a ResourceQuotaInfo ", "500", e);
        }
    }

    @Override
    public String getQueueName() {
        return queueName;
    }*/
}
