package service.impl;

import io.PathConfig;
import io.sql.Sql;
import io.txt.CityTxtMapper;
import io.txt.StudentTxtMapper;
import io.txt.TxtConfig;
import model.City;
import model.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repository.CityRepository;
import repository.StudentRepository;
import repository.impl.text.TextCityRepository;
import repository.impl.text.TextStudentRepository;
import service.StudentService;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class TxtStudentService extends MemDbStudentService {
    protected static Logger LOG = LoggerFactory.getLogger(TxtStudentService.class);

    public TxtStudentService(PathConfig config) {
        super(config);
    }


    @Override
    protected void setupRepository() {
        studentRepository = new TextStudentRepository(config.getStudentPath());
        cityRepository = new TextCityRepository(config.getCityPath());
    }

    @Override
    public void disposed() {
        try {
            new StudentTxtMapper().saveToFile(studentRepository.getAllStudents(), config.getStudentPath());
            new CityTxtMapper().saveToFile(cityRepository.getAllCities(), config.getCityPath());
        } catch (Exception e) {
            throw new RuntimeException("Failed save file because " + e.getMessage());
        }
    }
}
