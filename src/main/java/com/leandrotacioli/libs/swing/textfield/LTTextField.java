package com.leandrotacioli.libs.swing.textfield;

import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.awt.event.ItemListener;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.UIManager;

import com.leandrotacioli.libs.LTDataTypes;

import com.leandrotacioli.libs.swing.LTSwing;
import net.miginfocom.swing.MigLayout;

/**
 * Campos.
 * 
 * @author Leandro Tacioli
 */
public class LTTextField extends JPanel implements TextFieldInterface {
	private static final long serialVersionUID = 7550401382977332304L;
	
	private TextField objTextField;
	
	private String strColumnDatabase;
	
	/**
	 * Retorna a coluna do banco de dados do campo.
	 * 
	 * @return strColumnDatabase
	 */
	public String getColumnDatabase() {
		return strColumnDatabase;
	}

	/**
	 * Altera a coluna do banco de dados do campo.
	 * 
	 * @param strColumnDatabase
	 */
	public void setColumnDatabase(String strColumnDatabase) {
		this.strColumnDatabase = strColumnDatabase;
	}

	/**
	 * Campos.
	 * 
	 * @param strLabel
	 * @param objDataType
	 * @param blnEnabled
	 * @param blnMandatoryField
	 */
	public LTTextField(String strLabel, LTDataTypes objDataType, boolean blnEnabled, boolean blnMandatoryField) {
		this(strLabel, objDataType, blnEnabled, blnMandatoryField, 0);
	}
	
	/**
	 * 
	 * @param strLabel
	 * @param objDataType
	 * @param blnEnabled
	 * @param blnMandatoryField
	 * @param intMaximumLength
	 */
	public LTTextField(String strLabel, LTDataTypes objDataType, boolean blnEnabled, boolean blnMandatoryField, int intMaximumLength) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			
			if (objDataType == LTDataTypes.INTEGER) {
				objTextField = new TextFieldPanelInteger(strLabel, blnEnabled, blnMandatoryField);
				
			} else if (objDataType == LTDataTypes.LONG) {
				objTextField = new TextFieldPanelLong(strLabel, blnEnabled, blnMandatoryField);
				
			} else if (objDataType == LTDataTypes.DOUBLE) {
				objTextField = new TextFieldPanelDouble(strLabel, blnEnabled, blnMandatoryField);
				
			} else if (objDataType == LTDataTypes.STRING) {
				objTextField = new TextFieldPanelString(strLabel, blnEnabled, blnMandatoryField);
				objTextField.setStringLength(intMaximumLength);

			} else if (objDataType == LTDataTypes.TEXT) {
				objTextField = new TextFieldPanelText(strLabel, blnEnabled, blnMandatoryField);

			} else if (objDataType == LTDataTypes.DATE) {
				objTextField = new TextFieldPanelDate(strLabel, blnEnabled, blnMandatoryField);
				
			} else if (objDataType == LTDataTypes.HOUR) {
				objTextField = new TextFieldPanelHour(strLabel, blnEnabled, blnMandatoryField);

			} else if (objDataType == LTDataTypes.BOOLEAN) {
				objTextField = new TextFieldPanelBoolean(strLabel, blnEnabled, blnMandatoryField);
			}
			
			objTextField.setBackground(LTSwing.getInstance().getColorComponentPanelBackground());
			
			this.setLayout(new MigLayout("insets 0", "[grow]", "[grow]"));
			this.add(objTextField, "cell 0 0, grow");
			
		} catch(Exception e) {
        	e.printStackTrace();
        }
	}
	
	/**
	 * Atualiza o alinhamento horizontal do campo.
	 * 
	 * @param intHorizontalAlignment
	 */
	public void setHorizontalAlignment(int intHorizontalAlignment) {
		objTextField.setHorizontalAlignment(intHorizontalAlignment);
	}
	
	/**
	 * Atualiza a quantidade de casas decimais.
	 * <br>
	 * OBS: Válido apenas para <i>DOUBLE</i>.
	 * 
	 * @param intFractionDigits
	 */
	public void setFractionDigits(int intFractionDigits) {
		objTextField.setFractionDigits(intFractionDigits);
	}
	
	/**
	 * Exibe o campo com uma máscara de porcentagem.
	 * <br>
	 * OBS 1: Válido apenas para <i>DOUBLE</i>.
	 * OBS 2: O cálculo de porcentagem não é realizado automaticamente.
	 * 
	 * @param blnShowAsPercentage
	 */
	public void setShowAsPercentage(boolean blnShowAsPercentage) {
		objTextField.setShowAsPercentage(blnShowAsPercentage);
	}

	// *********************************************************************
	// Implementa TextFieldInterface
	@Override
	public String getLabel() {
		return objTextField.getLabel();
	}
	
	@Override
	public void setLabel(String strLabel) {
		objTextField.setLabel(strLabel);
	}
	
	@Override
	public boolean getIsMandatoryFieldEmpty() {
		return objTextField.getIsMandatoryFieldEmpty();
	}
	
	@Override
	public Object getValue() {
		return objTextField.getValue();
	}
	
	@Override
	public void setValue(Object objValue) {
		objTextField.setValue(objValue);
	}
	
	@Override
	public void setEnabled(boolean blnEnabled) {
		objTextField.setEnabled(blnEnabled);
	}
	
	@Override
	public void setFocus() {
		objTextField.setFocus();
	}
	
	@Override
	public void addActionListener(ActionListener actionListener) {
		objTextField.addActionListener(actionListener);
	}
	
	@Override
	public void addKeyListener(KeyListener keyListener) {
		objTextField.addKeyListener(keyListener);
	}
	
	@Override
	public void addFocusListener(FocusListener focusListener) {
		objTextField.addFocusListener(focusListener);
	}
	
	@Override
	public void addItemListener(ItemListener itemListener) {
		objTextField.addItemListener(itemListener);
	}
}