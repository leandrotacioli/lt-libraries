package com.leandrotacioli.libs.swing.textfield.lthour;

import javax.swing.JFormattedTextField;

import com.leandrotacioli.libs.swing.textfield.ltstring.TextFieldStringDocument;

/**
 * 
 * @author Leandro Tacioli
 * @version 1.0 - 09/Jun/2020
 */
public class TextFieldHour extends JFormattedTextField {
	private static final long serialVersionUID = 393750898707041886L;
	
	private String strHour;
	
	public String getHour() {
		return strHour;
	}

	protected void setHour(String strHour) {
		this.strHour = strHour;
	}
	
	/**
	 * 
	 */
	public TextFieldHour() {
		setHorizontalAlignment(JFormattedTextField.LEFT);
		setHourFormat();
	}
	
	/**
	 * Estabelece o formato padrão da hora.
	 */
	public void setHourFormat() {
		setFormatterFactory(TextFieldHourFormatterFactory.hourFormatterFactory());
		setInputVerifier(new TextFieldHourVerifier(this));
		setDocument(new TextFieldStringDocument(5));
	}
	
	@Override
	public Object getValue() {
		return strHour;
	}
	
	@Override
	public void setValue(Object objValue) {
		if (objValue == null || objValue.toString().length() == 0) {
			strHour = null;
			
			setFormatterFactory(null);
			setInputVerifier(null);
			setText("");
			
		} else {
			// Se o formato padrão da data for "HH:mm"
			if (objValue.toString().substring(2, 3).equals(":")) {
				strHour = (String) objValue;
				
				setHourFormat();
				setText(strHour);
			}
		}
	}
}