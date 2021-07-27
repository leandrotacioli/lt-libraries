package com.leandrotacioli.libs.swing.table.editors;

import java.awt.Component;

import javax.swing.DefaultCellEditor;
import javax.swing.JTable;

import com.leandrotacioli.libs.LTParameters;
import com.leandrotacioli.libs.StringTransformations;
import com.leandrotacioli.libs.swing.textfield.ltdouble.TextFieldDouble;

/**
 * 
 * @author Leandro Tacioli
 * @version 1.2 - 21/Nov/2020
 */
public class TableEditorDouble extends DefaultCellEditor {
	private static final long serialVersionUID = 8304313692833448805L;
	
	private int intColumnDoubleFractionDigits;
	
	private static TextFieldDouble txtFieldDouble = new TextFieldDouble();
	
	/**
	 * 
	 */
	public TableEditorDouble(int intColumnDoubleFractionDigits) {
		super(txtFieldDouble);
		
		this.intColumnDoubleFractionDigits = intColumnDoubleFractionDigits;
		
		txtFieldDouble.setFractionDigits(intColumnDoubleFractionDigits);
	}	
	
	//*************************************************************************
	@Override
	public Component getTableCellEditorComponent(JTable table, Object aValue, boolean isSelected, int rowIndex, int columnIndex) {
		txtFieldDouble = (TextFieldDouble) super.getTableCellEditorComponent(table, aValue, isSelected, rowIndex, columnIndex);
		txtFieldDouble.setFont(LTParameters.getInstance().getFontComponentTextField());
		txtFieldDouble.setBorder(LTParameters.getInstance().getBorderTableTextFieldEditing());
		txtFieldDouble.setFractionDigits(intColumnDoubleFractionDigits);
		
		String strValue = "";
		
		if (aValue != null && !aValue.equals("")) {
			strValue = StringTransformations.setDoubleToString((double) aValue, intColumnDoubleFractionDigits);
			
			// Retira os caracteres de separador de milhares e altera o separador para o padrão configurado nos parâmetros
			if (LTParameters.getInstance().getDecimalMark().equals("COMMA")) {
				strValue = strValue.replace(".", "");
				strValue.replace(".", ",");
			} else if (LTParameters.getInstance().getDecimalMark().equals("PERIOD")) {
				strValue = strValue.replace(",", "");
			}
		}
		
		txtFieldDouble.setText("" + strValue);

		return txtFieldDouble;
	}
}