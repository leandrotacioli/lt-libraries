package com.leandrotacioli.libs.swing.table.renderers;

import java.awt.Color;
import java.awt.Component;
import java.text.DecimalFormat;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import com.leandrotacioli.libs.LTDataTypes;
import com.leandrotacioli.libs.LTParameters;
import com.leandrotacioli.libs.swing.LTSwing;
import com.leandrotacioli.libs.transformation.DoubleTransformation;

/**
 * 
 * @author Leandro Tacioli
 */
public class TableRendererDefault extends DefaultTableCellRenderer {

	private LTDataTypes objDataType;
	
	private boolean blnReadOnly;
	private boolean blnFullRowSelection;

	private Color colorBackground;
	
	private DecimalFormat decimalFormat;
	
	private boolean blnColumnDoubleShowAsPercentage;
	
	/**
	 * Altera a quantidade de casas decimais (Tipo de dados DOUBLE).
	 * 
	 * @param intColumnDoubleFractionDigits
	 */
	public void setColumnDoubleFractionDigits(int intColumnDoubleFractionDigits) {
		decimalFormat = DoubleTransformation.getDecimalFormat(intColumnDoubleFractionDigits);
	}
	
	/**
	 * Altera o status de exibição do campo com uma máscara de porcentagem (Tipo de dados DOUBLE).
	 * 
	 * @param blnColumnDoubleShowAsPercentage
	 */
	public void setColumnDoubleShowAsPercentage(boolean blnColumnDoubleShowAsPercentage) {
		this.blnColumnDoubleShowAsPercentage = blnColumnDoubleShowAsPercentage;
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
		setFont(LTSwing.getInstance().getFontTableTextField());
		setBackground(colorBackground);
		
		if (objDataType == LTDataTypes.DOUBLE) {
			setColumnDoubleFractionDigits(2);
			setColumnDoubleShowAsPercentage(false);
		}
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object aValue, boolean isSelected, boolean hasFocus, int rowIndex, int columnIndex) {
		if (objDataType == LTDataTypes.DOUBLE) {
			if (aValue != null && !aValue.equals("")) {
				setValue(decimalFormat.format((Number) aValue) + (blnColumnDoubleShowAsPercentage ? "%" : ""));
				setToolTipText(decimalFormat.format((Number) aValue) + (blnColumnDoubleShowAsPercentage ? "%" : ""));
			} else {
				setValue("");
				setToolTipText(null);
			}
		} else {
			if (aValue != null && !aValue.equals("")) {
				setValue(aValue);
				setToolTipText(aValue.toString());
			} else {
				setValue("");
				setToolTipText(null);
			}
		}
		
		setBackground(colorBackground);
		setBorder(table.getBorder());
		
		if (hasFocus) {
			setBorder(LTSwing.getInstance().getBorderTableTextFieldFocus());
		} else {
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