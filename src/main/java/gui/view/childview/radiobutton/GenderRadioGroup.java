package gui.view.childview.radiobutton;

import gui.model.StudentInfoModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GenderRadioGroup extends JPanel implements ActionListener {
    private JLabel label;
    private JRadioButton rbMale;
    private JRadioButton rbFemale;
    private StudentInfoModel.BooleanWrapper isMale;

    public GenderRadioGroup(String text) {
        isMale = new StudentInfoModel.BooleanWrapper();
        label = new JLabel(text);
        label.setText(text);
        rbMale = new JRadioButton("M");
        rbFemale = new JRadioButton("F");
        ButtonGroup bg = new ButtonGroup();
        bg.add(rbMale);
        bg.add(rbFemale);

        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.LINE_START;
        c.gridx = 0;
        c.gridy = 0;
        this.add(label, c);
        c.weightx = 1;
        c.gridx++;
        this.add(Box.createHorizontalStrut(150));
        c.gridx++;
        this.add(rbMale, c);
        c.gridx++;
        this.add(rbFemale);
        rbMale.addActionListener(this);
        rbFemale.addActionListener(this);
    }

    public boolean isMale() {
        return rbMale.isSelected();
    }

    public GenderRadioGroup setMaleObject(StudentInfoModel.BooleanWrapper isMale) {
        this.isMale = isMale;
        rbMale.setSelected(isMale.getValue());
        rbFemale.setSelected(!isMale.getValue());
        return this;
    }

    public GenderRadioGroup setMale(boolean isMale) {
        this.isMale.setValue(isMale);
        rbMale.setSelected(isMale);
        rbFemale.setSelected(!isMale);
        return this;
    }

    public Boolean getMale() {
        return isMale.getValue();
    }

    public void readOnly(boolean readonly) {
        rbMale.setEnabled(!readonly);
        rbFemale.setEnabled(!readonly);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        isMale.setValue(rbMale.isSelected());
        System.out.println(isMale);
    }

}
