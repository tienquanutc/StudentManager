package gui.view.childview.input;

import gui.verifier.IntegerInputVerifier;

public class IntegerSimpleInput extends SimpleInput {
    public IntegerSimpleInput(String labelText) {
        super(labelText);
        setInputVerifier(new IntegerInputVerifier(0, Integer.MAX_VALUE));
    }
}
