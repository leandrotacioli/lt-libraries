package com.leandrotacioli.libs.swing.textfield.ltstring;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * Define a quantidade de caracteres que a <i>STRING</i> pode conter.
 * 
 * @author Leandro Tacioli
 */
public class TextFieldStringDocument extends PlainDocument {

	private int intMaximumLength;

	/**
	 * Define a quantidade de caracteres que a <i>STRING</i> pode conter. 
	 * 
	 * @param intMaximumLength
	 */
	public TextFieldStringDocument(int intMaximumLength) {
		super();
		
		this.intMaximumLength = intMaximumLength;
	}

	//*************************************************************************
	@Override
	public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
		if (str == null) {
			return;
		}
		
		if (intMaximumLength <= 0) {
			super.insertString(offset, str, attr);
			
			return;
		}
		
		int intLength = (getLength() + str.length());
		
		if (intLength <= intMaximumLength) {
			super.insertString(offset, str, attr);
			
		} else {
			if (getLength() == intMaximumLength) {
				return;
				
			} else {
				String strNewString = str.substring(0, intMaximumLength - getLength());
				super.insertString(offset, strNewString, attr);
			}
		}
	}
}