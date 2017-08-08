package com.leandrotacioli.libs.swing;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import com.leandrotacioli.libs.LTParameters;

/**
 * Cria uma extensão para <i>TitledBorder</i>
 * que efetua a correção das margens da borda
 * e altera a fonte do título da borda.
 * 
 * @author Leandro Tacioli
 * @version 1.0 - 29/Abr/2015
 */
public class LTTitledBorder extends TitledBorder {
	private static final long serialVersionUID = -3604949041695752341L;

	public LTTitledBorder(Border arg0, String arg1, int arg2, int arg3, Font arg4, Color arg5) {
		super(arg0, arg1, arg2, arg3, arg4, arg5);
	}

	public LTTitledBorder(Border border, String title, int titleJustification, int titlePosition, Font titleFont) {
		super(border, title, titleJustification, titlePosition, titleFont);
	}

	public LTTitledBorder(Border border, String title, int titleJustification, int titlePosition) {
		super(border, title, titleJustification, titlePosition);
	}

	public LTTitledBorder(Border border, String title) {
		super(border, title);
	}

	public LTTitledBorder(Border border) {
		super(border);
	}

	public LTTitledBorder(String title) {
		super(title);
	}
	
	@Override
	public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
		super.paintBorder(c, g, x - 2, y, width + 4, height);
	}

	@Override
	public void setTitle(String strTitle) {
		super.setTitle(strTitle);
		
		setTitleFont(LTParameters.getInstance().getFontComponentLabel());
	}
	
	@Override
	public void setTitleFont(Font titleFont) {
		super.setTitleFont(titleFont);
	}
}