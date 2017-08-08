package com.leandrotacioli.libs.swing.textfield;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import com.leandrotacioli.libs.LTParameters;

/**
 * 
 * @author Leandro Tacioli
 * @version 1.0 - 05/Abr/2015
 */
public class TextFieldPanel extends JPanel {
	private static final long serialVersionUID = 3500342282829187859L;
	
	private JLabel lblAlert;
	
	/**
	 * 
	 * @param strLabel
	 * @param component
	 */
	public TextFieldPanel(Component component, String strLabel) {
		// Campo não é Boolean
		if (!(component instanceof JCheckBox)) {
			JLabel lblLabel = new JLabel(strLabel);
			lblLabel.setFont(LTParameters.getInstance().getFontComponentLabel());
			
			ImageIcon iconAlert = new ImageIcon("res/images/alert.png");
			lblAlert = new JLabel(iconAlert, JLabel.CENTER);
			lblAlert.setToolTipText(LTParameters.getInstance().getBundle().getString("text_field_panel_mandatory_field"));
			lblAlert.setVisible(false);
			
			this.setLayout(new MigLayout("insets 0", "[grow]", "[] 0 [grow]"));
			this.setBackground(LTParameters.getInstance().getColorComponentPanelBackground());
			
			this.add(lblLabel, "cell 0 0, grow");
			this.add(component, "cell 0 1, grow");
			this.add(lblAlert, "cell 0 1, gap 0, hidemode 3");
			
		// Campo Boolean
		} else {
			this.setLayout(new MigLayout("insets 0", "[grow]", "[grow]"));
			this.setBackground(LTParameters.getInstance().getColorComponentPanelBackground());
			
			this.add(component, "cell 0 0, grow");
		}
	}
	
	/**
	 * 
	 * @param blnVisible
	 */
	protected void setAlertVisible(boolean blnVisible) {
		if (lblAlert != null) {
			lblAlert.setVisible(blnVisible);
		}
	}
}