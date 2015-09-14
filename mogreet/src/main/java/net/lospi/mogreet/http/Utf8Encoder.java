package net.lospi.mogreet.http;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Utf8Encoder implements UrlEncoder {
    public Utf8Encoder() {

    }

    @Override
    public String encode(String token) throws UnsupportedEncodingException {
        return URLEncoder.encode(token, "UTF-8");
    }

    @Override
    public String encode(int token) throws UnsupportedEncodingException {
        return URLEncoder.encode(String.format("%d", token), "UTF-8");
    }
}
