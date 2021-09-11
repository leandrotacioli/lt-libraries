package com.leandrotacioli.libs.swing.textfield.ltstring;

import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * 
 * @author Leandro Tacioli
 * @version 2.0 - 11/Sep/2021
 */
public class TextFieldString extends JTextField {
	private static final long serialVersionUID = 3190372699935806445L;

	private int intMaximumLength;
	private int intHorizontalAlignment;
	
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
	}
	
	/**
	 * 
	 * @return intHorizontalAlignment
	 */
	public int getHorizontalAlignment() {
		return intHorizontalAlignment;
	}

	/**
	 * 
	 * @param intHorizontalAlignment
	 */
	public void setHorizontalAlignment(int intHorizontalAlignment) {
		this.intHorizontalAlignment = intHorizontalAlignment;
	}

	/**
	 * 
	 */
	public TextFieldString() {
		this(20, SwingConstants.LEFT);
	}
	
	/**
	 * 
	 * @param intMaximumLength
	 */
	public TextFieldString(int intMaximumLength, int intHorizontalAlignment) {
		this.intMaximumLength = intMaximumLength;
		this.intHorizontalAlignment = intHorizontalAlignment;
		
		setStringFormat();
	}
	
	/**
	 * Estabelece o formato padrão da string.
	 */
	private void setStringFormat() {
		setHorizontalAlignment(intHorizontalAlignment);
		setDocument(new TextFieldStringDocument(intMaximumLength));
	}
}