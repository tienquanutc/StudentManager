package model;

import io.sql.anonation.Mapper;

import java.util.Objects;

public class City {
    public static final String TABLE_CITY= "City";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";

    @Mapper(Column = COLUMN_ID)
    private Integer id;
    @Mapper(Column = COLUMN_NAME)
    private String name;

    public City() {
    }

    public City(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof City)) return false;
        City city = (City) o;
        return this.id.equals(city.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return name;
    }
}
