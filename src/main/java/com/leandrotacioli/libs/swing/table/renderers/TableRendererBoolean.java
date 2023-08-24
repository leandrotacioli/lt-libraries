package com.leandrotacioli.libs.swing.table.renderers;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import com.leandrotacioli.libs.LTParameters;
import com.leandrotacioli.libs.swing.LTSwing;

/**
 * 
 * @author Leandro Tacioli
 */
public class TableRendererBoolean extends JCheckBox implements TableCellRenderer {

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
		
		if (Boolean.TRUE.equals(aValue)) {
			setToolTipText(LTParameters.getInstance().getBundle().getString("field_boolean_true"));
		} else {
			setToolTipText(LTParameters.getInstance().getBundle().getString("field_boolean_false"));
		}
		
		if (hasFocus) {
			setBorder(LTSwing.getInstance().getBorderTableTextFieldFocus());
		} else {
			setBackground(colorBackground);
            setBorder(table.getBorder());
		}
		
		if (blnFullRowSelection) {
			if (isSelected) {
				setBackground(LTSwing.getInstance().getColorTableRowSelected());
				setBorder(table.getBorder());
			} else {
				setBackground(colorBackground);
				setBorder(table.getBorder());
			}
			
		} else {
			if (blnReadOnly) {
				if (isSelected) {
					setBackground(LTSwing.getInstance().getColorTableRowSelected());
				} else {
					setBackground(colorBackground);
				}
				
				setBorder(table.getBorder());
			}
		}
		
		return this;
	}
}