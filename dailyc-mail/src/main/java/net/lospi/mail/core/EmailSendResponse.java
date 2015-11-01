package net.lospi.mail.core;

public class EmailSendResponse {
    private final String messageResponse;
    private final boolean successful;

    public EmailSendResponse(String messageResponse, boolean successful) {
        this.messageResponse = messageResponse;
        this.successful = successful;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public String getMessageResponse() {
        return messageResponse;
    }
}
