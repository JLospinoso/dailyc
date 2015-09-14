package net.lospi.mogreet.core;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class UploadResponseTest {
    private String nameResponse;
    private boolean successful;
    private String statusResponse;
    private String contentIdResponse;
    private String smartUrlResponse;
    private String messageResponse;
    private String xmlResponse;

    @BeforeMethod
    public void setUp() {
        nameResponse = "nameResponse";
        statusResponse = "statusResponse";
        smartUrlResponse = "smartUrlResponse";
        messageResponse = "messageResponse";
        contentIdResponse = "contentIdResponse";
        xmlResponse = "xmlResponse";
        successful = true;
    }

    @Test
    public void getSmartUrlResponse() {
        UploadResponse underStudy = new UploadResponse(successful, statusResponse, messageResponse, nameResponse, contentIdResponse, smartUrlResponse, xmlResponse);
        String result = underStudy.getSmartUrlResponse();
        assertThat(result, is(smartUrlResponse));
    }

    @Test
    public void getXmlResponse() {
        UploadResponse underStudy = new UploadResponse(successful, statusResponse, messageResponse, nameResponse, contentIdResponse, smartUrlResponse, xmlResponse);
        String result = underStudy.getXmlResponse();
        assertThat(result, is(xmlResponse));
    }

    @Test
    public void canToString() {
        UploadResponse underStudy = new UploadResponse(successful, statusResponse, messageResponse, nameResponse, contentIdResponse, smartUrlResponse, xmlResponse);
        String result = underStudy.toString();
        assertThat(result, is(notNullValue()));
    }

    @Test
    public void getNameResponse() {
        UploadResponse underStudy = new UploadResponse(successful, statusResponse, messageResponse, nameResponse, contentIdResponse, smartUrlResponse, xmlResponse);
        String result = underStudy.getNameResponse();
        assertThat(result, is(nameResponse));
    }

    @Test
    public void getMessageResponse() {
        UploadResponse underStudy = new UploadResponse(successful, statusResponse, messageResponse, nameResponse, contentIdResponse, smartUrlResponse, xmlResponse);
        String result = underStudy.getMessageResponse();
        assertThat(result, is(messageResponse));
    }

    @Test
    public void getContentIdResponse() {
        UploadResponse underStudy = new UploadResponse(successful, statusResponse, messageResponse, nameResponse, contentIdResponse, smartUrlResponse, xmlResponse);
        String result = underStudy.getContentIdResponse();
        assertThat(result, is(contentIdResponse));
    }

    @Test
    public void getStatusResponse() {
        UploadResponse underStudy = new UploadResponse(successful, statusResponse, messageResponse, nameResponse, contentIdResponse, smartUrlResponse, xmlResponse);
        String result = underStudy.getStatusResponse();
        assertThat(result, is(statusResponse));
    }

    @Test
    public void isSuccessful() {
        UploadResponse underStudy = new UploadResponse(successful, statusResponse, messageResponse, nameResponse, contentIdResponse, smartUrlResponse, xmlResponse);
        boolean result = underStudy.isSuccessful();
        assertThat(result, is(successful));
    }
}
