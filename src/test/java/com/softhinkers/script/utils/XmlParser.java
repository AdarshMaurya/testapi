package com.softhinkers.script.utils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

public class XmlParser {

    public static void checkXml(String xml, String tagsToCheck, String expectedValue) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new InputSource(new StringReader(xml)));
            Element rootElement = document.getDocumentElement();
            String actualValue = getString(tagsToCheck, rootElement);
            assertEquals(expectedValue, actualValue);
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    private static String getString(String tagName, Element element) {
        NodeList list = element.getElementsByTagName(tagName);
        if (list != null && list.getLength() > 0) {
            NodeList subList = list.item(0).getChildNodes();

            if (subList != null && subList.getLength() > 0) {
                return subList.item(0).getNodeValue();
            }
        }
        return null;
    }

}
