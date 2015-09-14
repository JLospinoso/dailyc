package net.lospi.mogreet.http;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;


public class HttpPostFactoryTest {
    private String baseUrl;
    private HttpEntity entity;

    @BeforeMethod
    public void setUp() {
        baseUrl = "https://lospi.net";
        entity = mock(HttpEntity.class);
    }

    @Test
    public void createsHttpPostWithCorrectUrl(){
        HttpPostFactory factory = new HttpPostFactory();
        HttpPost post = factory.createHttpPost(baseUrl, entity);
        assertThat(post.getURI().toString(), is(baseUrl));
    }

    @Test
    public void createsHttpPostWithCorrectEntity(){
        HttpPostFactory factory = new HttpPostFactory();
        HttpPost post = factory.createHttpPost(baseUrl, entity);
        assertThat(post.getEntity(), is(entity));
    }
}