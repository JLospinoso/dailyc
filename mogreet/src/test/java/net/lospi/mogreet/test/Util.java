package net.lospi.mogreet.test;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Util {
    private Util() {

    }
    public static Document parseDocument(Path xmlPath) throws ParserConfigurationException, SAXException, IOException {
        File fXmlFile = new File(xmlPath.toUri());
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        return dBuilder.parse(fXmlFile);
    }

    public static String parseString(Path xmlPath) throws ParserConfigurationException, SAXException, IOException {
        File fXmlFile = new File(xmlPath.toUri());
        byte[] fileAsBytes = Files.readAllBytes(fXmlFile.toPath());
        return new String(fileAsBytes);
    }
}
