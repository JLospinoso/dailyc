package net.lospi.mogreet.core;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class SendResponseTest {
    private boolean successful;
    private String statusResponse;
    private int messageIdResponse;
    private String messageResponse;
    private String hashResponse;
    private String xmlResponse;

    @BeforeMethod
    public void setUp() {
        hashResponse = "hashResponse";
        statusResponse = "statusResponse";
        messageIdResponse = 12345;
        messageResponse = "messageResponse";
        xmlResponse = "xmlResponse";
        successful = true;
    }

    @Test
    public void getMessageIdResponse() {
        SendResponse underStudy = new SendResponse(successful, statusResponse, messageResponse, messageIdResponse, hashResponse, xmlResponse);
        int result = underStudy.getMessageIdResponse();
        assertThat(result, is(messageIdResponse));
    }

    @Test
    public void getXmlResponse() {
        SendResponse underStudy = new SendResponse(successful, statusResponse, messageResponse, messageIdResponse, hashResponse, xmlResponse);
        String result = underStudy.getXmlResponse();
        assertThat(result, is(xmlResponse));
    }

    @Test
    public void isSuccessful() {
        SendResponse underStudy = new SendResponse(successful, statusResponse, messageResponse, messageIdResponse, hashResponse, xmlResponse);
        boolean result = underStudy.isSuccessful();
        assertThat(result, is(successful));
    }

    @Test
    public void canToString() {
        SendResponse underStudy = new SendResponse(successful, statusResponse, messageResponse, messageIdResponse, hashResponse, xmlResponse);
        String result = underStudy.toString();
        assertThat(result, is(notNullValue()));
    }

    @Test
    public void getHashResponse() {
        SendResponse underStudy = new SendResponse(successful, statusResponse, messageResponse, messageIdResponse, hashResponse, xmlResponse);
        String result = underStudy.getHashResponse();
        assertThat(result, is(hashResponse));
    }

    @Test
    public void getMessageResponse() {
        SendResponse underStudy = new SendResponse(successful, statusResponse, messageResponse, messageIdResponse, hashResponse, xmlResponse);
        String result = underStudy.getMessageResponse();
        assertThat(result, is(messageResponse));
    }

    @Test
    public void getStatusResponse() {
        SendResponse underStudy = new SendResponse(successful, statusResponse, messageResponse, messageIdResponse, hashResponse, xmlResponse);
        String result = underStudy.getStatusResponse();
        assertThat(result, is(statusResponse));
    }
}
