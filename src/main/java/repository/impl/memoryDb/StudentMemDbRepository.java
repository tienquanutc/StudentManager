package repository.impl.memoryDb;

import io.txt.StudentTxtMapper;
import model.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repository.StudentRepository;
import repository.impl.text.TextStudentRepository;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

public abstract class StudentMemDbRepository implements StudentRepository {
    protected static Logger LOG = LoggerFactory.getLogger(StudentMemDbRepository.class);

    protected List<Student> studentList;

    public StudentMemDbRepository(String dbPath) {
        loadDb(dbPath);
    }

    public abstract void loadDb(String dbPath);

    @Override
    public List<Student> getAllStudents() {
        return studentList;
    }

    @Override
    public List<Student> getStudents(int offset, int limit) {
        return studentList.stream().skip(offset).limit(limit).collect(Collectors.toList());
    }

    @Override
    public boolean insertStudent(Student student) {
        if (studentList.contains(student)) {
            LOG.debug("duplicate id " + student.getId());
            throw new RuntimeException("duplicate id " + student.getId());
        }
        studentList.add(student);
        return true;
    }

    @Override
    public Student getStudentById(int id) {
        return studentList.stream().filter(s -> s.getId() == id).findFirst().orElse(null);
    }

    @Override
    public boolean updateStudent(Student student) {
        if (!studentList.contains(student)) {
            LOG.debug("id " + student.getId() + " not found to update");
            throw new RuntimeException("id " + student.getId() + " not found to update");
        }
        studentList.set(studentList.indexOf(student), student);
        return false;
    }

    @Override
    public boolean deleteStudent(Student student) {
        return studentList.remove(student);
    }

    @Override
    public boolean deleteStudent(int id) {
        return studentList.removeIf(s -> s.getId() == id);
    }

    @Override
    public int batchUpdateStudent(Student student) {
        throw new RuntimeException("method not already implement");
    }

    @Override
    public int batchDeleteStudent(Student student) {
        throw new RuntimeException("method not already implement");
    }
}
