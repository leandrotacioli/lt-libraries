package com.leandrotacioli.libs.swing.table;

import lombok.Getter;

import javax.swing.Icon;

public class TableButton {
    @Getter
    private Icon icon;

    @Getter
    private String tooltip;

    @Getter
    private boolean clickEnabled;

    public TableButton(Icon icon, String tooltip) {
        this(icon, tooltip, true);
    }

    public TableButton(Icon icon, String tooltip, boolean clickEnabled) {
        this.icon = icon;
        this.tooltip = tooltip;
        this.clickEnabled = clickEnabled;
    }
}