package com.leandrotacioli.libs.swing.textfield;

import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyListener;

import net.miginfocom.swing.MigLayout;

import com.leandrotacioli.libs.LTParameters;
import com.leandrotacioli.libs.swing.textfield.ltdate.TextFieldDate;

/**
 * 
 * @author Leandro Tacioli
 * @version 1.1 - 02/Mai/2016
 */
public class TextFieldPanelDate extends TextField implements FocusListener {
	private static final long serialVersionUID = -6513521813043168930L;
	
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
		txtDateField.setMinimumSize(LTParameters.getInstance().getDimensionComponentMinimumSize());
		txtDateField.setMaximumSize(LTParameters.getInstance().getDimensionComponentMaximumSize());
		txtDateField.setBorder(LTParameters.getInstance().getBorderComponent());
		txtDateField.setFont(LTParameters.getInstance().getFontComponentTextField());
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
		txtDateField.setForeground(LTParameters.getInstance().getColorComponentForeground());
		txtDateField.setBackground(LTParameters.getInstance().getColorComponentBackground());
		
		if (!getEnabled()) {
			txtDateField.setBackground(LTParameters.getInstance().getColorComponentBackgroundDisabled());
			objTextFieldPanel.setAlertVisible(false);
		}
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
		
		objTextFieldPanel.setAlertVisible(false);
		
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
		txtDateField.setBackground(LTParameters.getInstance().getColorComponentBackgroundFocus());
		
		if ((txtDateField.getDate() == null || txtDateField.getDate().length() == 0)) {
			txtDateField.setDateFormat();
		//} else {
		//	txtDateField.select(0, txtDateField.getText().length());
		}
	}

	@Override
	public void focusLost(FocusEvent event) {
		if (txtDateField.getDate() == null || txtDateField.getDate().length() == 0) {
			txtDateField.setFormatterFactory(null);
			txtDateField.setInputVerifier(null);
			txtDateField.setText(txtDateField.getDate());
		}
		
		txtDateField.setBackground(LTParameters.getInstance().getColorComponentBackground());
		
		if (getEnabled()) {
			if (getIsMandatoryFieldEmpty()) {
				txtDateField.setCaretPosition(0);
				objTextFieldPanel.setAlertVisible(true);
			} else {
				txtDateField.setCaretPosition(txtDateField.getText().length());
				objTextFieldPanel.setAlertVisible(false);
			}
		} else {
			txtDateField.setBackground(LTParameters.getInstance().getColorComponentBackgroundDisabled());
			objTextFieldPanel.setAlertVisible(false);
		}
		
		//objTextFieldPanel.setBackground(new Color(50, 50, 50, 50));
		
		//txtDateField.revalidate();
		//objTextFieldPanel.revalidate();
	}
}