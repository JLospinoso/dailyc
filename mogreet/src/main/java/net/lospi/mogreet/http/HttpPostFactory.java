package net.lospi.mogreet.http;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;

public class HttpPostFactory {
    HttpPost createHttpPost(String baseUrl, HttpEntity httpEntity) {
        HttpPost httpPost = new HttpPost(baseUrl);
        httpPost.setEntity(httpEntity);
        return httpPost;
    }
}
