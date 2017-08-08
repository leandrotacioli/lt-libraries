package com.leandrotacioli.libs.swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

/**
 * Creates a default Button Panel
 */
public class LTMenuButtonPanel extends JPanel {
	private static final long serialVersionUID = 3097720929428167821L;

	// Constructor
	public LTMenuButtonPanel() {
 		ImageIcon icon = new ImageIcon("res/images/menu_start.png");
		JLabel label = new JLabel(icon, JLabel.CENTER);
		label.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
		
		setMinimumSize(new Dimension(0, 32));
		setPreferredSize(new Dimension(0, 32));
		setMaximumSize(new Dimension(10000, 32));
		setLayout(new MigLayout("insets 0 0 0 0", "[]", "[]"));
		setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));
		add(label);
	}
	
	//*************************************************************************
	// Paints the JPanel
	@Override
	protected void paintComponent(Graphics g) {  
		Graphics2D g2 = (Graphics2D)g.create();
	    Paint p = new GradientPaint(0, 0, new Color(215, 230, 254), 0, getHeight(), new Color(135, 175, 230), true);
	    g2.setPaint(p);
	    g2.fillRect(0, 0, getWidth(), getHeight());
	    g2.dispose();
    }
}
