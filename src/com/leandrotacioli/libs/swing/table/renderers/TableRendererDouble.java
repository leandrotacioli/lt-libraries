package com.leandrotacioli.libs.swing.table.renderers;

import java.awt.Color;
import java.awt.Component;
import java.text.DecimalFormat;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import com.leandrotacioli.libs.LTParameters;

/**
 * 
 * @author Leandro Tacioli
 * @version 1.2 - 11/Set/2015
 */
public class TableRendererDouble extends DefaultTableCellRenderer {
	private static final long serialVersionUID = 3585207723628352892L;
	
	private String strColumnName;
	
	private boolean blnReadOnly;
	private boolean blnFullRowSelection;

	private Color colorBackground;
	
	private DecimalFormat decimalFormat;
	
	/**
	 * Retorna o nome da coluna.
	 * 
	 * @param strColumnName
	 */
	public String getColumnName() {
		return strColumnName;
	}
	
	/**
	 * Altera o status de seleção para toda a linha da tabela.
	 * 
	 * @param blnFullRowSelection -
	 */
	public void setFullRowSelection(boolean blnFullRowSelection) {
		this.blnFullRowSelection = blnFullRowSelection;
	}
	
	/**
	 * Altera a quantidade de casas decimais.
	 * 
	 * @param intColumnDoubleFractionDigits
	 */
	public void setColumnDoubleFractionDigits(int intColumnDoubleFractionDigits) {
		decimalFormat = new DecimalFormat();
		decimalFormat.setMinimumFractionDigits(intColumnDoubleFractionDigits);
		decimalFormat.setMaximumFractionDigits(intColumnDoubleFractionDigits);
		decimalFormat.setDecimalFormatSymbols(LTParameters.getInstance().getDecimalFormatSymbols());
	}
	
	/**
	 * Altera a cor de background da coluna <i>INTEGER</i>.
	 * 
	 * @param colorBackground
	 */
	public void setColorBackground(Color colorBackground) {
		this.colorBackground = colorBackground;
	}
	
	/**
	 * 
	 * @param strColumnName
	 * @param blnReadOnly
	 * @param blnFullRowSelection
	 * @param intColumnDoubleFractionDigits
	 * @param color
	 */
	public TableRendererDouble(String strColumnName, boolean blnReadOnly, boolean blnFullRowSelection, int intColumnDoubleFractionDigits, Color colorBackground) {
		this.strColumnName = strColumnName;
		this.blnReadOnly = blnReadOnly;
		this.blnFullRowSelection = blnFullRowSelection;
		this.colorBackground = colorBackground;
		
		decimalFormat = new DecimalFormat();
		decimalFormat.setMinimumFractionDigits(intColumnDoubleFractionDigits);
		decimalFormat.setMaximumFractionDigits(intColumnDoubleFractionDigits);
		decimalFormat.setDecimalFormatSymbols(LTParameters.getInstance().getDecimalFormatSymbols());
		
		setHorizontalAlignment(SwingConstants.RIGHT);
		setFont(LTParameters.getInstance().getFontTableTextField());
		setBackground(colorBackground);
	}
	
	//*************************************************************************
	public Component getTableCellRendererComponent(JTable table, Object aValue, boolean isSelected, boolean hasFocus, int rowIndex, int columnIndex) {
		setValue(decimalFormat.format((Number) aValue));

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