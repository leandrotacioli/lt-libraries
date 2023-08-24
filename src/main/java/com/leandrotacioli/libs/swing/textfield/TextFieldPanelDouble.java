package com.leandrotacioli.libs.swing.textfield;

import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyListener;

import com.leandrotacioli.libs.swing.LTSwing;
import net.miginfocom.swing.MigLayout;

import com.leandrotacioli.libs.LTParameters;
import com.leandrotacioli.libs.swing.textfield.ltdouble.TextFieldDouble;

/**
 * 
 * @author Leandro Tacioli
 */
public class TextFieldPanelDouble extends TextField implements FocusListener {

	private TextFieldPanel objTextFieldPanel;
	
	private TextFieldDouble txtDoubleField;
	
	/**
	 * 
	 * @param strLabel
	 * @param blnEnabled
	 * @param blnMandatoryField
	 */
	protected TextFieldPanelDouble(String strLabel, boolean blnEnabled, boolean blnMandatoryField) {
		super(strLabel, blnEnabled, blnMandatoryField);
		
		txtDoubleField = new TextFieldDouble();
		txtDoubleField.setMinimumSize(LTSwing.getInstance().getDimensionComponentMinimumSize());
		txtDoubleField.setMaximumSize(LTSwing.getInstance().getDimensionComponentMaximumSize());
		txtDoubleField.setBorder(LTSwing.getInstance().getBorderComponent());
		txtDoubleField.setFont(LTSwing.getInstance().getFontComponentTextField());
		txtDoubleField.addFocusListener(this);
		
		objTextFieldPanel = new TextFieldPanel(txtDoubleField, strLabel);
		
		setLayout(new MigLayout("insets 0", "[grow]", "[grow]"));
		add(objTextFieldPanel, "cell 0 0, grow");
		
		setDoubleProperties();
    }
	
	/**
	 * Estabelece as propriedades do DOUBLE.
	 */
	private void setDoubleProperties() {
		txtDoubleField.setEnabled(getEnabled());
		txtDoubleField.setForeground(LTSwing.getInstance().getColorComponentForeground());
		txtDoubleField.setBackground(LTSwing.getInstance().getColorComponentBackground());
		
		if (!getEnabled()) {
			txtDoubleField.setBackground(LTSwing.getInstance().getColorComponentBackgroundDisabled());
			objTextFieldPanel.setAlertVisible(false);
		}
	}
	
	@Override
	public void setLabel(String strLabel) {
		objTextFieldPanel.setLabel(strLabel);
	}
	
	@Override
	protected void setFractionDigits(int intFractionDigits) {
		txtDoubleField.setFractionDigits(intFractionDigits);
	}
	
	@Override
	protected void setShowAsPercentage(boolean blnShowAsPercentage) {
		txtDoubleField.setShowAsPercentage(blnShowAsPercentage);
	}
	
	@Override
	public boolean getIsMandatoryFieldEmpty() {
		if ((txtDoubleField.getText() == null || txtDoubleField.getText().length() == 0) && getMandatoryField()) {
			objTextFieldPanel.setAlertVisible(true);
			return true;
		} else {
			objTextFieldPanel.setAlertVisible(false);
			return false;
		}
	}
	
	@Override
	public Object getValue() {
		return txtDoubleField.getValue();
	}
	
	@Override
	public void setValue(Object objValue) {
		txtDoubleField.setValue(objValue);
	}
	
	@Override
	public void setEnabled(boolean blnEnabled) {
		super.setEnabled(blnEnabled);
		
		setDoubleProperties();
	}
	
	@Override
	public void setFocus() {
		txtDoubleField.requestFocus();
	}

	@Override
	public void addActionListener(ActionListener actionListener) {
		txtDoubleField.addActionListener(actionListener);
	}
	
	@Override
	public void addKeyListener(KeyListener keyListener) {
		txtDoubleField.addKeyListener(keyListener);
	}
	
	@Override
	public void addFocusListener(FocusListener focusListener) {
		txtDoubleField.addFocusListener(focusListener);
	}

	// *********************************************************************
	// Implementa FocusListener
	@Override
	public void focusGained(FocusEvent event) {
		if (getEnabled()) {
			String strDouble = txtDoubleField.getText();
			strDouble = strDouble.replace("%", "");
			
			if (LTParameters.getInstance().getDecimalMark().equals("COMMA")) {
				strDouble = strDouble.replace(".", "");   // Retira os separadores de milhar
				strDouble = strDouble.replace(",", ".");
			} else if (LTParameters.getInstance().getDecimalMark().equals("PERIOD")) {
				strDouble = strDouble.replace(",", "");   // Retira os separadores de milhar
			}
			
			txtDoubleField.setText(strDouble);
			txtDoubleField.select(0, txtDoubleField.getText().length());
			txtDoubleField.setBackground(LTSwing.getInstance().getColorComponentBackgroundFocus());
		} else {
			txtDoubleField.setBackground(LTSwing.getInstance().getColorComponentBackgroundDisabled());
		}
	}

	@Override
	public void focusLost(FocusEvent event) {
		txtDoubleField.setBackground(LTSwing.getInstance().getColorComponentBackground());
		
		if (getEnabled()) {
			if (getIsMandatoryFieldEmpty()) {
				objTextFieldPanel.setAlertVisible(true);
			} else {
				objTextFieldPanel.setAlertVisible(false);
				setValue(txtDoubleField.getText().replace("%", ""));
			}
		} else {
			txtDoubleField.setBackground(LTSwing.getInstance().getColorComponentBackgroundDisabled());
			objTextFieldPanel.setAlertVisible(false);
		}
	}
}