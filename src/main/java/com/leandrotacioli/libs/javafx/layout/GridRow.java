package com.leandrotacioli.libs.javafx.layout;

import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Grid Row.
 */
public class GridRow {

    private final List<GridColumn> columns = new ArrayList<>();

    /**
     * Grid Row.
     */
    public GridRow() {

    }

    /**
     * Add a resizeable grid column object.
     *
     * @param column the object to be added
     */
    public void addColumn(GridColumn column) {
        columns.add(column);
    }

    /**
     * Remove a grid column object.
     *
     * @param column the object to be removed
     */
    public void removeColumn(GridColumn column) {
        columns.remove(column);
    }

    /**
     * Remove all columns from the row.
     */
    public void clear() {
        columns.clear();
    }

    /**
     * Get all columns in the row.
     *
     * @return an unmodifiable view of the columns in this row.
     */
    public List<GridColumn> getColumns() {
        return Collections.unmodifiableList(columns);
    }

    /**
     * Calculates row positions.
     *
     * @param lastGridPaneRow
     * @param currentWindowSize
     *
     * @return
     */
    public int calculateRowPositions(int lastGridPaneRow, WindowSize currentWindowSize) {
        int inputRow = lastGridPaneRow;

        if (this.getColumns().isEmpty()) return 0;

        // start in the first column
        int currentGridPaneColumn = 0;

        for (GridColumn column : this.getColumns()) {
            int contentWidth = column.getColumnWidth(currentWindowSize);

            if (currentGridPaneColumn + contentWidth > 12) {
                currentGridPaneColumn = 0;
                lastGridPaneRow++;
            }

            GridPane.setConstraints(column.getContent(), currentGridPaneColumn, lastGridPaneRow, contentWidth, 1);

            currentGridPaneColumn += contentWidth;
        }

        return lastGridPaneRow - inputRow + 1;
    }
}
