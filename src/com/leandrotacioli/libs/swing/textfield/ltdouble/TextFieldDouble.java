package com.leandrotacioli.libs.swing.textfield.ltdouble;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DecimalFormat;

import javax.swing.JFormattedTextField;

import com.leandrotacioli.libs.LTParameters;
import com.leandrotacioli.libs.StringTransformations;

/**
 * 
 * @author Leandro Tacioli
 * @version 1.0 - 07/Abr/2015
 */
public class TextFieldDouble extends JFormattedTextField implements KeyListener {
	private static final long serialVersionUID = 8041888551259051911L;
	
	/**
	 * Valor máximo para o campo DOUBLE.
	 */
	public static final double MAX_VALUE = 9999999999999.99999999;
	
	private DecimalFormat decimalFormat;
	
	private double dblValue;

	private int intFractionDigits;
	
	private String strValueBeforeDeleted;
	private boolean blnDeleteKey;

	/**
	 * Retorna a quantidade de casas decimais.
	 * 
	 * @return intFractionDigits
	 */
	public int getFractionDigits() {
		return intFractionDigits;
	}

	/**
	 * Altera a quantidade de casas decimais.
	 * 
	 * @param intFractionDigits
	 */
	public void setFractionDigits(int intFractionDigits) {
		this.intFractionDigits = intFractionDigits;
		
		setDoubleFormat();
	}

	/**
	 * 
	 */
	public TextFieldDouble() {
		intFractionDigits = 2;

		setHorizontalAlignment(JFormattedTextField.RIGHT);
		setDoubleFormat();
		setValue(0.00);
		addKeyListener(this);
	}
	
	/**
	 * Estabelece o formato padrão da data.
	 */
	private void setDoubleFormat() {
		decimalFormat = new DecimalFormat();
		decimalFormat.setMinimumFractionDigits(intFractionDigits);
		decimalFormat.setMaximumFractionDigits(intFractionDigits);
		decimalFormat.setDecimalFormatSymbols(LTParameters.getInstance().getDecimalFormatSymbols());
		
		setInputVerifier(new TextFieldDoubleVerifier(this, decimalFormat));
		setDocument(new TextFieldDoubleDocument(intFractionDigits));
		setValue(getValue());
	}
	
	@Override
	public Object getValue() {
		return dblValue;
	}
	
	@Override
	public void setValue(Object objValue) {
		try {
			if (objValue instanceof Double) {
				dblValue = (double) objValue; 
				
			} else if (objValue instanceof String) {
				String strValue = objValue.toString();
				
				if (!strValue.equals("0.0")) {
					if (LTParameters.getInstance().getDecimalMark().equals("COMMA")) {
						strValue = strValue.replace(".", "");   // Retira os separadores de milhar
						strValue = strValue.replace(",", ".");
					} else if (LTParameters.getInstance().getDecimalMark().equals("PERIOD")) {
						strValue = strValue.replace(",", "");   // Retira os separadores de milhar
					}
				}
				
				dblValue = StringTransformations.setStringToDouble(strValue);
			}

		} catch (Exception e) {
			dblValue = 0.00;
		}
		
		setText(decimalFormat.format(dblValue));
	}

	// Implementa KeyListener
	@Override
	public void keyPressed(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.VK_BACK_SPACE || event.getKeyCode() == KeyEvent.VK_DELETE) {
			strValueBeforeDeleted = getText();
			blnDeleteKey = true;
		} else {
			blnDeleteKey = false;
		}
	}

	@Override
	public void keyReleased(KeyEvent event) {
	}

	@Override
	public void keyTyped(KeyEvent event) {
		if (blnDeleteKey) {
			String strValue = getText();

			if (strValue != null && strValue.length() > 0) {
				// Insere zero se não houver nenhum número antes da vírgula
				if (strValue.substring(0, 1).equals(",")) {
					setText("0" + strValue);
					setCaretPosition(1);
				}
				
				// Verifica se o valor não ficará maior que o permitido
				double dblValue = StringTransformations.setStringToDouble(strValue);
				
				if (dblValue > MAX_VALUE) {
					setText(strValueBeforeDeleted);
				}
			}
			
			blnDeleteKey = false;
		}
	}
}