package net.lospi.mogreet.parse;

import net.lospi.exception.DevelopmentException;
import net.lospi.exception.MogreetException;
import net.lospi.mogreet.core.SendResponse;
import net.lospi.mogreet.http.XmlResponseReader;
import org.w3c.dom.Document;

import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import static net.lospi.mogreet.util.MogreetXPathExpressions.*;

public class SendResultParser implements Parser<SendResponse> {
    private final XmlResponseReader xmlResponseReader;

    public SendResultParser(XmlResponseReader xmlResponseReader) {
        this.xmlResponseReader = xmlResponseReader;
    }

    @Override
    public SendResponse parse(Document xmlDoc) throws MogreetException {
        try {
            int codeResponse = ((Double) (CODE.evaluate(xmlDoc, XPathConstants.NUMBER))).intValue();
            String statusResponse = (String) (STATUS.evaluate(xmlDoc, XPathConstants.STRING));
            String messageResponse = (String) (MESSAGE_RESPONSE.evaluate(xmlDoc, XPathConstants.STRING));
            if ("".equals(messageResponse)) {
                messageResponse = (String) (MESSAGE.evaluate(xmlDoc, XPathConstants.STRING));
            }
            String xmlResponse = xmlResponseReader.read(xmlDoc);
            int messageIdResponse = ((Double) (MESSAGE_ID.evaluate(xmlDoc, XPathConstants.NUMBER))).intValue();
            String hashResponse = (String) (HASH.evaluate(xmlDoc, XPathConstants.STRING));
            return new SendResponse(1 == codeResponse, statusResponse, messageResponse,
                    messageIdResponse, hashResponse,
                    xmlResponse);
        } catch (XPathExpressionException e) {
            throw new DevelopmentException(String.format("Problem evaluating XPATH: %s", e));
        } catch (TransformerException e) {
            throw new MogreetException(e);
        }
    }
}
