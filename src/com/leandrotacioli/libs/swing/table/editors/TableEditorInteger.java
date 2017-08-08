package com.leandrotacioli.libs.swing.table.editors;

import java.awt.Component;

import javax.swing.DefaultCellEditor;
import javax.swing.JTable;

import com.leandrotacioli.libs.LTParameters;
import com.leandrotacioli.libs.swing.textfield.ltinteger.TextFieldInteger;

/**
 * 
 * @author Leandro Tacioli
 * @version 1.0 - 14/Abr/2015
 */
public class TableEditorInteger extends DefaultCellEditor {
	private static final long serialVersionUID = 4667845356960784235L;

	/**
	 * 
	 */
	public TableEditorInteger() {
		super(new TextFieldInteger());
	}	
		
	//*************************************************************************
	@Override
	public Component getTableCellEditorComponent(JTable table, Object aValue, boolean isSelected, int rowIndex, int columnIndex) {
		TextFieldInteger textFieldEditor = (TextFieldInteger) super.getTableCellEditorComponent(table, aValue, isSelected, rowIndex, columnIndex);
		textFieldEditor.setFont(LTParameters.getInstance().getFontComponentTextField());
		textFieldEditor.setBorder(LTParameters.getInstance().getBorderTableTextFieldEditing());
		textFieldEditor.setText("" + aValue);
		
		return textFieldEditor;
	}
}