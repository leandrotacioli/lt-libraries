package com.leandrotacioli.libs.swing.textfield.ltstring;

import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * 
 * @author Leandro Tacioli
 * @version 1.0 - 07/Abr/2015
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
		setHorizontalAlignment(SwingConstants.LEFT);
		setDocument(new TextFieldStringDocument(intMaximumLength));
	}
}