package app;

import gui.MainGUI;
import gui.dialog.DIALOG;
import gui.loading.LoadingProgress;
import service.StudentService;
import util.FailSafe;

import java.io.File;
import java.io.IOException;

public class Runner {
    public static void main(String[] args) {
        Runnable runnable = () -> {
            String configPath = System.getProperty("user.dir") + "/conf/application.yaml";
            File file = new File(configPath);
            AppConfig config;

            try {
                config = AppConfig.newInstance(file);
            } catch (IOException e) {
                DIALOG.error(null, "Failed to load config because config not found " + configPath, "LOAD CONFIG FILE ERROR");
                return;
            } catch (Exception e) {
                DIALOG.error(null, e.getMessage(), "Failed to startup");
                return;
            }

            try {
                safeCreate(config).run();
            } catch (Exception e) {
                DIALOG.error(null, "Runtime exception " + e.getMessage(), "ERROR");
            }
        };
        LoadingProgress.run(runnable);
    }

    public static MainGUI safeCreate(AppConfig config) {
        return FailSafe.withOnRetry(ex -> {
            DIALOG.error(null, ex.getMessage(), "ERROR");
            int choose = DIALOG.choose(null, "Choose another mode", "FailOver", "SQL", "TXT", "XML", 1);
            config.setMode(getModeByInt(choose));
        }).get(() -> createMainGUI(config));
    }

    private static String getModeByInt(int i) {
        switch (i) {
            case 0:
                return "sql";
            case 1:
                return "txt";
            case 2:
                return "xml";
        }
        throw new RuntimeException("Mode not supported");
    }

    private static MainGUI createMainGUI(AppConfig config) throws Exception {
        MainGUI newGUI = new MainGUI(config);
        return new MainGUI(config);
    }
}
