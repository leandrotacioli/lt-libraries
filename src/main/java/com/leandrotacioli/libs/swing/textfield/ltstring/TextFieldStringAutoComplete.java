package com.leandrotacioli.libs.swing.textfield.ltstring;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Extended JTextField which will display a whole word while the user
 * types part of it. It is necessary to inform a list of possibilities
 */
public class TextFieldStringAutoComplete extends JTextField implements DocumentListener {

	private ArrayList<String> lstPossibilities;
    private Color incompleteColor;
    private int currentGuess;
    private boolean blnGuessing;
    private boolean blnCaseSensitive;
    
    // Getters & Setters
    public void setCaseSensitive(boolean blnCaseSensitive) {
        this.blnCaseSensitive = blnCaseSensitive;
    }
	
    /**
     * Extended JTextField which will display a whole word while the user
     * types part of it. It is necessary to inform a list of possibilities.
     */
	public TextFieldStringAutoComplete(int intMaxLength) {
		super();
		this.setDocument(new TextFieldStringDocument(intMaxLength));
		this.lstPossibilities = new ArrayList<String>();
        this.incompleteColor = Color.LIGHT_GRAY;
        this.currentGuess = -1;
        this.blnGuessing = false;
        this.blnCaseSensitive = false;
        this.getDocument().addDocumentListener(this);
	}
	
	/**
	 * Add a new possibility to the list of possibilities.
	 * 
	 * @param strPossibility
	 */
    public void addPossibility(String strPossibility) {
        lstPossibilities.add(strPossibility);
    }

    /**
     * Removes a possibility from the list of possibilities
     * 
     * @param strPossibility
     */
    public void removePossibility(String strPossibility) {
        lstPossibilities.remove(strPossibility);
    }

    /**
     * Removes all possibilities from the list of possibilities
     */
    public void removeAllPossibilities() {
        lstPossibilities = new ArrayList<String>();
    }

    /**
     * Returns the current guess from the list of possibilities.
     * 
     * @return
     */
    private String getCurrentGuess() {
        if (currentGuess != -1) {
            return lstPossibilities.get(currentGuess);
        }

        return this.getText();
    }
    
    /**
     * Finds the current guess string
     */
    private void findCurrentGuess() {
        String strEntered = getText();
        
        if (!blnCaseSensitive) {
        	strEntered = strEntered.toLowerCase();
        }

        for (int intIndex = 0; intIndex < lstPossibilities.size(); intIndex++) {
            currentGuess = -1;

            String strPossibility = lstPossibilities.get(intIndex);
            if (!blnCaseSensitive) {
            	strPossibility = strPossibility.toLowerCase();
            }
            
            if (strPossibility.startsWith(strEntered)) {
                currentGuess = intIndex;
                break;
            }
        }
    }
    
	//*************************************************************************
    @Override
    public void setText(String text) {
        super.setText(text);
        blnGuessing = false;
        currentGuess = -1;
    }

	//*************************************************************************
    @Override
    public void paintComponent(Graphics g) {
    	String strGuess = getCurrentGuess();
        String drawGuess = strGuess;

        super.paintComponent(g);
        String strEntered = getText();
        Rectangle2D enteredBounds = g.getFontMetrics().getStringBounds(strEntered, g);
        
        if (!(blnCaseSensitive)) {
        	strEntered = strEntered.toLowerCase();
            strGuess = strGuess.toLowerCase();
        }
        
        if (!(strGuess.startsWith(strEntered))) {
            blnGuessing = false;
        }

        if (strEntered != null && !(strEntered.equals("")) && blnGuessing) {
            String subGuess = drawGuess.substring(strEntered.length(), drawGuess.length());
            Rectangle2D subGuessBounds = g.getFontMetrics().getStringBounds(drawGuess, g);

            int centeredY = ((getHeight() / 2) + (int)(subGuessBounds.getHeight() / 2));

            g.setColor(incompleteColor);
            g.drawString(subGuess, (int)(enteredBounds.getWidth()) + 3, centeredY - 2);
        }
    }
	
	//*************************************************************************
	// Implements DocumentListener
	@Override
	public void changedUpdate(DocumentEvent event) {
        String temp = getText();

        if (temp.length() == 1) {
            blnGuessing = true;
        }

        if (blnGuessing) {
            findCurrentGuess();
        }
	}

	@Override
	public void insertUpdate(DocumentEvent event) {
        String temp = getText();

        if (temp.length() == 1) {
            blnGuessing = true;
        }

        if (blnGuessing) {
            findCurrentGuess();
        }
	}

	@Override
	public void removeUpdate(DocumentEvent event) {
        String temp = getText();

        if (!(blnGuessing)) {
            blnGuessing = true;
        }

        if (temp.length() == 0) {
            blnGuessing = false;
        } else if (blnGuessing) {
            findCurrentGuess();
        }
	}
}
