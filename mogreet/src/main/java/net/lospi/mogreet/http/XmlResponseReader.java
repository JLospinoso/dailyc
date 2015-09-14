package net.lospi.mogreet.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.StringWriter;

public class XmlResponseReader {
    private static final Logger LOG = LoggerFactory.getLogger(XmlResponseReader.class);
    private final TransformerFactory factory;

    public XmlResponseReader(TransformerFactory factory) {
        this.factory = factory;
    }

    public String read(Document xmlDoc) throws TransformerException {
        Transformer transformer = factory.newTransformer();
        StringWriter writer = new StringWriter();
        Result result = new StreamResult(writer);
        Source source = new DOMSource(xmlDoc);
        transformer.transform(source, result);
        try {
            writer.close();
        } catch (IOException e) {
            LOG.error("Error closing writer: %s", e);
        }
        return writer.toString();
    }
}