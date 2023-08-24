package com.leandrotacioli.libs.swing.table.editors;

import java.awt.Component;

import javax.swing.DefaultCellEditor;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import com.leandrotacioli.libs.swing.LTSwing;
import com.leandrotacioli.libs.swing.textfield.lttime.TextFieldTime;

/**
 * 
 * @author Leandro Tacioli
 */
public class TableEditorTime extends DefaultCellEditor {

	private static TextFieldTime txtFieldTime = new TextFieldTime();
	
	private int intHorizontalAlignment;
	
	public TableEditorTime() {
		this(SwingConstants.LEFT);
	}
	
	public TableEditorTime(int intHorizontalAlignment) {
		super(txtFieldTime);
		
		this.intHorizontalAlignment = intHorizontalAlignment;
	}
	
	//*************************************************************************
	@Override
	public Component getTableCellEditorComponent(JTable table, Object aValue, boolean isSelected, int rowIndex, int columnIndex) {
		txtFieldTime = (TextFieldTime) super.getTableCellEditorComponent(table, aValue, isSelected, rowIndex, columnIndex);
		txtFieldTime.setHorizontalAlignment(intHorizontalAlignment);
		txtFieldTime.setFont(LTSwing.getInstance().getFontComponentTextField());
		txtFieldTime.setBorder(LTSwing.getInstance().getBorderTableTextFieldEditing());
		txtFieldTime.setText("" + aValue);
		
		return txtFieldTime;
	}
	
	//*************************************************************************
    //Override to ensure that the value remains a Date
    @Override
    public Object getCellEditorValue() {
		txtFieldTime.setInputVerifier(null);
    	
    	String strTime = txtFieldTime.getText();
		strTime = strTime.trim();
    	
    	if (strTime.length() == 0) {
			strTime = "";
    		
    	} else if (strTime.substring(0, 1).equals(":")) {
    		String strTimeTemp = strTime.replace(":", "");
			strTimeTemp = strTimeTemp.trim();
    		
    		// Foi preenchido fora do padrão
    		if (strTimeTemp.length() > 0) {
				strTime = "";
    			
    		// Campo está vazio
    		} else {
				strTime = "";
    		}

    	} else {
    		if (strTime.matches("^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$")) {
    			// Hora no formato correto
    		} else {
    			return "";
    		}
    	}
        
        return strTime;
    }
}