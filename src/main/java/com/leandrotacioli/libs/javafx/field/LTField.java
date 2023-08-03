package com.leandrotacioli.libs.javafx.field;

import com.leandrotacioli.libs.LTDataTypes;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Custom fields using JavaFX.
 */
public class LTField {

    private static final long serialVersionUID = -8501806288615642473L;

    private AnchorPane fieldPane;
    private Label labelField;
    private TextField textField;

    private LTDataTypes dataType;
    private boolean isEnabled;
    private boolean isMandatoryField;
    private int maximumLength;

    /**
     * Custom fields using JavaFX.
     *
     * @param label
     * @param dataType
     * @param isEnabled
     * @param isMandatoryField
     */
    public LTField(String label, LTDataTypes dataType, boolean isEnabled, boolean isMandatoryField) {
        this(label, dataType, isEnabled, isMandatoryField, 0);
    }

    /**
     * Custom fields using JavaFX.
     *
     * @param label
     * @param dataType
     * @param isEnabled
     * @param isMandatoryField
     * @param maximumLength
     */
    public LTField(String label, LTDataTypes dataType, boolean isEnabled, boolean isMandatoryField, int maximumLength) {
        this.dataType = dataType;
        this.isEnabled = isEnabled;
        this.isMandatoryField = isMandatoryField;
        this.maximumLength = maximumLength;

        labelField = new Label(label);
        labelField.setFont(Font.font("System", FontWeight.BOLD, 13));

        textField = new TextField();
        textField.setEditable(isEnabled);

        if (dataType == LTDataTypes.INTEGER) {
            throw new UnsupportedOperationException("Not supported yet.");

        } else if (dataType == LTDataTypes.LONG) {
            throw new UnsupportedOperationException("Not supported yet.");

        } else if (dataType == LTDataTypes.DOUBLE) {
            throw new UnsupportedOperationException("Not supported yet.");

        } else if (dataType == LTDataTypes.STRING) {
            setMaximumLength(maximumLength);

        } else if (dataType == LTDataTypes.TEXT) {
            throw new UnsupportedOperationException("Not supported yet.");

        } else if (dataType == LTDataTypes.DATE) {
            throw new UnsupportedOperationException("Not supported yet.");

        } else if (dataType == LTDataTypes.HOUR) {
            throw new UnsupportedOperationException("Not supported yet.");

        } else if (dataType == LTDataTypes.BOOLEAN) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        AnchorPane.setLeftAnchor(labelField, 5.0);
        AnchorPane.setRightAnchor(labelField, 5.0);
        AnchorPane.setTopAnchor(labelField, 5.0);

        AnchorPane.setLeftAnchor(textField, 5.0);
        AnchorPane.setRightAnchor(textField, 5.0);
        AnchorPane.setTopAnchor(textField, 23.0);
        AnchorPane.setBottomAnchor(textField, 5.0);

        fieldPane = new AnchorPane();
        fieldPane.minWidth(40);
        fieldPane.minHeight(40);

        fieldPane.getChildren().add(labelField);
        fieldPane.getChildren().add(textField);
    }

    /**
     * Loads the custom fields.
     *
     * @return fieldPane
     */
    public AnchorPane load() {
        return fieldPane;
    }

    public Object getValue() {
        return textField.getText();
    }

    public void setValue(Object objValue) {
        textField.setText((String) objValue);

        validateMaximumLength();
    }

    public void setEnabled(boolean isEnabled) {
        textField.setEditable(isEnabled);
    }

    public void setFocus() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void addFocusListener(ChangeListener<Boolean> changeListener) {
        textField.focusedProperty().addListener(changeListener);
    }

    public void setMaximumLength(int maximumLength) {
        this.maximumLength = maximumLength;

        textField.setOnKeyTyped(event -> {
            validateMaximumLength();
        });
    }

    private void validateMaximumLength() {
        String string = textField.getText();

        if (string.length() > maximumLength) {
            textField.setText(string.substring(0, maximumLength));
            textField.positionCaret(string.length());
        }
    }
}