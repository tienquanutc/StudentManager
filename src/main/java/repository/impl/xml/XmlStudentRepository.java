package repository.impl.xml;

import io.txt.StudentTxtMapper;
import io.xml.StudentXmlMapper;
import io.xml.XmlConfig;
import model.Student;
import repository.StudentRepository;
import repository.impl.memoryDb.StudentMemDbRepository;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

public class XmlStudentRepository extends StudentMemDbRepository {

    public XmlStudentRepository(String dbPath) {
        super(dbPath);
    }

    @Override
    public void loadDb(String dbPath) {
        try {
            this.studentList = new StudentXmlMapper().readFromFile(dbPath);
        } catch (Exception e) {
            throw new RuntimeException("Failed read file " + dbPath + " because " + e.getMessage());
        }
    }
}
