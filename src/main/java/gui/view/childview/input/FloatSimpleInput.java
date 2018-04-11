package gui.view.childview.input;

import gui.verifier.FloatInputVerifier;

public class FloatSimpleInput extends SimpleInput {
    public FloatSimpleInput(String labelText) {
        super(labelText);
        setInputVerifier(new FloatInputVerifier(0f, 10f));
        setVerifyInputWhenFocusTarget(true);
    }
}
