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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class MemDbStudentService implements StudentService {
    protected static Logger LOG = LoggerFactory.getLogger(MemDbStudentService.class);

    protected PathConfig config;
    protected StudentRepository studentRepository;
    protected CityRepository cityRepository;
    
    public MemDbStudentService(PathConfig config) {
        this.config = config;
        setupRepository();
        mapCity();
    }

    protected abstract void setupRepository();

    private void mapCity() {
        try {
            List<Student> students = studentRepository.getAllStudents();
            Map<Integer, City> mapCity = new HashMap<>();
            cityRepository.getAllCities().forEach(c -> mapCity.put(c.getId(), c));
            students.forEach(s -> s.setCity(mapCity.get(s.getCityId())));
        } catch (Exception ignored) {
        }

    }

    @Override
    public List<Student> getAllStudents() throws Exception {
        return studentRepository.getAllStudents();
    }

    @Override
    public List<Student> getAllStudents(Predicate<Student> filter) throws Exception {
        return studentRepository.getAllStudents().stream().filter(filter).collect(Collectors.toList());
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
    public abstract void disposed();
}
