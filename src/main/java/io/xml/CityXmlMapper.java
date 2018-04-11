package io.xml;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import model.City;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

public class CityXmlMapper implements XmlMapper<City> {
    private static final String TAG_ROOT_LIST = "danhsach";
    private static final String TAG_LIST_CITY = "quequan";
    private static final String TAG_CITY_ID = "matinh";
    private static final String TAG_CITY_NAME = "tentinh";

    @Override
    public Document toDocument(List<City> list) throws ParserConfigurationException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        Document document = docBuilder.newDocument();
        Element rootListCitiesElement = document.createElement(TAG_ROOT_LIST);
        document.appendChild(rootListCitiesElement);
        list.forEach(c -> {
            appendChildCity(document, rootListCitiesElement, c);
        });
        return document;
    }

    private void appendChildCity(Document document, Element rootListCitiesElement, City city) {
        Element cityIdElement = document.createElement(TAG_CITY_ID);
        cityIdElement.setTextContent(String.valueOf(city.getId()));
        Element cityNameElement = document.createElement(TAG_CITY_NAME);
        cityNameElement.setTextContent(city.getName());

        Element cityElement = document.createElement(TAG_LIST_CITY);
        cityElement.appendChild(cityIdElement);
        cityElement.appendChild(cityNameElement);

        rootListCitiesElement.appendChild(cityElement);
    }

    @Override
    public List<City> toModels(Document document) {
        List<City> cities = new ArrayList<>();
        NodeList citiesNodeList = document.getElementsByTagName(TAG_LIST_CITY);
        for (int i = 0; i < citiesNodeList.getLength(); ++i) {
            cities.add(toModel((Element) citiesNodeList.item(i)));
        }
        return cities;
    }


    private City toModel(Element element) {
        String sId = element.getElementsByTagName(TAG_CITY_ID).item(0).getTextContent();
        int id = Integer.valueOf(sId);
        String name = element.getElementsByTagName(TAG_CITY_NAME).item(0).getTextContent();
        return new City(id, name);
    }
}
