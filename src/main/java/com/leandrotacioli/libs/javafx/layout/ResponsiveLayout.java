package com.leandrotacioli.libs.javafx.layout;

import javafx.geometry.Pos;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;

/**
 * Creates a JavaFX Responsive Layout similar to Bootstrap CSS Framework.
 *
 * Based on: https://github.com/edencoding/javafx-layouts
 */
public class ResponsiveLayout extends GridPane {

    private final List<GridRow> rows = new ArrayList<>();
    private WindowSize currentWindowSize = WindowSize.XSMALL;

    /**
     * Creates JavaFX Responsive Layout similar to Bootstrap CSS Framework.
     */
    public ResponsiveLayout() {
        super();

        setAlignment(Pos.TOP_CENTER);
        setColumnConstraints();
        setWidthEventHandlers();
    }

    /**
     * Set column constraints.
     */
    private void setColumnConstraints() {
        getColumnConstraints().clear();

        // Create 12 equally sized columns for layout
        double width = 100.0 / 12.0;

        for (int i = 1; i <= 12; i++) {
            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setPercentWidth(width);
            getColumnConstraints().add(columnConstraints);
        }
    }

    private void setWidthEventHandlers() {
        this.widthProperty().addListener((observable, oldValue, newValue) -> {
            WindowSize newWindowSize = WindowSize.XSMALL;

            if (newValue.doubleValue() > 576)  newWindowSize = WindowSize.SMALL;
            if (newValue.doubleValue() > 768)  newWindowSize = WindowSize.MEDIUM;
            if (newValue.doubleValue() > 992)  newWindowSize = WindowSize.LARGE;
            if (newValue.doubleValue() > 1200) newWindowSize = WindowSize.XLARGE;

            if (newWindowSize != currentWindowSize) {
                currentWindowSize = newWindowSize;
                calculateRowPositions();
            }
        });
    }

    /**
     * Add a row to the layout.
     *
     * @param row the row to be added
     */
    public void addRow(GridRow row) {
        rows.add(row);
        calculateRowPositions();

        for (GridColumn column : row.getColumns()) {
            getChildren().add(column.getContent());
            GridPane.setFillWidth(column.getContent(), true);
            GridPane.setFillHeight(column.getContent(), true);
        }
    }

    /**
     * Remove a row from the layout.
     *
     * @param row the row to be removed
     */
    public void removeRow(GridRow row) {
        rows.remove(row);
        calculateRowPositions();

        for (GridColumn column : row.getColumns()) {
            getChildren().remove(column.getContent());
        }
    }

    /**
     * Calculate row positions.
     */
    private void calculateRowPositions() {
        int currentGridPaneRow = 0;

        for (GridRow row : rows) {
            currentGridPaneRow += row.calculateRowPositions(currentGridPaneRow, currentWindowSize);
        }
    }
}
