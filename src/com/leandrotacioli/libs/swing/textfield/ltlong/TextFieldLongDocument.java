package com.leandrotacioli.libs.swing.textfield.ltlong;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * 
 * @author Leandro Tacioli
 * @version 1.0 - 16/Abr/2015
 */
public class TextFieldLongDocument extends PlainDocument {
	private static final long serialVersionUID = 8140317813250240429L;

	/**
	 * 
	 */
	public TextFieldLongDocument() {
		super();
	}
	
	//*************************************************************************
	@Override
	public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
		try {
			char c = str.charAt(0);
			
			if (Character.isDigit(c)) {
				long lgnValue = Long.parseLong(getText(0, offset) + c + getText(offset, getLength() - offset));
				
				if (lgnValue <= Long.MAX_VALUE) {
					super.insertString(offset, str.toString(), attr);
				}
			}
		
		} catch (Exception e) {

		}
	}
}