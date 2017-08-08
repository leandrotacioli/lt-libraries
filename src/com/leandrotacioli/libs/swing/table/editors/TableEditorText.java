package com.leandrotacioli.libs.swing.table.editors;

import java.awt.Component;

import javax.swing.DefaultCellEditor;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.leandrotacioli.libs.LTParameters;

/**
 * 
 * @author Leandro Tacioli
 * @version 1.0 - 06/Abr/2015
 */
public class TableEditorText extends DefaultCellEditor {
	private static final long serialVersionUID = 764663649164438553L;
	
	public TableEditorText() {
		super(new JTextField());
	}
		
	@Override
	public Component getTableCellEditorComponent(JTable table, Object aValue, boolean isSelected, int rowIndex, int columnIndex) {
		JTextField textFieldEditor = (JTextField) super.getTableCellEditorComponent(table, aValue, isSelected, rowIndex, columnIndex);
		textFieldEditor.setFont(LTParameters.getInstance().getFontComponentTextField());
		textFieldEditor.setBorder(LTParameters.getInstance().getBorderTableTextFieldEditing());
		textFieldEditor.setText((String) aValue);
		
		return textFieldEditor;
	}
}