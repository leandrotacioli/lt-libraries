package com.leandrotacioli.libs.swing.textfield.ltinteger;

import javax.swing.JFormattedTextField;

/**
 * 
 * @author Leandro Tacioli
 */
public class TextFieldInteger extends JFormattedTextField {

	private int intValue;
	
	/**
	 * 
	 */
	public TextFieldInteger() {
		setHorizontalAlignment(JFormattedTextField.RIGHT);
		setInputVerifier(new TextFieldIntegerVerifier(this));
		setDocument(new TextFieldIntegerDocument());
		setValue(0);
	}
	
	@Override
	public Object getValue() {
		return intValue;
	}
	
	@Override
	public void setValue(Object objValue) {
		try {
			if (objValue instanceof Integer) {
				intValue = Integer.valueOf(objValue.toString());
			} else if (objValue instanceof String) {
				intValue = Integer.parseInt((String) objValue);
			}
			
		} catch (Exception e) {
			intValue = 0;
		}
		
		setText("" + intValue);
	}
}