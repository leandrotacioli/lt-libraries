package com.leandrotacioli.libs.javafx.field;

import com.leandrotacioli.libs.LTDataTypes;
import com.leandrotacioli.libs.javafx.field.interfaces.IField;
import com.leandrotacioli.libs.javafx.field.interfaces.IFieldText;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.TextField;

public class FieldString extends TextField implements IField, IFieldText {

    private boolean isEnabled;

    protected FieldString(boolean isEnabled) {
        setEnabled(isEnabled);
        setFocusProperties();
        setValue(null);
    }

    private void setFocusProperties() {
        this.addFocusListener((obs, oldVal, newVal) -> {
            this.getStyleClass().removeAll(FieldStyles.FOCUS, FieldStyles.FOCUS_DISABLED);

            // Focus gained
            if (newVal) {
                this.positionCaret(this.getText().length());
                this.getStyleClass().add(isEnabled ? FieldStyles.FOCUS : FieldStyles.FOCUS_DISABLED);
            }

            // Focus lost
            if (oldVal) {

            }
        });
    }

    @Override
    public void setEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;

        this.setEditable(isEnabled);
        this.getStyleClass().add(isEnabled ? FieldStyles.ENABLED : FieldStyles.DISABLED);
    }

    @Override
    public Object getValue() {
        return this.getText();
    }

    @Override
    public void setValue(Object value) {
        this.setText(value != null ? (String) value : "");
    }

    @Override
    public void addFocusListener(ChangeListener<Boolean> changeListener) {
        this.focusedProperty().addListener(changeListener);
    }

    @Override
    public void setMaximumLength(int maximumLength) {
        if (maximumLength > 0) {
            FieldValidator fieldValidator = new FieldValidator(LTDataTypes.STRING);
            fieldValidator.textPattern(maximumLength);

            this.setTextFormatter(fieldValidator.getFormatter());
        }
    }

}
