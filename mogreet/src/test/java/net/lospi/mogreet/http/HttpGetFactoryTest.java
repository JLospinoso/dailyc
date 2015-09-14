package net.lospi.mogreet.http;

import org.apache.http.client.methods.HttpGet;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class HttpGetFactoryTest {
    private String uri;

    @BeforeMethod
    public void setUp() {
        uri = "http://lospi.net";
    }

    @Test
    public void hasCorrectUri() {
        HttpGetFactory underStudy = new HttpGetFactory();
        HttpGet result = underStudy.createHttpGet(uri);
        assertThat(result.getURI().toString(), is(uri));
    }
}