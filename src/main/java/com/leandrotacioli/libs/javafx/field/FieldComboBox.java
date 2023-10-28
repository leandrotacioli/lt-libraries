package com.leandrotacioli.libs.javafx.field;

import com.leandrotacioli.libs.javafx.field.interfaces.IField;
import com.leandrotacioli.libs.javafx.field.interfaces.IFieldComboBox;
import javafx.beans.value.ChangeListener;
import javafx.scene.layout.AnchorPane;

public class FieldComboBox extends AnchorPane implements IField, IFieldComboBox {

    private boolean isEnabled;

    private FieldComboBoxAutoComplete comboBox;

    protected FieldComboBox(boolean isEnabled) {
        comboBox = new FieldComboBoxAutoComplete();

        setEnabled(isEnabled);
        setFocusProperties();

        AnchorPane.setLeftAnchor(comboBox, 0.0);
        AnchorPane.setRightAnchor(comboBox, 0.0);
        AnchorPane.setTopAnchor(comboBox, 0.0);

        this.getChildren().addAll(comboBox);
    }

    private void setFocusProperties() {
        comboBox.focusedProperty().addListener((obs, oldVal, newVal) -> {
            // Focus gained
            if (newVal) {
                if (isEnabled) {
                    comboBox.setStyle(FieldStyles.PROPERTY_BACKGROUND_FOCUS + " " + FieldStyles.PROPERTY_BORDER_COLOR_FOCUS + " " + FieldStyles.PROPERTY_BORDER_RADIUS_2);
                }
            }

            // Focus lost
            if (oldVal) {
                if (isEnabled) {
                    comboBox.setStyle(FieldStyles.PROPERTY_BACKGROUND_ENABLED + " " + FieldStyles.PROPERTY_BORDER_COLOR + " " + FieldStyles.PROPERTY_BORDER_RADIUS_2);
                }
            }
        });
    }

    @Override
    public void setEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;

        comboBox.getEditor().setEditable(isEnabled);

        if (isEnabled) {
            comboBox.setOnShown(null);
            comboBox.setStyle(FieldStyles.PROPERTY_BACKGROUND_ENABLED + " " + FieldStyles.PROPERTY_BORDER_COLOR + " " + FieldStyles.PROPERTY_BORDER_RADIUS_2);
        } else {
            comboBox.setOnShown(event -> comboBox.hide());
            comboBox.setStyle(FieldStyles.PROPERTY_BACKGROUND_DISABLED + " " + FieldStyles.PROPERTY_BORDER_COLOR + " " + FieldStyles.PROPERTY_BORDER_RADIUS_2);
        }
    }

    @Override
    public Object getValue() {
        return comboBox.getSelectedValue();
    }

    @Override
    public void setValue(Object value) {
        try {
            comboBox.setSelectedValue(value);
            comboBox.setValue(comboBox.getSelectedDescription());
        } catch (Exception e) {
            System.err.println("Error on field validation - Invalid COMBOBOX value (" + value + ").");
        }
    }

    @Override
    public void addFocusListener(ChangeListener<Boolean> changeListener) {
        comboBox.focusedProperty().addListener(changeListener);
    }

    @Override
    public void addValues(String key, String description) {
        try {
            comboBox.addValues(key, description);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

}
