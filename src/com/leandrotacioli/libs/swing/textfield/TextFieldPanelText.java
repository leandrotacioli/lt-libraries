package com.leandrotacioli.libs.swing.textfield;

import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import net.miginfocom.swing.MigLayout;

import com.leandrotacioli.libs.LTParameters;

/**
 * 
 * @author Leandro Tacioli
 * @version 1.1 - 02/Mai/2016
 */
public class TextFieldPanelText extends TextField implements FocusListener, KeyListener {
	private static final long serialVersionUID = -1135666578204973748L;
	
	private TextFieldPanel objTextFieldPanel;
	
	private JScrollPane txtTextField;
	
	private JTextArea textArea;

	/**
	 * 
	 * @param strLabel
	 * @param blnEnabled
	 * @param blnMandatoryField
	 */
	protected TextFieldPanelText(String strLabel, boolean blnEnabled, boolean blnMandatoryField) {
		super(strLabel, blnEnabled, blnMandatoryField);
		
		textArea = new JTextArea();
		textArea.setBorder(null);
		textArea.setFont(LTParameters.getInstance().getFontComponentTextField());
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.addFocusListener(this);
		textArea.addKeyListener(this);
		
		txtTextField = new JScrollPane();
		txtTextField.setMinimumSize(LTParameters.getInstance().getDimensionComponentMinimumSize());
		txtTextField.setBorder(LTParameters.getInstance().getBorderComponent());
		txtTextField.setEnabled(blnEnabled);
		txtTextField.setViewportView(textArea);
		
		objTextFieldPanel = new TextFieldPanel(txtTextField, strLabel);
		
		setLayout(new MigLayout("insets 0", "[grow]", "[grow]"));
		add(objTextFieldPanel, "cell 0 0, grow");
		
		setTextProperties();
	}
	
	/**
	 * Estabelece as propriedades da TEXT.
	 */
	private void setTextProperties() {
		textArea.setEnabled(getEnabled());
		textArea.setForeground(LTParameters.getInstance().getColorComponentForeground());
		textArea.setBackground(LTParameters.getInstance().getColorComponentBackground());
		txtTextField.setBackground(LTParameters.getInstance().getColorComponentBackground());
		
		if (!getEnabled()) {
			textArea.setForeground(LTParameters.getInstance().getColorComponentBackgroundDisabled());
			textArea.setBackground(LTParameters.getInstance().getColorComponentBackgroundDisabled());
			txtTextField.setBackground(LTParameters.getInstance().getColorComponentBackgroundDisabled());
			objTextFieldPanel.setAlertVisible(false);
		}
	}
	
	@Override
	public void setLabel(String strLabel) {
		objTextFieldPanel.setLabel(strLabel);
	}
	
	@Override
	public boolean getIsMandatoryFieldEmpty() {
		if ((textArea.getText() == null || textArea.getText().length() == 0) && getMandatoryField()) {
			objTextFieldPanel.setAlertVisible(true);
			return true;
		} else {
			objTextFieldPanel.setAlertVisible(false);
			return false;
		}
	}
	
	@Override
	public Object getValue() {
		return textArea.getText();
	}
	
	@Override
	public void setValue(Object objValue) {
		String strValue = (String) objValue;
		
		if (strValue == null) {
			textArea.setText("");
		} else {
			textArea.setText(strValue.trim());
		}
		
		textArea.setCaretPosition(0);
	}
	
	@Override
	public void setEnabled(boolean blnEnabled) {
		super.setEnabled(blnEnabled);
		
		setTextProperties();
	}
	
	@Override
	public void setFocus() {
		textArea.requestFocus();
	}
	
	@Override
	public void addActionListener(ActionListener actionListener) {
		
	}
	
	@Override
	public void addKeyListener(KeyListener keyListener) {
		textArea.addKeyListener(keyListener);
	}
	
	@Override
	public void addFocusListener(FocusListener focusListener) {
		textArea.addFocusListener(focusListener);
	}
	
	// *********************************************************************
	// Implementa FocusListener
	@Override
	public void focusGained(FocusEvent event) {
		if (getEnabled()) {
			textArea.setCaretPosition(0);
			textArea.setBackground(LTParameters.getInstance().getColorComponentBackgroundFocus());
			txtTextField.setBackground(LTParameters.getInstance().getColorComponentBackgroundFocus());
		} else {
			textArea.setForeground(LTParameters.getInstance().getColorComponentBackgroundDisabled());
			textArea.setBackground(LTParameters.getInstance().getColorComponentBackgroundDisabled());
			txtTextField.setBackground(LTParameters.getInstance().getColorComponentBackgroundDisabled());
		}
	}

	@Override
	public void focusLost(FocusEvent event) {
		textArea.setText(textArea.getText().trim());
		textArea.setBackground(LTParameters.getInstance().getColorComponentBackground());
		txtTextField.setBackground(LTParameters.getInstance().getColorComponentBackground());
		
		if (getEnabled()) {
			if (getIsMandatoryFieldEmpty()) {
				objTextFieldPanel.setAlertVisible(true);
			} else {
				objTextFieldPanel.setAlertVisible(false);
			}
		} else {
			textArea.setForeground(LTParameters.getInstance().getColorComponentBackgroundDisabled());
			textArea.setBackground(LTParameters.getInstance().getColorComponentBackgroundDisabled());
			txtTextField.setBackground(LTParameters.getInstance().getColorComponentBackgroundDisabled());
			objTextFieldPanel.setAlertVisible(false);
		}
		
		textArea.setCaretPosition(0);
	}

	// *********************************************************************
	// Implementa KeyListener
	@Override
	public void keyPressed(KeyEvent event) {
		int keyCode = event.getKeyCode();
		
		if (keyCode == KeyEvent.VK_TAB) {					
			if(event.getModifiers() > 0) {
				((Component) event.getSource()).transferFocusBackward();
			} else {
    			((Component) event.getSource()).transferFocus();
			}
			
			event.consume();
		}
	}

	@Override
	public void keyReleased(KeyEvent event) {
		
	}

	@Override
	public void keyTyped(KeyEvent event) {
		
	}
}