package com.leandrotacioli.libs.javafx.field;

import com.leandrotacioli.libs.LTDataTypes;
import com.leandrotacioli.libs.javafx.field.interfaces.IField;
import com.leandrotacioli.libs.javafx.field.interfaces.IFieldComboBox;
import com.leandrotacioli.libs.javafx.field.interfaces.IFieldDate;
import com.leandrotacioli.libs.javafx.field.interfaces.IFieldNumeric;
import com.leandrotacioli.libs.javafx.field.interfaces.IFieldText;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 * Custom fields using JavaFX.
 */
public class LTField implements IField, IFieldComboBox, IFieldDate, IFieldNumeric, IFieldText {

    private AnchorPane fieldPane;
    private Label labelField;

    private FieldNumeric fieldNumeric;
    private FieldString fieldString;
    private FieldText fieldText;
    private FieldDate fieldDate;
    private FieldTime fieldTime;
    private FieldBoolean fieldBoolean;
    private FieldComboBox fieldComboBox;

    private LTDataTypes dataType;
    private boolean isEnabled;
    private boolean isMandatoryField;

    /**
     * Custom fields using JavaFX.
     *
     * @param label
     * @param dataType
     * @param isEnabled
     * @param isMandatoryField
     */
    public LTField(String label, LTDataTypes dataType, boolean isEnabled, boolean isMandatoryField) {
        this.dataType = dataType;
        this.isEnabled = isEnabled;
        this.isMandatoryField = isMandatoryField;

        labelField = new Label(label);

        AnchorPane.setLeftAnchor(labelField, 5.0);
        AnchorPane.setRightAnchor(labelField, 5.0);
        AnchorPane.setTopAnchor(labelField, 5.0);

        fieldPane = new AnchorPane();
        fieldPane.minWidth(40);
        fieldPane.minHeight(40);
        fieldPane.getStylesheets().add(getClass().getResource("/css/javafx.css").toExternalForm());

        fieldPane.getChildren().add(labelField);

        setFieldType();
    }

    private void setFieldType() {
        if (this.dataType == LTDataTypes.INTEGER) {
            fieldNumeric = new FieldNumeric(dataType, isEnabled);
            fieldNumeric.setAlignment(Pos.CENTER_RIGHT);

        } else if (this.dataType == LTDataTypes.LONG) {
            fieldNumeric = new FieldNumeric(dataType, isEnabled);
            fieldNumeric.setAlignment(Pos.CENTER_RIGHT);

        } else if (this.dataType == LTDataTypes.DOUBLE) {
            fieldNumeric = new FieldNumeric(dataType, isEnabled,2);
            fieldNumeric.setAlignment(Pos.CENTER_RIGHT);

        } else if (this.dataType == LTDataTypes.STRING) {
            fieldString = new FieldString(isEnabled);

        } else if (this.dataType == LTDataTypes.TEXT) {
            fieldText = new FieldText(isEnabled);

        } else if (this.dataType == LTDataTypes.DATE) {
            fieldDate = new FieldDate(isEnabled);

        } else if (this.dataType == LTDataTypes.TIME) {
            fieldTime = new FieldTime(isEnabled);

        } else if (this.dataType == LTDataTypes.BOOLEAN) {
            fieldBoolean = new FieldBoolean(labelField.getText(), isEnabled);
            labelField.setText("");

        } else if (this.dataType == LTDataTypes.COMBOBOX) {
            fieldComboBox = new FieldComboBox(isEnabled);

        } else {
            throw new UnsupportedOperationException("Not supported yet for " + this.dataType + " field.");
        }

        setMinHeight(FieldStyles.MINIMUM_HEIGHT);

        addFieldToPane(fieldNumeric);
        addFieldToPane(fieldString);
        addFieldToPane(fieldText);
        addFieldToPane(fieldDate);
        addFieldToPane(fieldTime);
        addFieldToPane(fieldBoolean);
        addFieldToPane(fieldComboBox);
    }

    private void addFieldToPane(Node field) {
        if (field != null) {
            AnchorPane.setLeftAnchor(field, 5.0);
            AnchorPane.setRightAnchor(field, 5.0);
            AnchorPane.setTopAnchor(field, 23.0);
            AnchorPane.setBottomAnchor(field, 5.0);

            fieldPane.getChildren().add(field);
        }
    }

    /**
     * Loads the custom fields.
     *
     * @return fieldPane
     */
    public AnchorPane load() {
        return fieldPane;
    }

