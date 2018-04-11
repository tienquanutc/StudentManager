package gui.view.childview.combobox;

import gui.model.CityComboBoxModel;
import model.City;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.*;

public class CityComboBox extends JPanel {

    protected JLabel label;
    protected JComboBox<City> comboBox;
    protected CityComboBoxModel cityComboBoxModel;

    public CityComboBox(CityComboBoxModel cityComboBoxModel, String text) {
        this.cityComboBoxModel = cityComboBoxModel;
        initComponents(text);
    }

    private void initComponents(String text) {
        label = new JLabel();
        label.setText(text);
        comboBox = new JComboBox<>();
        comboBox.setModel(cityComboBoxModel);
        comboBox.setPreferredSize(new Dimension(168, 20));
        label.setLabelFor(comboBox);
        this.setLayout(new GridLayout(1, 2));
        add(label);
        add(comboBox);
    }

    public void setSelectedItem(City city) {
        comboBox.setSelectedItem(city);
    }

    public void clearSelected() {
        setSelectedIndex(-1);
    }

    public void setSelectedIndex(int index) {
        comboBox.setSelectedIndex(index);
    }

    public void readOnly(boolean readonly) {
        comboBox.setEnabled(!readonly);
    }
}

