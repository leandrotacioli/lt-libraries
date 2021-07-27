package com.leandrotacioli.libs.swing.table.renderers;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import com.leandrotacioli.libs.LTParameters;

/**
 * 
 * @author Leandro Tacioli
 * @version 2.0 - 09/Nov/2020
 */
public class TableRendererBoolean extends JCheckBox implements TableCellRenderer {
	private static final long serialVersionUID = -3981049152248920887L;
	
	private boolean blnReadOnly;
	private boolean blnFullRowSelection;
	
	private Color colorBackground;

	/**
	 * 
	 * @param blnReadOnly
	 * @param blnFullRowSelection
	 * @param colorBackground
	 */
	public TableRendererBoolean(boolean blnReadOnly, boolean blnFullRowSelection, Color colorBackground) {
		this.blnReadOnly = blnReadOnly;
		this.blnFullRowSelection = blnFullRowSelection;
		this.colorBackground = colorBackground;
		
		setHorizontalAlignment(0);      // Centralizado
		setBackground(colorBackground);
		setBorderPainted(true);
		setOpaque(true);
	}

	//*************************************************************************
	@Override
	public Component getTableCellRendererComponent(JTable table, Object aValue, boolean isSelected, boolean hasFocus, int rowIndex, int columnIndex) {
		setSelected(Boolean.TRUE.equals(aValue));
		
		if (hasFocus) {
			setBorder(LTParameters.getInstance().getBorderTableTextFieldFocus());
		} else {
			setBackground(colorBackground);
            setBorder(table.getBorder());
		}
		
		if (blnFullRowSelection) {
			if (isSelected) {
				setBackground(LTParameters.getInstance().getColorTableRowSelected());
				setBorder(table.getBorder());
			} else {
				setBackground(colorBackground);
				setBorder(table.getBorder());
			}
			
		} else {
			if (blnReadOnly) {
				if (isSelected) {
					setBackground(LTParameters.getInstance().getColorTableRowSelected());
				} else {
					setBackground(colorBackground);
				}
				
				setBorder(table.getBorder());
			}
		}
		
		return this;
	}
}