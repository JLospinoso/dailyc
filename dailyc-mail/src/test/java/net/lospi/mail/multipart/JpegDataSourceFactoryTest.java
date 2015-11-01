package net.lospi.mail.multipart;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.activation.DataSource;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.testng.Assert.*;

public class JpegDataSourceFactoryTest {
    private byte[] image;

    @BeforeMethod
    public void setUp() {
        image = new byte[]{1, 2, 3};
    }

    @Test
    public void testCreate() throws Exception {
        JpegDataSourceFactory factory = new JpegDataSourceFactory();
        DataSource result = factory.create(image);
        assertThat(result.getContentType(), is("application/jpg"));
    }
}