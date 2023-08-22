package com.leandrotacioli.libs.javafx.field;

import com.leandrotacioli.libs.LTDataTypes;
import com.leandrotacioli.libs.LTParameters;
import com.leandrotacioli.libs.transformation.DateTransformation;
import com.leandrotacioli.libs.transformation.DoubleTransformation;
import javafx.scene.control.TextFormatter;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.regex.Pattern;

public class FieldValidator {

    private LTDataTypes dataType;
    private Pattern pattern;

    private int fractionDigits;
    private int maxLength;
    private String dateFormat;

    protected FieldValidator(LTDataTypes dataType) {
        this.dataType = dataType;

        if (this.dataType == LTDataTypes.INTEGER) integerPattern();
        else if (this.dataType == LTDataTypes.LONG) longPattern();
        else if (this.dataType == LTDataTypes.DOUBLE) doublePattern(2);
        else if (this.dataType == LTDataTypes.STRING) textPattern(0);
        else if (this.dataType == LTDataTypes.TEXT) textPattern(0);
        else if (this.dataType == LTDataTypes.DATE) datePattern(LTParameters.getInstance().getDateFormat());
    }

    protected TextFormatter<Object> getFormatter() {
        if (this.dataType == LTDataTypes.INTEGER || this.dataType == LTDataTypes.LONG || this.dataType == LTDataTypes.DOUBLE) {
            return new TextFormatter<>(this::validateNumericChange);
        } else if (this.dataType == LTDataTypes.STRING || this.dataType == LTDataTypes.TEXT) {
            return new TextFormatter<>(this::validateTextChange);
        } else if (this.dataType == LTDataTypes.DATE) {
            return new TextFormatter<>(this::validateDateChange);
        }

        return new TextFormatter<>(this::validateDefaultChange);
    }

    protected TextFormatter.Change validateDefaultChange(TextFormatter.Change change) {
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

    private TextFormatter.Change validateDateChange(TextFormatter.Change change) {
        // TODO: Date Regex not working properly
        // That's why we are also validating the date
        if (pattern.matcher(change.getControlNewText()).matches()) {
            return change;
        } else {
            try {
                validateDateValue(dateFormat, change.getControlNewText());
                return change;
            } catch (Exception e) {
                System.err.println(e.getMessage());
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

    protected void textPattern(int maxLength) {
        this.maxLength = maxLength;

        if (maxLength <= 0) {
            this.pattern = Pattern.compile(".");
        } else {
            this.pattern = Pattern.compile(".{0," + maxLength + "}");
        }
    }

    protected void datePattern(String dateFormat) {
        this.dateFormat = dateFormat;

        if (dateFormat.equals("dd/MM/yyyy")) {
            this.pattern = Pattern.compile(
                    "^(29/02/(2000|2400|2800|(19|2[0-9])(0[48]|[2468][048]|[13579][26])))$|" +
                          "^((0[1-9]|1[0-9]|2[0-8])/02/((19|2[0-9])[0-9]{2}))$|" +
                          "^((0[1-9]|[12][0-9]|3[01])/(0[13578]|10|12)/((19|2[0-9])[0-9]{2}))$|" +
                          "^((0[1-9]|[12][0-9]|30)/(0[469]|11)/((19|2[0-9])[0-9]{2}))$");

        } else if (dateFormat.equals("MM/dd/yyyy")) {
            this.pattern = Pattern.compile(
                    "^(02/29/(2000|2400|2800|(19|2[0-9])(0[48]|[2468][048]|[13579][26])))$|" +
                          "^(02/((19|2[0-9])[0-9]{2})/(0[1-9]|1[0-9]|2[0-8]))$|" +
                          "^((0[13578]|10|12)/(0[1-9]|[12][0-9]|3[01])/((19|2[0-9])[0-9]{2}))$|" +
                          "^((0[469]|11)/(0[1-9]|[12][0-9]|30)/((19|2[0-9])[0-9]{2}))$");

        } else if (dateFormat.equals("yyyy-MM-dd")) {
            this.pattern = Pattern.compile(
                    "^((2000|2400|2800|(19|2[0-9])(0[48]|[2468][048]|[13579][26]))-02-29)$|" +
                          "^(((19|2[0-9])[0-9]{2})-02-(0[1-9]|1[0-9]|2[0-8]))$|" +
                          "^(((19|2[0-9])[0-9]{2})-(0[13578]|10|12)-(0[1-9]|[12][0-9]|3[01]))$|" +
                          "^(((19|2[0-9])[0-9]{2})-(0[469]|11)-(0[1-9]|[12][0-9]|30))$");
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

    protected static LocalDate validateDateValue(String dateFormat, Object value) throws IllegalArgumentException {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
            simpleDateFormat.setLenient(false);

            Date date = simpleDateFormat.parse((String) value);

            return LocalDate.parse(DateTransformation.dateToString(date, "yyyy-MM-dd"));

        } catch (Exception e) {
            throw new IllegalArgumentException("Error on field validation - Invalid DATE value (" + value + ") - Expected format: " + dateFormat + ".");
        }
    }
}
