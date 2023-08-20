package com.leandrotacioli.libs.javafx.field;

import com.leandrotacioli.libs.LTDataTypes;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.TextField;

public class FieldString extends TextField implements FieldInterface {

    protected FieldString(boolean isEnabled) {
        this.setEditable(isEnabled);
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
    }

    @Override
    public void setMaximumLength(int maximumLength) {
        if (maximumLength > 0) {
            FieldValidator fieldValidator = new FieldValidator(LTDataTypes.STRING);
            fieldValidator.stringPattern(maximumLength);

            this.setTextFormatter(fieldValidator.getFormatter());
        }
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
