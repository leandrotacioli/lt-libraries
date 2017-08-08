package com.leandrotacioli.libs.swing.table.editors;

import java.awt.Component;

import javax.swing.DefaultCellEditor;
import javax.swing.JTable;

import com.leandrotacioli.libs.LTParameters;
import com.leandrotacioli.libs.swing.textfield.ltstring.TextFieldString;

/**
 * 
 * @author Leandro Tacioli
 * @version 1.0 - 06/Abr/2015
 */
public class TableEditorString extends DefaultCellEditor {
	private static final long serialVersionUID = 764663649164438553L;
	
	private int intMaximumLength;
	
	public TableEditorString(int intMaximumLength) {
		super(new TextFieldString(intMaximumLength));
		
		this.intMaximumLength = intMaximumLength;
	}
		
	@Override
	public Component getTableCellEditorComponent(JTable table, Object aValue, boolean isSelected, int rowIndex, int columnIndex) {
		TextFieldString textFieldEditor = (TextFieldString) super.getTableCellEditorComponent(table, aValue, isSelected, rowIndex, columnIndex);
		textFieldEditor.setMaximumLength(intMaximumLength);
		textFieldEditor.setFont(LTParameters.getInstance().getFontComponentTextField());
		textFieldEditor.setBorder(LTParameters.getInstance().getBorderTableTextFieldEditing());
		textFieldEditor.setText((String) aValue);
		
		return textFieldEditor;
	}
}