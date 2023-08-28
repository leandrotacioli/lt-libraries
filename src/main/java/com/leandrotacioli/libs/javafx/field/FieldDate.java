package com.leandrotacioli.libs.javafx.field;

import com.leandrotacioli.libs.LTDataTypes;
import com.leandrotacioli.libs.LTParameters;
import com.leandrotacioli.libs.transformation.DateTransformation;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.DatePicker;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class FieldDate extends AnchorPane implements FieldInterface {

    private boolean isEnabled;

    private DatePicker datePicker;
    private String dateFormat;

    protected FieldDate(boolean isEnabled) {
        datePicker = new DatePicker();

        setEnabled(isEnabled);
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
            if (datePicker.getEditor().getText().length() >= 1 && datePicker.getEditor().getText().length() <= 10) {
                if (removeCharacter(datePicker.getEditor().getText(), keyEvent.getCharacter())) {
                    datePicker.getEditor().setText(datePicker.getEditor().getText().substring(0, datePicker.getEditor().getText().length() - 1));
                    datePicker.getEditor().positionCaret(datePicker.getEditor().getText().length());
                }
            }

            // Add date separator '/' or '-'
            if (keyEvent.getCharacter().trim().length() > 0) {
                if (dateFormat.equals("dd/MM/yyyy") || dateFormat.equals("MM/dd/yyyy")) {
                    if (datePicker.getEditor().getText().length() == 2 || datePicker.getEditor().getText().length() == 5) {
                        datePicker.getEditor().setText(datePicker.getEditor().getText() + "/");
                        datePicker.getEditor().positionCaret(datePicker.getEditor().getText().length());
                    }
                } else if (dateFormat.equals("yyyy-MM-dd")) {
                    if (datePicker.getEditor().getText().length() == 4 || datePicker.getEditor().getText().length() == 7) {
                        datePicker.getEditor().setText(datePicker.getEditor().getText() + "-");
                        datePicker.getEditor().positionCaret(datePicker.getEditor().getText().length());
                    }
                }
            }
        });
    }

    protected boolean removeCharacter(String date, String lastCharacter) {
        if (lastCharacter.equals("\b")) return false;      // Backspace
        if (lastCharacter.equals("\t")) return false;      // TAB
        if (lastCharacter.equals("\n")) return false;      // ENTER
        if (lastCharacter.equals("\u001B")) return false;  // ESC
        if (lastCharacter.equals("\u007F")) return false;  // DEL

        if (dateFormat.equals("dd/MM/yyyy") || dateFormat.equals("MM/dd/yyyy")) {
            if ((date.length() == 3 || date.length() == 6) && lastCharacter.equals("/"))              return false;  // Date separator '/'
            if ((date.length() == 3 || date.length() == 6) && "0123456789".contains(lastCharacter))   return true;   // Avoid digit as third and fifth character
        } else if (dateFormat.equals("yyyy-MM-dd")) {
            if ((date.length() == 5 || date.length() == 8) && lastCharacter.equals("-"))              return false;  // Date separator '-'
            if ((date.length() == 5 || date.length() == 8) && "0123456789".contains(lastCharacter))   return true;   // Avoid digit as third and fifth character
        }

        if ("0123456789".contains(lastCharacter)) return false;
        if (date.length() == 10 && "0123456789".contains(date.substring(date.length() - 1))) return false;

        return true;
    }

    private void setFocusProperties() {
        datePicker.focusedProperty().addListener((obs, oldVal, newVal) -> {
            // Focus gained
            if (newVal) {
                FieldValidator fieldValidator = new FieldValidator(LTDataTypes.STRING);
                fieldValidator.textPattern(10);

                datePicker.getEditor().setTextFormatter(fieldValidator.getFormatter());
                datePicker.getEditor().positionCaret(datePicker.getEditor().getText().length());
                datePicker.getStyleClass().add(isEnabled ? FieldStyles.FOCUS : FieldStyles.FOCUS_DISABLED);
            }

            // Focus lost
            if (oldVal) {
                datePicker.getStyleClass().removeAll(FieldStyles.FOCUS, FieldStyles.FOCUS_DISABLED, FieldStyles.FOCUS_ERROR_VALIDATION);

                if (datePicker.getEditor().getText() != null && datePicker.getEditor().getText().length() > 0) {
                    try {
                        datePicker.setValue(FieldValidator.validateDateValue(dateFormat, datePicker.getEditor().getText()));
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                        datePicker.requestFocus();
                        datePicker.getStyleClass().add(FieldStyles.FOCUS_ERROR_VALIDATION);
                    }
                }
            }
        });
    }

    @Override
    public void setEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;

        datePicker.setEditable(isEnabled);

        if (isEnabled) {
            datePicker.getStyleClass().addAll("combo-box-base", "date-picker");
        } else {
            datePicker.getStyleClass().removeAll("combo-box-base", "date-picker");
        }
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
            datePicker.setValue(value != null ? FieldValidator.validateDateValue(dateFormat, value) : null);
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
