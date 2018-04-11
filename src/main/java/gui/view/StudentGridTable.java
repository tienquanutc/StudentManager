package gui.view;

import gui.view.childview.border.RoundedBorder;
import model.Student;
import gui.model.StudentTableModel;
import gui.view.childview.cell.CityComboBoxCellEditor;
import gui.view.childview.cell.GenderCellEditor;

import javax.swing.*;
import java.awt.*;

public class StudentGridTable extends JPanel {
    private JTable jTable;
    private StudentTableModel studentTableModel;
    private DefaultListSelectionModel defaultListSelectionModel;

    public StudentGridTable(StudentTableModel studentTableModel, DefaultListSelectionModel defaultListSelectionModel) {
        this.studentTableModel = studentTableModel;
        this.defaultListSelectionModel = defaultListSelectionModel;
        initComponents();
    }

    private void initComponents() {
        defaultListSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jTable = new JTable(studentTableModel);
        jTable.setSelectionModel(defaultListSelectionModel);
        jTable.getColumnModel().getColumn(StudentTableModel.COLUMN_NO).setMaxWidth(20);
        jTable.getColumnModel().getColumn(StudentTableModel.COLUMN_NAME).setMinWidth(200);
        jTable.getColumnModel().getColumn(StudentTableModel.COLUMN_SEX).setMaxWidth(50);
        jTable.getColumnModel().getColumn(StudentTableModel.COLUMN_MATH).setMaxWidth(50);
        jTable.getColumnModel().getColumn(StudentTableModel.COLUMN_PHYSICAL).setMaxWidth(50);
        jTable.getColumnModel().getColumn(StudentTableModel.COLUMN_CHEMISTRY).setMaxWidth(50);
        jTable.setRowHeight(jTable.getRowHeight() + 5);
        this.jTable.getColumnModel().getColumn(StudentTableModel.COLUMN_PLACE).setCellEditor(new CityComboBoxCellEditor(studentTableModel.getCities()));
        this.jTable.getColumnModel().getColumn(StudentTableModel.COLUMN_SEX).setCellRenderer(new GenderCellEditor());
        this.jTable.getColumnModel().getColumn(StudentTableModel.COLUMN_SEX).setCellEditor(new GenderCellEditor());
        this.setLayout(new BorderLayout(10, 10));
        JScrollPane scrollableSearchResult = new JScrollPane(jTable);
        this.add(scrollableSearchResult, BorderLayout.CENTER);
    }

    public StudentGridTable setBorderTitle(String title) {
        this.setBorder(BorderFactory.createTitledBorder(new RoundedBorder(5),title, 0,0 , null, Color.BLUE ));
        return this;
    }

    public Student getSelectedRow() {
        return studentTableModel.getRowAt(jTable.convertRowIndexToModel(jTable.getSelectedRow()));
    }

    public DefaultListSelectionModel getListSelectionModel() {
        return defaultListSelectionModel;
    }
}
