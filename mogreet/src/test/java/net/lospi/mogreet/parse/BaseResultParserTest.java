package net.lospi.mogreet.parse;

import net.lospi.mogreet.core.BaseResponse;
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

public class BaseResultParserTest {
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
        setXml("/result-samples/subscribe-sample2.xml");
        BaseResultParser underStudy = new BaseResultParser(xmlResponseReader);
        BaseResponse result = underStudy.parse(xmlDoc);
        assertThat(result.getStatusResponse(), is("failure"));
        assertThat(result.getMessageResponse(), is("opt status updated"));
        assertThat(result.isSuccessful(), is(false));
        assertThat(result.getXmlResponse(), is(expectedXml));
    }

    @Test
    public void parseMessage() throws Exception {
        setXml("/result-samples/subscribe-sample1.xml");
        BaseResultParser underStudy = new BaseResultParser(xmlResponseReader);
        BaseResponse result = underStudy.parse(xmlDoc);
        assertThat(result.getStatusResponse(), is("success"));
        assertThat(result.getMessageResponse(), is("opt status updated"));
        assertThat(result.isSuccessful(), is(true));
        assertThat(result.getXmlResponse(), is(expectedXml));
    }

    void setXml(String path) throws Exception {
        Path xmlPath = Paths.get(getClass().getResource(path).toURI());
        xmlDoc = parseDocument(xmlPath);
        expectedXml = parseString(xmlPath);
    }
}
