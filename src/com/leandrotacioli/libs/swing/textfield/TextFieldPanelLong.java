package com.leandrotacioli.libs.swing.textfield;

import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyListener;

import net.miginfocom.swing.MigLayout;

import com.leandrotacioli.libs.LTParameters;
import com.leandrotacioli.libs.swing.textfield.ltlong.TextFieldLong;

/**
 * 
 * @author Leandro Tacioli
 * @version 1.1 - 02/Mai/2016
 */
public class TextFieldPanelLong extends TextField implements FocusListener {
	private static final long serialVersionUID = 3971201519880334285L;

	private TextFieldPanel objTextFieldPanel;
	
	private TextFieldLong txtLongField;

	/**
	 * 
	 * @param strLabel
	 * @param blnEnabled
	 * @param blnMandatoryField
	 */
	protected TextFieldPanelLong(String strLabel, boolean blnEnabled, boolean blnMandatoryField) {
		super(strLabel, blnEnabled, blnMandatoryField);
		
		txtLongField = new TextFieldLong();
		txtLongField.setMinimumSize(LTParameters.getInstance().getDimensionComponentMinimumSize());
		txtLongField.setMaximumSize(LTParameters.getInstance().getDimensionComponentMaximumSize());
		txtLongField.setBorder(LTParameters.getInstance().getBorderComponent());
		txtLongField.addFocusListener(this);
		
		objTextFieldPanel = new TextFieldPanel(txtLongField, strLabel);
		
		setLayout(new MigLayout("insets 0", "[grow]", "[grow]"));
		add(objTextFieldPanel, "cell 0 0, grow");
		
		setLongProperties();
    }
	
	/**
	 * Estabelece as propriedades do LONG.
	 */
	private void setLongProperties() {
		txtLongField.setEnabled(getEnabled());
		txtLongField.setFont(LTParameters.getInstance().getFontComponentTextField());
		txtLongField.setForeground(LTParameters.getInstance().getColorComponentForeground());
		txtLongField.setBackground(LTParameters.getInstance().getColorComponentBackground());
		
		if (!getEnabled()) {
			txtLongField.setBackground(LTParameters.getInstance().getColorComponentBackgroundDisabled());
			objTextFieldPanel.setAlertVisible(false);
		}
	}
	
	@Override
	public void setLabel(String strLabel) {
		objTextFieldPanel.setLabel(strLabel);
	}
	
	@Override
	public boolean getIsMandatoryFieldEmpty() {
		if ((txtLongField.getText() == null || txtLongField.getText().length() == 0) && getMandatoryField()) {
			objTextFieldPanel.setAlertVisible(true);
			return true;
		} else {
			objTextFieldPanel.setAlertVisible(false);
			return false;
		}
	}
	
	@Override
	public Object getValue() {
		return txtLongField.getValue();
	}
	
	@Override
	public void setValue(Object objValue) {
		txtLongField.setValue(objValue);
	}
	
	@Override
	public void setEnabled(boolean blnEnabled) {
		super.setEnabled(blnEnabled);
		
		setLongProperties();
	}
	
	@Override
	public void setFocus() {
		txtLongField.requestFocus();
	}

	@Override
	public void addActionListener(ActionListener actionListener) {
		txtLongField.addActionListener(actionListener);
	}
	
	@Override
	public void addKeyListener(KeyListener keyListener) {
		txtLongField.addKeyListener(keyListener);
	}
	
	@Override
	public void addFocusListener(FocusListener focusListener) {
		txtLongField.addFocusListener(focusListener);
	}

	// *********************************************************************
	// Implementa FocusListener
	@Override
	public void focusGained(FocusEvent event) {
		txtLongField.setBackground(LTParameters.getInstance().getColorComponentBackgroundFocus());
		txtLongField.select(0, txtLongField.getText().length());
	}

	@Override
	public void focusLost(FocusEvent event) {
		txtLongField.setBackground(LTParameters.getInstance().getColorComponentBackground());
		
		if (getEnabled()) {
			if (getIsMandatoryFieldEmpty()) {
				objTextFieldPanel.setAlertVisible(true);
			} else {
				objTextFieldPanel.setAlertVisible(false);
				setValue(txtLongField.getText());
			}
		} else {
			txtLongField.setBackground(LTParameters.getInstance().getColorComponentBackgroundDisabled());
			objTextFieldPanel.setAlertVisible(false);
		}
	}
}