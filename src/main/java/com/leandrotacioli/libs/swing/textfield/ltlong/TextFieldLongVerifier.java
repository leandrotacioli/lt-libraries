package com.leandrotacioli.libs.swing.textfield.ltlong;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;

/**
 * Verifica se o campo <i>LONG</i> está vazio.
 * 
 * @author Leandro Tacioli
 */
public class TextFieldLongVerifier extends InputVerifier {
	private JFormattedTextField textField;
	
	/**
	 * Verifica se o campo <i>LONG</i> está vazio.
	 * 
	 * @param textField
	 */
    public TextFieldLongVerifier(JFormattedTextField textField) {
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