package com.leandrotacioli.libs.javafx.field;

import com.leandrotacioli.libs.LTDataTypes;
import com.leandrotacioli.libs.javafx.field.interfaces.IField;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class FieldTime extends TextField implements IField {

    private boolean isEnabled;

    protected FieldTime(boolean isEnabled) {
        setEnabled(isEnabled);
        setFieldMask();
        setFocusProperties();
        setValue(null);
    }

    private void setFieldMask() {
        this.setOnKeyTyped((KeyEvent keyEvent) -> {
            if (this.getText().length() >= 1 && this.getText().length() <= 5) {
                if (removeCharacter(this.getText(), keyEvent.getCharacter())) {
                    this.setText(this.getText().substring(0, this.getText().length() - 1));
                    this.positionCaret(this.getText().length());
                }
            }

            // Add time separator ':'
            if (keyEvent.getCharacter().trim().length() > 0) {
                if (this.getText().length() == 2) {
                    this.setText(this.getText() + ":");
                    this.positionCaret(this.getText().length());
                }
            }
        });
    }

    protected boolean removeCharacter(String time, String lastCharacter) {
        if (lastCharacter.equals("\b")) return false;      // Backspace
        if (lastCharacter.equals("\t")) return false;      // TAB
        if (lastCharacter.equals("\n")) return false;      // ENTER
        if (lastCharacter.equals("\u001B")) return false;  // ESC
        if (lastCharacter.equals("\u007F")) return false;  // DEL

        // Time separator ':'
        if (time.length() == 3 && lastCharacter.equals(":")) return false;
        if (time.length() == 3 && !lastCharacter.equals(":")) return true;

        if ("0123456789".contains(lastCharacter)) return false;
        if (time.length() == 5 && "0123456789".contains(time.substring(time.length() - 1))) return false;

        return true;
    }

    private void setFocusProperties() {
        this.addFocusListener((obs, oldVal, newVal) -> {
            this.getStyleClass().removeAll(FieldStyles.FOCUS, FieldStyles.FOCUS_DISABLED, FieldStyles.FOCUS_ERROR_VALIDATION);

            // Focus gained
            if (newVal) {
                FieldValidator fieldValidator = new FieldValidator(LTDataTypes.STRING);
                fieldValidator.textPattern(5);

                this.setTextFormatter(fieldValidator.getFormatter());
                this.positionCaret(this.getText().length());
                this.getStyleClass().add(isEnabled ? FieldStyles.FOCUS : FieldStyles.FOCUS_DISABLED);
            }

            // Focus lost
            if (oldVal) {
                if (this.getText() != null && this.getText().length() > 0) {
                    try {
                        this.setValue(String.valueOf(FieldValidator.validateTimeValue(this.getText())));
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                        this.requestFocus();
                        this.getStyleClass().add(FieldStyles.FOCUS_ERROR_VALIDATION);
                    }
                }
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
        try {
            this.setText(value != null ? String.valueOf(FieldValidator.validateTimeValue(value)) : null);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            this.setText(null);
        }
    }

    @Override
    public void addFocusListener(ChangeListener<Boolean> changeListener) {
        this.focusedProperty().addListener(changeListener);
    }

}
