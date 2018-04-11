package gui.verifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.DateUtils;

import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateInputVerifier extends InputVerifier {
    protected static Logger LOG = LoggerFactory.getLogger(DateInputVerifier.class);

    @Override
    public boolean verify(JComponent input) {
        String text = ((JTextField) input).getText();
        try {
            DateUtils.parse(text);
            return true;
        } catch (ParseException e) {
            LOG.info("Verify '" + text + "' failed because " + e.getMessage());
            return false;
        }
    }
}
