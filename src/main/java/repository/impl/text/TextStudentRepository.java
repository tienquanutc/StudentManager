package repository.impl.text;

import io.txt.StudentTxtMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repository.impl.memoryDb.StudentMemDbRepository;

import java.io.FileNotFoundException;

public class TextStudentRepository extends StudentMemDbRepository {
    protected static Logger LOG = LoggerFactory.getLogger(TextStudentRepository.class);

    public TextStudentRepository(String dbPath) {
        super(dbPath);
    }

    @Override
    public void loadDb(String dbPath) {
        try {
            this.studentList = new StudentTxtMapper().read(dbPath);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Failed read file " + dbPath + " because " + e.getMessage());
        }
    }
}
