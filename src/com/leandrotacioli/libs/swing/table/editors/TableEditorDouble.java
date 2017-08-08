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
 * @version 1.1 - 11/Set/2015
 */
public class TableEditorDouble extends DefaultCellEditor {
	private static final long serialVersionUID = 8304313692833448805L;
	
	private int intColumnDoubleFractionDigits;
	
	/**
	 * 
	 */
	public TableEditorDouble(int intColumnDoubleFractionDigits) {
		super(new TextFieldDouble());
		
		this.intColumnDoubleFractionDigits = intColumnDoubleFractionDigits;
	}	
	
	//*************************************************************************
	@Override
	public Component getTableCellEditorComponent(JTable table, Object aValue, boolean isSelected, int rowIndex, int columnIndex) {
		TextFieldDouble textFieldEditor = (TextFieldDouble) super.getTableCellEditorComponent(table, aValue, isSelected, rowIndex, columnIndex);
		textFieldEditor.setFont(LTParameters.getInstance().getFontComponentTextField());
		textFieldEditor.setBorder(LTParameters.getInstance().getBorderTableTextFieldEditing());
		textFieldEditor.setFractionDigits(intColumnDoubleFractionDigits);
		
		String strValue = StringTransformations.setDoubleToString((double) aValue, intColumnDoubleFractionDigits);
		
		// Retira os caracteres de separador de milhares
		// e altera o separador para o padrão configurado nos parâmetros
		if (LTParameters.getInstance().getDecimalMark().equals("COMMA")) {
			strValue = strValue.replace(".", "");
			strValue.replace(".", ",");
		} else if (LTParameters.getInstance().getDecimalMark().equals("PERIOD")) {
			strValue = strValue.replace(",", "");
		}
		
		textFieldEditor.setText("" + strValue);

		return textFieldEditor;
	}
}