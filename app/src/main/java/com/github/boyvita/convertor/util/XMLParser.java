package com.github.boyvita.convertor.util;

import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class XMLParser {
    HashMap<String, Double> courses;

    public Double get(String CharCode) {
        return courses.get(CharCode);
    }

    public XMLParser(String date) throws IOException, SAXException, ParserConfigurationException {
        try {
            URL url = new URL("http://www.cbr.ru/scripts/XML_daily.asp?date_req=" + date);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse(new InputSource(url.openStream()));
            document.getDocumentElement().normalize();
            courses = new HashMap<>();
            courses.put("RUB", new Double(1));
            NodeList nodeList = document.getElementsByTagName("Valute");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                Element elem = (Element) node;
                String charCode = elem.getElementsByTagName("CharCode").item(0).getChildNodes().item(0).getNodeValue();
                String nominal = elem.getElementsByTagName("Nominal").item(0).getChildNodes().item(0).getNodeValue();
                String value = elem.getElementsByTagName("Value").item(0).getChildNodes().item(0).getNodeValue().replace(',', '.');
                courses.put(charCode, new Double(value) / new Double(nominal));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

}
