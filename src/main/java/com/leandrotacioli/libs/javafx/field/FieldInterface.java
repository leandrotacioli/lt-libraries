package com.leandrotacioli.libs.javafx.field;

import javafx.beans.value.ChangeListener;

public interface FieldInterface {

    public void setEnabled(boolean isEnabled);

    public Object getValue();

    public void setValue(Object value);

    public void setMaximumLength(int maximumLength);

    public void setFractionDigits(int fractionDigits);

    public void addFocusListener(ChangeListener<Boolean> changeListener);

}
