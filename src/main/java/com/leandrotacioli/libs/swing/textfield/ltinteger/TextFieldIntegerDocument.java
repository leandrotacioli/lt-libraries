package com.leandrotacioli.libs.swing.textfield.ltinteger;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * 
 * @author Leandro Tacioli
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
		if (offset == 0 && str.length() > 0) {
			super.insertString(offset, str, attr);
		} else {
			char cChar = str.charAt(0);

			if (Character.isDigit(cChar)) {
				try {
					Integer.parseInt(getText(0, offset) + cChar + getText(offset, getLength() - offset));

					super.insertString(offset, str, attr);
				} catch (Exception e) {
					//System.out.println("Invalid INTEGER value");
				}
			}
		}
	}
}