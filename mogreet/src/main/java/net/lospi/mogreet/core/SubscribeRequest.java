package net.lospi.mogreet.core;

public class SubscribeRequest {
    private final String number;
    private final String campaignId;
    private final String statusCode;

    public SubscribeRequest(String number, String campaignId, String statusCode) {
        this.number = number;
        this.campaignId = campaignId;
        this.statusCode = statusCode;
    }

    public String getNumber() {
        return number;
    }

    public String getCampaignId() {
        return campaignId;
    }

    public String getStatusCode() {
        return statusCode;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SubscribeRequest{");
        sb.append("number='").append(number).append('\'');
        sb.append(", campaignId='").append(campaignId).append('\'');
        sb.append(", statusCode='").append(statusCode).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
