package com.leandrotacioli.libs.swing.table.editors;

import java.awt.Component;

import javax.swing.DefaultCellEditor;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import com.leandrotacioli.libs.swing.LTSwing;
import com.leandrotacioli.libs.swing.textfield.ltinteger.TextFieldInteger;

/**
 * 
 * @author Leandro Tacioli
 */
public class TableEditorInteger extends DefaultCellEditor {
	private static final long serialVersionUID = 4667845356960784235L;

	private int intHorizontalAlignment;
	
	public TableEditorInteger() {
		this(SwingConstants.RIGHT);
	}
	
	public TableEditorInteger(int intHorizontalAlignment) {
		super(new TextFieldInteger());
		
		this.intHorizontalAlignment = intHorizontalAlignment;
	}
	
	//*************************************************************************
	@Override
	public Component getTableCellEditorComponent(JTable table, Object aValue, boolean isSelected, int rowIndex, int columnIndex) {
		TextFieldInteger textFieldEditor = (TextFieldInteger) super.getTableCellEditorComponent(table, aValue, isSelected, rowIndex, columnIndex);
		textFieldEditor.setHorizontalAlignment(intHorizontalAlignment);
		textFieldEditor.setFont(LTSwing.getInstance().getFontComponentTextField());
		textFieldEditor.setBorder(LTSwing.getInstance().getBorderTableTextFieldEditing());
		textFieldEditor.setText("" + aValue);
		
		return textFieldEditor;
	}
}