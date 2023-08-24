package com.leandrotacioli.libs.swing.textfield.ltlong;

import javax.swing.JFormattedTextField;

/**
 * 
 * @author Leandro Tacioli
 */
public class TextFieldLong extends JFormattedTextField {

	private long lgnValue;
	
	/**
	 * 
	 */
	public TextFieldLong() {
		setHorizontalAlignment(JFormattedTextField.RIGHT);
		setInputVerifier(new TextFieldLongVerifier(this));
		setDocument(new TextFieldLongDocument());
		setValue(0);
	}
	
	@Override
	public Object getValue() {
		return lgnValue;
	}
	
	@Override
	public void setValue(Object objValue) {
		try {
			if (objValue instanceof Integer) {
				lgnValue = Long.valueOf(objValue.toString());
			} else if (objValue instanceof Long) {
				lgnValue = (long) objValue;
			} else if (objValue instanceof String) {
				lgnValue = Long.parseLong((String) objValue);
			}
			
		} catch (Exception e) {
			lgnValue = 0;
		}
		
		setText("" + lgnValue);
	}
}