package gui.model;

import model.City;
import model.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class StudentTableModel extends AbstractTableModel {
    protected static Logger LOG = LoggerFactory.getLogger(StudentTableModel.class);

    public static final int COLUMN_NO = 0;
    public static final int COLUMN_ID = 1;
    public static final int COLUMN_NAME = 2;
    public static final int COLUMN_PLACE = 3;
    public static final int COLUMN_DATE = 4;
    public static final int COLUMN_SEX = 5;
    public static final int COLUMN_MATH = 6;
    public static final int COLUMN_PHYSICAL = 7;
    public static final int COLUMN_CHEMISTRY = 8;

    private List<Student> students;
    private List<City> cities;
    private Map<Integer, Function<Student, Object>> studentModelGetterMap;
    private Map<Integer, BiConsumer<Student, Object>> studentModelSetterMap;
    private String[] columnNames = {"NO", "ID", "Name", "Place", "Date", "Sex", "Math", "Physical", "Chemistry"};
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public StudentTableModel(List<Student> students, List<City> cities) {
        this.students = students;
        this.cities = cities;
        initialize();
    }


    private void initialize() {
        studentModelGetterMap = new HashMap<>();
        studentModelGetterMap.put(COLUMN_ID, Student::getId);
        studentModelGetterMap.put(COLUMN_NAME, Student::getFullName);
        studentModelGetterMap.put(COLUMN_PLACE, Student::getCity);
        studentModelGetterMap.put(COLUMN_DATE, s -> simpleDateFormat.format(s.getBirthday()));
        studentModelGetterMap.put(COLUMN_SEX, Student::isGender);
        studentModelGetterMap.put(COLUMN_MATH, Student::getMathScore);
        studentModelGetterMap.put(COLUMN_PHYSICAL, Student::getPhysicScore);
        studentModelGetterMap.put(COLUMN_CHEMISTRY, Student::getChemicalScore);

        studentModelSetterMap = new HashMap<>();
        studentModelSetterMap.put(COLUMN_ID, (s, o) -> s.setId((Integer) o));
        studentModelSetterMap.put(COLUMN_NAME, (s, o) -> s.setFullName((String) o));
        studentModelSetterMap.put(COLUMN_PLACE, (s, o) -> s.setCity((City) o));
        studentModelSetterMap.put(COLUMN_DATE, (s, o) -> {
            try {
                s.setBirthday(simpleDateFormat.parse((String) o));
            } catch (ParseException e) {
                LOG.warn("Failed parse Date " + o + " because " + e.getMessage());
            }
        });

        studentModelSetterMap.put(COLUMN_SEX, (s, o) -> s.setGender((boolean) o));
        studentModelSetterMap.put(COLUMN_MATH, (s, o) -> s.setMathScore((Float) o));
        studentModelSetterMap.put(COLUMN_PHYSICAL, (s, o) -> s.setPhysicScore((Float) o));
        studentModelSetterMap.put(COLUMN_CHEMISTRY, (s, o) -> s.setChemicalScore((Float) o));
    }

    public void clear() {
        students.clear();
    }

    @Override
    public int getRowCount() {
        return students.size();
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return getValueAt(0, columnIndex).getClass();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex != COLUMN_NO;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        //column_no increase auto
        if (columnIndex == COLUMN_NO)
            return rowIndex + 1;
        Student currentStudent = this.students.get(rowIndex);
        Function<Student, Object> studentObjectFunction = studentModelGetterMap.get(columnIndex);
        if (studentObjectFunction != null)
            return studentObjectFunction.apply(currentStudent);
        return "";
    }

    public Student getRowAt(int rowIndex) {
        if (rowIndex < 0 || rowIndex >= students.size())
            return null;
        return students.get(rowIndex);
    }

    @Override
    public void setValueAt(Object newValue, int rowIndex, int columnIndex) {
        if (columnIndex > getColumnCount())
            return;
        Student currentStudent = this.students.get(rowIndex);
        //check dirty cell
        Object oldValue = getValueAt(rowIndex, columnIndex);
        if (Objects.equals(newValue, oldValue)) {
            LOG.info("Cell at row " + rowIndex + " header " + columnNames[columnIndex] + " not dirty. Cancel updated.");
            return;
        }
        BiConsumer<Student, Object> studentSetter = studentModelSetterMap.get(columnIndex);
        if (studentSetter != null) {
            studentSetter.accept(currentStudent, newValue);
            updateRow(rowIndex, currentStudent);
            LOG.info("Cell at row " + rowIndex + " header " + columnNames[columnIndex] + " update form " + oldValue + " to " + newValue);
        }
    }

    public void addRow(Student studentRowModel) {
        this.students.add(studentRowModel);
        int index = students.size() - 1;
        fireTableRowsInserted(index, index);
    }

    public void updateRow(int index, Student student) {
        this.students.set(index, student);
        student.fireDataChange();
        fireTableRowsUpdated(index, index);
    }

    public void updateRow(Student studentSameId) {
        int index = this.students.indexOf(studentSameId);
        if (index < 0) {
            return;
        }
        this.students.set(index, studentSameId);
        fireTableRowsUpdated(index, index);
    }

    public void removeRow(int index) {
        //for txt, xml mode duplicate student
        if (index > 0) {
            students.remove(this.students.get(index));
        }
        fireTableRowsDeleted(index, index);
    }

    public void removeRow(Student student) {
        int index = students.indexOf(student);
        removeRow(index);
    }

    public List<City> getCities() {
        return cities;
    }

    public void fireTableRowsUpdated(Student student) {
        int index = students.indexOf(student);
        fireTableRowsUpdated(index, index);
    }

    public void fireTableRowsInserted(Student student) {
        //for txt and xml mode duplicate insert
        if (!students.contains(student)) {
            students.add(student);
        }
        fireTableRowsInserted(0, students.size() - 1);
        JTable jTable = (JTable) this.getTableModelListeners()[0];
        if (jTable == null)
            return;
        SwingUtilities.invokeLater(() -> {
            JViewport parent = (JViewport) jTable.getParent();
            parent.setViewPosition(new Point(0, jTable.getHeight()));
            jTable.setRowSelectionInterval(students.size() - 2, students.size() - 1);
        });
    }
}
