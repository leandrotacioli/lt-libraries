package com.leandrotacioli.libs.swing.textfield.ltdate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFormattedTextField;

import com.leandrotacioli.libs.LTParameters;
import com.leandrotacioli.libs.swing.textfield.ltstring.TextFieldStringDocument;

/**
 * 
 * @author Leandro Tacioli
 */
public class TextFieldDate extends JFormattedTextField {

	private String strDate;
	
	public String getDate() {
		return strDate;
	}

	protected void setDate(String strDate) {
		this.strDate = strDate;
	}

	/**
	 * 
	 */
	public TextFieldDate() {
		setHorizontalAlignment(JFormattedTextField.LEFT);
		setDateFormat();
	}
	
	/**
	 * Estabelece o formato padrão da data.
	 */
	public void setDateFormat() {
		setFormatterFactory(TextFieldDateFormatterFactory.dateFormatterFactory());
		setInputVerifier(new TextFieldDateVerifier(this));
		setDocument(new TextFieldStringDocument(10));
	}
	
	@Override
	public Object getValue() {
		return strDate;
	}
	
	@Override
	public void setValue(Object objValue) {
		if (objValue == null || objValue.toString().length() == 0) {
			setFormatterFactory(null);
			setInputVerifier(null);
			
			strDate = null;
			
		} else {
			// Se o formato padrão da data for "dd/MM/yyyy"
			if (objValue.toString().substring(2, 3).equals("/") && objValue.toString().substring(5, 6).equals("/")) {
				setDateFormat();
				strDate = (String) objValue;
				
			// Se o formato padrão da data for "yyyy-MM-dd" (armazenado no banco de dados)
			} else if (objValue.toString().substring(4, 5).equals("-") && objValue.toString().substring(7, 8).equals("-")) {
				try {
					SimpleDateFormat dataBaseFormat = new SimpleDateFormat("yyyy-MM-dd");  // Formato armazenado no banco de dados
					SimpleDateFormat dateFormat = new SimpleDateFormat(LTParameters.getInstance().getDateFormat());
					Date date = dataBaseFormat.parse((String) objValue);
					
					strDate = dateFormat.format(date);
					
				} catch (ParseException e) {
					//setFormatterFactory(null);
					//setInputVerifier(null);
					
					strDate = null;
				}
			}
		}
		
		setText(strDate);
	}
}