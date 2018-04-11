package gui.view.childview.input;

import gui.verifier.DateInputVerifier;

public class DateSimpleInput extends SimpleInput {
    public DateSimpleInput(String labelText) {
        super(labelText);
        setInputVerifier(new DateInputVerifier());
    }
}
