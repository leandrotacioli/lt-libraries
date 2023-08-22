package com.leandrotacioli.libs.javafx.field;

import com.leandrotacioli.libs.LTDataTypes;
import com.leandrotacioli.libs.LTParameters;
import com.leandrotacioli.libs.transformation.DateTransformation;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.DatePicker;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class FieldDate extends AnchorPane implements FieldInterface {

    private DatePicker datePicker;
    private String dateFormat;

    protected FieldDate(boolean isEnabled) {
        datePicker = new DatePicker();
        datePicker.setEditable(isEnabled);

        setDateFormat(LTParameters.getInstance().getDateFormat());
        setFieldMask();
        setFocusProperties();
        setValue(null);

        AnchorPane.setLeftAnchor(datePicker, 0.0);
        AnchorPane.setRightAnchor(datePicker, 0.0);
        AnchorPane.setTopAnchor(datePicker, 0.0);

        this.getChildren().addAll(datePicker);
    }

    private void setFieldMask() {
        datePicker.getEditor().setOnKeyTyped((KeyEvent keyEvent) -> {
            if (!"0123456789".contains(keyEvent.getCharacter())) {
                if ((datePicker.getEditor().getText().length() < 10 && (Character.isLetter(keyEvent.getCharacter().charAt(0)) || keyEvent.getCharacter().equals("/"))) ||
                        (datePicker.getEditor().getText().length() == 10 && Character.isLetter(datePicker.getEditor().getText().charAt(9)) || keyEvent.getCharacter().equals("/"))) {
                    datePicker.getEditor().setText(datePicker.getEditor().getText().substring(0, datePicker.getEditor().getText().length() - 1));
                    datePicker.getEditor().positionCaret(datePicker.getEditor().getText().length());
                }

                keyEvent.consume();
            }

            // Clear / Backspace
            if (keyEvent.getCharacter().trim().length() == 0) {
                switch (datePicker.getEditor().getText().length()) {
                    case 2, 5:
                        datePicker.getEditor().setText(datePicker.getEditor().getText().substring(0, datePicker.getEditor().getText().length()));
                        datePicker.getEditor().positionCaret(datePicker.getEditor().getText().length());
                        break;
                }
            } else {
                // Add date separator '/'
                switch (datePicker.getEditor().getText().length()) {
                    case 2, 5:
                        datePicker.getEditor().setText(datePicker.getEditor().getText() + "/");
                        datePicker.getEditor().positionCaret(datePicker.getEditor().getText().length());
                        break;
                }
            }
        });
    }

    private void setFocusProperties() {
        datePicker.focusedProperty().addListener((obs, oldVal, newVal) -> {
            // Focus gained
            if (newVal) {
                FieldValidator fieldValidator = new FieldValidator(LTDataTypes.STRING);
                fieldValidator.textPattern(10);

                datePicker.getEditor().setTextFormatter(fieldValidator.getFormatter());
                datePicker.getEditor().positionCaret(datePicker.getEditor().getText().length());
            }

            // Focus lost
            if (oldVal) {
                if (datePicker.getEditor().getText() != null && datePicker.getEditor().getText().length() > 0) {
                    try {
                        LocalDate localDate = FieldValidator.validateDateValue(dateFormat, datePicker.getEditor().getText());

                        datePicker.setValue(localDate);
                        datePicker.setBackground(null);

                    } catch (Exception e) {
                        System.err.println(e.getMessage());

                        datePicker.setBackground(new Background(new BackgroundFill(Color.RED, null, null)));
                        datePicker.requestFocus();
                    }
                } else {
                    datePicker.setBackground(null);
                }
            }
        });
    }

    @Override
    public void setEnabled(boolean isEnabled) {
        datePicker.setEditable(isEnabled);
    }

    @Override
    public Object getValue() {
        if (datePicker.getValue() != null) {
            Date date = DateTransformation.stringToDate(String.valueOf(datePicker.getValue()), "yyyy-MM-dd");
            return DateTransformation.dateToString(date, dateFormat);
        }

        return null;
    }

    @Override
    public void setValue(Object value) {
        try {
            LocalDate localDate = null;
            if (value != null) localDate = FieldValidator.validateDateValue(dateFormat, value);
            datePicker.setValue(localDate);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            datePicker.setValue(null);
        }
    }

    @Override
    public void setMaximumLength(int maximumLength) {
        throw new UnsupportedOperationException("This method is not allowed for " + LTDataTypes.DATE + " fields.");
    }

    @Override
    public void setFractionDigits(int fractionDigits) {
        throw new UnsupportedOperationException("This method is not allowed for " + LTDataTypes.DATE + " fields.");
    }

    @Override
    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;

        datePicker.setPromptText(dateFormat);
        datePicker.setConverter(new StringConverter<>() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(dateFormat);

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                }

                return "";
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && string.length() > 0) {
                    return LocalDate.parse(string, dateFormatter);
                }

                return null;
            }
        });
    }

    @Override
    public void addFocusListener(ChangeListener<Boolean> changeListener) {
        datePicker.focusedProperty().addListener(changeListener);
    }

}
