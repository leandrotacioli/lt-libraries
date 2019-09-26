package com.leandrotacioli.libs.swing.textfield;

import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.awt.event.ItemListener;
import java.awt.event.KeyListener;

/**
 * 
 * @author Leandro Tacioli
 * @version 1.5 - 12/Set/2016
 */
public interface TextFieldInterface {
	
	/**
	 * Retorna a descrição do campo.
	 * 
	 * @return strLabel
	 */
	public String getLabel();
	
	/**
	 * Altera a descrição do campo.
	 * 
	 * @param strLabel
	 */
	public void setLabel(String strLabel);
	
	/**
	 * Retorna o status de preenchimento do campo obrigatório.
	 * 
	 * @return <i>True</i> - Vazio
	 * 		   <br>
	 *         <i>False</i> - Preenchido
	 */
	public boolean getIsMandatoryFieldEmpty();
	
	/**
	 * Retorna o valor do campo.
	 * 
	 * @return objValue
	 */
	public Object getValue();
	
	/**
	 * Altera o valor do campo.
	 * 
	 * @return objValue
	 */
	public void setValue(Object objValue);
	
	/**
	 * Altera o status de exibição do campo.
	 * 
	 * @param blnEnabled
	 */
	public void setEnabled(boolean blnEnabled);
	
	/**
	 * Estabelece o foco no campo.
	 */
	public void setFocus();
	
	/**
	 * Adiciona um <i>ActionListener</i> ao campo.
	 * 
	 * @param actionListener
	 */
	public void addActionListener(ActionListener actionListener);
	
	/**
	 * Adiciona um <i>KeyListener</i> ao campo.
	 * 
	 * @param keyListener
	 */
	public void addKeyListener(KeyListener keyListener);
	
	/**
	 * Adiciona um <i>FocusListener</i> ao campo.
	 * 
	 * @param focusListener
	 */
	public void addFocusListener(FocusListener focusListener);
	
	/**
	 * Adiciona um <i>ItemListener</i> ao campo.
	 * 
	 * @param itemListener
	 */
	public void addItemListener(ItemListener itemListener);
}