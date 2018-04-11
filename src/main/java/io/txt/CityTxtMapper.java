package io.txt;

import model.City;

import java.util.ArrayList;
import java.util.List;

public class CityTxtMapper implements TxtMapper<City> {
    @Override
    public String toText(City city) {
        return city.getName() + "; " + city.getId();
    }

    @Override
    public City toModel(String text) {
        String[] param = text.split(";");
        if (param.length != 2)
            throw new RuntimeException("City line '" + text + "' not match rule ${city.name}; ${city.id}");
        trimParam(param);
        String cityName = param[0];
        int cityId;
        try {
            cityId = Integer.valueOf(param[1]);
        } catch (NumberFormatException ex) {
            throw new RuntimeException("cityId must be a number");
        }
        return new City(cityId, cityName);
    }
}
