package gui.view;

import gui.action.StudentCRUDAction;
import gui.view.childview.border.RoundedBorder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.net.URL;


public class StudentCRUDPanel extends JPanel {
    private JButton btnInsert;
    private JButton btnDelete;
    private JButton btnUpdate;
    private JButton btnCancel;

    public StudentCRUDPanel() {
        initComponents();
    }

    private void initComponents() {
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 60, 10));
        add(btnInsert = createButton("Insert", StudentCRUDAction.ACTION_COMMAND_INSERT, "ic_insert_16.png"));
        add(btnDelete = createButton("Delete", StudentCRUDAction.ACTION_COMMAND_DELETE, "ic_delete_16.png"));
        add(btnUpdate = createButton("Update", StudentCRUDAction.ACTION_COMMAND_UPDATE, "ic_update_16.png"));
        add(btnCancel = createButton("Cancel", StudentCRUDAction.ACTION_COMMAND_CANCEL, "ic_cancel_16.png"));
    }

    private JButton createButton(String text, String commandName, String icName) {
        JButton button = new JButton();
        URL resource = StudentCRUDPanel.class.getClassLoader().getResource(icName);
        if (resource != null) {
            button.setIcon(new ImageIcon(resource));
        }
        button.setText(text);
        button.setActionCommand(commandName);
        button.setBorder(new RoundedBorder(8));
        return button;
    }


    public void addActionListener(ActionListener listener) {
        btnInsert.addActionListener(listener);
        btnDelete.addActionListener(listener);
        btnUpdate.addActionListener(listener);
        btnCancel.addActionListener(listener);
    }

    public void removeActionListener(ActionListener listener) {
        btnInsert.removeActionListener(listener);
        btnDelete.removeActionListener(listener);
        btnUpdate.removeActionListener(listener);
        btnCancel.removeActionListener(listener);
    }
}
