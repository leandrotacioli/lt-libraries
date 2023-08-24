package com.leandrotacioli.libs.swing.table.editors;

import java.awt.Component;

import javax.swing.DefaultCellEditor;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import com.leandrotacioli.libs.swing.LTSwing;
import com.leandrotacioli.libs.swing.textfield.ltstring.TextFieldString;

/**
 * 
 * @author Leandro Tacioli
 */
public class TableEditorString extends DefaultCellEditor {

	private int intHorizontalAlignment;
	private int intMaximumLength;
	
	public TableEditorString() {
		this(SwingConstants.LEFT, 20);
	}
	
	public TableEditorString(int intHorizontalAlignment, int intMaximumLength) {
		super(new TextFieldString(intMaximumLength));
		
		this.intHorizontalAlignment = intHorizontalAlignment;
		this.intMaximumLength = intMaximumLength;
	}
	
	@Override
	public Component getTableCellEditorComponent(JTable table, Object aValue, boolean isSelected, int rowIndex, int columnIndex) {
		TextFieldString textFieldEditor = (TextFieldString) super.getTableCellEditorComponent(table, aValue, isSelected, rowIndex, columnIndex);
		textFieldEditor.setHorizontalAlignment(intHorizontalAlignment);
		textFieldEditor.setMaximumLength(intMaximumLength);
		textFieldEditor.setFont(LTSwing.getInstance().getFontComponentTextField());
		textFieldEditor.setBorder(LTSwing.getInstance().getBorderTableTextFieldEditing());
		textFieldEditor.setText((String) aValue);
		
		return textFieldEditor;
	}
}