package service;

import app.AppConfig;
import io.sql.Sql;
import model.City;
import model.Student;
import service.impl.SqlStudentService;
import service.impl.TxtStudentService;
import service.impl.XmlStudentService;

import java.util.List;
import java.util.function.Predicate;

public interface StudentService {
    List<Student> getAllStudents() throws Exception;

    List<Student> getAllStudents(Predicate<Student> filter) throws Exception;

    List<City> getAllCities() throws Exception;

    City getCityById(int id) throws Exception;

    boolean insertStudent(Student student) throws Exception;

    boolean removeStudent(int id) throws Exception;

    boolean updateStudent(Student update) throws Exception;

    void disposed();

    static void createServiceFor(AppConfig config) {
        final String mode = config.getMode();
        StudentService service;
        switch (mode) {
            case "sql":
                service = new SqlStudentService(new Sql(config.getSqlConfig()));
                break;
            case "txt":
                service = new TxtStudentService(config.getTxtConfig());
                break;
            case "xml":
                service = new XmlStudentService(config.getXmlConfig());
                break;
            default:
                throw new RuntimeException("mode not supported yet!!!");
        }
        config.setMgmtService(service);
    }
}
