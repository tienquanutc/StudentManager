package gui.action;

import app.AppConfig;
import app.Runner;
import gui.MainGUI;
import gui.dialog.DIALOG;
import gui.loading.LoadingProgress;
import service.StudentService;
import util.FailSafe;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class StudentMenuBarAction extends AbstractAction {
    private MainGUI mainGUI;
    private AppConfig config;

    public StudentMenuBarAction(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
        this.config = mainGUI.getConfig();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        final String command = e.getActionCommand();
        switch (command) {
            case "sql":
                config.setMode("sql");
                break;
            case "txt":
                config.setMode("txt");
                break;
            case "xml":
                config.setMode("xml");
                break;
            case "admin":
                break;
            default:
        }


        mainGUI.setVisible(false);
        mainGUI.dispose();
        LoadingProgress.run(() -> {
            mainGUI = Runner.safeCreate(config);
            mainGUI.run();
        });
    }

}
