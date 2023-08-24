package com.leandrotacioli.libs.swing.textfield.ltdate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.InputVerifier;
import javax.swing.JComponent;

import com.leandrotacioli.libs.LTParameters;
import com.leandrotacioli.libs.swing.LTSwing;

/**
 * 
 * @author Leandro Tacioli
 */
public class TextFieldDateVerifier extends InputVerifier {
	private TextFieldDate textField;
	private SimpleDateFormat dateFormat;
	private String strDate;

	/**
	 * 
	 * @param textField
	 */
    public TextFieldDateVerifier(TextFieldDate textField) {
        this.textField = textField;
        
        dateFormat = new SimpleDateFormat(LTParameters.getInstance().getDateFormat());
        dateFormat.setLenient(false);
    }

    @Override
    public boolean verify(JComponent component) {
        boolean blnResult = false;
        
        if (component == textField) {
            strDate = textField.getText();
            strDate = strDate.trim();       // Remove espaços em branco
            
            try {
            	if (strDate.length() == 0) {
            		strDate = null;
            		return true;
            		
            	} else if (strDate.substring(0, 1).equals("/") && strDate.substring(3, 4).equals("/")) {
            		String strDateTemp = strDate.replace("/", "");
            		strDateTemp = strDateTemp.trim();
            		
            		// Foi preenchido fora do padrão
            		if (strDateTemp.length() > 0) {
            			return false;
            			
            		// Campo está vazio
            		} else {
            			strDate = null;
                		return true;
            		}

            	} else {
	            	if (strDate.length() == 7 || strDate.length() == 9) {
	            		return false;
	            		
	            	} else if (strDate.length() == 8) {
	            		strDate = strDate.substring(0, 6) + "20" + strDate.substring(6, 8);
	            		
	            		@SuppressWarnings("unused")
						Date date = dateFormat.parse(strDate);
		                blnResult = true;
		                
	            	} else if (strDate.length() == 10) {
	            		@SuppressWarnings("unused")
						Date date = dateFormat.parse(strDate);
		                blnResult = true;
	            	}
            	}
            	
            } catch (ParseException e) {
            	blnResult = false;
            }
        }
        
        return blnResult;
    }

	//*************************************************************************
    @Override
    public boolean shouldYieldFocus(JComponent component) {
    	boolean blnValid = false;
    	
    	// Data válida
        if (verify(component)) {
        	textField.setBorder(LTSwing.getInstance().getBorderComponent());
        	blnValid = true;
            
        // Data inválida
        } else {
        	textField.setBorder(LTSwing.getInstance().getBorderComponentError());
        	blnValid = false;
        }
        
        textField.setDate(strDate);
        textField.setText(strDate);
        
        return blnValid;
    }
}