package net.lospi.mogreet.core;

public class SendRequest {
    private final String campaignId, to, from, message, contentId;

    public SendRequest(String campaignId, String to, String from, String message, String contentId) {
        this.campaignId = campaignId;
        this.to = to;
        this.from = from;
        this.message = message;
        this.contentId = contentId;
    }

    public String getCampaignId() {
        return campaignId;
    }

    public String getTo() {
        return to;
    }

    public String getFrom() {
        return from;
    }

    public String getMessage() {
        return message;
    }

    public String getContentId() {
        return contentId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SendRequest{");
        sb.append("campaignId='").append(campaignId).append('\'');
        sb.append(", to='").append(to).append('\'');
        sb.append(", from='").append(from).append('\'');
        sb.append(", message='").append(message).append('\'');
        sb.append(", contentId='").append(contentId).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
