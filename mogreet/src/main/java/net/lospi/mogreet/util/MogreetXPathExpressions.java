package net.lospi.mogreet.util;

import net.lospi.exception.DevelopmentException;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

public class MogreetXPathExpressions {
    public static final XPathExpression STATUS, CODE,
            MESSAGE_ID, MESSAGE_RESPONSE, MESSAGE,
            HASH,
            MEDIA_NAME, MEDIA_CONTENT_ID, MEDIA_SMART_URL;

    private MogreetXPathExpressions() {

    }

    static {
        try {
            XPath xPath = XPathFactory.newInstance().newXPath();
            STATUS = xPath.compile("/response/@status");
            CODE = xPath.compile("/response/@code");

            MESSAGE_ID = xPath.compile("/response/message_id/text()");
            MESSAGE_RESPONSE = xPath.compile("/response/messageResponse/text()");
            MESSAGE = xPath.compile("/response/message/text()");

            HASH = xPath.compile("/response/hash/text()");

            MEDIA_NAME = xPath.compile("/response/media/name/text()");
            MEDIA_CONTENT_ID = xPath.compile("/response/media/content_id/text()");
            MEDIA_SMART_URL = xPath.compile("/response/media/smart_url/text()");
        } catch (XPathExpressionException e) {
            throw new DevelopmentException(String.format("XPath construction problem: %s", e));
        }
    }
}
