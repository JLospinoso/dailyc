package net.lospi.dailyc

import net.lospi.mogreet.MogreetManager
import net.lospi.mogreet.core.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.support.AnnotationConfigContextLoader
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

import java.nio.file.Path
import java.nio.file.Paths

import static net.lospi.mogreet.util.MogreetConstants.USER_SETOPT_OPTIN
import static org.hamcrest.CoreMatchers.is
import static org.hamcrest.CoreMatchers.notNullValue
import static org.junit.Assert.assertThat

@Configuration
@PropertySource("classpath:application.properties")
@Test(groups=["integration"])
@ContextConfiguration(loader=AnnotationConfigContextLoader.class, classes = [MogreetManagerIntegrationTest.class, DailycConfiguration.class])
public class MogreetManagerIntegrationTest extends AbstractTestNGSpringContextTests {
    public static final Logger LOG = LoggerFactory.getLogger(MogreetManagerIntegrationTest.class);
    @Value('${testJpgPath}')
    private String testJpgPath;
    @Value('${phoneNumber}')
    private String phoneNumber;
    @Value('${campaignId}')
    private String campaignId;
    @Value('${testContentId}')
    private String testContentId;
    @Autowired
    private MogreetManager mogreetManager;
    private Path imagePath;

    @BeforeMethod
    public void setUp() throws Exception {
        imagePath = Paths.get(getClass().getResource(testJpgPath).toURI());
    }

    public void canPing() throws Exception {
        BaseResponse pingResponse = mogreetManager.ping();
        LOG.info(pingResponse.getXmlResponse());

        assertThat(pingResponse.isSuccessful(), is(true));
        assertThat(pingResponse.getStatusResponse(), is("success"));
        assertThat(pingResponse.getMessageResponse(), is("pong"));
        assertThat(pingResponse.getXmlResponse(), is(notNullValue()));
    }

    public void canSend() throws Exception {
        String message = "MogreetManagerIntegrationTest.canSend() successful";
        SendRequest sendRequest = new SendRequest(campaignId, phoneNumber, phoneNumber, message, testContentId);

        SendResponse sendResponse = mogreetManager.send(sendRequest);
        LOG.info(sendResponse.getXmlResponse());

        assertThat(sendResponse.isSuccessful(), is(true));
        assertThat(sendResponse.getStatusResponse(), is("success"));
        assertThat(sendResponse.getHashResponse(), is(notNullValue()));
        assertThat(sendResponse.getMessageIdResponse(), is(notNullValue()));
        assertThat(sendResponse.getMessageResponse(), is("API Request Accepted"));
        assertThat(sendResponse.getXmlResponse(), is(notNullValue()));
    }

    public void canUpload() throws Exception {
        String fileName = "test_image.jpg";
        File file = new File(imagePath.toUri());
        UploadImageRequest uploadImageRequest = new UploadImageRequest(fileName, file.getBytes());

        UploadResponse uploadResponse = mogreetManager.upload(uploadImageRequest);
        LOG.info(uploadResponse.getXmlResponse());

        assertThat(uploadResponse.isSuccessful(), is(true));
        assertThat(uploadResponse.getStatusResponse(), is("success"));
        assertThat(uploadResponse.getXmlResponse(), is(notNullValue()));
        assertThat(uploadResponse.getMessageResponse(), is("media correctly uploaded"));
        assertThat(uploadResponse.getSmartUrlResponse(), is(notNullValue()));
        assertThat(uploadResponse.getContentIdResponse(), is(notNullValue()));
        assertThat(uploadResponse.getNameResponse(), is(fileName));
    }

    public void canSubscribe() throws Exception {
        SubscribeRequest subscribeRequest = new SubscribeRequest(phoneNumber, campaignId, USER_SETOPT_OPTIN);

        BaseResponse baseResponse = mogreetManager.subscribe(subscribeRequest);
        LOG.info(baseResponse.getXmlResponse());

        assertThat(baseResponse.isSuccessful(), is(true));
        assertThat(baseResponse.getStatusResponse(), is("success"));
        assertThat(baseResponse.getXmlResponse(), is(notNullValue()));
        assertThat(baseResponse.getMessageResponse(), is("opt status updated"));
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}