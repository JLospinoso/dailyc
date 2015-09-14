package net.lospi.mogreet.http;

import java.io.UnsupportedEncodingException;

public class NopEncoder implements UrlEncoder {
    @Override
    public String encode(String token) throws UnsupportedEncodingException {
        return token;
    }

    @Override
    public String encode(int token) throws UnsupportedEncodingException {
        return String.format("%d", token);
    }
}
