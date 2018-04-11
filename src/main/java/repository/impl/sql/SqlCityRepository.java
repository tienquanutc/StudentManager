package repository.impl.sql;

import io.sql.Sql;
import model.City;
import repository.CityRepository;

import java.sql.SQLException;
import java.util.List;

import static model.City.COLUMN_ID;
import static model.City.COLUMN_NAME;
import static model.City.TABLE_CITY;

public class SqlCityRepository implements CityRepository {

    protected Sql sql;

    public SqlCityRepository(Sql sql) {
        this.sql = sql;
    }

    @Override
    public List<City> getAllCities() throws SQLException {
        String query = "SELECT " + COLUMN_ID + ", " + COLUMN_NAME + " FROM " + TABLE_CITY;
        return sql.query(query).toModels(City.class);
    }

    @Override
    public List<City> getCities(int offset, int limit) throws SQLException {
        String query = "SELECT " + COLUMN_ID + ", " + COLUMN_NAME
                + ", FROM " + TABLE_CITY + " OFFSET ? ROWS FETCH NEXT ? ROWS ONLY;";
        return sql.query(query).toModels(City.class);
    }

    @Override
    public boolean insertCity(City city) throws SQLException {
        String query = "INSERT INTO " + TABLE_CITY + "(" +COLUMN_NAME + ") " +
                " + VALUES (" + city.getName() + ")";
        return sql.executeUpdate(query) == 1;
    }

    @Override
    public City getCityById(int id) throws SQLException {
        String query = "SELECT " + COLUMN_ID + ", " + COLUMN_NAME + " FROM " + TABLE_CITY
                + " WHERE " + COLUMN_ID + "= " + id;
        List<City> cities = sql.query(query).toModels(City.class);
        return cities.size() == 0 ? null : cities.get(0);
    }

    @Override
    public boolean updateCity(City city) throws SQLException {
        String query = "UPDATE " + TABLE_CITY + "SET " + COLUMN_NAME + " = " + city.getName()
                + " WHERE " + COLUMN_ID + "= " + city.getId();
        return sql.executeUpdate(query) == 1;
    }

    @Override
    public boolean deleteCity(City city) throws SQLException {
        return deleteCity(city.getId());
    }

    @Override
    public boolean deleteCity(int id) throws SQLException {
        String query = "DELETE FROM " + TABLE_CITY + " WHERE id= " + id;
        return sql.executeUpdate(query) == 1;
    }

    @Override
    public int batchUpdateCity(List<City> cities) {
        return 0;
    }

    @Override
    public int batchDeleteCity(List<Integer> ids) {
        return 0;
    }

    @Override
    public int batchInsertCity(List<City> cities) {
        return 0;
    }
}
