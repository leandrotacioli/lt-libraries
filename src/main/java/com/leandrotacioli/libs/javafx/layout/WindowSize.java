package com.leandrotacioli.libs.javafx.layout;

public enum WindowSize {
    XSMALL(0),
    SMALL(1),
    MEDIUM(2),
    LARGE(3),
    XLARGE(4);

    private int value;

    public int getValue() {
        return value;
    }

    WindowSize(int value) {
        this.value = value;
    }
}
