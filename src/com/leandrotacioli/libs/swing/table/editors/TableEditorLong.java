package com.leandrotacioli.libs.swing.table.editors;

import java.awt.Component;

import javax.swing.DefaultCellEditor;
import javax.swing.JTable;

import com.leandrotacioli.libs.LTParameters;
import com.leandrotacioli.libs.swing.textfield.ltlong.TextFieldLong;

/**
 * 
 * @author Leandro Tacioli
 * @version 1.0 - 14/Abr/2015
 */
public class TableEditorLong extends DefaultCellEditor {
	private static final long serialVersionUID = -1479599424775321309L;

	/**
	 * 
	 */
	public TableEditorLong() {
		super(new TextFieldLong());
	}	
		
	//*************************************************************************
	@Override
	public Component getTableCellEditorComponent(JTable table, Object aValue, boolean isSelected, int rowIndex, int columnIndex) {
		TextFieldLong textFieldEditor = (TextFieldLong) super.getTableCellEditorComponent(table, aValue, isSelected, rowIndex, columnIndex);
		textFieldEditor.setFont(LTParameters.getInstance().getFontComponentTextField());
		textFieldEditor.setBorder(LTParameters.getInstance().getBorderTableTextFieldEditing());
		textFieldEditor.setText("" + aValue);
		
		return textFieldEditor;
	}
}