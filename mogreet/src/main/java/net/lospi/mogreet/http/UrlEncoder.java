package net.lospi.mogreet.http;

import java.io.UnsupportedEncodingException;

public interface UrlEncoder {
    String encode(String token) throws UnsupportedEncodingException;
    String encode(int token) throws UnsupportedEncodingException;
}
