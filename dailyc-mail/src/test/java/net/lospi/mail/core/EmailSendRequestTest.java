package net.lospi.mail.core;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

public class EmailSendRequestTest {
    private String from;
    private String to;
    private String contents;
    private byte[] image;

    @BeforeMethod
    public void setUp() {
        to = "to";
        image = new byte[] { 1, 2, 3};
        from = "from";
        contents = "contents";
    }

    @Test
    public void getFrom() {
        EmailSendRequest underStudy = new EmailSendRequest(to, from, contents, image);
        String result = underStudy.getFrom();
        assertThat(result, is(from));
    }

    @Test
    public void getContents() {
        EmailSendRequest underStudy = new EmailSendRequest(to, from, contents, image);
        String result = underStudy.getContents();
        assertThat(result, is(contents));
    }

    @Test
    public void getImage() {
        EmailSendRequest underStudy = new EmailSendRequest(to, from, contents, image);
        byte[] result = underStudy.getImage();
        assertThat(result, is(image));
    }

    @Test
    public void canToString() {
        EmailSendRequest underStudy = new EmailSendRequest(to, from, contents, image);
        String result = underStudy.toString();
        assertThat(result, is(notNullValue()));
    }

    @Test
    public void getTo() {
        EmailSendRequest underStudy = new EmailSendRequest(to, from, contents, image);
        String result = underStudy.getTo();
        assertThat(result, is(to));
    }
}
