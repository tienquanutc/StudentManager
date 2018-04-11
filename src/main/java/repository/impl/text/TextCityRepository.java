package repository.impl.text;

import io.txt.CityTxtMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repository.impl.memoryDb.CityMemDbRepository;

import java.io.FileNotFoundException;

public class TextCityRepository extends CityMemDbRepository {
    protected static Logger LOG = LoggerFactory.getLogger(TextCityRepository.class);

    public TextCityRepository(String dbPath) {
        super(dbPath);
    }

    @Override
    public void loadDb(String dbPath) {
        try {
            citiesCache = new CityTxtMapper().read(dbPath);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Failed read file " + dbPath + " because " + e.getMessage());
        }
    }
}
