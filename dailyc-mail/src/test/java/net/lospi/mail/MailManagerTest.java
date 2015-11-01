package net.lospi.mail;

import net.lospi.mail.core.EmailSendRequest;
import net.lospi.mail.core.EmailSendResponse;
import net.lospi.mail.core.EmailSendResponseFactory;
import net.lospi.mail.multipart.JpegDataSourceFactory;
import net.lospi.mail.multipart.MultipartMailFactory;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.activation.DataSource;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MailManagerTest {
    private MultipartMailFactory multipartMailFactory;
    private EmailSendResponseFactory emailSendResponseFactory;
    private String userName;
    private String sslSmtpPort;
    private JpegDataSourceFactory jpegDataSourceFactory;
    private String toName;
    private EmailSendRequest emailSendRequest;
    private String hostName;
    private String fromName;
    private String attachmentName;
    private String password;
    private byte[] imageBytes;
    private DataSource dataSource;
    private MultiPartEmail email;
    private String contents;
    private String from;
    private String to;
    private String sendResultString;

    @BeforeMethod
    public void setUp() throws EmailException {
        emailSendResponseFactory = mock(EmailSendResponseFactory.class);
        multipartMailFactory = mock(MultipartMailFactory.class);
        jpegDataSourceFactory = mock(JpegDataSourceFactory.class);
        emailSendRequest = mock(EmailSendRequest.class);
        dataSource = mock(DataSource.class);
        email = mock(MultiPartEmail.class);
        contents = "contents";
        from = "from";
        to = "to";
        password = "password";
        userName = "userName";
        toName = "toName";
        attachmentName = "attachname";
        sslSmtpPort = "port";
        hostName = "hostName";
        fromName = "fromName";
        sendResultString = "sendResultString";
        imageBytes = new byte[]{0, 1, 2};
        when(emailSendRequest.getImage()).thenReturn(imageBytes);
        when(emailSendRequest.getContents()).thenReturn(contents);
        when(emailSendRequest.getFrom()).thenReturn(from);
        when(emailSendRequest.getTo()).thenReturn(to);
        when(jpegDataSourceFactory.create(imageBytes)).thenReturn(dataSource);
        when(multipartMailFactory.create()).thenReturn(email);
        when(email.send()).thenReturn(sendResultString);
    }

    @Test
    public void sendSuccess() throws EmailException {
        EmailSendResponse expected = mock(EmailSendResponse.class);
        when(emailSendResponseFactory.createSuccessfulResponse(sendResultString)).thenReturn(expected);
        MailManager underStudy = new MailManager(userName, password, hostName, sslSmtpPort, attachmentName, fromName, toName,
                multipartMailFactory, emailSendResponseFactory, jpegDataSourceFactory);

        EmailSendResponse result = underStudy.send(emailSendRequest);

        assertThat(result, is(expected));
        verify(email).setHostName(hostName);
        verify(email).setAuthentication(userName, password);
        verify(email).setSslSmtpPort(sslSmtpPort);
        verify(email).addTo(to, toName);
        verify(email).setFrom(from, fromName);
        verify(email).setSubject(contents);
        verify(email).setMsg(contents);
        verify(email).attach(dataSource, attachmentName, attachmentName);
        verify(email).attach(dataSource, attachmentName, attachmentName);
    }

    @Test
    public void sendFailure() throws EmailException {
        String exceptionMessage = "exceptionMessage";
        EmailException emailException = mock(EmailException.class);
        when(emailException.getMessage()).thenReturn(exceptionMessage);
        when(email.send()).thenThrow(emailException);
        EmailSendResponse expected = mock(EmailSendResponse.class);
        when(emailSendResponseFactory.createUnuccessfulResponse(exceptionMessage)).thenReturn(expected);
        MailManager underStudy = new MailManager(userName, password, hostName, sslSmtpPort, attachmentName, fromName, toName,
                multipartMailFactory, emailSendResponseFactory, jpegDataSourceFactory);

        EmailSendResponse result = underStudy.send(emailSendRequest);

        assertThat(result, is(expected));
        verify(email).setHostName(hostName);
        verify(email).setAuthentication(userName, password);
        verify(email).setSslSmtpPort(sslSmtpPort);
        verify(email).addTo(to, toName);
        verify(email).setFrom(from, fromName);
        verify(email).setSubject(contents);
        verify(email).setMsg(contents);
        verify(email).attach(dataSource, attachmentName, attachmentName);
        verify(email).attach(dataSource, attachmentName, attachmentName);
    }

}
