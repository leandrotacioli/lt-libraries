package com.leandrotacioli.libs.swing;

import com.leandrotacioli.libs.LTParameters;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RenderingHints;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

/**
 * Create a JButton default for the Menu Panel
 */
public class LTMenuButton extends JButton {

	private String strLabel;
	private ImageIcon iconButton;
	private int intWidth, intHeight;
	
	// Constructor
	public LTMenuButton(String strLabel) {
		this.strLabel = strLabel;
		intWidth = getFontMetrics(getFont()).stringWidth(strLabel);
		setDefaultProperties();
	}
	
	public LTMenuButton(String strLabel, String strIcon) {
		this.strLabel = strLabel;

		// Sets button icon
		iconButton = new ImageIcon(strIcon);
	 	setIcon(iconButton);
	 	
	 	intWidth = getFontMetrics(getFont()).stringWidth(strLabel) + iconButton.getIconWidth();
		setDefaultProperties();
	}
	
	//*************************************************************************
	// Sets JButton default properties
	private void setDefaultProperties() {
		final Border originalBorder = BorderFactory.createEmptyBorder(2, 5, 2, 6);
				
		intHeight = getFontMetrics(getFont()).getHeight();
		setPreferredSize(new Dimension(intWidth + 2, intHeight + 15));
		setText(strLabel);
		setFont(LTSwing.getInstance().getFontComponentLabel());
		setHorizontalAlignment(SwingConstants.LEFT);
		setBorder(originalBorder);
		setFocusable(false);
		setContentAreaFilled(false);
	}
	
	//*************************************************************************
	@Override
    public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D)g.create();
		
		Color borderColor = new Color(120, 140, 160);
		Paint p = new GradientPaint(0, 0, new Color(110, 160, 230), 0, getHeight(), new Color(200, 220, 250));

        if (getModel().isRollover()) {
        	g.setColor(borderColor);
        	g.drawRoundRect(1, 1, getWidth() - 4, getHeight() - 4, 8, 8);
        	g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        	g2.setPaint(p);
        	g2.fillRoundRect(2, 2, getWidth() - 5, getHeight() - 5, 8, 8);
        }
        
        if (getModel().isPressed()) {
        	g.setColor(borderColor);
        	g.drawRoundRect(1, 1, getWidth() - 4, getHeight() - 4, 8, 8);
        	g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        	g2.setPaint(p);
        	g2.fillRoundRect(2, 2, getWidth() - 5, getHeight() - 5, 8, 8);
        }
        
        super.paintComponent(g);
    }
	
	//*************************************************************************
	// Creates a JLabel as a button separator
	public static JLabel insertMenuButtonSeparator() {
		JLabel label = new JLabel();
		
 		ImageIcon iconSeparator = new ImageIcon(LTParameters.getInstance().getResourcesFolder() + "images/menu_separator.png");
 		label.setIcon(iconSeparator);
 		label.setVisible(true);
 		
 		return label;
	}
	
}