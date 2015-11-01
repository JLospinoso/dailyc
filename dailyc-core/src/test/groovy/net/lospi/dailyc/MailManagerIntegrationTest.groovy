package net.lospi.dailyc

import net.lospi.mail.MailManager
import net.lospi.mail.core.EmailSendRequest
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

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

import static org.hamcrest.CoreMatchers.is
import static org.hamcrest.CoreMatchers.notNullValue
import static org.junit.Assert.assertThat

@Configuration
@PropertySource("classpath:application.properties")
@Test(groups=["integration"])
@ContextConfiguration(loader=AnnotationConfigContextLoader.class, classes = [MailManagerIntegrationTest.class, DailycConfiguration.class])
public class MailManagerIntegrationTest extends AbstractTestNGSpringContextTests {
    @Value('${email.from}')
    private String fromEmail
    @Value('${email.to}')
    private String toEmail
    private String bodyText
    private Path imagePath

    @Autowired
    private MailManager manager

    @BeforeMethod
    public void setUp() throws Exception {
        imagePath = Paths.get(getClass().getResource("/dailyc.jpg").toURI());
        bodyText = "Dailyc test!";
    }

    public void canSend() throws Exception {
        def jpgBytes = Files.readAllBytes(imagePath);
        EmailSendRequest sendRequest = new EmailSendRequest(toEmail, fromEmail, bodyText, jpgBytes);
        def sendResult = manager.send(sendRequest);

        assertThat(sendResult.successful, is(true));
        assertThat(sendResult.messageResponse, is(notNullValue()));
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}