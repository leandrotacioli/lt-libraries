package com.leandrotacioli.libs.swing.textfield.lthour;

import javax.swing.InputVerifier;
import javax.swing.JComponent;

import com.leandrotacioli.libs.swing.LTSwing;

/**
 * 
 * @author Leandro Tacioli
 * @version 1.0 - 09/Jun/2020
 */
public class TextFieldHourVerifier extends InputVerifier {
	private TextFieldHour textField;
	private String strHour;

	/**
	 * 
	 * @param textField
	 */
    public TextFieldHourVerifier(TextFieldHour textField) {
        this.textField = textField;
    }

    @Override
    public boolean verify(JComponent component) {
        if (component == textField) {
        	strHour = textField.getText();
        	strHour = strHour.trim();       // Remove espaços em branco
            
            try {
            	if (strHour.length() == 0) {
            		strHour = "";
            		return true;
            		
            	} else if (strHour.substring(0, 1).equals(":")) {
            		String strHourTemp = strHour.replace(":", "");
            		strHourTemp = strHourTemp.trim();
            		
            		// Foi preenchido fora do padrão
            		if (strHourTemp.length() > 0) {
            			return false;
            			
            		// Campo está vazio
            		} else {
            			strHour = "";
                		return true;
            		}

            	} else {
            		if (strHour.matches("^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$")) {
            			return true;	
            		} else {
            			return false;
            		}
            	}
            	
            } catch (Exception e) {
            	return false;
            }
        }
        
        return false;
    }

	//*************************************************************************
    @Override
    public boolean shouldYieldFocus(JComponent component) {
    	boolean blnValid = false;
    	
    	// Hora válida
        if (verify(component)) {
        	textField.setBorder(LTSwing.getInstance().getBorderComponent());
        	blnValid = true;
            
        // Hora inválida
        } else {
        	textField.setBorder(LTSwing.getInstance().getBorderComponentError());
        	blnValid = false;
        }
        
        textField.setText(strHour);
        textField.setValue(strHour);
        
        return blnValid;
    }
}