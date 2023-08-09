package com.leandrotacioli.libs.javafx.layout;

import com.leandrotacioli.libs.javafx.field.LTField;
import javafx.scene.Node;
import lombok.Getter;

/**
 * Grid Column.
 */
public class GridColumn {

    @Getter
    private final Node content;

    private int[] columnWidths = new int[] {
         1,  // XSMALL (default)
        -1,  // SMALL
        -1,  // MEDIUM
        -1,  // LARGE
        -1   // XLARGE
    };

    /**
     * Grid Column.
     * <p>Default width for all window sizes = 1</p>
     *
     * @param content the content to be added to the column
     */
    public GridColumn(Node content) {
        this(content, 1, 1, 1, 1, 1);
    }

    /**
     * Grid Column.
     *
     * @param content the content to be added to the column
     * @param width the requested width for all window sizes (must be between 1 and 12)
     */
    public GridColumn(Node content, int width) {
        this(content, width, width, width, width, width);
    }

    /**
     * Grid Column.
     * <p>Default width for all window sizes = 1</p>
     *
     * @param field the field to be added to the column
     */
    public GridColumn(LTField field) {
        this(field.load(), 1, 1, 1, 1, 1);
    }

    /**
     * Grid Column.
     *
     * @param field the field to be added to the column
     * @param width the requested width for all window sizes (must be between 1 and 12)
     */
    public GridColumn(LTField field, int width) {
        this(field.load(), width, width, width, width, width);
    }

    /**
     * Grid Column.
     *
     * @param content the content to be added to the column
     * @param widthXSmall the requested width for the Extra Small size (must be between 1 and 12)
     * @param widthSmall the requested width for the Small size (must be between 1 and 12)
     * @param widthMedium the requested width for the Medium size (must be between 1 and 12)
     * @param widthLarge the requested width for the Large size (must be between 1 and 12)
     * @param widthXLarge the requested width for the Extra Large size (must be between 1 and 12)
     */
    public GridColumn(Node content, int widthXSmall, int widthSmall, int widthMedium, int widthLarge, int widthXLarge) {
        this.content = content;

        setColumnWidth(WindowSize.XSMALL, widthXSmall);
        setColumnWidth(WindowSize.SMALL, widthSmall);
        setColumnWidth(WindowSize.MEDIUM, widthMedium);
        setColumnWidth(WindowSize.LARGE, widthLarge);
        setColumnWidth(WindowSize.XLARGE, widthXLarge);
    }

    /**
     * Set the column width of the content for all window sizes.
     *
     * @param width the requested width (must be between 1 and 12)
     */
    public void setColumnWidth(int width) {
        setColumnWidth(WindowSize.XSMALL, width);
        setColumnWidth(WindowSize.SMALL, width);
        setColumnWidth(WindowSize.MEDIUM, width);
        setColumnWidth(WindowSize.LARGE, width);
        setColumnWidth(WindowSize.XLARGE, width);
    }

    /**
     * Set the column width of the content for all window sizes.
     *
     * @param widthXSmall the requested width for the Extra Small size (must be between 1 and 12)
     * @param widthSmall the requested width for the Small size (must be between 1 and 12)
     * @param widthMedium the requested width for the Medium size (must be between 1 and 12)
     * @param widthLarge the requested width for the Large size (must be between 1 and 12)
     * @param widthXLarge the requested width for the Extra Large size (must be between 1 and 12)
     */
    public void setColumnWidth(int widthXSmall, int widthSmall, int widthMedium, int widthLarge, int widthXLarge) {
        setColumnWidth(WindowSize.XSMALL, widthXSmall);
        setColumnWidth(WindowSize.SMALL, widthSmall);
        setColumnWidth(WindowSize.MEDIUM, widthMedium);
        setColumnWidth(WindowSize.LARGE, widthLarge);
        setColumnWidth(WindowSize.XLARGE, widthXLarge);
    }

    /**
     * Set the column width of the content at the specified windows size
     *
     * @param windowSize the window size being specified
     * @param width      the requested width at this window size (must be between 1 and 12)
     */
    public void setColumnWidth(WindowSize windowSize, int width) {
        columnWidths[windowSize.getValue()] = clamp(width, 1, 12);
    }

    /**
     * Iterate through window sizes, beginning at the specified bp, travelling down. Return first valid bp value.
     * If none are valid, return 1
     *
     * @param windowSize the window size at which to determine the column width
     *
     * @return the requested width at that window size, or based on a lower window size if the specified bp has not been set.
     */
    public int getColumnWidth(WindowSize windowSize) {
        for (int i = windowSize.getValue(); i >= 0; i--) {
            if (columnWidths[i] > 0 && columnWidths[i] <= 12) {
                return columnWidths[i];
            }
        }

        // If none are valid, return 1
        return 1;
    }

    /**
     * Return an integer between the stated max and min values
     *
     * @param value the value to be clamped
     * @param max   the maximum allowed integer value to be returned
     * @param min   the minimum allowed integer value to be returned
     *
     * @return the clamped value
     */
    private int clamp(int value, int min, int max) {
        if (max < min) throw new IllegalArgumentException("Cannot clamp when max is greater than min");

        if (value > max) {
            return max;
        } else if (value < min) {
            return min;
        } else {
            return value;
        }
    }
}
