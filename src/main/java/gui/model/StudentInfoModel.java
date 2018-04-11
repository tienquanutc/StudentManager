package gui.model;

import app.AppConfig;
import gui.view.StudentInfoPanel;
import model.City;
import model.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class StudentInfoModel extends Observable implements Observer {
    protected static Logger LOG = LoggerFactory.getLogger(AppConfig.class);

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    private Student bindingStudent;
    private Document idDocument;
    private Document nameDocument;
    private CityComboBoxModel cityComboBoxModel;
    private Document dateDocument;
    private BooleanWrapper sexObject;
    private Document mathDocument;
    private Document physicDocument;
    private Document chemicalDocument;

    private boolean readOnly;

    public StudentInfoModel(List<City> cities) {
        bindingStudent = new Student();
        idDocument = new PlainDocument();
        nameDocument = new PlainDocument();
        cityComboBoxModel = new CityComboBoxModel(cities);
        dateDocument = new PlainDocument();
        sexObject = new BooleanWrapper();
        mathDocument = new PlainDocument();
        physicDocument = new PlainDocument();
        chemicalDocument = new PlainDocument();
        readOnly = true;
    }

    public Student getBindingStudent() {
        return bindingStudent;
    }

    public void setBindingStudent(Student newBindingStudent) {
        if (this.bindingStudent != null) {
            this.bindingStudent.deleteObserver(this);
        }
        this.bindingStudent = newBindingStudent;

        if (newBindingStudent != null) {
            newBindingStudent.addObserver(this);
        }
        setChanged();
        notifyObservers(newBindingStudent);
    }

    public void updateStudent() throws BadLocationException, ParseException {
        parseTo(bindingStudent);
    }

    public Student getNewStudent() {
        Student student = new Student();
        return parseTo(student);
    }

    private Student parseTo(Student student) {
        student.setId(parseInteger(idDocument, "id"));
        student.setFullName(parseText(nameDocument, "name"));
        student.setGender(sexObject.getValue());
        student.setBirthday(parseDate(dateDocument, "birthday"));
        student.setCity((City) cityComboBoxModel.getSelectedItem());
        student.setMathScore(parseFloat(mathDocument, "math score"));
        student.setPhysicScore(parseFloat(physicDocument, "physic score"));
        student.setChemicalScore(parseFloat(chemicalDocument, "chemical score"));
        return student;
    }


    private int parseInteger(Document document, String nameOnFail) {
        try {
            return Integer.valueOf(parseText(document, nameOnFail));
        } catch (Exception e) {
            throw new RuntimeException(nameOnFail + " must be a integer");
        }
    }

    private float parseFloat(Document document, String nameOnFail) {
        try {
            float f = Float.valueOf(parseText(document, nameOnFail));
            if (0f <= f && f <= 10f)
                return f;
            throw new RuntimeException(nameOnFail + " must be on between 0 to 10");
        } catch (Exception e) {
            throw new RuntimeException(nameOnFail + " must be a number between 0 to 10");
        }
    }

    private Date parseDate(Document document, String nameOnFail) {
        try {
            return dateFormat.parse(parseText(document, nameOnFail));
        } catch (ParseException e) {
            throw new RuntimeException(nameOnFail + " must be fomat dd/MM/yyyy");
        }
    }

    private String parseText(Document document, String nameOnFail) {
        try {
            return document.getText(0, document.getLength());
        } catch (BadLocationException e) {
            throw new RuntimeException(nameOnFail + " must be not empty!");
        }
    }

    public Document getIdDocument() {
        return idDocument;
    }

    public Document getNameDocument() {
        return nameDocument;
    }


    public Document getDateDocument() {
        return dateDocument;
    }

    public Document getMathDocument() {
        return mathDocument;
    }

    public Document getPhysicDocument() {
        return physicDocument;
    }

    public Document getChemicalDocument() {
        return chemicalDocument;
    }

    public BooleanWrapper getSexObject() {
        return sexObject;
    }

    public CityComboBoxModel getCityComboBoxModel() {
        return cityComboBoxModel;
    }

    public boolean isReadOnly() {
        return readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }



    @Override
    public void update(Observable o, Object arg) {
        setChanged();
        notifyObservers(bindingStudent);
    }

    public static class BooleanWrapper {
        private boolean value;

        public BooleanWrapper() {
            this.value = true;
        }

        public boolean getValue() {
            return value;
        }

        public void setValue(boolean value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return String.valueOf(getValue());
        }
    }
}
