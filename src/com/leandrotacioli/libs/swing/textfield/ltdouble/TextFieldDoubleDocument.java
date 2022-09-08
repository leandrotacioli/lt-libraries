package com.leandrotacioli.libs.swing.textfield.ltdouble;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import com.leandrotacioli.libs.LTParameters;
import com.leandrotacioli.libs.StringTransformations;

/**
 * 
 * @author Leandro Tacioli
 */
public class TextFieldDoubleDocument extends PlainDocument {
	private static final long serialVersionUID = 1191649656321787135L;
	
	private int intFractionDigits;
	
	/**
	 * 
	 */
	public TextFieldDoubleDocument(int intFractionDigits) {
		super();
		
		this.intFractionDigits = intFractionDigits;
	}
	
	//*************************************************************************
	@Override
	public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
		if (offset == 0 && str.length() > 0) {
			super.insertString(offset, str, attr);
		} else {
			char cChar = str.charAt(0);

			if (Character.isDigit(cChar) || cChar == '.' || cChar == ',') {
				String strDouble = getText(0, getLength());
				boolean blnSeparator = false;

				for (int intIndex = 0; intIndex < strDouble.length(); intIndex++) {
					if (strDouble.charAt(intIndex) == '.' || strDouble.charAt(intIndex) == ',') {
						blnSeparator = true;
					}
				}

				String strValue = getText(0, offset) + str + getText(offset, getLength() - offset);
				double dblValue = StringTransformations.setStringToDouble(strValue);
				
				String[] strIntegerDecimalParts = getIntegerDecimalParts(strValue);
				
				// Permite inclusão de apenas 1 separador
				if (blnSeparator) {
					if (Character.isDigit(cChar)) {
						// Permitir apenas o número de casas decimais estabelecido no TextFieldDouble
						if (dblValue >= TextFieldDouble.MIN_VALUE && dblValue <= TextFieldDouble.MAX_VALUE && strIntegerDecimalParts[1].length() <= intFractionDigits) {
							super.insertString(offset, str, attr);
						}
					}
					
				// Caso ainda não possua separador
				} else {
					if (cChar == '.' || cChar == ',') {
						strValue = getText(0, offset) + "." + getText(offset, getLength() - offset);
						
						if (!str.equals(".") && !str.equals(",") && getText(offset, getLength() - offset).equals("")) {
							strValue = strValue + "0";
						}
						
						dblValue = StringTransformations.setStringToDouble(strValue);
					}
					
					if (dblValue >= TextFieldDouble.MIN_VALUE && dblValue <= TextFieldDouble.MAX_VALUE) {
						// Altera o separador para o padrão configurado nos parâmetros
						if (cChar == '.' || cChar == ',') {
							// Separador é uma vírgula
							if (LTParameters.getInstance().getDecimalMark().equals("COMMA")) {
								if (strValue.equals(".")) {
									super.insertString(offset, "0,", attr);
								} else {
									super.insertString(offset, ",", attr);
								}
							
							// Separador é um ponto
							} else if (LTParameters.getInstance().getDecimalMark().equals("PERIOD")) {
								if (strValue.equals(".")) {
									super.insertString(offset, "0.", attr);
								} else {
									super.insertString(offset, ".", attr);
								}
							}
							
						// Insere valor à string
						} else {
							super.insertString(offset, str, attr);
						}
					}
				}
			}
		}
	}
	
	/**
	 * Retorna a parte inteira e a decimal de um número separadas em um array.
	 * 
	 * @param strValue
	 * 
	 * @return strSeparated
	 */
	private String[] getIntegerDecimalParts(String strValue) {
		String[] strSeparated = new String[2];
		String[] strValues = new String[2];
		
		if (strValue.equals(".") || strValue.equals(",")) {
			strSeparated[0] = "";
			strSeparated[1] = "";
			
		} else {
			if (strValue.contains(".")) {
				strValues = strValue.split(".");
			} else if (strValue.contains(",")) {
				strValues = strValue.split(",");
			} else {
				strValues[0] = strValue;
				strValues[1] = "0";
			}
			
			if (strValues.length == 0) {
				strSeparated[0] = "0";
				strSeparated[1] = "0";
			} else if (strValues.length == 1) {
				strSeparated[0] = strValues[0];
				strSeparated[1] = "0";
			} else if (strValues.length == 2) {
				strSeparated[0] = strValues[0];
				strSeparated[1] = strValues[1];
			}
		}

		return strSeparated;
	}
}