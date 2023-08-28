package com.leandrotacioli.libs.javafx.field;

import com.leandrotacioli.libs.LTDataTypes;
import javafx.beans.value.ChangeListener;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class FieldText extends TextArea implements FieldInterface {

    private boolean isEnabled;

    protected FieldText(boolean isEnabled) {
        setEnabled(isEnabled);
        setFocusProperties();
        setEventHandler();
        setValue(null);
    }

    private void setFocusProperties() {
        this.addFocusListener((obs, oldVal, newVal) -> {
            // Focus gained
            if (newVal) {
                this.positionCaret(this.getText() != null ? this.getText().length() : 0);
                this.getStyleClass().add(isEnabled ? FieldStyles.FOCUS : FieldStyles.FOCUS_DISABLED);
            }

            // Focus lost
            if (oldVal) {
                this.getStyleClass().removeAll(FieldStyles.FOCUS, FieldStyles.FOCUS_DISABLED);
            }
        });
    }

    /**
     * Sets an Event Handler to allow the TAB key to navigate to the next component.
     */
    private void setEventHandler() {
        this.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
            if (keyEvent.getCode() == KeyCode.TAB) {
                if (!keyEvent.isShiftDown() && !keyEvent.isControlDown()) {
                    KeyEvent newKeyEvent = new KeyEvent(keyEvent.getSource(), keyEvent.getTarget(), keyEvent.getEventType(),
                            keyEvent.getCharacter(), keyEvent.getText(), keyEvent.getCode(),
                            keyEvent.isShiftDown(), true, keyEvent.isAltDown(), keyEvent.isMetaDown()
                    );

                    Node node = (Node) keyEvent.getSource();
                    node.fireEvent(newKeyEvent);

                    keyEvent.consume();
                }
            }
        });
    }

    @Override
    public void setEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;

        this.setEditable(isEnabled);
    }

    @Override
    public Object getValue() {
        return this.getText();
    }

    @Override
    public void setValue(Object objValue) {
        this.setText((String) objValue);
    }

    @Override
    public void setMaximumLength(int maximumLength) {
        if (maximumLength > 0) {
            FieldValidator fieldValidator = new FieldValidator(LTDataTypes.TEXT);
            fieldValidator.textPattern(maximumLength);

            this.setTextFormatter(fieldValidator.getFormatter());
        }
    }

    @Override
    public void setFractionDigits(int fractionDigits) {
        throw new UnsupportedOperationException("This method is not allowed for " + LTDataTypes.TEXT + " fields.");
    }

    @Override
    public void setDateFormat(String dateFormat) {
        throw new UnsupportedOperationException("This method is not allowed for " + LTDataTypes.TEXT + " fields.");
    }

    @Override
    public void addFocusListener(ChangeListener<Boolean> changeListener) {
        this.focusedProperty().addListener(changeListener);
    }

}
