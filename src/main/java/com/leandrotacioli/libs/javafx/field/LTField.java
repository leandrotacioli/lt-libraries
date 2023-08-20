package com.leandrotacioli.libs.javafx.field;

import com.leandrotacioli.libs.LTDataTypes;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Custom fields using JavaFX.
 */
public class LTField implements FieldInterface {

    private final int MINIMUM_HEIGHT = 25;

    private AnchorPane fieldPane;
    private Label labelField;

    private FieldNumeric fieldNumeric;
    private FieldString fieldString;
    private FieldText fieldText;

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
            fieldNumeric.setAlignment(Pos.CENTER_RIGHT);
            fieldNumeric.setValue(0);

        } else if (this.dataType == LTDataTypes.LONG) {
            fieldNumeric = new FieldNumeric(dataType, isEnabled);
            fieldNumeric.setAlignment(Pos.CENTER_RIGHT);
            fieldNumeric.setValue(0);

        } else if (this.dataType == LTDataTypes.DOUBLE) {
            fieldNumeric = new FieldNumeric(dataType, isEnabled,2);
            fieldNumeric.setAlignment(Pos.CENTER_RIGHT);
            fieldNumeric.setValue(0.00);

        } else if (this.dataType == LTDataTypes.STRING) {
            fieldString = new FieldString(isEnabled);
            fieldString.setValue("");

        } else if (this.dataType == LTDataTypes.TEXT) {
            fieldText = new FieldText(isEnabled);
            fieldText.setValue("");

        } else if (this.dataType == LTDataTypes.DATE) {
            throw new UnsupportedOperationException("Not supported yet for " + this.dataType + " field.");

        } else if (this.dataType == LTDataTypes.HOUR) {
            throw new UnsupportedOperationException("Not supported yet for " + this.dataType + " field.");

        } else if (this.dataType == LTDataTypes.BOOLEAN) {
            throw new UnsupportedOperationException("Not supported yet for " + this.dataType + " field.");

        } else {
            throw new UnsupportedOperationException("Not supported yet for " + this.dataType + " field.");
        }

        setMinHeight(MINIMUM_HEIGHT);

        addFieldToPane(fieldNumeric);
        addFieldToPane(fieldString);
        addFieldToPane(fieldText);
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
        if (minHeight < MINIMUM_HEIGHT) {
            System.err.println("setMinHeight - Height not allowed for the field '" + labelField.getText() + "'. The minimum value required is " + MINIMUM_HEIGHT + ".");
            return;
        }

        if (fieldNumeric != null) fieldNumeric.setMinHeight(minHeight);
        if (fieldString != null) fieldString.setMinHeight(minHeight);
        if (fieldText != null) fieldText.setMinHeight(minHeight);
    }

    /**
     * Sets the horizontal alignment of the custom field.
     *
     * @param horizontalAlignment
     */
    public void setHorizontalAlignment(Pos horizontalAlignment) {
        if (fieldNumeric != null) fieldNumeric.setAlignment(horizontalAlignment);
        if (fieldString != null) fieldString.setAlignment(horizontalAlignment);
        if (fieldText != null) System.err.println("setHorizontalAlignment - This method is not allowed for " + this.dataType + " fields.");
    }

    @Override
    public void setEnabled(boolean isEnabled) {
        if (fieldNumeric != null) fieldNumeric.setEnabled(isEnabled);
        if (fieldString != null) fieldString.setEnabled(isEnabled);
        if (fieldText != null) fieldText.setEnabled(isEnabled);
    }

    @Override
    public Object getValue() {
        if (fieldNumeric != null) return fieldNumeric.getValue();
        if (fieldString != null) return fieldString.getValue();
        if (fieldText != null) return fieldText.getValue();

        throw new UnsupportedOperationException("getValue - Not supported yet for " + this.dataType + " field.");
    }

    @Override
    public void setValue(Object value) {
        if (fieldNumeric != null) fieldNumeric.setValue(value);
        if (fieldString != null) fieldString.setValue(value);
        if (fieldText != null) fieldText.setValue(value);
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

    @Override
    public void setFractionDigits(int fractionDigits) {
        if (this.dataType == LTDataTypes.DOUBLE) {
            fieldNumeric.setFractionDigits(fractionDigits);
        } else {
            System.err.println("setFractionDigits - This method is not allowed for " + this.dataType + " fields.");
        }
    }

    @Override
    public void addFocusListener(ChangeListener<Boolean> changeListener) {
        if (fieldNumeric != null) fieldNumeric.addFocusListener(changeListener);
        if (fieldString != null) fieldString.addFocusListener(changeListener);
        if (fieldText != null) fieldText.addFocusListener(changeListener);
    }

}
