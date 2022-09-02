package com.leandrotacioli.libs.swing.table;

import javax.swing.*;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.util.Comparator;

public class TableComparatorEmptyRow<COLUMN_TYPE extends Comparable<COLUMN_TYPE>> implements Comparator<Object> {
    private TableRowSorter<TableModel> sorter;
    private int intColumn;

    public TableComparatorEmptyRow(TableRowSorter<TableModel> sorter, int intColumn) {
        this.sorter = sorter;
        this.intColumn = intColumn;
    }

    private int getSortOrder() {
        SortOrder order = SortOrder.ASCENDING;

        for (RowSorter.SortKey sortKey : sorter.getSortKeys()) {
            if (sortKey.getColumn() == this.intColumn) {
                order = sortKey.getSortOrder();

                break;
            }
        }

        return order == SortOrder.ASCENDING ? 1 : -1;
    }

    @Override
    @SuppressWarnings("unchecked")
    public int compare(Object arg0, Object arg1) {
        boolean empty1 = arg0 == "";
        boolean empty2 = arg1 == "";

        if (empty1 && empty2) {
            return 0;
        } else if (empty1) {
            return 1 * getSortOrder();
        } else if (empty2) {
            return -1 * getSortOrder();
        }

        return ((Comparable<COLUMN_TYPE>) (COLUMN_TYPE) arg0).compareTo((COLUMN_TYPE) arg1);
    }
}