package net.lospi.mogreet.core;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class AuthorizationTokenTest {
    private String token;
    private int clientId;

    @BeforeMethod
    public void setUp() {
        clientId = 1234;
        token = "5678";
    }

    @Test
    public void testToString() {
        AuthorizationToken underStudy = new AuthorizationToken(clientId, token);
        String result = underStudy.toString();
        assertThat(result, is(notNullValue()));
    }

    @Test
    public void getClientId() {
        AuthorizationToken underStudy = new AuthorizationToken(clientId, token);
        int result = underStudy.getClientId();
        assertThat(result, is(clientId));
    }

    @Test
    public void getToken() {
        AuthorizationToken underStudy = new AuthorizationToken(clientId, token);
        String result = underStudy.getToken();
        assertThat(result, is(token));
    }
}
