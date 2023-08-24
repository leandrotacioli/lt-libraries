package com.leandrotacioli.libs.swing.table.renderers;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import com.leandrotacioli.libs.swing.LTSwing;

/**
 * 
 * @author Leandro Tacioli
 */
public class TableRendererFixed extends DefaultTableCellRenderer {

	/**
	 * 
	 */
	public TableRendererFixed() {
		setBackground(LTSwing.getInstance().getColorTableGridDisabled());
	}
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object aValue, boolean isSelected, boolean hasFocus, int rowIndex, int columnIndex) {
		if (isSelected) {
			setBackground(LTSwing.getInstance().getColorTableRowSelected());
		} else {
			setBackground(LTSwing.getInstance().getColorTableGridDisabled());
		}
		
		return this;
	}
}