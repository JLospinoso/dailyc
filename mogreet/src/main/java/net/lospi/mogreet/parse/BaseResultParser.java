package net.lospi.mogreet.parse;

import net.lospi.exception.DevelopmentException;
import net.lospi.exception.MogreetException;
import net.lospi.mogreet.core.BaseResponse;
import net.lospi.mogreet.http.XmlResponseReader;
import org.w3c.dom.Document;

import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import static net.lospi.mogreet.util.MogreetXPathExpressions.*;

public class BaseResultParser implements Parser<BaseResponse> {
    private final XmlResponseReader xmlResponseReader;

    public BaseResultParser(XmlResponseReader xmlResponseReader) {
        this.xmlResponseReader = xmlResponseReader;
    }

    @Override
    public BaseResponse parse(Document xmlDoc) throws MogreetException {
        try {
            String responseStatus = (String) (STATUS.evaluate(xmlDoc, XPathConstants.STRING));
            int responseCode = ((Double) (CODE.evaluate(xmlDoc, XPathConstants.NUMBER))).intValue();
            String messageResponse = (String) (MESSAGE_RESPONSE.evaluate(xmlDoc, XPathConstants.STRING));
            if("".equals(messageResponse)) {
                messageResponse = (String) (MESSAGE.evaluate(xmlDoc, XPathConstants.STRING));
            }
            String xmlResponse = xmlResponseReader.read(xmlDoc);
            return new BaseResponse(1 == responseCode, responseStatus, messageResponse, xmlResponse);
        } catch (XPathExpressionException e) {
            throw new DevelopmentException(String.format("Problem evaluating XPATH: %s", e));
        } catch (TransformerException e) {
            throw new MogreetException(e);
        }
    }
}
