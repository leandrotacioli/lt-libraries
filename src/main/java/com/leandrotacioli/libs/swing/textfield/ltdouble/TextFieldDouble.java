package com.leandrotacioli.libs.swing.textfield.ltdouble;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.math.BigDecimal;
import java.text.DecimalFormat;

import javax.swing.JFormattedTextField;

import com.leandrotacioli.libs.LTParameters;
import com.leandrotacioli.libs.transformation.DoubleTransformation;

/**
 *
 * @author Leandro Tacioli
 */
public class TextFieldDouble extends JFormattedTextField implements KeyListener {

	/** Valor mínimo para o campo DOUBLE. */
	public static final BigDecimal MIN_VALUE = new BigDecimal("-9999999999999999.9999999999");
	
	/** Valor máximo para o campo DOUBLE. */
	public static final BigDecimal MAX_VALUE = new BigDecimal("9999999999999999.9999999999");
	
	private DecimalFormat decimalFormat;
	
	private BigDecimal value;

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
		decimalFormat = DoubleTransformation.getDecimalFormat(intFractionDigits);
		
		setInputVerifier(new TextFieldDoubleVerifier(this, decimalFormat));
		setDocument(new TextFieldDoubleDocument(intFractionDigits));
		setValue(getValue());
	}
	
	@Override
	public Object getValue() {
		return value;
	}
	
	@Override
	public void setValue(Object objValue) {
		try {
			if (objValue != null) {
				if (objValue instanceof BigDecimal) {
					value = (BigDecimal) objValue;

				} else if (objValue instanceof Double) {
					value = new BigDecimal(objValue.toString());

				} else if (objValue instanceof Integer) {
					value = new BigDecimal(objValue.toString());

				} else if (objValue instanceof String) {
					String strValue = objValue.toString();

					if (!strValue.equals("0.0")) {
						strValue = DoubleTransformation.replaceDecimalSeparator(strValue);
					}

					value = new BigDecimal(strValue);
				}
			} else {
				value = new BigDecimal("0.00");
			}

		} catch (Exception e) {
			value = new BigDecimal("0.00");
		}
		
		setText(decimalFormat.format(value) + (blnShowAsPercentage ? "%" : ""));
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
				BigDecimal doubleValue = new BigDecimal(strValue);

				if (doubleValue.compareTo(MIN_VALUE) >= 0 && doubleValue.compareTo(MAX_VALUE) <= 0) {
					setText(strValueBeforeDeleted + (blnShowAsPercentage ? "%" : ""));
				}
			}
			
			blnDeleteKey = false;
		}
	}
}