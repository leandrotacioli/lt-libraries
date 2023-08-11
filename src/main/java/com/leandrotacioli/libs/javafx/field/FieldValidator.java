package com.leandrotacioli.libs.javafx.field;

import com.leandrotacioli.libs.LTDataTypes;
import com.leandrotacioli.libs.LTParameters;
import com.leandrotacioli.libs.transformation.DoubleTransformation;
import javafx.scene.control.TextFormatter;

import java.math.BigDecimal;
import java.util.regex.Pattern;

public class FieldValidator {

    private static final BigDecimal DOUBLE_MIN_VALUE = new BigDecimal("-9999999999999999.9999999999");
    private static final BigDecimal DOUBLE_MAX_VALUE = new BigDecimal("9999999999999999.9999999999");

    private LTDataTypes dataType;
    private Pattern pattern;

    protected FieldValidator(LTDataTypes dataType) {
        this(dataType, 0);
    }

    protected FieldValidator(LTDataTypes dataType, int fractionDigits) {
        this.dataType = dataType;

        if (this.dataType == LTDataTypes.INTEGER) pattern = integerLongPattern();
        else if (this.dataType == LTDataTypes.LONG) pattern = integerLongPattern();
        else if (this.dataType == LTDataTypes.DOUBLE) pattern = doublePattern(fractionDigits);
    }

    protected TextFormatter<Object> getFormatter() {
        return new TextFormatter<>(this::validateChange);
    }

    private TextFormatter.Change validateChange(TextFormatter.Change change) {
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

    private Pattern integerLongPattern() {
        return Pattern.compile("-?\\d*");
    }

    private Pattern doublePattern(int fractionDigits) {
        return Pattern.compile("-?\\d*(\\" + LTParameters.getInstance().getDecimalFormatSymbols().getDecimalSeparator() + "\\d{0," + fractionDigits + "})?");
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
