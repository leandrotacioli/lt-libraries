package com.leandrotacioli.libs.javafx.field;

import javafx.beans.value.ChangeListener;
import javafx.scene.control.TextField;

public class FieldString extends TextField implements FieldInterface {

    private int maximumLength;

    protected FieldString(boolean isEnabled) {
        this.setEditable(isEnabled);
    }

    private void validateMaximumLength() {
        String string = this.getText();

        if (string.length() > maximumLength) {
            this.setText(string.substring(0, maximumLength));
            this.positionCaret(string.length());
        }
    }

    @Override
    public void setEnabled(boolean isEnabled) {
        this.setEditable(isEnabled);
    }

    @Override
    public Object getValue() {
        return this.getText();
    }

    @Override
    public void setValue(Object objValue) {
        this.setText((String) objValue);

        validateMaximumLength();
    }

    @Override
    public void setMaximumLength(int maximumLength) {
        this.maximumLength = maximumLength;

        this.setOnKeyTyped(event -> validateMaximumLength());
    }

    @Override
    public void addFocusListener(ChangeListener<Boolean> changeListener) {
        this.focusedProperty().addListener(changeListener);
    }

}
