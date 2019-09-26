package com.leandrotacioli.libs.swing.table.renderers;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import com.leandrotacioli.libs.LTDataTypes;
import com.leandrotacioli.libs.LTParameters;

/**
 * 
 * @author Leandro Tacioli
 * @version 1.1 - 06/Ago/2018
 */
public class TableRendererDefault extends DefaultTableCellRenderer {
	private static final long serialVersionUID = 3585207723628352892L;
	
	private LTDataTypes objDataType;
	
	private boolean blnReadOnly;
	private boolean blnFullRowSelection;

	private Color colorBackground;

	/**
	 * Altera o status de seleção para toda a linha da tabela.
	 * 
	 * @param blnFullRowSelection
	 */
	public void setFullRowSelection(boolean blnFullRowSelection) {
		this.blnFullRowSelection = blnFullRowSelection;
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
	 * Estabelece um <i>renderer</i> padrão para as colunas da tabela.
	 *  
	 * @param blnReadOnly         - 
	 * @param blnFullRowSelection - 
	 * @param colorBackground     - Cor de background
	 */
	public TableRendererDefault(LTDataTypes objDataType, boolean blnReadOnly, boolean blnFullRowSelection, Color colorBackground) {
		this.objDataType = objDataType;
		this.blnReadOnly = blnReadOnly;
		this.blnFullRowSelection = blnFullRowSelection;
		this.colorBackground = colorBackground;
		
		if (this.objDataType == LTDataTypes.INTEGER) {
			setHorizontalAlignment(SwingConstants.RIGHT);
		} else if (this.objDataType == LTDataTypes.LONG) {
			setHorizontalAlignment(SwingConstants.RIGHT);
		} else if (this.objDataType == LTDataTypes.DOUBLE) {
			setHorizontalAlignment(SwingConstants.RIGHT);
		} else if (this.objDataType == LTDataTypes.STRING) {
			setHorizontalAlignment(SwingConstants.LEFT);
		} else if (this.objDataType == LTDataTypes.TEXT) {
			setHorizontalAlignment(SwingConstants.LEFT);
		} else if (this.objDataType == LTDataTypes.DATE) {
			setHorizontalAlignment(SwingConstants.LEFT);
		}
		
		setFont(LTParameters.getInstance().getFontTableTextField());
		setBackground(colorBackground);
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object aValue, boolean isSelected, boolean hasFocus, int rowIndex, int columnIndex) {
		setValue(aValue);
		
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