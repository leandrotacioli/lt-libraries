package com.leandrotacioli.libs.javafx.field;

import com.leandrotacioli.libs.LTDataTypes;
import com.leandrotacioli.libs.LTParameters;
import com.leandrotacioli.libs.transformation.DoubleTransformation;
import javafx.scene.control.TextFormatter;

import java.math.BigDecimal;
import java.util.regex.Pattern;

public class FieldValidator {

    private LTDataTypes dataType;
    private Pattern pattern;

    private int fractionDigits;
    private int maxLength;

    protected FieldValidator(LTDataTypes dataType) {
        this.dataType = dataType;

        if (this.dataType == LTDataTypes.INTEGER) integerPattern();
        else if (this.dataType == LTDataTypes.LONG) longPattern();
        else if (this.dataType == LTDataTypes.DOUBLE) doublePattern(2);
        else if (this.dataType == LTDataTypes.STRING) stringPattern(0);
        else if (this.dataType == LTDataTypes.TEXT) textPattern(0);
    }

    protected TextFormatter<Object> getFormatter() {
        if (this.dataType == LTDataTypes.INTEGER || this.dataType == LTDataTypes.LONG || this.dataType == LTDataTypes.DOUBLE) {
            return new TextFormatter<>(this::validateNumericChange);
        } else if (this.dataType == LTDataTypes.STRING || this.dataType == LTDataTypes.TEXT) {
            return new TextFormatter<>(this::validateTextChange);
        }

        return new TextFormatter<>(this::validateDefaultChange);
    }

    private TextFormatter.Change validateDefaultChange(TextFormatter.Change change) {
        if (pattern.matcher(change.getControlNewText()).matches()) {
            return change;
        }

        return null;
    }

    private TextFormatter.Change validateNumericChange(TextFormatter.Change change) {
        if (pattern.matcher(change.getControlNewText()).matches()) {
            if (change.getControlNewText() != null && change.getControlNewText().length() > 0 && !change.getControlNewText().equals("-")) {
                try {
                    if (this.dataType == LTDataTypes.INTEGER) validateIntegerValue(change.getControlNewText());
                    else if (this.dataType == LTDataTypes.LONG) validateLongValue(change.getControlNewText());
                    else if (this.dataType == LTDataTypes.DOUBLE) validateDoubleValue(DoubleTransformation.replaceDecimalSeparator(change.getControlNewText()));
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                    return null;
                }
            }

            return change;
        }

        return null;
    }

    private TextFormatter.Change validateTextChange(TextFormatter.Change change) {
        if (change.getControlNewText().length() <= maxLength) {
            return change;

        } else {
            if (change.getControlText().length() <= maxLength || change.getRangeStart() != change.getRangeEnd()) {
                String oldTextBeforeAnchor = change.getControlText().substring(0, change.getControlAnchor());
                String oldTextAfterAnchor = change.getControlText().substring(change.getControlAnchor(), change.getControlText().length());

                if (change.getControlText().length() == maxLength && change.getRangeStart() != change.getRangeEnd()) {
                    oldTextBeforeAnchor = change.getControlText().substring(0, change.getRangeStart());
                    oldTextAfterAnchor = change.getControlText().substring(change.getRangeEnd(), change.getControlText().length());
                }

                String newText = change.getText().substring(0, (maxLength - oldTextBeforeAnchor.length() - oldTextAfterAnchor.length()));

                change.setText(oldTextBeforeAnchor + newText + oldTextAfterAnchor);
                change.setRange(0, change.getControlText().length());
                change.setAnchor(oldTextBeforeAnchor.length() + newText.length());
                change.setCaretPosition(oldTextBeforeAnchor.length() + newText.length());

                return change;

            } else {
                return null;
            }
        }
    }

    protected void integerPattern() {
        this.pattern = Pattern.compile("-?\\d*");
    }

    protected void longPattern() {
        this.pattern = Pattern.compile("-?\\d*");
    }

    protected void doublePattern(int fractionDigits) {
        this.pattern = Pattern.compile("-?\\d*(\\" + LTParameters.getInstance().getDecimalFormatSymbols().getDecimalSeparator() + "\\d{0," + fractionDigits + "})?");
    }

    protected void stringPattern(int maxLength) {
        this.maxLength = maxLength;

        if (maxLength <= 0) {
            this.pattern = Pattern.compile(".");
        } else {
            this.pattern = Pattern.compile(".{0," + maxLength + "}");
        }
    }

    protected void textPattern(int maxLength) {
        this.maxLength = maxLength;

        if (maxLength <= 0) {
            this.pattern = Pattern.compile(".");
        } else {
            this.pattern = Pattern.compile(".{0," + maxLength + "}");
        }
    }

    protected static int validateIntegerValue(Object value) throws IllegalArgumentException {
        try {
            return Integer.parseInt(value.toString());
        } catch (Exception e) {
            throw new IllegalArgumentException("Error on field validation - Invalid INTEGER value (" + value + ").");
        }
    }

    protected static long validateLongValue(Object value) throws IllegalArgumentException {
        try {
            return Long.parseLong(value.toString());
        } catch (Exception e) {
            throw new IllegalArgumentException("Error on field validation - Invalid LONG value (" + value + ").");
        }
    }

    protected static BigDecimal validateDoubleValue(Object value) throws IllegalArgumentException {
        BigDecimal DOUBLE_MIN_VALUE = new BigDecimal("-9999999999999999.9999999999");
        BigDecimal DOUBLE_MAX_VALUE = new BigDecimal("9999999999999999.9999999999");

        try {
            BigDecimal bigDecimalValue = new BigDecimal(value.toString());

            if (bigDecimalValue.compareTo(DOUBLE_MIN_VALUE) >= 0 && bigDecimalValue.compareTo(DOUBLE_MAX_VALUE) <= 0) {
                return bigDecimalValue;
            } else {
                throw new IllegalArgumentException("Error on field validation - Invalid DOUBLE value (" + value + ").");
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Error on field validation - Invalid DOUBLE value (" + value + ").");
        }
    }
}
