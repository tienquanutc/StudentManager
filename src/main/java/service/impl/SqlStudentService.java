package service.impl;

import io.sql.Sql;
import model.City;
import model.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repository.CityRepository;
import repository.StudentRepository;
import repository.impl.sql.SqlCityRepository;
import repository.impl.sql.SqlStudentRepository;
import service.StudentService;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class SqlStudentService implements StudentService {
    protected static Logger LOG = LoggerFactory.getLogger(Sql.class);

    private Sql sql;

    private StudentRepository studentRepository;
    private CityRepository cityRepository;

    private Map<Integer, City> citiesCache;

    public SqlStudentService(Sql sql) {
        this.sql = sql;
        this.studentRepository = new SqlStudentRepository(sql);
        this.cityRepository = new SqlCityRepository(sql);
        citiesCache = new HashMap<>();
        try {
            cityRepository.getAllCities().forEach(c -> citiesCache.put(c.getId(), c));
        } catch (Exception e) {
            LOG.warn("Failed load cities from db because " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<Student> getAllStudents() throws Exception {
        List<Student> students = studentRepository.getAllStudents();
        for (Student s : students) {
            s.setCity(citiesCache.get(s.getCityId()));
        }
        return students;
    }

    @Override
    public List<Student> getAllStudents(Predicate<Student> filter) throws Exception {
        return getAllStudents().stream().filter(filter).collect(Collectors.toList());
    }

    @Override
    public List<City> getAllCities() throws Exception {
        return cityRepository.getAllCities();
    }

    @Override
    public City getCityById(int id) throws Exception {
        return cityRepository.getCityById(id);
    }

    @Override
    public boolean insertStudent(Student student) throws Exception {
        student.setCity(citiesCache.get(student.getCityId()));
        return studentRepository.insertStudent(student);
    }

    @Override
    public boolean removeStudent(int id) throws Exception {
        return studentRepository.deleteStudent(id);
    }

    @Override
    public boolean updateStudent(Student update) throws Exception {
        return studentRepository.updateStudent(update);
    }

    @Override
    public void disposed() {
        sql.close();
    }


}
