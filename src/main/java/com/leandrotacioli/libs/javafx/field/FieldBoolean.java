package com.leandrotacioli.libs.javafx.field;

import com.leandrotacioli.libs.javafx.field.interfaces.IField;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.CheckBox;

public class FieldBoolean extends CheckBox implements IField {

    private boolean isEnabled;
    private boolean lockValueChange;

    protected FieldBoolean(String label, boolean isEnabled) {
        this.setText(label);
        this.setEnabled(isEnabled);
        this.selectedProperty().addListener((observable, oldValue, newValue) -> handleChanged(newValue));

        setFocusProperties();
        setValue(false);
        setBoxLookUp(false);
    }

    private void setFocusProperties() {
        this.addFocusListener((obs, oldVal, newVal) -> {
            // Focus gained
            if (newVal) {
                setBoxLookUp(true);
            }

            // Focus lost
            if (oldVal) {
                setBoxLookUp(false);
            }
        });
    }

    /**
     * Creates a handler to retrieve the checkbox original value when disabled.
     *
     * @param newValue - New value
     */
    private void handleChanged(Boolean newValue) {
        if (!lockValueChange) {
            lockValueChange = true;

            if (!isEnabled) {
                this.setSelected(!newValue);
            }

            lockValueChange = false;
        }
    }

    protected void setBoxLookUp(boolean isOnFocus) {
        if (this.lookup(".box") != null) {
            if (isOnFocus) {
                if (isEnabled) {
                    this.lookup(".box").setStyle(FieldStyles.PROPERTY_BACKGROUND_FOCUS + " " + FieldStyles.PROPERTY_BORDER_COLOR_FOCUS);
                } else {
                    this.lookup(".box").setStyle(FieldStyles.PROPERTY_BACKGROUND_DISABLED + " " + FieldStyles.PROPERTY_BORDER_COLOR_FOCUS);
                }
            } else {
                if (isEnabled) {
                    this.lookup(".box").setStyle(FieldStyles.PROPERTY_BACKGROUND_ENABLED + " " + FieldStyles.PROPERTY_BORDER_COLOR);
                } else {
                    this.lookup(".box").setStyle(FieldStyles.PROPERTY_BACKGROUND_DISABLED + " " + FieldStyles.PROPERTY_BORDER_COLOR);
                }
            }
        }
    }

    @Override
    public void setEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
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
    public void addFocusListener(ChangeListener<Boolean> changeListener) {
        this.focusedProperty().addListener(changeListener);
    }

}
