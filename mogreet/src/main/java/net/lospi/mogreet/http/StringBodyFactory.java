package net.lospi.mogreet.http;

import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.content.StringBody;

public class StringBodyFactory {
    public StringBody create(String value) {
        return new StringBody(value, ContentType.TEXT_PLAIN);
    }

    public StringBody create(int value) {
        String valueAsString = String.format("%d", value);
        return new StringBody(valueAsString, ContentType.TEXT_PLAIN);
    }
}
