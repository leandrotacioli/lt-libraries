package com.leandrotacioli.libs.swing.textfield;

import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.awt.event.ItemListener;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

/**
 *
 * @author Leandro Tacioli
 * @version 1.0 - 03/Abr/2015
 */
public class TextField extends JPanel implements TextFieldInterface {
	private static final long serialVersionUID = 9058121922409010383L;

	private String strLabel;

	private boolean blnEnabled;
	private boolean blnMandatoryField;
	
	/**
	 * Retorna o status de exibição do campo.
	 * 
	 * @return blnEnabled
	 */
	protected boolean getEnabled() {
		return blnEnabled;
	}

	/**
	 * Retorna o status de campo obrigatório.
	 * 
	 * @return blnMandatoryField
	 */
	protected boolean getMandatoryField() {
		return blnMandatoryField;
	}

	/**
	 * 
	 * @param strLabel
	 * @param blnEnabled
	 * @param blnMandatoryField
	 */
	protected TextField(String strLabel, boolean blnEnabled, boolean blnMandatoryField) {
		this.strLabel = strLabel;
		this.blnEnabled = blnEnabled;
		this.blnMandatoryField = blnMandatoryField;
	}

	/**
	 * Altera a quantidade máxima de caracteres do campo <i>STRING</i>.
	 * 
	 * @param intMaximumLength
	 */
	protected void setStringLength(int intMaximumLength) {
		
	}
	
	/**
	 * Atualiza a quantidade de casas decimais do campo <i>DOUBLE</i>.
	 * 
	 * @param intFractionDigits
	 */
	protected void setFractionDigits(int intFractionDigits) {
		
	}

	// *********************************************************************
	// Implementa TextFieldInterface
	@Override
	public String getLabel() {
		return strLabel;
	}
	
	@Override
	public boolean getIsMandatoryFieldEmpty() {
		return false;
	}
	
	@Override
	public Object getValue() {
		return null;
	}
	
	@Override
	public void setValue(Object objValue) {
		
	}
	
	@Override
	public void setEnabled(boolean blnEnabled) {
		this.blnEnabled = blnEnabled;
	}
	
	@Override
	public void setFocus() {
		
	}
		
	@Override
	public void addActionListener(ActionListener actionListener) {
		
	}
	
	@Override
	public void addKeyListener(KeyListener keyListener) {
		
	}
	
	@Override
	public void addFocusListener(FocusListener focusListener) {
		
	}
	
	@Override
	public void addItemListener(ItemListener itemListener) {

	}
}