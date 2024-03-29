package com.leandrotacioli.libs.swing.textfield;

import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.leandrotacioli.libs.swing.LTSwing;
import net.miginfocom.swing.MigLayout;

/**
 * 
 * @author Leandro Tacioli
 */
public class TextFieldPanelText extends TextField implements FocusListener, KeyListener {

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
		textArea.setFont(LTSwing.getInstance().getFontComponentTextField());
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.addFocusListener(this);
		textArea.addKeyListener(this);
		
		txtTextField = new JScrollPane();
		txtTextField.setMinimumSize(LTSwing.getInstance().getDimensionComponentMinimumSize());
		txtTextField.setBorder(LTSwing.getInstance().getBorderComponent());
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
		textArea.setForeground(LTSwing.getInstance().getColorComponentForeground());
		textArea.setBackground(LTSwing.getInstance().getColorComponentBackground());
		txtTextField.setBackground(LTSwing.getInstance().getColorComponentBackground());
		
		if (!getEnabled()) {
			textArea.setForeground(LTSwing.getInstance().getColorComponentBackgroundDisabled());
			textArea.setBackground(LTSwing.getInstance().getColorComponentBackgroundDisabled());
			txtTextField.setBackground(LTSwing.getInstance().getColorComponentBackgroundDisabled());
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
			textArea.setCaretPosition(textArea.getText().length());
			textArea.setBackground(LTSwing.getInstance().getColorComponentBackgroundFocus());
			txtTextField.setBackground(LTSwing.getInstance().getColorComponentBackgroundFocus());
		} else {
			textArea.setForeground(LTSwing.getInstance().getColorComponentBackgroundDisabled());
			textArea.setBackground(LTSwing.getInstance().getColorComponentBackgroundDisabled());
			txtTextField.setBackground(LTSwing.getInstance().getColorComponentBackgroundDisabled());
		}
	}

	@Override
	public void focusLost(FocusEvent event) {
		textArea.setText(textArea.getText().trim());
		textArea.setBackground(LTSwing.getInstance().getColorComponentBackground());
		txtTextField.setBackground(LTSwing.getInstance().getColorComponentBackground());
		
		if (getEnabled()) {
			if (getIsMandatoryFieldEmpty()) {
				objTextFieldPanel.setAlertVisible(true);
			} else {
				objTextFieldPanel.setAlertVisible(false);
			}
		} else {
			textArea.setForeground(LTSwing.getInstance().getColorComponentBackgroundDisabled());
			textArea.setBackground(LTSwing.getInstance().getColorComponentBackgroundDisabled());
			txtTextField.setBackground(LTSwing.getInstance().getColorComponentBackgroundDisabled());
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