package net.lospi.mogreet.http;

import net.lospi.mogreet.core.AuthorizationToken;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ByteArrayBody;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PostRequestEncoder {
    private final AuthorizationToken authorizationToken;
    private final MultipartEntityBuilderFactory multipartEntityBuilderFactory;
    private final ByteArrayBodyFactory byteArrayBodyFactory;
    private final HttpPostFactory httpPostFactory;
    private final StringBodyFactory stringBodyFactory;

    public PostRequestEncoder(AuthorizationToken authorizationToken,
                              MultipartEntityBuilderFactory multipartEntityBuilderFactory,
                              ByteArrayBodyFactory byteArrayBodyFactory, HttpPostFactory httpPostFactory,
                              StringBodyFactory stringBodyFactory) {
        this.authorizationToken = authorizationToken;
        this.multipartEntityBuilderFactory = multipartEntityBuilderFactory;
        this.byteArrayBodyFactory = byteArrayBodyFactory;
        this.httpPostFactory = httpPostFactory;
        this.stringBodyFactory = stringBodyFactory;
    }

    public PostRequestEncoder.Stub with() {
        return new Stub();
    }

    public class Stub {
        private String baseUrl;
        private Map<String, String> parameters;
        private byte[] image;
        private String fileName;

        private Stub() {
            parameters = new ConcurrentHashMap<>();
        }

        public Stub baseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public Stub parameter(String key, String value) {
            parameters.put(key, value);
            return this;
        }

        public Stub imageFileName(String fileName) throws IOException {
            this.fileName = fileName;
            return this;
        }

        public Stub image(byte[] image) throws IOException {
            this.image = image;
            return this;
        }

        public HttpPost build() throws IOException {
            if(baseUrl == null) {
                throw new IllegalStateException("You must specify a base url.");
            }
            MultipartEntityBuilder entityBuilder = multipartEntityBuilderFactory.create();
            entityBuilder.addPart("client_id", stringBodyFactory.create(authorizationToken.getClientId()));
            entityBuilder.addPart("token", stringBodyFactory.create(authorizationToken.getToken()));
            for (Map.Entry<String, String> entry : parameters.entrySet()){
                entityBuilder.addPart(entry.getKey(), stringBodyFactory.create(entry.getValue()));
            }
            if(null != image && null != fileName) {
                ByteArrayBody body = byteArrayBodyFactory.buildImage(image, fileName);
                entityBuilder.addPart("file", body);
            }
            HttpEntity httpEntity = entityBuilder.build();
            return httpPostFactory.createHttpPost(baseUrl, httpEntity);
        }
    }
}
