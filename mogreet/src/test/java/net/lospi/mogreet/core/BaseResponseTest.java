package net.lospi.mogreet.core;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class BaseResponseTest {
    private String message;
    private boolean successful;
    private String xmlResponse;
    private String responseStatus;

    @BeforeMethod
    public void setUp() {
        message = "message";
        responseStatus = "responseStatus";
        xmlResponse = "xmlResponse";
        successful = true;
    }

    @Test
    public void canToString() {
        BaseResponse underStudy = new BaseResponse(successful, responseStatus, message, xmlResponse);
        String result = underStudy.toString();
        assertThat(result, is(notNullValue()));
    }

    @Test
    public void isSuccessful() {
        BaseResponse underStudy = new BaseResponse(successful, responseStatus, message, xmlResponse);
        boolean result = underStudy.isSuccessful();
        assertThat(result, is(successful));
    }

    @Test
    public void getXmlResponse() {
        BaseResponse underStudy = new BaseResponse(successful, responseStatus, message, xmlResponse);
        String result = underStudy.getXmlResponse();
        assertThat(result, is(xmlResponse));
    }

    @Test
    public void getMessage() {
        BaseResponse underStudy = new BaseResponse(successful, responseStatus, message, xmlResponse);
        String result = underStudy.getMessageResponse();
        assertThat(result, is(message));
    }

    @Test
    public void getResponseStatus() {
        BaseResponse underStudy = new BaseResponse(successful, responseStatus, message, xmlResponse);
        String result = underStudy.getStatusResponse();
        assertThat(result, is(responseStatus));
    }

}
