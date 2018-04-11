package gui;

import app.AppConfig;
import gui.action.StudentCRUDAction;
import gui.action.StudentMenuBarAction;
import gui.action.StudentSearchAction;
import gui.dialog.DIALOG;
import gui.model.CityComboBoxModel;
import gui.model.StudentInfoModel;
import gui.model.StudentTableModel;
import gui.view.MenuBar;
import gui.view.*;
import model.City;
import model.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.StudentService;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainGUI extends JFrame implements WindowListener {
    protected static Logger LOG = LoggerFactory.getLogger(MainGUI.class);

    private AppConfig config;

    private StudentService service;
    private List<Student> students;
    private List<City> cities;

    public MainGUI(AppConfig config) throws Exception {
        this.config = config;
        StudentService.createServiceFor(config);
        service = config.getMgmtService();
        students = service.getAllStudents();
        cities = service.getAllCities();
        initComponents();
    }

    public void run() {
        setVisible(true);
    }

    private void initComponents() {
        setTitle("Student Manager");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        /*Model*/
        StudentTableModel studentTableModel = new StudentTableModel(students, cities);
        DefaultListSelectionModel studentListSelectionModel = new DefaultListSelectionModel();
        Document searchInput = new PlainDocument();
        StudentInfoModel studentInfoModel = new StudentInfoModel(cities);
        List<City> citiesForFilter = new ArrayList<>();
        citiesForFilter.add(new City(0, "Tất cả"));
        citiesForFilter.addAll(cities);
        CityComboBoxModel cityComboBoxModelFilter = new CityComboBoxModel(citiesForFilter);

        /*View*/
        MenuBar menuBar = new MenuBar();
        StudentFilter studentFilter = new StudentFilter(cityComboBoxModelFilter, searchInput).setTitleBorder("Student Filter");
        StudentGridTable studentGridTable = new StudentGridTable(studentTableModel, studentListSelectionModel).setBorderTitle("List Students");
        StudentInfoPanel studentDetailsPanel = new StudentInfoPanel(studentInfoModel).setTitleBorder("Student Details");
        StudentCRUDPanel studentCRUDPanel = new StudentCRUDPanel();

        /*Action*/
        Action studentMenuBarAction = new StudentMenuBarAction(this);
        Action studentSearchAction = new StudentSearchAction(searchInput, studentTableModel, cityComboBoxModelFilter, service);
        Action studentCRUDAction = new StudentCRUDAction(service, studentInfoModel, studentTableModel);

        menuBar.addActionListener(studentMenuBarAction);

        studentFilter.addActionFilter(studentSearchAction);
        studentGridTable.getListSelectionModel().addListSelectionListener(l -> {
            if (!l.getValueIsAdjusting())
                studentInfoModel.setBindingStudent(studentGridTable.getSelectedRow());
        });

        studentListSelectionModel.addListSelectionListener(ListSelectionEvent::getFirstIndex);

        studentCRUDPanel.addActionListener(studentCRUDAction);

        /*Add to jframe*/
        this.setJMenuBar(menuBar);
        Container contentPane = getContentPane();

        contentPane.add(studentFilter, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(studentGridTable, BorderLayout.CENTER);
        centerPanel.add(studentDetailsPanel, BorderLayout.SOUTH);
        contentPane.add(centerPanel, BorderLayout.CENTER);

        contentPane.add(studentCRUDPanel, BorderLayout.SOUTH);

        addWindowListener(this);
    }

    @Override
    public void windowOpened(WindowEvent e) {
//        LOG.info("windowOpened");
    }

    @Override
    public void windowClosing(WindowEvent e) {
        if (DIALOG.confirm(null, "Are you sure you want to exit the program?", "Exit Program Message Box", "Yep!", "No", false)) {
            LOG.info("window closing, run save change");
            dispose();
        } else {
            setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        }
        new ArrayList<Object>(-1);
    }

    @Override
    public void dispose() {
        super.dispose();
        service.disposed();
        String configPath = System.getProperty("user.dir") + "/conf/application.yaml";
        try {
            config.saveToFile(new File(configPath));
        } catch (IOException e) {
            LOG.warn("Failed save config because " + e.getMessage());
        }
    }
    @Override
    public void windowClosed(WindowEvent e) {
//        LOG.info("windowClosed");
    }

    @Override
    public void windowIconified(WindowEvent e) {
//        LOG.info("windowIconified");
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
//        LOG.info("windowDeiconified");
    }

    @Override
    public void windowActivated(WindowEvent e) {
//        LOG.info("windowActivated");
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
//        LOG.info("window closed");
    }

    public AppConfig getConfig() {
        return config;
    }
}
