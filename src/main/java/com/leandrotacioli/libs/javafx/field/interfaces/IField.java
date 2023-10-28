package com.leandrotacioli.libs.javafx.field.interfaces;

import javafx.beans.value.ChangeListener;

public interface IField {

    public void setEnabled(boolean isEnabled);

    public Object getValue();

    public void setValue(Object value);

    public void addFocusListener(ChangeListener<Boolean> changeListener);

}
