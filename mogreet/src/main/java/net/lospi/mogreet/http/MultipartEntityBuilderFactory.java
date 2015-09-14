package net.lospi.mogreet.http;

import org.apache.http.entity.mime.MultipartEntityBuilder;

public class MultipartEntityBuilderFactory {
    public MultipartEntityBuilderFactory() {

    }

    public MultipartEntityBuilder create() {
        return MultipartEntityBuilder.create();
    }
}
