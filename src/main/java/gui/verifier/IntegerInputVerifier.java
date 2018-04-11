package gui.verifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class IntegerInputVerifier extends InputVerifier implements ActionListener {
    protected static Logger LOG = LoggerFactory.getLogger(IntegerInputVerifier.class);

    private int lbound, rbound;

    public IntegerInputVerifier(int lbound, int rbound) {
        this.lbound = lbound;
        this.rbound = rbound;
    }

    @Override
    public boolean verify(JComponent input) {
        JTextField jTextField = ((JTextField) input);
        String text = jTextField.getText();
        try {
            int id = Integer.valueOf(text);
            boolean valid = lbound <= id && id <= rbound;
            if (!valid) {
                LOG.info("Verify '" + text + "' failed because not in bound " + lbound + " to " + rbound);
            }
            return valid ? validateSuccess(jTextField) : validateFailure(jTextField);
        } catch (NumberFormatException e) {
            LOG.info("Verify '" + text + "' failed because " + e.getMessage());
            return validateFailure(jTextField);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        verify((JComponent) e.getSource());
    }

    private boolean validateFailure(JTextField jTextField) {
        jTextField.setBorder(BorderFactory.createLineBorder(Color.RED));
        return false;
    }

    private boolean validateSuccess(JTextField jTextField) {
        jTextField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        return true;
    }
}
