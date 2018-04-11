package gui.verifier;

import gui.dialog.DIALOG;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FloatInputVerifier extends InputVerifier implements ActionListener {
    protected static Logger LOG = LoggerFactory.getLogger(DateInputVerifier.class);

    private float lbound, rbound;

    public FloatInputVerifier(float lbound, float rbound) {
        this.lbound = lbound;
        this.rbound = rbound;
    }

    @Override
    public boolean verify(JComponent input) {
        JTextField jTextField = ((JTextField) input);
        String text = jTextField.getText();
        try {
            float score = Float.valueOf(text);
            boolean valid = lbound <= score && score <= rbound;
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

    @Override
    public boolean shouldYieldFocus(JComponent input) {
        return super.shouldYieldFocus(input);
    }

    private boolean validateFailure(JTextField jTextField) {
        jTextField.setBorder(BorderFactory.createLineBorder(Color.RED));
        DIALOG.error(null, "Number in " + jTextField.getName() + " must be between " + lbound + " and " + rbound, "Input validation");
        return false;
    }

    private boolean validateSuccess(JTextField jTextField) {
        jTextField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        jTextField.repaint();
        return true;
    }
}