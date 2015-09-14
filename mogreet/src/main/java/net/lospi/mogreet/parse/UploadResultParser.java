package net.lospi.mogreet.parse;

import net.lospi.exception.DevelopmentException;
import net.lospi.exception.MogreetException;
import net.lospi.mogreet.core.UploadResponse;
import net.lospi.mogreet.http.XmlResponseReader;
import org.w3c.dom.Document;

import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;

import static net.lospi.mogreet.util.MogreetXPathExpressions.*;

public class UploadResultParser implements Parser<UploadResponse> {
    private final XmlResponseReader xmlResponseReader;

    public UploadResultParser(XmlResponseReader xmlResponseReader) {
        this.xmlResponseReader = xmlResponseReader;
    }

    @Override
    public UploadResponse parse(Document xmlDoc) throws MogreetException {
        try {
            int codeResponse = ((Double) (CODE.evaluate(xmlDoc, XPathConstants.NUMBER))).intValue();
            String statusResponse = getStringFrom(STATUS, xmlDoc);
            String messageResponse = getStringFrom(MESSAGE_RESPONSE, xmlDoc);
            if("".equals(messageResponse)) {
                messageResponse = (String) (MESSAGE.evaluate(xmlDoc, XPathConstants.STRING));
            }
            String nameResponse = getStringFrom(MEDIA_NAME, xmlDoc);
            String contentIdResponse = getStringFrom(MEDIA_CONTENT_ID, xmlDoc);
            String smartUrlResponse = getStringFrom(MEDIA_SMART_URL, xmlDoc);
            String xmlResponse = xmlResponseReader.read(xmlDoc);
            return new UploadResponse(1 == codeResponse, statusResponse, messageResponse,
                    nameResponse, contentIdResponse, smartUrlResponse, xmlResponse);
        } catch (XPathExpressionException e) {
            throw new DevelopmentException(String.format("Problem evaluating XPATH: %s", e));
        } catch (TransformerException e) {
            throw new MogreetException(e);
        }
    }

    String getStringFrom(XPathExpression expression, Document xmlDoc) throws XPathExpressionException {
        return (String) (expression.evaluate(xmlDoc, XPathConstants.STRING));
    }
}
