package com.leandrotacioli.libs.swing.table.editors;

import java.awt.Component;

import javax.swing.DefaultCellEditor;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import com.leandrotacioli.libs.swing.LTSwing;
import com.leandrotacioli.libs.swing.textfield.ltlong.TextFieldLong;

/**
 * 
 * @author Leandro Tacioli
 */
public class TableEditorLong extends DefaultCellEditor {
	private static final long serialVersionUID = -1479599424775321309L;

	private int intHorizontalAlignment;
	
	public TableEditorLong() {
		this(SwingConstants.RIGHT);
	}
	
	public TableEditorLong(int intHorizontalAlignment) {
		super(new TextFieldLong());
		
		this.intHorizontalAlignment = intHorizontalAlignment;
	}	
		
	//*************************************************************************
	@Override
	public Component getTableCellEditorComponent(JTable table, Object aValue, boolean isSelected, int rowIndex, int columnIndex) {
		TextFieldLong textFieldEditor = (TextFieldLong) super.getTableCellEditorComponent(table, aValue, isSelected, rowIndex, columnIndex);
		textFieldEditor.setHorizontalAlignment(intHorizontalAlignment);
		textFieldEditor.setFont(LTSwing.getInstance().getFontComponentTextField());
		textFieldEditor.setBorder(LTSwing.getInstance().getBorderTableTextFieldEditing());
		textFieldEditor.setText("" + aValue);
		
		return textFieldEditor;
	}
}