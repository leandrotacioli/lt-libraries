package com.leandrotacioli.libs.swing.textfield;

import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyListener;

import net.miginfocom.swing.MigLayout;

import com.leandrotacioli.libs.LTParameters;
import com.leandrotacioli.libs.swing.textfield.ltinteger.TextFieldInteger;

/**
 * 
 * @author Leandro Tacioli
 * @version 1.1 - 02/Mai/2016
 */
public class TextFieldPanelInteger extends TextField implements FocusListener {
	private static final long serialVersionUID = -311259449788891812L;
	
	private TextFieldPanel objTextFieldPanel;
	
	private TextFieldInteger txtIntegerField;

	/**
	 * 
	 * @param strLabel
	 * @param blnEnabled
	 * @param blnMandatoryField
	 */
	protected TextFieldPanelInteger(String strLabel, boolean blnEnabled, boolean blnMandatoryField) {
		super(strLabel, blnEnabled, blnMandatoryField);
		
		txtIntegerField = new TextFieldInteger();
		txtIntegerField.setMinimumSize(LTParameters.getInstance().getDimensionComponentMinimumSize());
		txtIntegerField.setMaximumSize(LTParameters.getInstance().getDimensionComponentMaximumSize());
		txtIntegerField.setBorder(LTParameters.getInstance().getBorderComponent());
		txtIntegerField.addFocusListener(this);
		
		objTextFieldPanel = new TextFieldPanel(txtIntegerField, strLabel);
		
		setLayout(new MigLayout("insets 0", "[grow]", "[grow]"));
		add(objTextFieldPanel, "cell 0 0, grow");
		
		setIntegerProperties();
    }
	
	/**
	 * Estabelece as propriedades do INTEGER.
	 */
	private void setIntegerProperties() {
		txtIntegerField.setEnabled(getEnabled());
		txtIntegerField.setFont(LTParameters.getInstance().getFontComponentTextField());
		txtIntegerField.setForeground(LTParameters.getInstance().getColorComponentForeground());
		txtIntegerField.setBackground(LTParameters.getInstance().getColorComponentBackground());
		
		if (!getEnabled()) {
			txtIntegerField.setBackground(LTParameters.getInstance().getColorComponentBackgroundDisabled());
			objTextFieldPanel.setAlertVisible(false);
		}
	}
	
	@Override
	public boolean getIsMandatoryFieldEmpty() {
		if ((txtIntegerField.getText() == null || txtIntegerField.getText().length() == 0) && getMandatoryField()) {
			objTextFieldPanel.setAlertVisible(true);
			return true;
		} else {
			objTextFieldPanel.setAlertVisible(false);
			return false;
		}
	}
	
	@Override
	public Object getValue() {
		return txtIntegerField.getValue();
	}
	
	@Override
	public void setValue(Object objValue) {
		txtIntegerField.setValue(objValue);
	}
	
	@Override
	public void setEnabled(boolean blnEnabled) {
		super.setEnabled(blnEnabled);
		
		setIntegerProperties();
	}
	
	@Override
	public void setFocus() {
		txtIntegerField.requestFocus();
	}

	@Override
	public void addActionListener(ActionListener actionListener) {
		txtIntegerField.addActionListener(actionListener);
	}
	
	@Override
	public void addKeyListener(KeyListener keyListener) {
		txtIntegerField.addKeyListener(keyListener);
	}
	
	@Override
	public void addFocusListener(FocusListener focusListener) {
		txtIntegerField.addFocusListener(focusListener);
	}

	// *********************************************************************
	// Implementa FocusListener
	@Override
	public void focusGained(FocusEvent event) {
		txtIntegerField.setBackground(LTParameters.getInstance().getColorComponentBackgroundFocus());
		txtIntegerField.select(0, txtIntegerField.getText().length());
	}

	@Override
	public void focusLost(FocusEvent event) {
		txtIntegerField.setBackground(LTParameters.getInstance().getColorComponentBackground());
		
		if (getEnabled()) {
			if (getIsMandatoryFieldEmpty()) {
				objTextFieldPanel.setAlertVisible(true);
			} else {
				objTextFieldPanel.setAlertVisible(false);
				setValue(txtIntegerField.getText());
			}
		} else {
			txtIntegerField.setBackground(LTParameters.getInstance().getColorComponentBackgroundDisabled());
			objTextFieldPanel.setAlertVisible(false);
		}
	}
}