package com.leandrotacioli.libs.swing.textfield;

import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyListener;

import com.leandrotacioli.libs.swing.LTSwing;
import net.miginfocom.swing.MigLayout;

import com.leandrotacioli.libs.swing.textfield.ltdate.TextFieldDate;

/**
 * 
 * @author Leandro Tacioli
 */
public class TextFieldPanelDate extends TextField implements FocusListener {

	private TextFieldPanel objTextFieldPanel;
	
	private TextFieldDate txtDateField;

	/**
	 * 
	 * @param strLabel
	 * @param blnEnabled
	 * @param blnMandatoryField
	 */
	protected TextFieldPanelDate(String strLabel, boolean blnEnabled, boolean blnMandatoryField) {
		super(strLabel, blnEnabled, blnMandatoryField);
		
		txtDateField = new TextFieldDate();
		txtDateField.setMinimumSize(LTSwing.getInstance().getDimensionComponentMinimumSize());
		txtDateField.setMaximumSize(LTSwing.getInstance().getDimensionComponentMaximumSize());
		txtDateField.setBorder(LTSwing.getInstance().getBorderComponent());
		txtDateField.setFont(LTSwing.getInstance().getFontComponentTextField());
		txtDateField.addFocusListener(this);
		
		objTextFieldPanel = new TextFieldPanel(txtDateField, strLabel);
		
		setLayout(new MigLayout("insets 0", "[grow]", "[grow]"));
		add(objTextFieldPanel, "cell 0 0, grow");
		
		setDateProperties();
	}
	
	/**
	 * Estabelece as propriedades de DATE.
	 */
	private void setDateProperties() {
		txtDateField.setEnabled(getEnabled());
		txtDateField.setForeground(LTSwing.getInstance().getColorComponentForeground());
		txtDateField.setBackground(LTSwing.getInstance().getColorComponentBackground());
		
		if (!getEnabled()) {
			txtDateField.setBackground(LTSwing.getInstance().getColorComponentBackgroundDisabled());
			objTextFieldPanel.setAlertVisible(false);
		}
	}
	
	@Override
	public void setLabel(String strLabel) {
		objTextFieldPanel.setLabel(strLabel);
	}
	
	@Override
	public boolean getIsMandatoryFieldEmpty() {
		if ((txtDateField.getDate() == null || txtDateField.getDate().length() == 0) && getMandatoryField()) {
			objTextFieldPanel.setAlertVisible(true);
			return true;
		} else {
			objTextFieldPanel.setAlertVisible(false);
			return false;
		}
	}
	
	@Override
	public Object getValue() {
		return txtDateField.getValue();
	}
	
	@Override
	public void setValue(Object objValue) {
		txtDateField.setValue(objValue);
	}
	
	@Override
	public void setEnabled(boolean blnEnabled) {
		super.setEnabled(blnEnabled);
		
		setDateProperties();
	}
	
	@Override
	public void setFocus() {
		txtDateField.requestFocus();
	}

	@Override
	public void addActionListener(ActionListener actionListener) {
		txtDateField.addActionListener(actionListener);
	}
	
	@Override
	public void addKeyListener(KeyListener keyListener) {
		txtDateField.addKeyListener(keyListener);
	}
	
	@Override
	public void addFocusListener(FocusListener focusListener) {
		txtDateField.addFocusListener(focusListener);
	}

	// *********************************************************************
	// Implementa FocusListener
	@Override
	public void focusGained(FocusEvent event) {
		if (getEnabled()) {
			txtDateField.setBackground(LTSwing.getInstance().getColorComponentBackgroundFocus());
			
			if ((txtDateField.getDate() == null || txtDateField.getDate().length() == 0)) {
				txtDateField.setDateFormat();
			}
		} else {
			txtDateField.setBackground(LTSwing.getInstance().getColorComponentBackgroundDisabled());
		}
	}

	@Override
	public void focusLost(FocusEvent event) {
		if (txtDateField.getDate() == null || txtDateField.getDate().length() == 0) {
			txtDateField.setFormatterFactory(null);
			txtDateField.setInputVerifier(null);
			txtDateField.setText(txtDateField.getDate());
		}
		
		txtDateField.setBackground(LTSwing.getInstance().getColorComponentBackground());
		
		if (getEnabled()) {
			if (getIsMandatoryFieldEmpty()) {
				txtDateField.setCaretPosition(0);
				objTextFieldPanel.setAlertVisible(true);
			} else {
				txtDateField.setCaretPosition(txtDateField.getText().length());
				objTextFieldPanel.setAlertVisible(false);
			}
		} else {
			txtDateField.setBackground(LTSwing.getInstance().getColorComponentBackgroundDisabled());
			objTextFieldPanel.setAlertVisible(false);
		}
	}
}