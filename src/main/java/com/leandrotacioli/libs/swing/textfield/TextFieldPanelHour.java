package com.leandrotacioli.libs.swing.textfield;

import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyListener;

import com.leandrotacioli.libs.swing.LTSwing;
import net.miginfocom.swing.MigLayout;

import com.leandrotacioli.libs.swing.textfield.lthour.TextFieldHour;

/**
 * 
 * @author Leandro Tacioli
 * @version 1.0 - 09/Jun/2020
 */
public class TextFieldPanelHour extends TextField implements FocusListener {
	private static final long serialVersionUID = -8051708775213719117L;

	private TextFieldPanel objTextFieldPanel;
	
	private TextFieldHour txtHourField;

	/**
	 * 
	 * @param strLabel
	 * @param blnEnabled
	 * @param blnMandatoryField
	 */
	protected TextFieldPanelHour(String strLabel, boolean blnEnabled, boolean blnMandatoryField) {
		super(strLabel, blnEnabled, blnMandatoryField);
		
		txtHourField = new TextFieldHour();
		txtHourField.setBorder(LTSwing.getInstance().getBorderComponent());
		txtHourField.setFont(LTSwing.getInstance().getFontComponentTextField());
		txtHourField.addFocusListener(this);
		
		objTextFieldPanel = new TextFieldPanel(txtHourField, strLabel);
		
		setLayout(new MigLayout("insets 0", "[grow]", "[grow]"));
		add(objTextFieldPanel, "cell 0 0, grow");
		
		setHourProperties();
	}
	
	/**
	 * Estabelece as propriedades da STRING.
	 */
	private void setHourProperties() {
		txtHourField.setEnabled(getEnabled());
		txtHourField.setForeground(LTSwing.getInstance().getColorComponentForeground());
		txtHourField.setBackground(LTSwing.getInstance().getColorComponentBackground());
		
		if (!getEnabled()) {
			txtHourField.setBackground(LTSwing.getInstance().getColorComponentBackgroundDisabled());
			objTextFieldPanel.setAlertVisible(false);
		}
	}
	
	@Override
	public void setLabel(String strLabel) {
		objTextFieldPanel.setLabel(strLabel);
	}
	
	@Override
	public boolean getIsMandatoryFieldEmpty() {
		if (txtHourField.getValue() == null && getMandatoryField()) {
			objTextFieldPanel.setAlertVisible(true);
			return true;
		} else {
			objTextFieldPanel.setAlertVisible(false);
			return false;
		}
	}
	
	@Override
	public Object getValue() {
		return txtHourField.getText();
	}
	
	@Override
	public void setValue(Object objValue) {
		String strValue = (String) objValue;
		
		if (strValue == null) {
			txtHourField.setFormatterFactory(null);
			txtHourField.setInputVerifier(null);
			txtHourField.setText("");
		
		} else {
			txtHourField.setValue(strValue);
		}
	}
	
	@Override
	public void setEnabled(boolean blnEnabled) {
		super.setEnabled(blnEnabled);
		
		setHourProperties();
	}
	
	@Override
	public void setFocus() {
		txtHourField.requestFocus();
	}
	
	@Override
	public void addActionListener(ActionListener actionListener) {
		txtHourField.addActionListener(actionListener);
	}
	
	@Override
	public void addKeyListener(KeyListener keyListener) {
		txtHourField.addKeyListener(keyListener);
	}
	
	@Override
	public void addFocusListener(FocusListener focusListener) {
		txtHourField.addFocusListener(focusListener);
	}

	// *********************************************************************
	// Implementa FocusListener
	@Override
	public void focusGained(FocusEvent event) {
		if (getEnabled()) {
			txtHourField.setBackground(LTSwing.getInstance().getColorComponentBackgroundFocus());
			txtHourField.setHourFormat();
		} else {
			txtHourField.setBackground(LTSwing.getInstance().getColorComponentBackgroundDisabled());
		}
	}
	
	@Override
	public void focusLost(FocusEvent event) {
		if (txtHourField.getHour() == null || txtHourField.getHour().length() == 0) {
			txtHourField.setFormatterFactory(null);
			txtHourField.setInputVerifier(null);
			txtHourField.setText(txtHourField.getHour());
		}
		
		txtHourField.setBackground(LTSwing.getInstance().getColorComponentBackground());
		
		if (getEnabled()) {
			if (getIsMandatoryFieldEmpty()) {
				objTextFieldPanel.setAlertVisible(true);
			} else {
				objTextFieldPanel.setAlertVisible(false);
			}
		} else {
			txtHourField.setBackground(LTSwing.getInstance().getColorComponentBackgroundDisabled());
			objTextFieldPanel.setAlertVisible(false);
		}
	}
}