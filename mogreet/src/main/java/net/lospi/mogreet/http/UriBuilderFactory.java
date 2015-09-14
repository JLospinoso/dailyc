package net.lospi.mogreet.http;

import org.apache.http.client.utils.URIBuilder;

import java.net.URISyntaxException;

public class UriBuilderFactory {
    public URIBuilder create(String path) throws URISyntaxException {
        return new URIBuilder(path);
    }
}
