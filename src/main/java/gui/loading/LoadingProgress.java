package gui.loading;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.concurrent.CompletableFuture;

public class LoadingProgress extends JDialog {
    public LoadingProgress() {
        super((Dialog) null, "", false);
        initComponents();
    }

    private void initComponents() {
        setUndecorated(true);
        getRootPane().setOpaque(false);
        setBackground(new Color(0,0,0,0));
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setSize(128, 128);
        add(loadingPanel());
        setLocationRelativeTo(null);
    }

    private void doRun(Runnable doRun) {
        setVisible(true);
        SwingUtilities.invokeLater(() -> {
            CompletableFuture.runAsync(doRun).thenRun(this::dispose).exceptionally((e -> {this.dispose(); return null;}));
        });
    }

    public static void run(Runnable doRun) {
        new LoadingProgress().doRun(doRun);
    }

    private static JPanel loadingPanel() {
        JPanel panel = new JPanel();
        panel.setOpaque(true);
        BoxLayout layoutMgr = new BoxLayout(panel, BoxLayout.PAGE_AXIS);
        panel.setLayout(layoutMgr);
        URL resource = LoadingProgress.class.getClassLoader().getResource("loading.gif");
        ImageIcon imageIcon = new ImageIcon(resource);
        JLabel iconLabel = new JLabel();
        iconLabel.setIcon(imageIcon);
        iconLabel.setBackground(new Color(0,0,0,0));
        iconLabel.setOpaque(true);
        imageIcon.setImageObserver(iconLabel);

        panel.add(iconLabel);
        return panel;
    }

}
