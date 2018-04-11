package gui.view.childview.cell;

import model.City;
import gui.model.CityComboBoxModel;
import gui.model.StudentTableModel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CityComboBoxCellEditor extends DefaultCellEditor {
    public CityComboBoxCellEditor(List<City> cities) {
        super(new JComboBox<>(new CityComboBoxModel(cities)));
        initEvents();
    }

    private void initEvents() {

    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        if(column == StudentTableModel.COLUMN_PLACE) {
            JComboBox<City> cityJComboBox = (JComboBox<City>)getComponent();
            cityJComboBox.setSelectedItem(value);
            cityJComboBox.setSelectedItem(value);
            return cityJComboBox;
        }
        return super.getTableCellEditorComponent(table, value, isSelected, row, column);
    }
}
