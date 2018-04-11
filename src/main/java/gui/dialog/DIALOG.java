package gui.dialog;

import javax.swing.*;
import java.awt.*;

public class DIALOG {
    public static void error(Component parentComponent, String message, String title) {
        JOptionPane.showMessageDialog(parentComponent, message, title, JOptionPane.ERROR_MESSAGE);
    }

    public static void info(Component parentComponent, String message, String title) {
        JOptionPane.showMessageDialog(parentComponent, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public static boolean confirm(Component parentComponent, String message, String title, String yes, String no, boolean defaultYes) {
        String[] options = new String[]{yes, no};
        int defaultOption = defaultYes ? 0 : 1;
        return JOptionPane.showOptionDialog(parentComponent, message, title,
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null
                , options, options[defaultOption]) == 0;
    }

    public static int choose(Component parentComponent, String message, String title, String option1, String option2, String option3, int defaultOption) {
        String[] options = new String[]{option1, option2, option3};
        return JOptionPane.showOptionDialog(parentComponent, message, title,
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null
                , options, options[defaultOption]);
    }

//    public static void loading() {
//        PleaseWaitDialog pleaseWaitDialog = new PleaseWaitDialog();
//    }
}
