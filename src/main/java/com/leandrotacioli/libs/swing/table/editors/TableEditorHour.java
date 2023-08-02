package com.leandrotacioli.libs.swing.table.editors;

import java.awt.Component;

import javax.swing.DefaultCellEditor;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import com.leandrotacioli.libs.swing.LTSwing;
import com.leandrotacioli.libs.swing.textfield.lthour.TextFieldHour;

/**
 * 
 * @author Leandro Tacioli
 */
public class TableEditorHour extends DefaultCellEditor {
	private static final long serialVersionUID = -5687943739963048019L;
	
	private static TextFieldHour txtFieldHour = new TextFieldHour();
	
	private int intHorizontalAlignment;
	
	public TableEditorHour() {
		this(SwingConstants.LEFT);
	}
	
	public TableEditorHour(int intHorizontalAlignment) {
		super(txtFieldHour);
		
		this.intHorizontalAlignment = intHorizontalAlignment;
	}
	
	//*************************************************************************
	@Override
	public Component getTableCellEditorComponent(JTable table, Object aValue, boolean isSelected, int rowIndex, int columnIndex) {
		txtFieldHour = (TextFieldHour) super.getTableCellEditorComponent(table, aValue, isSelected, rowIndex, columnIndex);
		txtFieldHour.setHorizontalAlignment(intHorizontalAlignment);
		txtFieldHour.setFont(LTSwing.getInstance().getFontComponentTextField());
		txtFieldHour.setBorder(LTSwing.getInstance().getBorderTableTextFieldEditing());
		txtFieldHour.setText("" + aValue);
		
		return txtFieldHour;
	}
	
	//*************************************************************************
    //Override to ensure that the value remains a Date
    @Override
    public Object getCellEditorValue() {
    	txtFieldHour.setInputVerifier(null);
    	
    	String strHour = txtFieldHour.getText();
    	strHour = strHour.trim();
    	
    	if (strHour.length() == 0) {
    		strHour = "";
    		
    	} else if (strHour.substring(0, 1).equals(":")) {
    		String strHourTemp = strHour.replace(":", "");
    		strHourTemp = strHourTemp.trim();
    		
    		// Foi preenchido fora do padrão
    		if (strHourTemp.length() > 0) {
    			strHour = "";
    			
    		// Campo está vazio
    		} else {
    			strHour = "";
    		}

    	} else {
    		if (strHour.matches("^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$")) {
    			// Hora no formato correto
    		} else {
    			return "";
    		}
    	}
        
        return strHour;
    }
}