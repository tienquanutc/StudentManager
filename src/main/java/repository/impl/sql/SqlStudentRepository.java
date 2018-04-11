package repository.impl.sql;

import io.sql.Sql;
import model.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repository.StudentRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static model.Student.*;

public class SqlStudentRepository implements StudentRepository {
    protected static Logger LOG = LoggerFactory.getLogger(Sql.class);
    private static final String FULL_COLUMN_SELECTED = String.join(", ", COLUMN_ID, COLUMN_FULL_NAME, COLUMN_BIRTHDAY
            , COLUMN_GENDER, COLUMN_CITY_ID, COLUMN_MATH_SCORE, COLUMN_PHYSIC_SCORE, COLUMN_CHEMICAL_SCORE);


    private Sql sql;

    public SqlStudentRepository(Sql sql) {
        this.sql = sql;
    }

    @Override
    public List<Student> getAllStudents() throws SQLException {
        String query = "SELECT " + FULL_COLUMN_SELECTED + " FROM " + TABLE_STUDENT;
        return sql.query(query).toModels(Student.class);
    }

    @Override
    public List<Student> getStudents(int offset, int limit) throws SQLException {
        String query = "SELECT " + FULL_COLUMN_SELECTED + " FROM " + TABLE_STUDENT + " OFFSET ? ROWS FETCH NEXT ? ROWS ONLY;";
        return sql.query(query, offset, limit).toModels(Student.class);
    }

    @Override
    public boolean insertStudent(Student student) throws SQLException {
        String query = "INSERT INTO " + TABLE_STUDENT + "(" + COLUMN_ID + ", " + COLUMN_FULL_NAME + ", " + COLUMN_BIRTHDAY + ", " + COLUMN_GENDER + ", " + COLUMN_CITY_ID + ", "
                + COLUMN_MATH_SCORE + ", " + COLUMN_PHYSIC_SCORE + ", " + COLUMN_CHEMICAL_SCORE + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
        return sql.executeUpdate(query, populateStudentToParam(student)) == 1;
    }

    @Override
    public Student getStudentById(int id) throws SQLException {
        String query = "SELECT " + FULL_COLUMN_SELECTED + " FROM " + TABLE_STUDENT + " WHERE " + COLUMN_ID + "= " + id;
        List<Student> students = sql.query(query).toModels(Student.class);
        return students.size() == 0 ? null : students.get(0);
    }

    @Override
    public boolean updateStudent(Student student) throws SQLException {
        String query = "UPDATE " + TABLE_STUDENT + " SET " + COLUMN_ID + " = ?," + COLUMN_FULL_NAME + " = ?" + ", " + COLUMN_BIRTHDAY + "= ?" + ", " + COLUMN_GENDER + " = ?" + ", " + COLUMN_CITY_ID + " = ?,"
                + COLUMN_MATH_SCORE + " = ?," + COLUMN_PHYSIC_SCORE + " = ?," + COLUMN_CHEMICAL_SCORE + "= ? WHERE Id = ?";
        return sql.executeUpdate(query, student.getId(), student.getFullName(), student.getBirthday(), student.getGender()
                , student.getCityId(), student.getMathScore(), student.getPhysicScore()
                , student.getChemicalScore(), student.getId()) == 1;
    }

    @Override
    public boolean deleteStudent(Student student) throws SQLException {
        return deleteStudent(student.getId());
    }

    @Override
    public boolean deleteStudent(int id) throws SQLException {
        String query = "DELETE FROM " + TABLE_STUDENT + " WHERE " + COLUMN_ID + "= " + id;
        return sql.executeUpdate(query) == 1;
    }

    @Override
    public int batchUpdateStudent(Student student) {
        return 0;
    }

    @Override
    public int batchDeleteStudent(Student student) {
        return 0;
    }

    private static List<Object> populateStudentToParam(Student student) {
        List<Object> param = new ArrayList<>();
        param.add(student.getId());
        param.add(student.getFullName());
        param.add(student.getBirthday());
        param.add(student.getGender());
        param.add(student.getCityId());
        param.add(student.getMathScore());
        param.add(student.getPhysicScore());
        param.add(student.getChemicalScore());
        return param;
    }
}
