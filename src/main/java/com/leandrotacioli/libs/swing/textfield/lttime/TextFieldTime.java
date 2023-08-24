package com.leandrotacioli.libs.swing.textfield.lttime;

import javax.swing.JFormattedTextField;

import com.leandrotacioli.libs.swing.textfield.ltstring.TextFieldStringDocument;

/**
 * 
 * @author Leandro Tacioli
 */
public class TextFieldTime extends JFormattedTextField {

	private String strTime;
	
	public String getTime() {
		return strTime;
	}

	protected void setTime(String strTime) {
		this.strTime = strTime;
	}
	
	/**
	 * 
	 */
	public TextFieldTime() {
		setHorizontalAlignment(JFormattedTextField.LEFT);
		setTimeFormat();
	}
	
	/**
	 * Estabelece o formato padrão da hora.
	 */
	public void setTimeFormat() {
		setFormatterFactory(TextFieldTimeFormatterFactory.timeFormatterFactory());
		setInputVerifier(new TextFieldTimeVerifier(this));
		setDocument(new TextFieldStringDocument(5));
	}
	
	@Override
	public Object getValue() {
		return strTime;
	}
	
	@Override
	public void setValue(Object objValue) {
		if (objValue == null || objValue.toString().length() == 0) {
			strTime = null;
			
			setFormatterFactory(null);
			setInputVerifier(null);
			setText("");
			
		} else {
			// Se o formato padrão da data for "HH:mm"
			if (objValue.toString().substring(2, 3).equals(":")) {
				strTime = (String) objValue;
				
				setTimeFormat();
				setText(strTime);
			}
		}
	}
}