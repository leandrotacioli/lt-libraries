package com.leandrotacioli.libs.javafx.field;

import com.leandrotacioli.libs.LTDataTypes;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class FieldTime extends TextField implements FieldInterface {

    protected FieldTime(boolean isEnabled) {
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
            // Focus gained
            if (newVal) {
                FieldValidator fieldValidator = new FieldValidator(LTDataTypes.STRING);
                fieldValidator.textPattern(5);

                this.setTextFormatter(fieldValidator.getFormatter());
                this.positionCaret(this.getText().length());
                this.setStyle("-fx-focus-color: #FEFFB5;");
                //this.setStyle("-fx-background-color: #FEFFB5; -fx-text-box-border: #A59B9B;");
            }

            // Focus lost
            if (oldVal) {
                if (this.getText() != null && this.getText().length() > 0) {
                    try {
                        this.setValue(String.valueOf(FieldValidator.validateTimeValue(this.getText())));
                        this.setStyle(null);
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                        this.setStyle("-fx-focus-color: #FFB8B8;");
                        this.requestFocus();
                    }
                } else {
                    this.setStyle(null);
                }
            }
        });
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
        try {
            this.setText(value != null ? String.valueOf(FieldValidator.validateTimeValue(value)) : null);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            this.setText(null);
        }
    }

    @Override
    public void setMaximumLength(int maximumLength) {
        throw new UnsupportedOperationException("This method is not allowed for " + LTDataTypes.TIME + " fields.");
    }

    @Override
    public void setFractionDigits(int fractionDigits) {
        throw new UnsupportedOperationException("This method is not allowed for " + LTDataTypes.TIME + " fields.");
    }

    @Override
    public void setDateFormat(String dateFormat) {
        throw new UnsupportedOperationException("This method is not allowed for " + LTDataTypes.TIME + " fields.");
    }

    @Override
    public void addFocusListener(ChangeListener<Boolean> changeListener) {
        this.focusedProperty().addListener(changeListener);
    }

}
