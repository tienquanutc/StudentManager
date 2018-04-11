package gui.model;

import model.City;

import javax.swing.*;
import javax.swing.event.ListDataListener;
import java.util.List;

public class CityComboBoxModel implements ComboBoxModel<City> {

    private List<City> cities;
    private City selected = null;

    public CityComboBoxModel(List<City> cities) {
        this.cities = cities;
    }


    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        if (cities == null) {
            System.out.println("null");
        }
        this.cities = cities;
    }

    @Override
    public void setSelectedItem(Object anItem) {
        selected = (City)anItem;
    }

    @Override
    public Object getSelectedItem() {
        return selected;
    }

    public int getSelectedIndex() {
        return cities.indexOf(selected);
    }

    @Override
    public int getSize() {
        if (cities == null) {
            System.out.println("null");
        }
        return cities.size();
    }

    @Override
    public City getElementAt(int index) {
        return cities.get(index);
    }

    @Override
    public void addListDataListener(ListDataListener l) {

    }

    @Override
    public void removeListDataListener(ListDataListener l) {

    }
}
