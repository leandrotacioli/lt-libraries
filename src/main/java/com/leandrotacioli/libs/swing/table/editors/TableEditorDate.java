package com.leandrotacioli.libs.swing.table.editors;

import javax.swing.DefaultCellEditor;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import java.awt.Component;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.leandrotacioli.libs.LTParameters;
import com.leandrotacioli.libs.swing.LTSwing;
import com.leandrotacioli.libs.swing.textfield.ltdate.TextFieldDate;

/**
 * 
 * @author Leandro Tacioli
 */
public class TableEditorDate extends DefaultCellEditor {

	private static TextFieldDate txtFieldDate = new TextFieldDate();
	
	private int intHorizontalAlignment;
	
	private SimpleDateFormat dateFormat;
	
	public TableEditorDate() {
		this(SwingConstants.LEFT);
	}
	
    public TableEditorDate(int intHorizontalAlignment) {
        super(txtFieldDate);
        
        this.intHorizontalAlignment = intHorizontalAlignment;
        
        dateFormat = new SimpleDateFormat(LTParameters.getInstance().getDateFormat());
        dateFormat.setLenient(false);
    }

	//*************************************************************************
    //Override to invoke setValue on the formatted text field.
    @Override
    public Component getTableCellEditorComponent(JTable table, Object aValue, boolean isSelected, int rowIndex, int columnIndex) {
    	txtFieldDate = (TextFieldDate) super.getTableCellEditorComponent(table, aValue, isSelected, rowIndex, columnIndex);
    	txtFieldDate.setHorizontalAlignment(intHorizontalAlignment);
    	txtFieldDate.setFont(LTSwing.getInstance().getFontComponentTextField());
    	txtFieldDate.setBorder(LTSwing.getInstance().getBorderTableTextFieldEditing());

        return txtFieldDate;
    }
   
	//*************************************************************************
    //Override to ensure that the value remains a Date
    @Override
    public Object getCellEditorValue() {
    	txtFieldDate.setInputVerifier(null);
    	
    	String strDate = txtFieldDate.getText();
        strDate = strDate.trim();
        
        try {
        	if (strDate.length() == 0) {
        		return "";
        		
        	} else if (strDate.substring(0, 1).equals("/") && strDate.substring(3, 4).equals("/")) {
        		String strDateTemp = strDate.replace("/", "");
        		strDateTemp = strDateTemp.trim();
        		
        		// Foi preenchido fora do padrão
        		if (strDateTemp.length() > 0) {
        			return "";
        			
        		// Campo está vazio
        		} else {
            		return "";
        		}

        	} else {
            	if (strDate.length() == 7 || strDate.length() == 9) {
            		return "";
            		
            	} else if (strDate.length() == 8) {
            		strDate = strDate.substring(0, 6) + "20" + strDate.substring(6, 8);
            		
					dateFormat.parse(strDate);
	                
            	} else if (strDate.length() == 10) {
					dateFormat.parse(strDate);
					
            	} else {
					dateFormat.parse(strDate);
            	}
        	}
        	
        } catch (ParseException e) {
        	return "";
        }
        
        return strDate;
    }
}