package net.lospi.mogreet.parse;

import net.lospi.mogreet.core.UploadResponse;
import net.lospi.mogreet.http.XmlResponseReader;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.w3c.dom.Document;

import javax.xml.transform.TransformerFactory;
import java.nio.file.Path;
import java.nio.file.Paths;

import static net.lospi.mogreet.test.Util.parseDocument;
import static net.lospi.mogreet.test.Util.parseString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class UploadResultParserTest {
    private XmlResponseReader xmlResponseReader;
    private Document xmlDoc;
    private String expectedXml;

    @BeforeMethod
    public void setUp() {
        TransformerFactory factory = TransformerFactory.newInstance();
        xmlResponseReader = new XmlResponseReader(factory);
    }

    @Test
    public void parseMessageResponse() throws Exception {
        setXml("/result-samples/upload-sample2.xml");
        UploadResultParser underStudy = new UploadResultParser(xmlResponseReader);
        UploadResponse result = underStudy.parse(xmlDoc);
        assertThat(result.getStatusResponse(), is("failure"));
        assertThat(result.getMessageResponse(), is("media correctly uploaded"));
        assertThat(result.getNameResponse(), is("test_image.jpg"));
        assertThat(result.getContentIdResponse(), is("5435111"));
        assertThat(result.getSmartUrlResponse(), is("http://m.mogreet.com/oc/apxzik6z2l4"));
        assertThat(result.isSuccessful(), is(false));
        assertThat(result.getXmlResponse(), is(expectedXml));
    }

    @Test
    public void parseMessage() throws Exception {
        setXml("/result-samples/upload-sample1.xml");
        UploadResultParser underStudy = new UploadResultParser(xmlResponseReader);
        UploadResponse result = underStudy.parse(xmlDoc);
        assertThat(result.getStatusResponse(), is("success"));
        assertThat(result.getMessageResponse(), is("media correctly uploaded"));
        assertThat(result.getNameResponse(), is("test_image.jpg"));
        assertThat(result.getContentIdResponse(), is("5435111"));
        assertThat(result.getSmartUrlResponse(), is("http://m.mogreet.com/oc/apxzik6z2l4"));
        assertThat(result.isSuccessful(), is(true));
        assertThat(result.getXmlResponse(), is(expectedXml));
    }

    void setXml(String path) throws Exception {
        Path xmlPath = Paths.get(getClass().getResource(path).toURI());
        xmlDoc = parseDocument(xmlPath);
        expectedXml = parseString(xmlPath);
    }
}
