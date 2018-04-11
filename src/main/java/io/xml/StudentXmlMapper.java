package io.xml;

import model.Student;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import util.DateUtils;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StudentXmlMapper implements XmlMapper<Student> {

    private static final String TAG_ROOT_LIST = "danhsach";
    public static final String TAG_STUDENT = "thisinh";
    public static final String TAG_STUDENT_ID = "mathisinh";
    public static final String TAG_STUDENT_NAME = "ten";
    public static final String TAG_STUDENT_CITY_ID = "matinh";
    public static final String TAG_STUDENT_BIRTHDAY = "ngaysinh";
    public static final String TAG_STUDENT_GENDER = "gioitinh";
    public static final String TAG_STUDENT_MATH_SCORE = "toan";
    public static final String TAG_STUDENT_PHYSIC_SCORE = "ly";
    public static final String TAG_STUDENT_CHEMICAL_SCORE = "hoa";

    @Override
    public Document toDocument(List<Student> list) throws ParserConfigurationException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        Document document = docBuilder.newDocument();
        Element rootListStudentsElement = document.createElement(TAG_ROOT_LIST);
        document.appendChild(rootListStudentsElement);
        list.forEach(s -> {
            Element studentElement = document.createElement(TAG_STUDENT);
            rootListStudentsElement.appendChild(studentElement);
            appendChildStudent(document, studentElement, s);
        });
        return document;
    }

    private void appendChildStudent(Document document, Element toElement, Student s) {
        Element idElement = createElement(document, TAG_STUDENT_ID, s.getId());
        toElement.appendChild(idElement);

        Element nameElement = createElement(document, TAG_STUDENT_NAME, s.getFullName());
        toElement.appendChild(nameElement);

        Element cityIdElement = createElement(document, TAG_STUDENT_CITY_ID, s.getCityId());
        toElement.appendChild(cityIdElement);

        Element birthDayElement = createElement(document, TAG_STUDENT_BIRTHDAY, s.getBirthday());
        toElement.appendChild(birthDayElement);

        Element genderElement = createElement(document, TAG_STUDENT_GENDER, s.isGender());
        toElement.appendChild(genderElement);

        Element mathScoreElement = createElement(document, TAG_STUDENT_MATH_SCORE, s.getMathScore());
        toElement.appendChild(mathScoreElement);

        Element physicScoreElement = createElement(document, TAG_STUDENT_PHYSIC_SCORE, s.getPhysicScore());
        toElement.appendChild(physicScoreElement);

        Element chemicalScoreElement = createElement(document, TAG_STUDENT_CHEMICAL_SCORE, s.getChemicalScore());
        toElement.appendChild(chemicalScoreElement);
    }

    private Element createElement(Document document, String tagName, String content) {
        Element element = document.createElement(tagName);
        element.setTextContent(content);
        return element;
    }

    private Element createElement(Document document, String tagName, Float content) {
        Element element = document.createElement(tagName);
        element.setTextContent(String.valueOf(content));
        return element;
    }

    private Element createElement(Document document, String tagName, Integer content) {
        Element element = document.createElement(tagName);
        element.setTextContent(String.valueOf(content));
        return element;
    }

    private Element createElement(Document document, String tagName, Date content) {
        Element element = document.createElement(tagName);
        element.setTextContent(DateUtils.format(content));
        return element;
    }

    private Element createElement(Document document, String tagName, Boolean content) {
        Element element = document.createElement(tagName);
        element.setTextContent(String.valueOf(content));
        return element;
    }

    @Override
    public List<Student> toModels(Document document) throws ParseException {
        NodeList studentNodeList = document.getElementsByTagName(TAG_STUDENT);
        List<Student> students = new ArrayList<>();
        for (int i = 0; i < studentNodeList.getLength(); ++i) {
            students.add(toModel((Element) studentNodeList.item(i)));
        }
        return students;
    }

    private Student toModel(Element element) throws ParseException {
        int id = getIntContentByFirstTagName(element, TAG_STUDENT_ID);
        String name = getTextContentByFirstTagName(element, TAG_STUDENT_NAME);
        int cityId = getIntContentByFirstTagName(element, TAG_STUDENT_CITY_ID);
        Date birthDay = getDateContentByFirstTagName(element, TAG_STUDENT_BIRTHDAY);
        boolean gender = getBooleanContentByFirstTagName(element, TAG_STUDENT_GENDER);
        float mathScore = getFloatContentByFirstTagName(element, TAG_STUDENT_MATH_SCORE);
        float physicScore = getFloatContentByFirstTagName(element, TAG_STUDENT_PHYSIC_SCORE);
        float chemicalScore = getFloatContentByFirstTagName(element, TAG_STUDENT_CHEMICAL_SCORE);

        Student s = new Student();
        s.setId(id);
        s.setFullName(name);
        s.setCityId(cityId);
        s.setBirthday(birthDay);
        s.setGender(gender);
        s.setMathScore(mathScore);
        s.setPhysicScore(physicScore);
        s.setChemicalScore(chemicalScore);
        return s;
    }

    private Integer getIntContentByFirstTagName(Element element, String tagName) {
        String text = element.getElementsByTagName(tagName).item(0).getTextContent();
        return Integer.valueOf(text);
    }

    private Float getFloatContentByFirstTagName(Element element, String tagName) {
        String text = element.getElementsByTagName(tagName).item(0).getTextContent();
        return Float.valueOf(text);
    }

    private Date getDateContentByFirstTagName(Element element, String tagName) throws ParseException {
        String text = element.getElementsByTagName(tagName).item(0).getTextContent();
        return DateUtils.parse(text);
    }

    private Boolean getBooleanContentByFirstTagName(Element element, String tagName) {
        String text = element.getElementsByTagName(tagName).item(0).getTextContent();
        return Boolean.valueOf(text);
    }

    private String getTextContentByFirstTagName(Element element, String tagName) {
        return element.getElementsByTagName(tagName).item(0).getTextContent();
    }
}
