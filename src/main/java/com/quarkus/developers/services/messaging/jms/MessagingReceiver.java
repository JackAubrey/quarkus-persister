package com.quarkus.developers.services.messaging.jms;

import jakarta.jms.JMSException;
import jakarta.jms.Message;

public interface MessagingReceiver {
    String DEFAULT_QUEUE_NAME = "my_default_queue";

    void onMessage(Message message) throws JMSException;

    String getQueueName();
}
