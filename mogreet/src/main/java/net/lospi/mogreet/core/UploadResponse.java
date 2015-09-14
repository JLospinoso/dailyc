package net.lospi.mogreet.core;

public class UploadResponse {
    private final String nameResponse;
    private final String contentIdResponse;
    private final String smartUrlResponse;
    private final String xmlResponse;
    private final boolean successful;
    private final String statusResponse;
    private final String messageResponse;

    public UploadResponse(boolean successful, String statusResponse, String messageResponse,
                          String nameResponse, String contentIdResponse,
                          String smartUrlResponse, String xmlResponse) {
        this.successful = successful;
        this.statusResponse = statusResponse;
        this.messageResponse = messageResponse;
        this.nameResponse = nameResponse;
        this.contentIdResponse = contentIdResponse;
        this.smartUrlResponse = smartUrlResponse;
        this.xmlResponse = xmlResponse;
    }

    public String getMessageResponse() {
        return messageResponse;
    }

    public String getXmlResponse() {
        return xmlResponse;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public String getStatusResponse() {
        return statusResponse;
    }

    public String getNameResponse() {
        return nameResponse;
    }

    public String getContentIdResponse() {
        return contentIdResponse;
    }

    public String getSmartUrlResponse() {
        return smartUrlResponse;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UploadResponse{");
        sb.append("nameResponse='").append(nameResponse).append('\'');
        sb.append(", contentIdResponse='").append(contentIdResponse).append('\'');
        sb.append(", smartUrlResponse='").append(smartUrlResponse).append('\'');
        sb.append(", successful=").append(successful);
        sb.append(", statusResponse='").append(statusResponse).append('\'');
        sb.append(", messageResponse='").append(messageResponse).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
