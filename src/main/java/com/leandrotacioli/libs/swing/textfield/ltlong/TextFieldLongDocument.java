package com.leandrotacioli.libs.swing.textfield.ltlong;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * 
 * @author Leandro Tacioli
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
		if (offset == 0 && str.length() > 0) {
			super.insertString(offset, str, attr);
		} else {
			char cChar = str.charAt(0);

			if (Character.isDigit(cChar)) {
				try {
					Long.parseLong(getText(0, offset) + cChar + getText(offset, getLength() - offset));

					super.insertString(offset, str, attr);
				} catch (Exception e) {
					//System.out.println("Invalid LONG value");
				}
			}
		}
	}
}