package net.lospi.mail.multipart;

import org.apache.commons.mail.MultiPartEmail;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.testng.Assert.*;

public class MultipartMailFactoryTest {
    @Test
    public void testCreate() throws Exception {
        MultipartMailFactory factory = new MultipartMailFactory();
        MultiPartEmail result = factory.create();
        assertThat(result, is(notNullValue()));
    }
}