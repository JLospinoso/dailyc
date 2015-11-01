package net.lospi.mail;

import net.lospi.mail.core.EmailSendRequest;
import net.lospi.mail.core.EmailSendResponse;
import net.lospi.mail.core.EmailSendResponseFactory;
import net.lospi.mail.multipart.JpegDataSourceFactory;
import net.lospi.mail.multipart.MultipartMailFactory;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;

import javax.activation.DataSource;

public class MailManager {
    private final String userName;
    private final String password;
    private final String hostName;
    private final String sslSmtpPort;
    private final String attachmentName;
    private final String fromName;
    private final String toName;
    private final MultipartMailFactory multipartMailFactory;
    private final EmailSendResponseFactory emailSendResponseFactory;
    private final JpegDataSourceFactory jpegDataSourceFactory;

    public MailManager(String userName, String password, String hostName, String sslSmtpPort, String attachmentName,
                       String fromName, String toName, MultipartMailFactory multipartMailFactory,
                       EmailSendResponseFactory emailSendResponseFactory, JpegDataSourceFactory jpegDataSourceFactory) {
        this.userName = userName;
        this.password = password;
        this.hostName = hostName;
        this.sslSmtpPort = sslSmtpPort;
        this.attachmentName = attachmentName;
        this.fromName = fromName;
        this.toName = toName;
        this.multipartMailFactory = multipartMailFactory;
        this.emailSendResponseFactory = emailSendResponseFactory;
        this.jpegDataSourceFactory = jpegDataSourceFactory;
    }

    public EmailSendResponse send(EmailSendRequest emailSendRequest) {
        try {
            DataSource dataSource = jpegDataSourceFactory.create(emailSendRequest.getImage());
            MultiPartEmail email = multipartMailFactory.create();
            email.setHostName(hostName);
            email.setAuthentication(userName, password);
            email.setSslSmtpPort(sslSmtpPort);
            email.addTo(emailSendRequest.getTo(), toName);
            email.setFrom(emailSendRequest.getFrom(), fromName);
            email.setSubject(emailSendRequest.getContents());
            email.setMsg(emailSendRequest.getContents());
            email.attach(dataSource, attachmentName,  attachmentName);
            String result = email.send();
            return emailSendResponseFactory.createSuccessfulResponse(result);
        } catch (EmailException e) {
            return emailSendResponseFactory.createUnuccessfulResponse(e.getMessage());
        }
    }
}