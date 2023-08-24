package com.leandrotacioli.libs.swing.textfield;

import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyListener;

import com.leandrotacioli.libs.swing.LTSwing;
import net.miginfocom.swing.MigLayout;

import com.leandrotacioli.libs.swing.textfield.lttime.TextFieldTime;

/**
 * 
 * @author Leandro Tacioli
 */
public class TextFieldPanelTime extends TextField implements FocusListener {

	private TextFieldPanel objTextFieldPanel;
	
	private TextFieldTime txtTimeField;

	/**
	 * 
	 * @param strLabel
	 * @param blnEnabled
	 * @param blnMandatoryField
	 */
	protected TextFieldPanelTime(String strLabel, boolean blnEnabled, boolean blnMandatoryField) {
		super(strLabel, blnEnabled, blnMandatoryField);

		txtTimeField = new TextFieldTime();
		txtTimeField.setBorder(LTSwing.getInstance().getBorderComponent());
		txtTimeField.setFont(LTSwing.getInstance().getFontComponentTextField());
		txtTimeField.addFocusListener(this);
		
		objTextFieldPanel = new TextFieldPanel(txtTimeField, strLabel);
		
		setLayout(new MigLayout("insets 0", "[grow]", "[grow]"));
		add(objTextFieldPanel, "cell 0 0, grow");
		
		setTimeProperties();
	}
	
	/**
	 * Estabelece as propriedades da STRING.
	 */
	private void setTimeProperties() {
		txtTimeField.setEnabled(getEnabled());
		txtTimeField.setForeground(LTSwing.getInstance().getColorComponentForeground());
		txtTimeField.setBackground(LTSwing.getInstance().getColorComponentBackground());
		
		if (!getEnabled()) {
			txtTimeField.setBackground(LTSwing.getInstance().getColorComponentBackgroundDisabled());
			objTextFieldPanel.setAlertVisible(false);
		}
	}
	
	@Override
	public void setLabel(String strLabel) {
		objTextFieldPanel.setLabel(strLabel);
	}
	
	@Override
	public boolean getIsMandatoryFieldEmpty() {
		if (txtTimeField.getValue() == null && getMandatoryField()) {
			objTextFieldPanel.setAlertVisible(true);
			return true;
		} else {
			objTextFieldPanel.setAlertVisible(false);
			return false;
		}
	}
	
	@Override
	public Object getValue() {
		return txtTimeField.getText();
	}
	
	@Override
	public void setValue(Object objValue) {
		String strValue = (String) objValue;
		
		if (strValue == null) {
			txtTimeField.setFormatterFactory(null);
			txtTimeField.setInputVerifier(null);
			txtTimeField.setText("");
		
		} else {
			txtTimeField.setValue(strValue);
		}
	}
	
	@Override
	public void setEnabled(boolean blnEnabled) {
		super.setEnabled(blnEnabled);
		
		setTimeProperties();
	}
	
	@Override
	public void setFocus() {
		txtTimeField.requestFocus();
	}
	
	@Override
	public void addActionListener(ActionListener actionListener) {
		txtTimeField.addActionListener(actionListener);
	}
	
	@Override
	public void addKeyListener(KeyListener keyListener) {
		txtTimeField.addKeyListener(keyListener);
	}
	
	@Override
	public void addFocusListener(FocusListener focusListener) {
		txtTimeField.addFocusListener(focusListener);
	}

	// *********************************************************************
	// Implementa FocusListener
	@Override
	public void focusGained(FocusEvent event) {
		if (getEnabled()) {
			txtTimeField.setBackground(LTSwing.getInstance().getColorComponentBackgroundFocus());
			txtTimeField.setTimeFormat();
		} else {
			txtTimeField.setBackground(LTSwing.getInstance().getColorComponentBackgroundDisabled());
		}
	}
	
	@Override
	public void focusLost(FocusEvent event) {
		if (txtTimeField.getTime() == null || txtTimeField.getTime().length() == 0) {
			txtTimeField.setFormatterFactory(null);
			txtTimeField.setInputVerifier(null);
			txtTimeField.setText(txtTimeField.getTime());
		}

		txtTimeField.setBackground(LTSwing.getInstance().getColorComponentBackground());
		
		if (getEnabled()) {
			if (getIsMandatoryFieldEmpty()) {
				objTextFieldPanel.setAlertVisible(true);
			} else {
				objTextFieldPanel.setAlertVisible(false);
			}
		} else {
			txtTimeField.setBackground(LTSwing.getInstance().getColorComponentBackgroundDisabled());
			objTextFieldPanel.setAlertVisible(false);
		}
	}
}