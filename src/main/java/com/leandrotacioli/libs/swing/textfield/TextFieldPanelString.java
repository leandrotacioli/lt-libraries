package com.leandrotacioli.libs.swing.textfield;

import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyListener;

import com.leandrotacioli.libs.swing.LTSwing;
import net.miginfocom.swing.MigLayout;

import com.leandrotacioli.libs.swing.textfield.ltstring.TextFieldString;

/**
 * 
 * @author Leandro Tacioli
 */
public class TextFieldPanelString extends TextField implements FocusListener {

	private TextFieldPanel objTextFieldPanel;
	
	private TextFieldString txtStringField;

	/**
	 * 
	 * @param strLabel
	 * @param blnEnabled
	 * @param blnMandatoryField
	 */
	protected TextFieldPanelString(String strLabel, boolean blnEnabled, boolean blnMandatoryField) {
		super(strLabel, blnEnabled, blnMandatoryField);
		
		txtStringField = new TextFieldString();
		txtStringField.setMinimumSize(LTSwing.getInstance().getDimensionComponentMinimumSize());
		txtStringField.setMaximumSize(LTSwing.getInstance().getDimensionComponentMaximumSize());
		txtStringField.setBorder(LTSwing.getInstance().getBorderComponent());
		txtStringField.setFont(LTSwing.getInstance().getFontComponentTextField());
		txtStringField.addFocusListener(this);
		
		objTextFieldPanel = new TextFieldPanel(txtStringField, strLabel);
		
		setLayout(new MigLayout("insets 0", "[grow]", "[grow]"));
		add(objTextFieldPanel, "cell 0 0, grow");
		
		setStringProperties();
	}
	
	/**
	 * Estabelece as propriedades da STRING.
	 */
	private void setStringProperties() {
		txtStringField.setEnabled(getEnabled());
		txtStringField.setForeground(LTSwing.getInstance().getColorComponentForeground());
		txtStringField.setBackground(LTSwing.getInstance().getColorComponentBackground());
		
		if (!getEnabled()) {
			txtStringField.setBackground(LTSwing.getInstance().getColorComponentBackgroundDisabled());
			objTextFieldPanel.setAlertVisible(false);
		}
	}
	
	@Override
	public void setLabel(String strLabel) {
		objTextFieldPanel.setLabel(strLabel);
	}
	
	@Override
	protected void setStringLength(int intMaximumLength) {
		txtStringField.setMaximumLength(intMaximumLength);
	}
	
	@Override
	protected void setHorizontalAlignment(int intHorizontalAlignment) {
		txtStringField.setHorizontalAlignment(intHorizontalAlignment);
	}
	
	@Override
	public boolean getIsMandatoryFieldEmpty() {
		if ((txtStringField.getText() == null || txtStringField.getText().length() == 0) && getMandatoryField()) {
			objTextFieldPanel.setAlertVisible(true);
			return true;
		} else {
			objTextFieldPanel.setAlertVisible(false);
			return false;
		}
	}
	
	@Override
	public Object getValue() {
		return txtStringField.getText();
	}
	
	@Override
	public void setValue(Object objValue) {
		String strValue = (String) objValue;
		
		if (strValue == null) {
			txtStringField.setText("");
		} else {
			txtStringField.setText(strValue.trim());
		}
		
		txtStringField.setCaretPosition(0);
	}
	
	@Override
	public void setEnabled(boolean blnEnabled) {
		super.setEnabled(blnEnabled);
		
		setStringProperties();
	}
	
	@Override
	public void setFocus() {
		txtStringField.requestFocus();
	}
	
	@Override
	public void addActionListener(ActionListener actionListener) {
		txtStringField.addActionListener(actionListener);
	}
	
	@Override
	public void addKeyListener(KeyListener keyListener) {
		txtStringField.addKeyListener(keyListener);
	}
	
	@Override
	public void addFocusListener(FocusListener focusListener) {
		txtStringField.addFocusListener(focusListener);
	}

	// *********************************************************************
	// Implementa FocusListener
	@Override
	public void focusGained(FocusEvent event) {
		if (getEnabled()) {
			txtStringField.setBackground(LTSwing.getInstance().getColorComponentBackgroundFocus());
			txtStringField.setCaretPosition(txtStringField.getText().length());
		} else {
			txtStringField.setBackground(LTSwing.getInstance().getColorComponentBackgroundDisabled());
		}
	}
	
	@Override
	public void focusLost(FocusEvent event) {
		txtStringField.setText(txtStringField.getText().trim());
		txtStringField.setBackground(LTSwing.getInstance().getColorComponentBackground());
		txtStringField.setCaretPosition(0);
		
		if (getEnabled()) {
			if (getIsMandatoryFieldEmpty()) {
				objTextFieldPanel.setAlertVisible(true);
			} else {
				objTextFieldPanel.setAlertVisible(false);
			}
		} else {
			txtStringField.setBackground(LTSwing.getInstance().getColorComponentBackgroundDisabled());
			objTextFieldPanel.setAlertVisible(false);
		}
	}
}