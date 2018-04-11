package gui.view;

import app.AppConfig;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class MenuBar extends JMenuBar {
    private List<JMenuItem> menuItems;

    public MenuBar() {
        this.menuItems = new ArrayList<>();
        initComponents();
    }

    private void initComponents() {
        JMenu mnMode = new JMenu("Mode");
        JMenuItem itemSql = createMenuItem("SqlServer", "sql");
        JMenuItem itemText = createMenuItem("Text", "txt");
        JMenuItem itemXml = createMenuItem("Xml", "xml");
        mnMode.add(itemSql);
        mnMode.add(itemText);
        mnMode.add(itemXml);

        JMenu mnAbout = new JMenu("About");
        JMenuItem itemAdmin = createMenuItem("Nguyen Tien Quan", "admin");
        mnAbout.add(itemAdmin);

        menuItems.add(itemXml);
        menuItems.add(itemText);
        menuItems.add(itemSql);
        menuItems.add(itemAdmin);

        this.add(mnMode);
        this.add(mnAbout);
    }

    private JMenuItem createMenuItem(String s, String commandName) {
        JMenuItem item = new JMenuItem(s);
        item.setActionCommand(commandName);
        return item;
    }

    public void addActionListener(ActionListener listener) {
        menuItems.forEach(m -> m.addActionListener(listener));
    }

    public void removeActionListener(ActionListener listener) {
        menuItems.forEach(m -> m.removeActionListener(listener));
    }

}
