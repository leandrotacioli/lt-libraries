package com.leandrotacioli.libs.javafx.field;

import com.leandrotacioli.libs.LTDataTypes;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.TextField;

public class FieldString extends TextField implements FieldInterface {

    private int maximumLength;

    protected FieldString(boolean isEnabled) {
        this.setEditable(isEnabled);
    }

    private void validateMaximumLength() {
        String string = this.getText();

        if (maximumLength > 0 && string.length() > maximumLength) {
            this.setText(string.substring(0, maximumLength));
            this.positionCaret(string.length());
        }
    }

    @Override
    public void setEnabled(boolean isEnabled) {
        this.setEditable(isEnabled);
    }

    @Override
    public Object getValue() {
        return this.getText();
    }

    @Override
    public void setValue(Object value) {
        this.setText((String) value);

        validateMaximumLength();
    }

    @Override
    public void setMaximumLength(int maximumLength) {
        this.maximumLength = maximumLength;

        this.setOnKeyTyped(event -> validateMaximumLength());
    }

    @Override
    public void setFractionDigits(int fractionDigits) {
        throw new UnsupportedOperationException("This method is not allowed for " + LTDataTypes.STRING + " fields.");
    }

    @Override
    public void addFocusListener(ChangeListener<Boolean> changeListener) {
        this.focusedProperty().addListener(changeListener);
    }

}
