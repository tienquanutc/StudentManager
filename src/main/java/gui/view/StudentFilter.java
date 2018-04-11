package gui.view;

import gui.model.CityComboBoxModel;
import gui.view.childview.border.RoundedBorder;
import gui.view.childview.combobox.CityComboBox;
import gui.view.childview.input.SimpleInput;

import javax.swing.*;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.Objects;

public class StudentFilter extends JPanel {
    private CityComboBox cityComboBox;
    private SimpleInput filterInput;
    private JButton btnFilter;

    public StudentFilter(CityComboBoxModel cityComboBoxModel, Document searchDocument) {
        this.cityComboBox = new CityComboBox(cityComboBoxModel, "Place");
        this.filterInput = new SimpleInput("StudentID").setDocument(searchDocument);
        this.btnFilter = new JButton("Filter");
        initComponents();
    }

    private void initComponents() {
        cityComboBox.setSelectedIndex(0);
        this.setLayout(new FlowLayout(FlowLayout.LEADING, 5, 5));
        cityComboBox.setPreferredSize(new Dimension(200, 20));
        filterInput.setPreferredSize(new Dimension(250, 20));
        add(cityComboBox);
        add(Box.createRigidArea(new Dimension(100, 20)));
        add(filterInput);
        add(Box.createRigidArea(new Dimension(100, 20)));
        btnFilter.setPreferredSize(new Dimension(80, 30));
        btnFilter.setBorder(new RoundedBorder(Color.PINK, 10));
        btnFilter.setIcon(new ImageIcon(Objects.requireNonNull(this.getClass().getClassLoader().getResource("ic_filter_16.png"))));
        add(btnFilter);
    }

    public StudentFilter setTitleBorder(String title) {
        this.setBorder(BorderFactory.createTitledBorder(new RoundedBorder(5),title, 0,0 , null, Color.BLUE ));
        return this;
    }

    public void addActionFilter(ActionListener listener) {
        btnFilter.addActionListener(listener);
    }

    public void removeActionFilter(ActionListener listener) {
        btnFilter.removeActionListener(listener);
    }
}
