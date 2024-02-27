package com.quarkus.developers.services.messaging.jms;

public class MessagingReceiverException extends RuntimeException {
    public MessagingReceiverException() {
    }

    public MessagingReceiverException(String message) {
        super(message);
    }

    public MessagingReceiverException(String message, Throwable cause) {
        super(message, cause);
    }

    public MessagingReceiverException(Throwable cause) {
        super(cause);
    }

    public MessagingReceiverException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
