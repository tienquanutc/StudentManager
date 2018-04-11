package repository;

import model.Student;

import java.sql.SQLException;
import java.util.List;

public interface StudentRepository {
    List<Student> getAllStudents() throws Exception;

    List<Student> getStudents(int offset, int limit) throws Exception;

    boolean insertStudent(Student student) throws Exception;

    Student getStudentById(int id) throws Exception;

    boolean updateStudent(Student student) throws Exception;

    boolean deleteStudent(Student student) throws Exception;

    boolean deleteStudent(int id) throws Exception;

    int batchUpdateStudent(Student student) throws Exception;

    int batchDeleteStudent(Student student) throws Exception;
}
