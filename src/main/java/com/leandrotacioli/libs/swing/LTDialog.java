package com.leandrotacioli.libs.swing;

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;

/**
 * Cria uma extensão para <i>JDialog</i>.
 * 
 * Permite a escolha da tela ser fechada
 * pressionando o botão ESC.
 * 
 * @author Leandro Tacioli
 * @version 1.1 - 08/Out/2014
 */
public class LTDialog extends JDialog {
	private static final long serialVersionUID = -7947343252579970101L;
	
	private boolean blnAllowClosingByPressingEsc;
	
	public LTDialog(boolean blnAllowClosingByPressingEsc) {
		this.blnAllowClosingByPressingEsc = blnAllowClosingByPressingEsc;
	}
	
	public LTDialog(Frame owner, String strTitle, boolean blnAllowClosingByPressingEsc) {
		super(owner, strTitle);
		
		this.blnAllowClosingByPressingEsc = blnAllowClosingByPressingEsc;
	}
	
	public LTDialog(Dialog owner, String strTitle, boolean blnAllowClosingByPressingEsc) {
		super(owner, strTitle);
		
		this.blnAllowClosingByPressingEsc = blnAllowClosingByPressingEsc;
	}
	
	public LTDialog(Window owner, String strTitle, boolean blnAllowClosingByPressingEsc) {
		super(owner, strTitle);
		
		this.blnAllowClosingByPressingEsc = blnAllowClosingByPressingEsc;
	}

	protected JRootPane createRootPane() {
		JRootPane rootPane = new JRootPane();
		
	    KeyStroke stroke = KeyStroke.getKeyStroke("ESCAPE");
	    
	    Action actionListener = new AbstractAction() {
			private static final long serialVersionUID = 36309521709495007L;

			public void actionPerformed(ActionEvent actionEvent) {
	    		if (blnAllowClosingByPressingEsc) {
	    			setVisible(false);
	    		}
	    	}
	    };
	  
	    InputMap inputMap = rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
	    inputMap.put(stroke, "ESCAPE");
	    rootPane.getActionMap().put("ESCAPE", actionListener);
		
	    return rootPane;
	}
}