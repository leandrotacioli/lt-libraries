package com.leandrotacioli.libs.swing.textfield;

import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.awt.event.ItemListener;
import java.awt.event.KeyListener;

import javax.swing.JCheckBox;

import com.leandrotacioli.libs.swing.LTSwing;
import net.miginfocom.swing.MigLayout;

/**
 * Campo Boolean.
 * 
 * @author Leandro Tacioli
 * @version 1.2 - 12/Set/2016
 */
public class TextFieldPanelBoolean extends TextField {
	private static final long serialVersionUID = 7545050499375148923L;
	
	private TextFieldPanel objTextFieldPanel;
	
	private JCheckBox txtBooleanField;

	/**
	 * Campo Boolean.
	 * 
	 * @param strLabel
	 * @param blnEnabled
	 * @param blnMandatoryField
	 */
	protected TextFieldPanelBoolean(String strLabel, boolean blnEnabled, boolean blnMandatoryField) {
		super(strLabel, blnEnabled, blnMandatoryField);
		
		txtBooleanField = new JCheckBox();
		txtBooleanField.setEnabled(blnEnabled);
		txtBooleanField.setMinimumSize(LTSwing.getInstance().getDimensionComponentMinimumSize());
		txtBooleanField.setMaximumSize(LTSwing.getInstance().getDimensionComponentMaximumSize());
		//txtBooleanField.setBorder(LTParametersSwing.getInstance().getBorderComponent());
		//txtBooleanField.setBorderPainted(true);
		txtBooleanField.setText(strLabel);
		txtBooleanField.setFont(LTSwing.getInstance().getFontComponentLabel());
		
		objTextFieldPanel = new TextFieldPanel(txtBooleanField, strLabel);
		
		setLayout(new MigLayout("insets 0", "[grow]", "[grow]"));
		add(objTextFieldPanel, "cell 0 0, grow");
		
		setBooleanProperties();
	}
	
	/**
	 * Estabelece as propriedades do BOOLEAN.
	 */
	private void setBooleanProperties() {
		txtBooleanField.setEnabled(getEnabled());
		txtBooleanField.setForeground(LTSwing.getInstance().getColorComponentForeground());
		txtBooleanField.setBackground(LTSwing.getInstance().getColorComponentPanelBackground());
	}
	
	@Override
	public void setLabel(String strLabel) {
		txtBooleanField.setText(strLabel);
	}
	
	@Override
	public Object getValue() {
		return txtBooleanField.isSelected();
	}
	
	@Override
	public void setValue(Object objValue) {
		if (objValue == Boolean.FALSE || objValue == null || objValue.equals("0")) {
			txtBooleanField.setSelected(false);
		} else {
			txtBooleanField.setSelected(true);
		}
	}
	
	@Override
	public void setEnabled(boolean blnEnabled) {
		super.setEnabled(blnEnabled);
		
		setBooleanProperties();
	}
	
	@Override
	public void setFocus() {
		txtBooleanField.requestFocus();
	}

	@Override
	public void addActionListener(ActionListener actionListener) {
		txtBooleanField.addActionListener(actionListener);
	}
	
	@Override
	public void addKeyListener(KeyListener keyListener) {
		txtBooleanField.addKeyListener(keyListener);
	}
	
	@Override
	public void addFocusListener(FocusListener focusListener) {
		txtBooleanField.addFocusListener(focusListener);
	}
	
	@Override
	public void addItemListener(ItemListener itemListener) {
		txtBooleanField.addItemListener(itemListener);
	}
}