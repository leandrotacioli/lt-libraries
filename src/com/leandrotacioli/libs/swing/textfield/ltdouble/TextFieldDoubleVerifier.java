package com.leandrotacioli.libs.swing.textfield.ltdouble;

import java.text.DecimalFormat;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;

import com.leandrotacioli.libs.LTParameters;

/**
 * Verifica se o campo <i>DOUBLE</i> está preenchido corretamente.
 * 
 * @author Leandro Tacioli
 * @version 1.0 - 02/Abr/2015
 */
public class TextFieldDoubleVerifier extends InputVerifier {
	private JFormattedTextField textField;
	private DecimalFormat decimalFormat;

	/**
	 * Verifica se o campo <i>DOUBLE</i> está preenchido corretamente.
	 * 
	 * @param textField
	 * @param decimalFormat
	 */
    public TextFieldDoubleVerifier(JFormattedTextField textField, DecimalFormat decimalFormat) {
        this.textField = textField;
        this.decimalFormat = decimalFormat;
    }

	//*************************************************************************
    @Override
    public boolean verify(JComponent component) {
        boolean blnResult = false;
         
        try {
        	if (component == textField) {
        		// Se o campo estiver vazio
        		if (textField.getText() == null || textField.getText().length() == 0) {
        			textField.setText(decimalFormat.format(0));
        			
        			blnResult = true;
        		
        		// Se o campo já conter algum valor
        		} else {
        			textField.setText(textField.getText().replace(",", "."));
		            textField.setText(decimalFormat.format(Double.parseDouble(textField.getText())));

		            blnResult = true;
        		}
        	}
        	
        } catch (Exception e) {
    		return false;
    	}
        
        return blnResult;
    }

	//*************************************************************************
    @Override
    public boolean shouldYieldFocus(JComponent component) {
        if (verify(component)) {
        	textField.setBorder(LTParameters.getInstance().getBorderComponent());
            return true;
        } else {
        	textField.setBorder(LTParameters.getInstance().getBorderComponentError());
        	return false;
        }
    }
}