package com.leandrotacioli.libs.javafx.field;

import com.leandrotacioli.libs.LTDataTypes;
import javafx.beans.value.ChangeListener;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Custom fields using JavaFX.
 */
public class LTField implements FieldInterface {

    private AnchorPane fieldPane;
    private Label labelField;

    private FieldNumeric fieldNumeric;
    private FieldString fieldString;

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
        labelField.setFont(Font.font("System", FontWeight.BOLD, 13));

        AnchorPane.setLeftAnchor(labelField, 5.0);
        AnchorPane.setRightAnchor(labelField, 5.0);
        AnchorPane.setTopAnchor(labelField, 5.0);

        fieldPane = new AnchorPane();
        fieldPane.minWidth(40);
        fieldPane.minHeight(40);

        fieldPane.getChildren().add(labelField);

        setFieldType();
    }

    private void setFieldType() {
        if (this.dataType == LTDataTypes.INTEGER) {
            fieldNumeric = new FieldNumeric(dataType, isEnabled);

        } else if (this.dataType == LTDataTypes.LONG) {
            fieldNumeric = new FieldNumeric(dataType, isEnabled);

        } else if (this.dataType == LTDataTypes.DOUBLE) {
            fieldNumeric = new FieldNumeric(dataType, isEnabled,2);

        } else if (this.dataType == LTDataTypes.STRING) {
            fieldString = new FieldString(isEnabled);

        } else if (this.dataType == LTDataTypes.TEXT) {
            throw new UnsupportedOperationException("Not supported yet for " + this.dataType.toString() + " field.");

        } else if (this.dataType == LTDataTypes.DATE) {
            throw new UnsupportedOperationException("Not supported yet for " + this.dataType.toString() + " field.");

        } else if (this.dataType == LTDataTypes.HOUR) {
            throw new UnsupportedOperationException("Not supported yet for " + this.dataType.toString() + " field.");

        } else if (this.dataType == LTDataTypes.BOOLEAN) {
            throw new UnsupportedOperationException("Not supported yet for " + this.dataType.toString() + " field.");
        }

        addFieldToPane(fieldNumeric);
        addFieldToPane(fieldString);
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

    @Override
    public void setEnabled(boolean isEnabled) {
        if (fieldNumeric != null) fieldNumeric.setEnabled(isEnabled);
        if (fieldString != null) fieldString.setEnabled(isEnabled);
    }

    @Override
    public Object getValue() {
        if (fieldNumeric != null) return fieldNumeric.getValue();
        if (fieldString != null) return fieldString.getValue();

        throw new UnsupportedOperationException("Not supported yet for " + this.dataType.toString() + " field.");
    }

    @Override
    public void setValue(Object objValue) {
        if (fieldNumeric != null) fieldNumeric.setValue(objValue);
        if (fieldString != null) fieldString.setValue(objValue);
    }

    @Override
    public void setMaximumLength(int maximumLength) {
        if (fieldString != null) {
            fieldString.setMaximumLength(maximumLength);
        } else {
            throw new UnsupportedOperationException("This method is not allowed for " + this.dataType.toString() + " field.");
        }
    }

    @Override
    public void addFocusListener(ChangeListener<Boolean> changeListener) {
        if (fieldNumeric != null) fieldNumeric.addFocusListener(changeListener);
        if (fieldString != null) fieldString.addFocusListener(changeListener);
    }

}
