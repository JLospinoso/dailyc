package net.lospi.mail.core;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class EmailSendResponseTest {
    private boolean successful;
    private String messageResponse;

    @BeforeMethod
    public void setUp() {
        messageResponse = "my message";
        successful = true;
    }

    @Test
    public void isSuccessful() {
        EmailSendResponse underStudy = new EmailSendResponse(messageResponse, successful);
        boolean result = underStudy.isSuccessful();
        assertThat(result, is(true));
    }

    @Test
    public void getMessageResponse() {
        EmailSendResponse underStudy = new EmailSendResponse(messageResponse, successful);
        String result = underStudy.getMessageResponse();
        assertThat(result, is(messageResponse));
    }

}
