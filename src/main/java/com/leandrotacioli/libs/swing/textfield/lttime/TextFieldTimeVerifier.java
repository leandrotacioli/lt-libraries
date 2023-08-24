package com.leandrotacioli.libs.swing.textfield.lttime;

import javax.swing.InputVerifier;
import javax.swing.JComponent;

import com.leandrotacioli.libs.swing.LTSwing;

/**
 * 
 * @author Leandro Tacioli
 */
public class TextFieldTimeVerifier extends InputVerifier {
	private TextFieldTime textField;
	private String strTime;

	/**
	 * 
	 * @param textField
	 */
    public TextFieldTimeVerifier(TextFieldTime textField) {
        this.textField = textField;
    }

    @Override
    public boolean verify(JComponent component) {
        if (component == textField) {
			strTime = textField.getText();
			strTime = strTime.trim();       // Remove espaços em branco
            
            try {
            	if (strTime.length() == 0) {
					strTime = "";
            		return true;
            		
            	} else if (strTime.substring(0, 1).equals(":")) {
            		String strTimeTemp = strTime.replace(":", "");
					strTimeTemp = strTimeTemp.trim();
            		
            		// Foi preenchido fora do padrão
            		if (strTimeTemp.length() > 0) {
            			return false;
            			
            		// Campo está vazio
            		} else {
						strTime = "";
                		return true;
            		}

            	} else {
            		if (strTime.matches("^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$")) {
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
        
        textField.setText(strTime);
        textField.setValue(strTime);
        
        return blnValid;
    }
}