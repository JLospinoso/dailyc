package net.lospi.mail.core;

import net.lospi.mail.core.EmailSendResponse;

public class EmailSendResponseFactory {
    public EmailSendResponse createSuccessfulResponse(String message) {
        return new EmailSendResponse(message, true);
    }

    public EmailSendResponse createUnuccessfulResponse(String message) {
        return new EmailSendResponse(message, false);
    }
}
