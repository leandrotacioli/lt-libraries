package com.leandrotacioli.libs.swing.table.renderers;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import com.leandrotacioli.libs.LTParameters;

/**
 * 
 * @author Leandro Tacioli
 * @version 1.0 - 24/Abr/2015
 */
public class TableRendererFixed extends DefaultTableCellRenderer {
	private static final long serialVersionUID = -4336868718200331253L;

	/**
	 * 
	 */
	public TableRendererFixed() {
		setBackground(LTParameters.getInstance().getColorTableGridDisabled());
	}
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object aValue, boolean isSelected, boolean hasFocus, int rowIndex, int columnIndex) {
		if (isSelected) {
			setBackground(LTParameters.getInstance().getColorTableRowSelected());
		} else {
			setBackground(LTParameters.getInstance().getColorTableGridDisabled());
		}
		
		return this;
	}
}