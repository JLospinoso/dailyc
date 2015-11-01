package net.lospi.dailyc

import groovy.json.JsonSlurper
import groovy.util.logging.Slf4j
import net.lospi.dailyc.comm.MailSender
import net.lospi.dailyc.comm.MogreetSender
import net.lospi.dailyc.comm.MogreetSubscriber
import net.lospi.dailyc.comm.MogreetUploader
import net.lospi.dailyc.persistence.BatchLoader
import net.lospi.dailyc.persistence.Dao
import net.lospi.dailyc.util.Hasher
import net.lospi.mail.core.EmailSendResponseFactory
import net.lospi.mail.MailManager
import net.lospi.mail.multipart.JpegDataSourceFactory
import net.lospi.mail.multipart.MultipartMailFactory
import net.lospi.mogreet.MogreetManager
import net.lospi.mogreet.core.AuthorizationToken
import net.lospi.mogreet.core.BaseResponse
import net.lospi.mogreet.core.SendResponse
import net.lospi.mogreet.core.UploadResponse
import net.lospi.mogreet.http.*
import net.lospi.mogreet.parse.BaseResultParser
import net.lospi.mogreet.parse.Parser
import net.lospi.mogreet.parse.SendResultParser
import net.lospi.mogreet.parse.UploadResultParser
import org.apache.http.impl.client.CloseableHttpClient
import org.apache.http.impl.client.HttpClients
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.jdbc.datasource.DriverManagerDataSource
import org.springframework.stereotype.Component

import javax.sql.DataSource
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.TransformerFactory
import java.security.MessageDigest

@Slf4j
@Component
@Configuration
@PropertySource("classpath:application.properties")
class DailycConfiguration {
    @Value('${campaignId}')
    private String campaignId
    @Value('${from}')
    private String from
    @Value('${configPath}')
    private String configPath
    @Value('${clientId}')
    private String clientId
    @Value('${token}')
    private String token
    @Value('${database.driver}')
    private String databaseDriver
    @Value('${database.url}')
    private String databaseUrl
    @Value('${database.username}')
    private String databaseUsername
    @Value('${database.password}')
    private String databasePassword
    @Value('${email.userName}')
    private String emailUserName
    @Value('${email.password}')
    private String emailPassword
    @Value('${email.hostName}')
    private String emailHostName
    @Value('${email.sslSmtpPort}')
    private String sslSmtpPort
    @Value('${email.attachmentName}')
    private String emailAttachmentName
    @Value('${email.fromName}')
    private String emailFromName
    @Value('${email.toName}')
    private String emailToName
    @Value('${email.from}')
    private String emailFrom

    @Bean
    public DataSource dataSource() {
        new DriverManagerDataSource(driverClassName:databaseDriver, url:databaseUrl, username:databaseUsername,
                password:databasePassword)
    }

    @Bean
    public MogreetManager mogreetManager() throws IOException {
        UriBuilderFactory uriBuilderFactory = new UriBuilderFactory()
        HttpGetFactory httpGetFactory = new HttpGetFactory()
        UrlEncoder urlEncoder = new NopEncoder()
        StringBodyFactory stringBodyFactory = new StringBodyFactory()
        ByteArrayBodyFactory fileBodyFactory = new ByteArrayBodyFactory()
        HttpPostFactory httpPostFactory = new HttpPostFactory()
        MultipartEntityBuilderFactory multipartEntityBuilderFactory = new MultipartEntityBuilderFactory()
        TransformerFactory transformerFactory = TransformerFactory.newInstance()
        XmlResponseReader xmlResponseReader = new XmlResponseReader(transformerFactory)
        AuthorizationToken token = makeToken()
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance()
        CloseableHttpClient httpClient = HttpClients.createDefault()
        RequestManager requestManager = new RequestManager(httpClient, documentBuilderFactory)
        Parser<SendResponse> sendResultParser = new SendResultParser(xmlResponseReader)
        Parser<UploadResponse> uploadResultParser = new UploadResultParser(xmlResponseReader)
        Parser<BaseResponse> subscribeResultParser = new BaseResultParser(xmlResponseReader)
        GetRequestEncoder getRequestEncoder = new GetRequestEncoder(token, urlEncoder, uriBuilderFactory,
                httpGetFactory)
        PostRequestEncoder postRequestEncoder = new PostRequestEncoder(token, multipartEntityBuilderFactory,
                fileBodyFactory, httpPostFactory, stringBodyFactory)
        new MogreetManager(requestManager, sendResultParser, uploadResultParser, subscribeResultParser,
                getRequestEncoder, postRequestEncoder)
    }

    @Bean
    public MailManager mailManager() {
        MultipartMailFactory multipartMailFactory = new MultipartMailFactory()
        EmailSendResponseFactory emailSendResponseFactory = new EmailSendResponseFactory()
        JpegDataSourceFactory jpegDataSourceFactory = new JpegDataSourceFactory()
        return new MailManager(emailUserName, emailPassword, emailHostName, sslSmtpPort,
                emailAttachmentName, emailFromName,
                emailToName, multipartMailFactory, emailSendResponseFactory, jpegDataSourceFactory)
    }

    @Bean
    DailycManager dailycBatch() {
        File configFile = new File(configPath)
        Hasher hasher = new Hasher(digest: MessageDigest.getInstance("SHA"))
        def jsonSlurper = new JsonSlurper()
        BatchLoader batchLoader = new BatchLoader(configFile: configFile, slurper: jsonSlurper)
        Dao dao = new Dao(hasher: hasher)
        MogreetManager manager = mogreetManager()
        MogreetSender sender = new MogreetSender(manager: manager, campaignId: campaignId, from: from)
        MogreetSubscriber subscriber = new MogreetSubscriber(manager: manager, campaignId: campaignId)
        MogreetUploader mogreetUploader = new MogreetUploader(manager: manager)
        MailManager mailManager = mailManager()
        MailSender mailSender = new MailSender(from: emailFrom, mailManager: mailManager)
        new DailycManager(mogreetSender: sender, mogreetSubscriber: subscriber,
                mogreetUploader: mogreetUploader, dao: dao,
                batchLoader: batchLoader, mailSender: mailSender)
    }

    private AuthorizationToken makeToken() throws IOException {
        new AuthorizationToken(Integer.parseInt(clientId), token)
    }
}
