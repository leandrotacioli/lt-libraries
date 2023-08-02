package com.leandrotacioli.libs.swing.textfield.ltstring;

import javax.swing.JTextField;

/**
 * 
 * @author Leandro Tacioli
 */
public class TextFieldString extends JTextField {
	private static final long serialVersionUID = 3190372699935806445L;

	private int intMaximumLength;
	
	/**
	 * 
	 * @return intMaximumLength
	 */
	public int getMaximumLength() {
		return intMaximumLength;
	}

	/**
	 * 
	 * @param intMaximumLength
	 */
	public void setMaximumLength(int intMaximumLength) {
		this.intMaximumLength = intMaximumLength;
		
		setStringFormat();
	}

	/**
	 * 
	 */
	public TextFieldString() {
		this(20);
	}
	
	/**
	 * 
	 * @param intMaximumLength
	 */
	public TextFieldString(int intMaximumLength) {
		this.intMaximumLength = intMaximumLength;
		
		setStringFormat();
	}
	
	/**
	 * Estabelece o formato padrão da string.
	 */
	private void setStringFormat() {
		this.setDocument(new TextFieldStringDocument(intMaximumLength));
	}
}