package repository;

import model.City;
import model.Student;

import java.sql.SQLException;
import java.util.List;

public interface CityRepository {
    List<City> getAllCities() throws Exception;

    List<City> getCities(int offset, int limit) throws Exception;

    boolean insertCity(City city) throws Exception;

    City getCityById(int id) throws Exception;

    boolean updateCity(City city) throws Exception;

    boolean deleteCity(City city) throws Exception;
    boolean deleteCity(int id) throws Exception;

    int batchUpdateCity(List<City> cities) throws Exception;

    int batchDeleteCity(List<Integer> ids) throws Exception;

    int batchInsertCity(List<City> cities) throws Exception;

}
