package net.lospi.mogreet.http;

import net.lospi.mogreet.core.AuthorizationToken;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class PostRequestEncoderTest {
    private AuthorizationToken authorizationToken;
    private int clientId = 1234;
    private String token = "1234567890abcdefg";
    private String baseUrl = "http://base.url";
    private String key1, key2, value1, value2;
    private MultipartEntityBuilderFactory multipartEntityBuilderFactory;
    private MultipartEntityBuilder multipartEntityBuilder;
    private HttpPost expected;
    private HttpEntity multipart;
    private ByteArrayBodyFactory byteArrayBodyFactory;
    private HttpPostFactory httpPostFactory;
    private ByteArrayBody byteArrayBody;
    private StringBodyFactory stringBodyFactory;
    private StringBody clientIdBody, tokenBody, key1Body, key2Body;
    private byte[] imageBytes;
    private String imageName;

    @BeforeMethod
    public void setUp() throws IOException {
        clientIdBody = mock(StringBody.class);
        tokenBody = mock(StringBody.class);
        key1Body = mock(StringBody.class);
        key2Body = mock(StringBody.class);
        stringBodyFactory = mock(StringBodyFactory.class);
        byteArrayBody = mock(ByteArrayBody.class);
        byteArrayBodyFactory = mock(ByteArrayBodyFactory.class);
        httpPostFactory = mock(HttpPostFactory.class);
        multipartEntityBuilderFactory = mock(MultipartEntityBuilderFactory.class);
        authorizationToken = new AuthorizationToken(clientId, token);
        multipartEntityBuilder = mock(MultipartEntityBuilder.class);
        expected = mock(HttpPost.class);
        multipart = mock(HttpEntity.class);
        key1 = "key1";
        key2 = "key2";
        value1 = "value1";
        value2 = "value2";
        imageBytes = new byte[] {1, 2, 3};
        imageName = "image_name.jpg";

        when(multipartEntityBuilderFactory.create()).thenReturn(multipartEntityBuilder);
        when(multipartEntityBuilder.build()).thenReturn(multipart);
        when(byteArrayBodyFactory.buildImage(imageBytes, imageName)).thenReturn(byteArrayBody);
        when(stringBodyFactory.create(clientId)).thenReturn(clientIdBody);
        when(stringBodyFactory.create(token)).thenReturn(tokenBody);
        when(stringBodyFactory.create(value1)).thenReturn(key1Body);
        when(stringBodyFactory.create(value2)).thenReturn(key2Body);
        when(httpPostFactory.createHttpPost(baseUrl, multipart)).thenReturn(expected);
    }

    @Test
    public void buildsWithFile() throws IOException {
        PostRequestEncoder underStudy = new PostRequestEncoder(authorizationToken, multipartEntityBuilderFactory,
                byteArrayBodyFactory, httpPostFactory, stringBodyFactory);
        HttpPost result = underStudy.with()
                .baseUrl(baseUrl)
                .image(imageBytes)
                .imageFileName(imageName)
                .parameter(key1, value1)
                .parameter(key2, value2)
                .build();
        assertThat(result, is(expected));

        verify(multipartEntityBuilder).addPart("client_id", clientIdBody);
        verify(multipartEntityBuilder).addPart("token", tokenBody);
        verify(multipartEntityBuilder).addPart(key1, key1Body);
        verify(multipartEntityBuilder).addPart(key2, key2Body);
        verify(multipartEntityBuilder).addPart("file", byteArrayBody);
        verify(multipartEntityBuilder).build();
        verifyNoMoreInteractions(multipartEntityBuilder);
    }

    @Test
    public void buildsWithoutFile() throws IOException {
        PostRequestEncoder underStudy = new PostRequestEncoder(authorizationToken, multipartEntityBuilderFactory,
                byteArrayBodyFactory, httpPostFactory, stringBodyFactory);
        HttpPost result = underStudy.with()
                .baseUrl(baseUrl)
                .parameter(key1, value1)
                .parameter(key2, value2)
                .build();
        assertThat(result, is(expected));

        verify(multipartEntityBuilder).addPart("client_id", clientIdBody);
        verify(multipartEntityBuilder).addPart("token", tokenBody);
        verify(multipartEntityBuilder).addPart(key1, key1Body);
        verify(multipartEntityBuilder).addPart(key2, key2Body);
        verify(multipartEntityBuilder).build();
        verifyNoMoreInteractions(multipartEntityBuilder);
    }

    @Test(expectedExceptions = IllegalStateException.class, expectedExceptionsMessageRegExp = "You must specify a base url.")
    public void throwsWithoutUrl() throws IOException {
        PostRequestEncoder underStudy = new PostRequestEncoder(authorizationToken, multipartEntityBuilderFactory,
                byteArrayBodyFactory, httpPostFactory, stringBodyFactory);
        underStudy.with()
                .parameter(key1, value1)
                .parameter(key2, value2)
                .build();
    }
}
