package com.leandrotacioli.libs.javafx.field;

import com.leandrotacioli.libs.LTDataTypes;
import com.leandrotacioli.libs.LTParameters;
import javafx.scene.control.TextFormatter;

import java.util.regex.Pattern;

public class FieldValidator {

    private Pattern pattern;

    protected FieldValidator(LTDataTypes dataType) {
        this(dataType, 0);
    }

    protected FieldValidator(LTDataTypes dataType, int fractionDigits) {
        if (dataType == LTDataTypes.INTEGER) {
            pattern = integerLongPattern();

        } else if (dataType == LTDataTypes.LONG) {
            pattern = integerLongPattern();

        } else if (dataType == LTDataTypes.DOUBLE) {
            pattern = doublePattern(fractionDigits);
        }
    }

    protected TextFormatter<Object> getFormatter() {
        return new TextFormatter<>(this::validateChange);
    }

    private TextFormatter.Change validateChange(TextFormatter.Change change) {
        if (validate(change.getControlNewText())) {
            return change;
        }

        return null;
    }

    private boolean validate(String input) {
        return pattern.matcher(input).matches();
    }

    private static Pattern integerLongPattern() {
        return Pattern.compile("-?\\d*");
    }

    private static Pattern doublePattern(int fractionDigits) {
        return Pattern.compile("-?\\d*(\\" + LTParameters.getInstance().getDecimalFormatSymbols().getDecimalSeparator() + "\\d{0," + fractionDigits + "})?");
    }

}
