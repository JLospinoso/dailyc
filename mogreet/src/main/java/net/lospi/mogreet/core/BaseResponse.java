package net.lospi.mogreet.core;

public class BaseResponse {
    private final boolean successful;
    private final String statusResponse, messageResponse, xmlResponse;

    public BaseResponse(boolean successful,
                        String statusResponse,
                        String messageResponse,
                        String xmlResponse) {
        this.successful = successful;
        this.statusResponse = statusResponse;
        this.messageResponse = messageResponse;
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

    public String getXmlResponse() {
        return xmlResponse;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BaseResponse{");
        sb.append("successful=").append(successful);
        sb.append(", statusResponse='").append(statusResponse).append('\'');
        sb.append(", messageResponse='").append(messageResponse).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
