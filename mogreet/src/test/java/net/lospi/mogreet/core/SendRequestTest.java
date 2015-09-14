package net.lospi.mogreet.core;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class SendRequestTest {
    private String from;
    private String message;
    private String contentId;
    private String campaignId;
    private String to;

    @BeforeMethod
    public void setUp() {
        campaignId = "campaignId";
        to = "to";
        message = "message";
        from = "from";
        contentId = "contentId";
    }

    @Test
    public void getCampaignId() {
        SendRequest underStudy = new SendRequest(campaignId, to, from, message, contentId);
        String result = underStudy.getCampaignId();
        assertThat(result, is(campaignId));
    }

    @Test
    public void getContentId() {
        SendRequest underStudy = new SendRequest(campaignId, to, from, message, contentId);
        String result = underStudy.getContentId();
        assertThat(result, is(contentId));
    }

    @Test
    public void getFrom() {
        SendRequest underStudy = new SendRequest(campaignId, to, from, message, contentId);
        String result = underStudy.getFrom();
        assertThat(result, is(from));
    }

    @Test
    public void getTo() {
        SendRequest underStudy = new SendRequest(campaignId, to, from, message, contentId);
        String result = underStudy.getTo();
        assertThat(result, is(to));
    }

    @Test
    public void canToString() {
        SendRequest underStudy = new SendRequest(campaignId, to, from, message, contentId);
        String result = underStudy.toString();
        assertThat(result, is(notNullValue()));
    }

    @Test
    public void getMessage() {
        SendRequest underStudy = new SendRequest(campaignId, to, from, message, contentId);
        String result = underStudy.getMessage();
        assertThat(result, is(message));
    }
}
