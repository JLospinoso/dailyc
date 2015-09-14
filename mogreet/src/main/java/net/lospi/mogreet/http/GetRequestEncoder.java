package net.lospi.mogreet.http;

import net.lospi.mogreet.core.AuthorizationToken;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GetRequestEncoder {
    private final AuthorizationToken authorizationToken;
    private final UrlEncoder urlEncoder;
    private final UriBuilderFactory uriBuilderFactory;
    private final HttpGetFactory httpGetFactory;

    public GetRequestEncoder(AuthorizationToken authorizationToken, UrlEncoder urlEncoder,
                             UriBuilderFactory uriBuilderFactory, HttpGetFactory httpGetFactory) {
        this.authorizationToken = authorizationToken;
        this.urlEncoder = urlEncoder;
        this.uriBuilderFactory = uriBuilderFactory;
        this.httpGetFactory = httpGetFactory;
    }

    public GetRequestEncoder.Stub with() {
        return new Stub();
    }

    public class Stub {
        private String baseUrl;
        private Map<String, String> parameters;

        private Stub() {
            parameters = new ConcurrentHashMap<>();
        }

        public Stub baseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public Stub parameter(String key, String value) throws UnsupportedEncodingException {
            String encodedKey = urlEncoder.encode(key);
            String encodedValue = urlEncoder.encode(value);
            parameters.put(encodedKey, encodedValue);
            return this;
        }

        public HttpGet build() {
            if(baseUrl == null) {
                throw new IllegalStateException("You must specify a base url.");
            }
            try {
                String clientId = urlEncoder.encode(authorizationToken.getClientId());
                String token = urlEncoder.encode(authorizationToken.getToken());
                URIBuilder uriBuilder = uriBuilderFactory.create(baseUrl)
                        .setParameter("client_id", clientId)
                        .setParameter("token", token);
                for (Map.Entry<String, String> entry : parameters.entrySet()){
                    uriBuilder.setParameter(entry.getKey(), entry.getValue());
                }
                String uri = uriBuilder.build().toString();
                return httpGetFactory.createHttpGet(uri);
            } catch (UnsupportedEncodingException e) {
                throw new IllegalArgumentException(e);
            } catch (URISyntaxException e) {
                throw new IllegalArgumentException(e);
            }
        }
    }
}
