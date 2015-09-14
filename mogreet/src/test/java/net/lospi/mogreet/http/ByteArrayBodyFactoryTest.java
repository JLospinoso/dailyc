package net.lospi.mogreet.http;

import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.FileBody;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class ByteArrayBodyFactoryTest {
    private byte[] bytes;
    private String name;

    @BeforeMethod
    public void setUp() {
        bytes = new byte[]{1, 2, 3};
        name = "file_name.jpg";
    }

    @Test
    public void constructsWithCorrectFile() {
        ByteArrayBodyFactory underStudy = new ByteArrayBodyFactory();
        ByteArrayBody result = underStudy.buildImage(bytes, name);
        assertThat(result.getFilename(), is(name));
    }

    @Test
    public void constructsWithCorrectType() {
        ByteArrayBodyFactory underStudy = new ByteArrayBodyFactory();
        ByteArrayBody result = underStudy.buildImage(bytes, name);
        assertThat(result.getMimeType(), is("image/jpeg"));
    }
}
