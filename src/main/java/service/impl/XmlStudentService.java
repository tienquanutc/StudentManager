package service.impl;

import io.xml.CityXmlMapper;
import io.xml.StudentXmlMapper;
import io.xml.XmlConfig;
import repository.impl.xml.XmlCityRepository;
import repository.impl.xml.XmlStudentRepository;

public class XmlStudentService extends MemDbStudentService {

    public XmlStudentService(XmlConfig config) {
        super(config);
    }

    @Override
    protected void setupRepository() {
        studentRepository = new XmlStudentRepository(config.getStudentPath());
        cityRepository = new XmlCityRepository(config.getCityPath());
    }

    @Override
    public void disposed() {
        try {
            new StudentXmlMapper().write(studentRepository.getAllStudents(), config.getStudentPath());
            new CityXmlMapper().write(cityRepository.getAllCities(), config.getCityPath());
        } catch (Exception e) {
            throw new RuntimeException("Failed save file because " + e.getMessage());
        }
    }
}
