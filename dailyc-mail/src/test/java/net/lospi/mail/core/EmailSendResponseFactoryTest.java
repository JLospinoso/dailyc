package net.lospi.mail.core;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class EmailSendResponseFactoryTest {
    public String message;

    @BeforeMethod
    public void setUp() throws Exception {
        message = "My message";
    }

    @Test
    public void testCreateSuccessfulResponse() throws Exception {
        EmailSendResponseFactory factory = new EmailSendResponseFactory();
        EmailSendResponse result = factory.createSuccessfulResponse(message);
        assertThat(result.isSuccessful(), is(true));
        assertThat(result.getMessageResponse(), is(message));
    }

    @Test
    public void testCreateUnuccessfulResponse() throws Exception {
        EmailSendResponseFactory factory = new EmailSendResponseFactory();
        EmailSendResponse result = factory.createUnuccessfulResponse(message);
        assertThat(result.isSuccessful(), is(false));
        assertThat(result.getMessageResponse(), is(message));
    }
}