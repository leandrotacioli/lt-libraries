package com.leandrotacioli.libs.swing.table.renderers;

import java.awt.Color;
import java.awt.Component;
import java.text.DecimalFormat;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import com.leandrotacioli.libs.LTDataTypes;
import com.leandrotacioli.libs.LTParameters;

/**
 * 
 * @author Leandro Tacioli
 * @version 2.0 - 21/Nov/2020
 */
public class TableRendererDefault extends DefaultTableCellRenderer {
	private static final long serialVersionUID = 3585207723628352892L;
	
	private LTDataTypes objDataType;
	
	private boolean blnReadOnly;
	private boolean blnFullRowSelection;

	private Color colorBackground;
	
	private DecimalFormat decimalFormat;
	
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
	 * Estabelece um <i>renderer</i> padrão para as colunas da tabela.
	 * 
	 * @param blnReadOnly           - Apenas leitura
	 * @param blnFullRowSelection   - Linha inteira da tabela está sendo selecionada
	 * @param intHorizonalAlignment - Alinhamento horizonal
	 * @param colorBackground       - Cor de background
	 */
	public TableRendererDefault(LTDataTypes objDataType, boolean blnReadOnly, boolean blnFullRowSelection, int intHorizonalAlignment, Color colorBackground) {
		this.objDataType = objDataType;
		this.blnReadOnly = blnReadOnly;
		this.blnFullRowSelection = blnFullRowSelection;
		this.colorBackground = colorBackground;
		
		setHorizontalAlignment(intHorizonalAlignment);
		setFont(LTParameters.getInstance().getFontTableTextField());
		setBackground(colorBackground);
		
		if (objDataType == LTDataTypes.DOUBLE) {
			decimalFormat = new DecimalFormat();
			decimalFormat.setMinimumFractionDigits(2);
			decimalFormat.setMaximumFractionDigits(2);
			decimalFormat.setDecimalFormatSymbols(LTParameters.getInstance().getDecimalFormatSymbols());
		}
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object aValue, boolean isSelected, boolean hasFocus, int rowIndex, int columnIndex) {
		if (objDataType == LTDataTypes.DOUBLE) {
			if (aValue != null && !aValue.equals("")) {
				setValue(decimalFormat.format((Number) aValue));
			} else {
				setValue("");
			}
		} else {
			if (aValue != null && !aValue.equals("")) {
				setValue(aValue);
			} else {
				setValue("");
			}
		}
		
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