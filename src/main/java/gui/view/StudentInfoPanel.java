package gui.view;

import app.AppConfig;
import gui.view.childview.border.RoundedBorder;
import gui.view.childview.input.DateSimpleInput;
import gui.view.childview.input.IntegerSimpleInput;
import gui.view.childview.input.ScoreSimpleInput;
import model.Student;
import gui.model.CityComboBoxModel;
import gui.model.StudentInfoModel;
import gui.view.childview.combobox.CityComboBox;
import gui.view.childview.radiobutton.GenderRadioGroup;
import gui.view.childview.input.SimpleInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.DateUtils;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Observable;
import java.util.Observer;

public class StudentInfoPanel extends JPanel implements Observer {
    protected static Logger LOG = LoggerFactory.getLogger(AppConfig.class);


    private SimpleInput fieldId;
    private SimpleInput fieldName;
    private CityComboBox fieldPlace;
    private SimpleInput fieldDate;
    private GenderRadioGroup fieldSex;
    private SimpleInput fieldMath;
    private SimpleInput fieldPhysic;
    private SimpleInput fieldChemical;
    private SimpleInput fieldTotal;

    private CityComboBoxModel cityComboBoxModel;

    private StudentInfoModel model;


    public StudentInfoPanel(StudentInfoModel model) {
        this.model = model;
        model.addObserver(this);
        this.cityComboBoxModel = model.getCityComboBoxModel();
        initComponents();
    }

    private void initComponents() {
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.ipadx = 15;
        c.anchor = GridBagConstraints.LINE_START;
        c.gridx = 0;
        c.gridy = 0;
        c.gridx = 0;
        c.insets = new Insets(10, 20, 10, 10);
        this.add(fieldId = new IntegerSimpleInput("ID").setDocument(model.getIdDocument()), c);
        c.gridy++;
        this.add(fieldName = new SimpleInput("Name").setDocument(model.getNameDocument()), c);
        c.gridy++;
        this.add(fieldPlace = new CityComboBox(cityComboBoxModel, "Place"), c);
        c.gridy++;
        this.add(fieldDate = new DateSimpleInput("Date").setDocument(model.getDateDocument()), c);
        c.gridy++;
        this.add(fieldSex = new GenderRadioGroup("Sex").setMaleObject(model.getSexObject()), c);

        c.gridx = 1;
        c.gridy = 0;
        this.add(fieldMath = new ScoreSimpleInput("Math").setDocument(model.getMathDocument()), c);
        c.gridy++;
        this.add(fieldPhysic = new ScoreSimpleInput("Physic").setDocument(model.getPhysicDocument()), c);
        c.gridy++;
        this.add(fieldChemical = new ScoreSimpleInput("Chemistry").setDocument(model.getChemicalDocument()), c);
        c.gridy++;
        this.add(fieldTotal = new SimpleInput("Total"), c);
        fieldTotal.readOnly(true);
    }

    public StudentInfoPanel setTitleBorder(String title) {
        this.setBorder(BorderFactory.createTitledBorder(new RoundedBorder(5), title, 0, 0, null, Color.BLUE));
        return this;
    }

    public void bindStudent(Student student) {
        if (student == null) {
            clearPanel();
            return;
        }
        LOG.debug("bind model " + student);
        fieldId.setText(String.valueOf(student.getId()));
        fieldId.revalidate();
        fieldName.setText(String.valueOf(student.getFullName()));
        fieldPlace.setSelectedItem(student.getCity());
        fieldDate.setText(DateUtils.format(student.getBirthday()));
        fieldSex.setMale(student.getGender() == 1);
        fieldMath.setText(String.valueOf(student.getMathScore()));
        fieldPhysic.setText(String.valueOf(student.getPhysicScore()));
        fieldChemical.setText(String.valueOf(student.getChemicalScore()));
        fieldTotal.setText(String.valueOf(student.getTotalScore()));
        this.revalidate();
        this.repaint();
    }

    public void clearPanel() {
        fieldId.setText("");
        fieldName.setText("");
        fieldPlace.clearSelected();
        fieldDate.setText("");
        fieldSex.setMale(true);
        fieldMath.setText("");
        fieldPhysic.setText("");
        fieldChemical.setText("");
        fieldTotal.setText("");
        this.revalidate();
        this.repaint();
    }



    @Override
    public void update(Observable o, Object arg) {
        StudentInfoModel studentInfoModel = (StudentInfoModel) o;
        bindStudent(studentInfoModel.getBindingStudent());
    }
}
