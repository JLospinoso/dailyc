package net.lospi.mogreet.http;

import org.apache.http.client.methods.HttpGet;

public class HttpGetFactory {
    public HttpGetFactory() {

    }

    public HttpGet createHttpGet(String uri) {
        return new HttpGet(uri);
    }
}
