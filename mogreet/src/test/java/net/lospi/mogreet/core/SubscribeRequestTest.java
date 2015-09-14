package net.lospi.mogreet.core;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class SubscribeRequestTest {
    private String statusCode;
    private String campaignId;
    private String number;

    @BeforeMethod
    public void setUp() {
        statusCode = "statusCode";
        number = "number";
        campaignId = "campaignId";
    }

    @Test
    public void getNumber() {
        SubscribeRequest underStudy = new SubscribeRequest(number, campaignId, statusCode);
        String result = underStudy.getNumber();
        assertThat(result, is(number));
    }

    @Test
    public void canToString() {
        SubscribeRequest underStudy = new SubscribeRequest(number, campaignId, statusCode);
        String result = underStudy.toString();
        assertThat(result, is(notNullValue()));
    }

    @Test
    public void getCampaignId() {
        SubscribeRequest underStudy = new SubscribeRequest(number, campaignId, statusCode);
        String result = underStudy.getCampaignId();
        assertThat(result, is(campaignId));
    }

    @Test
    public void getStatusCode() {
        SubscribeRequest underStudy = new SubscribeRequest(number, campaignId, statusCode);
        String result = underStudy.getStatusCode();
        assertThat(result, is(statusCode));
    }
}