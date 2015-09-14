package net.lospi.mogreet.core;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class UploadImageRequestTest {
    private byte[] image;
    private String name;

    @BeforeMethod
    public void setUp() {
        image = new byte[]{1, 2, 3};
        name = "name";
    }

    @Test
    public void canToString() {
        UploadImageRequest underStudy = new UploadImageRequest(name, image);
        String result = underStudy.toString();
        assertThat(result, is(notNullValue()));
    }

    @Test
    public void getFile() {
        UploadImageRequest underStudy = new UploadImageRequest(name, image);
        byte[] result = underStudy.getImage();
        assertThat(result, is(image));
    }

    @Test
    public void getName() {
        UploadImageRequest underStudy = new UploadImageRequest(name, image);
        String result = underStudy.getName();
        assertThat(result, is(name));
    }
}