    /**
     * Sets the minimum height of the custom field.
     *
     * @param minHeight
     */
    public void setMinHeight(int minHeight) {
        if (minHeight < FieldStyles.MINIMUM_HEIGHT) {
            System.err.println("setMinHeight - Height not allowed for the field '" + labelField.getText() + "'. The minimum height required is " + FieldStyles.MINIMUM_HEIGHT + ".");
            return;
        }

        if (fieldNumeric != null) fieldNumeric.setMinHeight(minHeight);
        else if (fieldString != null) fieldString.setMinHeight(minHeight);
        else if (fieldText != null) fieldText.setMinHeight(minHeight);
        else if (fieldDate != null) fieldDate.setMinHeight(minHeight);
        else if (fieldTime != null) fieldTime.setMinHeight(minHeight);
        else if (fieldBoolean != null) fieldBoolean.setMinHeight(minHeight);
        else if (fieldComboBox != null) fieldComboBox.setMinHeight(minHeight);
    }

    /**
     * Sets the horizontal alignment of the custom field.
     *
     * @param horizontalAlignment
     */
    public void setHorizontalAlignment(Pos horizontalAlignment) {
        if (fieldNumeric != null) fieldNumeric.setAlignment(horizontalAlignment);
        else if (fieldString != null) fieldString.setAlignment(horizontalAlignment);
        else if (fieldTime != null) fieldTime.setAlignment(horizontalAlignment);
        else if (fieldBoolean != null) fieldBoolean.setAlignment(horizontalAlignment);
        else System.err.println("setHorizontalAlignment - This method is not allowed for " + this.dataType + " fields.");
    }

    @Override
    public void setEnabled(boolean isEnabled) {
        if (fieldNumeric != null) fieldNumeric.setEnabled(isEnabled);
        else if (fieldString != null) fieldString.setEnabled(isEnabled);
        else if (fieldText != null) fieldText.setEnabled(isEnabled);
        else if (fieldDate != null) fieldDate.setEnabled(isEnabled);
        else if (fieldTime != null) fieldTime.setEnabled(isEnabled);
        else if (fieldBoolean != null) fieldBoolean.setEnabled(isEnabled);
        else if (fieldComboBox != null) fieldComboBox.setEnabled(isEnabled);
    }

    @Override
    public Object getValue() {
        if (fieldNumeric != null) return fieldNumeric.getValue();
        else if (fieldString != null) return fieldString.getValue();
        else if (fieldText != null) return fieldText.getValue();
        else if (fieldDate != null) return fieldDate.getValue();
        else if (fieldTime != null) return fieldTime.getValue();
        else if (fieldBoolean != null) return fieldBoolean.getValue();
        else if (fieldComboBox != null) return fieldComboBox.getValue();

        throw new UnsupportedOperationException("getValue - Not supported yet for " + this.dataType + " field.");
    }

    @Override
    public void setValue(Object value) {
        if (fieldNumeric != null) fieldNumeric.setValue(value);
        else if (fieldString != null) fieldString.setValue(value);
        else if (fieldText != null) fieldText.setValue(value);
        else if (fieldDate != null) fieldDate.setValue(value);
        else if (fieldTime != null) fieldTime.setValue(value);
        else if (fieldBoolean != null) fieldBoolean.setValue(value);
        else if (fieldComboBox != null) fieldComboBox.setValue(value);
    }

    @Override
    public void addFocusListener(ChangeListener<Boolean> changeListener) {
        if (fieldNumeric != null) fieldNumeric.addFocusListener(changeListener);
        else if (fieldString != null) fieldString.addFocusListener(changeListener);
        else if (fieldText != null) fieldText.addFocusListener(changeListener);
        else if (fieldDate != null) fieldDate.addFocusListener(changeListener);
        else if (fieldTime != null) fieldTime.addFocusListener(changeListener);
        else if (fieldBoolean != null) fieldBoolean.addFocusListener(changeListener);
        else if (fieldComboBox != null) fieldComboBox.addFocusListener(changeListener);
    }

    @Override
    public void addValues(String key, String description) {
        if (this.dataType == LTDataTypes.COMBOBOX) {
            fieldComboBox.addValues(key, description);
        } else {
            System.err.println("addValues - This method is not allowed for " + this.dataType + " fields.");
        }
    }

    @Override
    public void setDateFormat(String dateFormat) {
        if (this.dataType == LTDataTypes.DATE) {
            fieldDate.setDateFormat(dateFormat);
        } else {
            System.err.println("setDateFormat - This method is not allowed for " + this.dataType + " fields.");
        }
    }

    @Override
    public void setFractionDigits(int fractionDigits) {
        if (this.dataType == LTDataTypes.DOUBLE) {
            fieldNumeric.setFractionDigits(fractionDigits);
        } else {
            System.err.println("setFractionDigits - This method is not allowed for " + this.dataType + " fields.");
        }
    }

    @Override
    public void setMaximumLength(int maximumLength) {
        if (this.dataType == LTDataTypes.STRING || this.dataType == LTDataTypes.TEXT) {
            if (fieldString != null) fieldString.setMaximumLength(maximumLength);
            else if (fieldText != null) fieldText.setMaximumLength(maximumLength);
        } else {
            System.err.println("setMaximumLength - This method is not allowed for " + this.dataType + " fields.");
        }
    }
}
