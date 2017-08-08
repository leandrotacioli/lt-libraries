package com.leandrotacioli.libs.swing.textfield.ltinteger;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;

/**
 * Verifica se o campo <i>INTEGER</i> está vazio.
 * 
 * @author Leandro Tacioli
 * @version 1.0 - 02/Abr/2015
 */
public class TextFieldIntegerVerifier extends InputVerifier {
	private JFormattedTextField textField;
	
	/**
	 * Verifica se o campo <i>INTEGER</i> está vazio.
	 * 
	 * @param textField
	 */
    public TextFieldIntegerVerifier(JFormattedTextField textField) {
        this.textField = textField;
    }

	//*************************************************************************
    @Override
    public boolean verify(JComponent component) {
        boolean blnResult = true;
         
        if (component == textField) {
    		if (textField.getText() == null || textField.getText().length() == 0) {
    			textField.setText("0");
    		}
        }
        
        return blnResult;
    }

	//*************************************************************************
    @Override
    public boolean shouldYieldFocus(JComponent component) {
        if (verify(component)) {
            return true;
        } else {
        	return false;
        }
    }
}