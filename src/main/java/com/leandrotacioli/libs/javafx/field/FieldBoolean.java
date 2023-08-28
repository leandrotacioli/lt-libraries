package com.leandrotacioli.libs.javafx.field;

import com.leandrotacioli.libs.LTDataTypes;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.CheckBox;

public class FieldBoolean extends CheckBox implements FieldInterface {

    private boolean isEnabled;

    protected FieldBoolean(String label, boolean isEnabled) {
        this.setText(label);
        this.setEnabled(isEnabled);

        setFocusProperties();
        setValue(false);
    }

    private void setFocusProperties() {
        this.addFocusListener((obs, oldVal, newVal) -> {
            // Focus gained
            if (newVal) {
                this.lookup(".box").setStyle("-fx-background-color: #FFFFBB;");
            }

            // Focus lost
            if (oldVal) {
                this.lookup(".box").setStyle("-fx-background-color: #FFFFFF;");
            }
        });
    }

    @Override
    public void setEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;

        this.setDisable(!isEnabled);
    }

    @Override
    public Object getValue() {
        return this.isSelected();
    }

    @Override
    public void setValue(Object value) {
        try {
            this.setSelected(value != null ? (Boolean) value : false);
        } catch (Exception e) {
            System.err.println("Error on field validation - Invalid BOOLEAN value (" + value + ").");
        }
    }

    @Override
    public void setMaximumLength(int maximumLength) {
        throw new UnsupportedOperationException("This method is not allowed for " + LTDataTypes.BOOLEAN + " fields.");
    }

    @Override
    public void setFractionDigits(int fractionDigits) {
        throw new UnsupportedOperationException("This method is not allowed for " + LTDataTypes.BOOLEAN + " fields.");
    }

    @Override
    public void setDateFormat(String dateFormat) {
        throw new UnsupportedOperationException("This method is not allowed for " + LTDataTypes.BOOLEAN + " fields.");
    }

    @Override
    public void addFocusListener(ChangeListener<Boolean> changeListener) {
        this.focusedProperty().addListener(changeListener);
    }

}
