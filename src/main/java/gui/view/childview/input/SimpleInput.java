package gui.view.childview.input;

import javax.swing.*;
import javax.swing.text.Document;
import java.awt.*;

public class SimpleInput extends JPanel {
    private static final int DEFAULT_TEXT_COLUMN = 15;

    private JLabel label;
    private JTextField textBox;
    private InputVerifier inputVerifier;

    public SimpleInput(String labelText) {
        this.label = new JLabel(labelText);
        this.textBox = new JTextField();
        textBox.setName(labelText);
        initComponents();
    }

    private void initComponents() {
        label.setLabelFor(textBox);
        textBox.setColumns(DEFAULT_TEXT_COLUMN);
        this.setLayout(new GridLayout(1, 2));
        add(label);
        add(textBox);
    }

    @Override
    public void setInputVerifier(InputVerifier inputVerifier) {
        textBox.setVerifyInputWhenFocusTarget(false);
        textBox.setInputVerifier(inputVerifier);
    }

    public SimpleInput withTextFieldName(String name) {
        textBox.setName(name);
        return this;
    }

    public void readOnly(boolean readonly) {
        textBox.setEnabled(!readonly);
    }


    public JLabel getLabel() {
        return label;
    }

    public JTextField getTextBox() {
        return textBox;
    }

    public SimpleInput setLabel(String text) {
        label.setText(text);
        return this;
    }

    public String getText() {
        return textBox.getText();
    }

    public void setText(String text) {
        textBox.setText(text);
    }

    public SimpleInput setColumnTextField(int column) {
        textBox.setColumns(column);
        return this;
    }

    public SimpleInput setAction(Action action) {
        textBox.setAction(action);
        return this;
    }

    public SimpleInput setDocument(Document document) {
        textBox.setDocument(document);
        return this;
    }

    public Document getDocument() {
        return textBox.getDocument();
    }
}
