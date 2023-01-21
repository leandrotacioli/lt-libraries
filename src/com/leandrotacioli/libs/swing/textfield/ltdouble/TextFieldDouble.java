package com.leandrotacioli.libs.swing.textfield.ltdouble;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DecimalFormat;

import javax.swing.JFormattedTextField;

import com.leandrotacioli.libs.LTParameters;
import com.leandrotacioli.libs.transformation.DoubleTransformation;

/**
 *
 * @author Leandro Tacioli
 */
public class TextFieldDouble extends JFormattedTextField implements KeyListener {
	private static final long serialVersionUID = 8041888551259051911L;
	
	/** Valor mínimo para o campo DOUBLE. */
	public static final double MIN_VALUE = -9999999999999.99999999;
	
	/** Valor máximo para o campo DOUBLE. */
	public static final double MAX_VALUE = 9999999999999.99999999;
	
	private DecimalFormat decimalFormat;
	
	private double dblValue;

	private int intFractionDigits;
	private boolean blnShowAsPercentage;
	
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
	 * Retorna o status de exibiçao do campo com uma máscara de porcentagem.
	 * 
	 * @param blnShowAsPercentage
	 */
	public boolean getShowAsPercentage() {
		return blnShowAsPercentage;
	}
	
	/**
	 * Altera o status de exibição do campo com uma máscara de porcentagem.
	 * <br>
	 * OBS: O cálculo de porcentagem não é realizado automaticamente.
	 * 
	 * @param blnShowAsPercentage
	 */
	public void setShowAsPercentage(boolean blnShowAsPercentage) {
		this.blnShowAsPercentage = blnShowAsPercentage;
		
		setValue(getValue());
	}

	/**
	 * 
	 */
	public TextFieldDouble() {
		this(2, false);
	}
	
	public TextFieldDouble(int intFractionDigits, boolean blnShowAsPercentage) {
		this.intFractionDigits = intFractionDigits;
		this.blnShowAsPercentage = blnShowAsPercentage;
		
		setHorizontalAlignment(JFormattedTextField.RIGHT);
		setDoubleFormat();
		setValue(0.00d);
		
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
			
			} else if (objValue instanceof Integer) {
				dblValue = Integer.valueOf(objValue.toString());
				
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
				
				dblValue = DoubleTransformation.stringToDouble(strValue);
			}

		} catch (Exception e) {
			dblValue = 0.00;
		}
		
		setText(decimalFormat.format(dblValue) + (blnShowAsPercentage ? "%" : ""));
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
					setText("0" + strValue + (blnShowAsPercentage ? "%" : ""));
					setCaretPosition(1);
				}
				
				// Verifica se o valor não ficará menor/maior que o permitido
				double dblValue = DoubleTransformation.stringToDouble(strValue);
				
				if (dblValue < MIN_VALUE || dblValue > MAX_VALUE) {
					setText(strValueBeforeDeleted + (blnShowAsPercentage ? "%" : ""));
				}
			}
			
			blnDeleteKey = false;
		}
	}
}