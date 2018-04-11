package app;

import io.txt.CityTxtMapper;
import io.xml.CityXmlMapper;
import model.City;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.List;

public class MigrateTxt2Xml {
    public static void main(String[] args) throws FileNotFoundException, TransformerException, ParserConfigurationException, ParseException {
        List<City> cities = new CityTxtMapper().read("/home/quannt/Desktop/lazy_sql/src/main/resources/quequan.txt");
        new CityXmlMapper().write(cities, "/home/quannt/Desktop/lazy_sql/src/main/resources/quequan.xml");
    }
}
