package repository.impl.memoryDb;

import model.City;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repository.CityRepository;

import java.util.List;
import java.util.stream.Collectors;

public abstract class CityMemDbRepository implements CityRepository {
    protected static Logger LOG = LoggerFactory.getLogger(CityMemDbRepository.class);

    protected List<City> citiesCache;

    public CityMemDbRepository(String dbPath) {
        loadDb(dbPath);
    }

    public abstract void loadDb(String dbPath);

    @Override
    public List<City> getAllCities() {
        return citiesCache;
    }

    @Override
    public List<City> getCities(int offset, int limit) {
        return citiesCache.stream().skip(offset).limit(limit).collect(Collectors.toList());
    }

    @Override
    public boolean insertCity(City city) {
        if (citiesCache.contains(city)) {
            LOG.debug("duplicate key " + city.getId());
            throw new RuntimeException("failed to insert because duplicate city id " + city.getId()
                    + "with " + citiesCache.get(city.getId()));
        }
        citiesCache.add(city);
        return true;
    }

    @Override
    public City getCityById(int id) {
        return citiesCache.get(id);
    }

    @Override
    public boolean updateCity(City city) {
        if (!citiesCache.contains(city)) {
            LOG.debug("Not found city with id = " + city.getId());
            throw new RuntimeException("Not found city with id = " + city.getId());
        }
        citiesCache.set(citiesCache.indexOf(city), city);
        return true;
    }

    @Override
    public boolean deleteCity(City city) {
        deleteCity(city.getId());
        return true;
    }

    @Override
    public boolean deleteCity(int id) {
        citiesCache.remove(id);
        return true;
    }

    @Override
    public int batchUpdateCity(List<City> cities) {
        throw new RuntimeException("method not already implement");
    }

    @Override
    public int batchDeleteCity(List<Integer> ids) {
        throw new RuntimeException("method not already implement");
    }

    @Override
    public int batchInsertCity(List<City> cities) {
        throw new RuntimeException("method not already implement");
    }
}
