package net.lospi.mogreet.parse;

import net.lospi.mogreet.core.SendResponse;
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

public class SendResultParserTest {
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
        setXml("/result-samples/send-sample2.xml");
        SendResultParser underStudy = new SendResultParser(xmlResponseReader);
        SendResponse result = underStudy.parse(xmlDoc);
        assertThat(result.getMessageIdResponse(), is(2147483647));
        assertThat(result.getHashResponse(), is("ff9rf970"));
        assertThat(result.getStatusResponse(), is("failure"));
        assertThat(result.getMessageResponse(), is("API Request Accepted"));
        assertThat(result.isSuccessful(), is(false));
        assertThat(result.getXmlResponse(), is(expectedXml));
    }

    @Test
    public void parseResponse() throws Exception {
        setXml("/result-samples/send-sample1.xml");
        SendResultParser underStudy = new SendResultParser(xmlResponseReader);
        SendResponse result = underStudy.parse(xmlDoc);
        assertThat(result.getMessageIdResponse(), is(2147483647));
        assertThat(result.getHashResponse(), is("ff9rf970"));
        assertThat(result.getStatusResponse(), is("success"));
        assertThat(result.getMessageResponse(), is("API Request Accepted"));
        assertThat(result.isSuccessful(), is(true));
        assertThat(result.getXmlResponse(), is(expectedXml));
    }

    void setXml(String path) throws Exception {
        Path xmlPath = Paths.get(getClass().getResource(path).toURI());
        xmlDoc = parseDocument(xmlPath);
        expectedXml = parseString(xmlPath);
    }
}
