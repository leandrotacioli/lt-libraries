package com.leandrotacioli.libs.swing.textfield.ltinteger;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * 
 * @author Leandro Tacioli
 * @version 1.0 - 16/Abr/2015
 */
public class TextFieldIntegerDocument extends PlainDocument {
	private static final long serialVersionUID = 6121219651008957847L;

	/**
	 * 
	 */
	public TextFieldIntegerDocument() {
		super();
	}
	
	//*************************************************************************
	@Override
	public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
		try {
			char c = str.charAt(0);
			
			if (Character.isDigit(c)) {
				int intValue = Integer.parseInt(getText(0, offset) + c + getText(offset, getLength() - offset));
				
				if (intValue <= Integer.MAX_VALUE) {
					super.insertString(offset, str.toString(), attr);
				}
			}
		
		} catch (Exception e) {

		}
	}
}