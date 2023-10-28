package com.leandrotacioli.libs.javafx.field;

import com.leandrotacioli.libs.LTDataTypes;
import com.leandrotacioli.libs.javafx.field.interfaces.IField;
import com.leandrotacioli.libs.javafx.field.interfaces.IFieldNumeric;
import com.leandrotacioli.libs.transformation.DoubleTransformation;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.TextField;

import java.text.DecimalFormat;

public class FieldNumeric extends TextField implements IField, IFieldNumeric {

    private LTDataTypes dataType;
    private boolean isEnabled;
    private int fractionDigits;

    private DecimalFormat decimalFormat;

    protected FieldNumeric(LTDataTypes dataType, boolean isEnabled) {
        this(dataType, isEnabled, 0);
    }

    protected FieldNumeric(LTDataTypes dataType, boolean isEnabled, int fractionDigits) {
        this.dataType = dataType;
        this.isEnabled = isEnabled;
        this.fractionDigits = fractionDigits;

        setEnabled(isEnabled);
        setFieldProperties();
        setValue(0);
    }

    private void setFieldProperties() {
        if (this.dataType == LTDataTypes.DOUBLE) {
            this.decimalFormat = DoubleTransformation.getDecimalFormat(fractionDigits);
        }

        setFocusProperties();
    }

    private void setFocusProperties() {
        this.addFocusListener((obs, oldVal, newVal) -> {
            this.getStyleClass().removeAll(FieldStyles.FOCUS, FieldStyles.FOCUS_DISABLED);

            // Focus gained
            if (newVal) {
                if (this.dataType == LTDataTypes.INTEGER) {
                    FieldValidator fieldValidator = new FieldValidator(LTDataTypes.INTEGER);
                    this.setTextFormatter(fieldValidator.getFormatter());
                } else if (this.dataType == LTDataTypes.LONG) {
                    FieldValidator fieldValidator = new FieldValidator(LTDataTypes.LONG);
                    this.setTextFormatter(fieldValidator.getFormatter());
                } else if (this.dataType == LTDataTypes.DOUBLE) {
                    FieldValidator fieldValidator = new FieldValidator(LTDataTypes.DOUBLE);
                    fieldValidator.doublePattern(fractionDigits);
                    this.setTextFormatter(fieldValidator.getFormatter());
                    this.setText(DoubleTransformation.removeGroupingSeparator(this.getText()));
                } else {
                    throw new UnsupportedOperationException("This method is not allowed for " + this.dataType + " fields.");
                }

                this.getStyleClass().add(isEnabled ? FieldStyles.FOCUS : FieldStyles.FOCUS_DISABLED);
            }

            // Focus lost
            if (oldVal) {
                this.setValue(getValue());
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
        if (this.dataType == LTDataTypes.INTEGER) {
            return this.getText();
        } else if (this.dataType == LTDataTypes.LONG) {
            return this.getText();
        } else if (this.dataType == LTDataTypes.DOUBLE) {
            return DoubleTransformation.replaceDecimalSeparator(this.getText());
        } else {
            throw new UnsupportedOperationException("This method is not allowed for " + this.dataType + " fields.");
        }
    }

    @Override
    public void setValue(Object value) {
        if (value == null || value.equals("") || value.equals("-")) value = 0;

        this.setTextFormatter(null);

        if (this.dataType == LTDataTypes.INTEGER) {
            value = FieldValidator.validateIntegerValue(value);
        } else if (this.dataType == LTDataTypes.LONG) {
            value = FieldValidator.validateLongValue(value);
        } else if (this.dataType == LTDataTypes.DOUBLE) {
            value = decimalFormat.format(FieldValidator.validateDoubleValue(value));
        } else {
            throw new UnsupportedOperationException("This method is not allowed for " + this.dataType + " fields.");
        }

        this.setText("" + value);
    }

    @Override
    public void addFocusListener(ChangeListener<Boolean> changeListener) {
        this.focusedProperty().addListener(changeListener);
    }

    @Override
    public void setFractionDigits(int fractionDigits) {
        if (this.dataType == LTDataTypes.DOUBLE) {
            this.fractionDigits = fractionDigits;

            setFieldProperties();
        } else {
            throw new UnsupportedOperationException("This method is not allowed for " + this.dataType + " fields.");
        }
    }

}
