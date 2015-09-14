package net.lospi.mogreet.http;

import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.content.ByteArrayBody;

public class ByteArrayBodyFactory {
    public ByteArrayBodyFactory() {

    }

    public ByteArrayBody buildImage(byte[] bytes, String filename) {
        return new ByteArrayBody(bytes, ContentType.create("image/jpeg"), filename);
    }
}
