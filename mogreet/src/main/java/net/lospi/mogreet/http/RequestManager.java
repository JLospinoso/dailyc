package net.lospi.mogreet.http;

import net.lospi.exception.MogreetException;
import net.lospi.mogreet.parse.Parser;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class RequestManager {
    private static final Logger LOG = LoggerFactory.getLogger(RequestManager.class);
    private final CloseableHttpClient httpClient;
    private final DocumentBuilderFactory builderFactory;

    public RequestManager(CloseableHttpClient httpClient, DocumentBuilderFactory builderFactory) {
        this.httpClient = httpClient;
        this.builderFactory = builderFactory;
        builderFactory.setNamespaceAware(true);
    }

    public <T> T executeRequest(final HttpUriRequest request, final Parser<T> resultParser) throws MogreetException {
        CloseableHttpResponse httpResponse;
        try {
            httpResponse = httpClient.execute(request);
        } catch (IOException e) {
            throw new MogreetException(e);
        }
        try {
            T result;
            Document xmlDoc;
            HttpEntity httpResponseEntity = httpResponse.getEntity();
            InputStream inputStream = httpResponseEntity.getContent();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            InputSource inputSource = new InputSource(bufferedReader);
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            xmlDoc = builder.parse(inputSource);
            result = resultParser.parse(xmlDoc);
            EntityUtils.consume(httpResponseEntity);
            return result;
        } catch (SAXException | ParserConfigurationException | IOException e) {
            throw new MogreetException(e);
        } finally {
            try {
                httpResponse.close();
            } catch (IOException e) {
                LOG.error("Could not close HttpResponse: %s", e);
            }
        }
    }
}
