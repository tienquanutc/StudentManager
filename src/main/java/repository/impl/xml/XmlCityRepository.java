package repository.impl.xml;

import io.xml.CityXmlMapper;
import io.xml.XmlConfig;
import model.City;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repository.CityRepository;
import repository.impl.memoryDb.CityMemDbRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class XmlCityRepository extends CityMemDbRepository {
    protected static Logger LOG = LoggerFactory.getLogger(XmlCityRepository.class);

    public XmlCityRepository(String dbPath) {
        super(dbPath);
    }

    @Override
    public void loadDb(String dbPath) {
        try {
            citiesCache = new CityXmlMapper().readFromFile(dbPath);
        } catch (Exception e) {
            LOG.error("Failed parse " + dbPath + " because " + e.getMessage());
            throw new RuntimeException("Failed parse " + dbPath + " because " + e.getMessage());
        }
    }
}
