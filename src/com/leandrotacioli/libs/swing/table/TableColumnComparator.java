package com.leandrotacioli.libs.swing.table;

import com.leandrotacioli.libs.transformation.DateTransformation;

import javax.swing.*;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.util.Comparator;
import java.util.Date;

public class TableColumnComparator<COLUMN_TYPE extends Comparable<COLUMN_TYPE>> implements Comparator<Object> {
    private TableRowSorter<TableModel> sorter;
    private int intColumn;
    private Class classType;

    public TableColumnComparator(TableRowSorter<TableModel> sorter, int intColumn) {
        this.sorter = sorter;
        this.intColumn = intColumn;

        Table table = (Table) sorter.getModel();
        this.classType = table.getColumnClass(intColumn);
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
    public int compare(Object obj1, Object obj2) {
        boolean isEmpty1 = (obj1 == "");
        boolean isEmpty2 = (obj2 == "");

        // Ordenar corretamente os registros que tiverem valores nulos
        if (isEmpty1 && isEmpty2) {
            return 0;
        } else if (isEmpty1) {
            return 1 * getSortOrder();
        } else if (isEmpty2) {
            return -1 * getSortOrder();
        }

        if (classType == Date.class) {
            Date date0 = DateTransformation.stringToDate((String) obj1);
            Date date1 = DateTransformation.stringToDate((String) obj2);

            return date0.compareTo(date1);
        }

        return ((Comparable<COLUMN_TYPE>) (COLUMN_TYPE) obj1).compareTo((COLUMN_TYPE) obj2);
    }
}