package com.leandrotacioli.libs.swing.table.editors;

import java.awt.Component;

import javax.swing.DefaultCellEditor;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.leandrotacioli.libs.swing.LTSwing;

/**
 * 
 * @author Leandro Tacioli
 */
public class TableEditorText extends DefaultCellEditor {
	private static final long serialVersionUID = 764663649164438553L;
	
	private int intHorizontalAlignment;
	
	public TableEditorText() {
		this(SwingConstants.LEFT);
	}
	
	public TableEditorText(int intHorizontalAlignment) {
		super(new JTextField());
		
		this.intHorizontalAlignment = intHorizontalAlignment;
	}
		
	@Override
	public Component getTableCellEditorComponent(JTable table, Object aValue, boolean isSelected, int rowIndex, int columnIndex) {
		JTextField textFieldEditor = (JTextField) super.getTableCellEditorComponent(table, aValue, isSelected, rowIndex, columnIndex);
		textFieldEditor.setHorizontalAlignment(intHorizontalAlignment);
		textFieldEditor.setFont(LTSwing.getInstance().getFontComponentTextField());
		textFieldEditor.setBorder(LTSwing.getInstance().getBorderTableTextFieldEditing());
		textFieldEditor.setText((String) aValue);
		
		return textFieldEditor;
	}
}