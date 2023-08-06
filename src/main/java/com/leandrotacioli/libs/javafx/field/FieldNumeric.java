package com.leandrotacioli.libs.javafx.field;

import com.leandrotacioli.libs.LTDataTypes;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.TextField;

public class FieldNumeric extends TextField implements FieldInterface {

    protected FieldNumeric(LTDataTypes dataType, boolean isEnabled) {
        this(dataType, isEnabled, 0);
    }

    protected FieldNumeric(LTDataTypes dataType, boolean isEnabled, int fractionDigits) {
        this.setEditable(isEnabled);

        this.setTextFormatter(new FieldValidator(dataType, fractionDigits).getFormatter());
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
    public void setValue(Object objValue) {
        this.setText((String) objValue);
    }

    @Override
    public void setMaximumLength(int maximumLength) {
        throw new UnsupportedOperationException("This method is not allowed for INTEGER, LONG and DOUBLE fields.");
    }

    @Override
    public void addFocusListener(ChangeListener<Boolean> changeListener) {
        this.focusedProperty().addListener(changeListener);
    }

}
