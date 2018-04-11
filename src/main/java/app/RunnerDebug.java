package app;

import gui.MainGUI;

import java.io.File;

public class RunnerDebug {
    public static void main(String[] args) throws Exception {
        File file = new File(System.getProperty("user.dir") + "/conf/application.yaml");
        AppConfig config = null;
        config = AppConfig.newInstance(file);
        MainGUI gui = new MainGUI(config);
    }
}
