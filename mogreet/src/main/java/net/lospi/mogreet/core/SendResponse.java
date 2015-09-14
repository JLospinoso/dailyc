package net.lospi.mogreet.core;

public class SendResponse {
    private final boolean successful;
    private final String statusResponse;
    private final String messageResponse;
    private final int messageIdResponse;
    private final String hashResponse;
    private final String xmlResponse;

    public SendResponse(boolean successful, String statusResponse, String messageResponse,
                        int messageIdResponse, String hashResponse, String xmlResponse) {

        this.successful = successful;
        this.statusResponse = statusResponse;
        this.messageResponse = messageResponse;
        this.messageIdResponse = messageIdResponse;
        this.hashResponse = hashResponse;
        this.xmlResponse = xmlResponse;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public String getStatusResponse() {
        return statusResponse;
    }

    public String getMessageResponse() {
        return messageResponse;
    }

    public int getMessageIdResponse() {
        return messageIdResponse;
    }

    public String getHashResponse() {
        return hashResponse;
    }

    public String getXmlResponse() {
        return xmlResponse;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SendResponse{");
        sb.append("successful=").append(successful);
        sb.append(", statusResponse='").append(statusResponse).append('\'');
        sb.append(", messageResponse='").append(messageResponse).append('\'');
        sb.append(", messageIdResponse=").append(messageIdResponse);
        sb.append(", hashResponse='").append(hashResponse).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
