package com.leandrotacioli.libs.swing.table.editors;

import javax.swing.DefaultCellEditor;
import javax.swing.JTable;

import java.awt.Component;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.leandrotacioli.libs.LTParameters;
import com.leandrotacioli.libs.swing.textfield.ltdate.TextFieldDate;

/**
 * 
 * @author Leandro Tacioli
 * @version 1.0 - 15/Abr/2015
 */
public class TableEditorDate extends DefaultCellEditor {
	private static final long serialVersionUID = -9025778248402406514L;
	
	private SimpleDateFormat dateFormat;
	
	/**
	 * 
	 */
    public TableEditorDate() {
        super(new TextFieldDate());
        
        dateFormat = new SimpleDateFormat(LTParameters.getInstance().getDateFormat());
        dateFormat.setLenient(false);
    }

	//*************************************************************************
    //Override to invoke setValue on the formatted text field.
    @Override
    public Component getTableCellEditorComponent(JTable table, Object aValue, boolean isSelected, int rowIndex, int columnIndex) {
    	TextFieldDate textFieldEditor = (TextFieldDate) super.getTableCellEditorComponent(table, aValue, isSelected, rowIndex, columnIndex);
    	textFieldEditor.setFont(LTParameters.getInstance().getFontComponentTextField());
		textFieldEditor.setBorder(LTParameters.getInstance().getBorderTableTextFieldEditing());
		//textFieldEditor.setValue((String) aValue);
		
		
		//if ((textFieldEditor.getDate() == null || textFieldEditor.getDate().length() == 0)) {
		//	textFieldEditor.setDateFormat();
			
		//}
		
		//textFieldEditor.setInputVerifier(null);

        return textFieldEditor;
    }
   
	//*************************************************************************
    //Override to ensure that the value remains a Date
    @Override
    public Object getCellEditorValue() {
    	TextFieldDate txtFieldDate = (TextFieldDate) getComponent();
    	txtFieldDate.setInputVerifier(null);
    	
    	String strDate = txtFieldDate.getText();
        strDate = strDate.trim();
        
        try {
        	if (strDate.length() == 0) {
        		return null;
        		
        	} else if (strDate.substring(0, 1).equals("/") && strDate.substring(3, 4).equals("/")) {
        		String strDateTemp = strDate.replace("/", "");
        		strDateTemp = strDateTemp.trim();
        		
        		// Foi preenchido fora do padrão
        		if (strDateTemp.length() > 0) {
        			return false;
        			
        		// Campo está vazio
        		} else {
            		return null;
        		}

        	} else {
            	if (strDate.length() == 7 || strDate.length() == 9) {
            		return false;
            		
            	} else if (strDate.length() == 8) {
            		strDate = strDate.substring(0, 6) + "20" + strDate.substring(6, 8);
            		
					@SuppressWarnings("unused")
					Date date = dateFormat.parse(strDate);
	                
            	} else if (strDate.length() == 10) {
					@SuppressWarnings("unused")
					Date date = dateFormat.parse(strDate);
					
            	} else {
					@SuppressWarnings("unused")
					Date date = dateFormat.parse(strDate);
            	}
        	}
        	
        } catch (ParseException e) {
        	txtFieldDate.setBorder(LTParameters.getInstance().getBorderTableTextFieldError());
			
        	return "20/20";
        }
        
        return strDate;
    }
}