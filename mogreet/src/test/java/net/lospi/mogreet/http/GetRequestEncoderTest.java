package net.lospi.mogreet.http;

import net.lospi.mogreet.core.AuthorizationToken;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class GetRequestEncoderTest {
    private UrlEncoder urlEncoder;
    private AuthorizationToken authorizationToken;
    private String baseUrl;
    private HttpGet expected;
    private URIBuilder uriBuilder;
    private URI uri;
    private HttpGetFactory httpGetFactory;
    private UriBuilderFactory uriBuilderFactory;
    private String key1, key2, value1, value2, token;
    private int clientId;
    private String encodedToken, encodedClientId, encodedKey1, encodedKey2, encodedValue1, encodedValue2;

    @BeforeMethod
    public void setUp() throws URISyntaxException, UnsupportedEncodingException {
        baseUrl = "https://lospi.net";
        key1 = "key1";
        key2 = "key2";
        value1 = "value1";
        value2 = "value2";
        token = "token";
        encodedToken = "encodedToken";
        encodedClientId = "encodedClientId";
        encodedKey1 = "encodedKey1";
        encodedKey2 = "encodedKey2";
        encodedValue1 = "encodedValue1";
        encodedValue2 = "encodedValue2";

        clientId = 1234;
        uri = URI.create(baseUrl);
        httpGetFactory = mock(HttpGetFactory.class);
        urlEncoder = mock(Utf8Encoder.class);
        authorizationToken = mock(AuthorizationToken.class);
        expected = mock(HttpGet.class);
        uriBuilderFactory = mock(UriBuilderFactory.class);
        uriBuilder = mock(URIBuilder.class);

        when(authorizationToken.getClientId()).thenReturn(clientId);
        when(authorizationToken.getToken()).thenReturn(token);
        when(uriBuilderFactory.create(baseUrl)).thenReturn(uriBuilder);
        when(uriBuilder.build()).thenReturn(uri);
        when(uriBuilder.setParameter(anyString(), anyString())).thenReturn(uriBuilder);
        when(httpGetFactory.createHttpGet(uri.toString())).thenReturn(expected);

        when(urlEncoder.encode(token)).thenReturn(encodedToken);
        when(urlEncoder.encode(clientId)).thenReturn(encodedClientId);
        when(urlEncoder.encode(key1)).thenReturn(encodedKey1);
        when(urlEncoder.encode(key2)).thenReturn(encodedKey2);
        when(urlEncoder.encode(value1)).thenReturn(encodedValue1);
        when(urlEncoder.encode(value2)).thenReturn(encodedValue2);
    }

    @Test
    public void buildsWithNoParameters() throws UnsupportedEncodingException, URISyntaxException {
        GetRequestEncoder encoder = new GetRequestEncoder(authorizationToken, urlEncoder, uriBuilderFactory, httpGetFactory);

        HttpGet result = encoder.with()
                .baseUrl(baseUrl)
                .build();

        assertThat(result, is(expected));

        verify(uriBuilder).setParameter("client_id", encodedClientId);
        verify(uriBuilder).setParameter("token", encodedToken);
        verify(uriBuilder).build();
        verifyNoMoreInteractions(uriBuilder);
    }

    @Test
    public void buildsWithOneParameter() throws UnsupportedEncodingException, URISyntaxException {
        GetRequestEncoder encoder = new GetRequestEncoder(authorizationToken, urlEncoder, uriBuilderFactory, httpGetFactory);

        HttpGet result = encoder.with()
                .baseUrl(baseUrl)
                .parameter(key1, value1)
                .build();

        assertThat(result, is(expected));
    }

    @Test
    public void buildsWithTwoParameters() throws UnsupportedEncodingException, URISyntaxException {
        GetRequestEncoder encoder = new GetRequestEncoder(authorizationToken, urlEncoder, uriBuilderFactory, httpGetFactory);

        HttpGet result = encoder.with()
                .baseUrl(baseUrl)
                .parameter(key1, value1)
                .parameter(key2, value2)
                .build();

        assertThat(result, is(expected));
    }

    @Test(expectedExceptions = IllegalStateException.class)
    public void throwsWithNoUrl() throws UnsupportedEncodingException, URISyntaxException {
        GetRequestEncoder encoder = new GetRequestEncoder(authorizationToken, urlEncoder, uriBuilderFactory, httpGetFactory);

        HttpGet result = encoder.with()
                .parameter(key1, value1)
                .parameter(key2, value2)
                .build();

        assertThat(result, is(expected));
    }
}