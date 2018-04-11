package gui.action;

import gui.model.CityComboBoxModel;
import gui.model.StudentTableModel;
import model.City;
import model.Student;
import service.StudentService;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.awt.event.ActionEvent;

public class StudentSearchAction extends AbstractAction {
    private Document searchInput;
    private StudentTableModel searchResult;
    private StudentService service;
    private CityComboBoxModel cityComboBoxModel;

    public StudentSearchAction(Document searchInput, StudentTableModel searchResult, CityComboBoxModel cityComboBoxModel, StudentService service) {
        this.searchInput = searchInput;
        this.searchResult = searchResult;
        this.cityComboBoxModel = cityComboBoxModel;
        this.service = service;
    }

    public void actionPerformed(ActionEvent e) {
        String searchString = getSearchString();
        int cityId = getSearchCityId();
        RowFilter<StudentTableModel, Integer> rowFilter = new RowFilter<StudentTableModel, Integer>() {
            @Override
            public boolean include(Entry<? extends StudentTableModel, ? extends Integer> entry) {
                StudentTableModel model = entry.getModel();
                int identifier = entry.getIdentifier();
                return isMatchStudent(model.getRowAt(identifier), searchString, cityId);
            }
        };
        TableRowSorter<StudentTableModel> studentTableModelRowSorter = new TableRowSorter<>(searchResult);
        ((JTable) searchResult.getTableModelListeners()[0]).setRowSorter(studentTableModelRowSorter);
        studentTableModelRowSorter.setRowFilter(rowFilter);
    }

    private String getSearchString() {
        try {
            return searchInput.getText(0, searchInput.getLength());
        } catch (BadLocationException e) {
            return null;
        }
    }

    private int getSearchCityId() {
        return ((City) cityComboBoxModel.getSelectedItem()).getId();
    }

    private boolean isMatchStudent(Student student, String searchString, int cityId) {
        boolean matchId = student.getFullName().contains(searchString) || searchString.contains(student.getFullName());
        boolean matchCity = cityId == 0 || student.getCityId() == cityId;
        return matchId && matchCity;
    }

}
