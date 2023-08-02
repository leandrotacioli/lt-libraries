package com.leandrotacioli.libs.swing.comboboxfield;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicComboBoxUI;

import com.leandrotacioli.libs.swing.LTSwing;
import net.miginfocom.swing.MigLayout;

import com.leandrotacioli.libs.LTParameters;

/**
 * Combobox.
 * 
 * @author Leandro Tacioli
 */
public class LTComboBoxField extends JPanel implements LTComboBoxFieldInterface, MouseListener, FocusListener, KeyListener, ItemListener {
	private static final long serialVersionUID = -2715899779863536880L;

	private String strLabel;
	private String strValue;
	private String strColumnDatabase;
	
	private boolean blnEnabled;
	private boolean blnMandatoryField;
	private boolean blnValueEmpty;
	
	private JLabel lblLabel;
	private JLabel lblAlert;
	private JComboBox<String> comboBox;
	
	private List<LTComboBoxFieldValues> lstComboBoxValues;
	
	private final String CLEAN_LT_COMBOBOX = "CLEAN_LT_COMBOBOX";
	
	/**
	 * Retorna a coluna do banco de dados do campo.
	 * 
	 * @return strColumnDatabase
	 */
	public String getColumnDatabase() {
		return strColumnDatabase;
	}

	/**
	 * Altera a coluna do banco de dados do campo.
	 * 
	 * @param strColumnDatabase
	 */
	public void setColumnDatabase(String strColumnDatabase) {
		this.strColumnDatabase = strColumnDatabase;
	}
	
	/**
	 * Retorna a lista de valores atribuídos ao campo.
	 * 
	 * @return lstComboBoxValues
	 */
	public List<LTComboBoxFieldValues> getComboBoxValues() {
		return lstComboBoxValues;
	}
		
	/**
	 * Combobox.
	 * 
	 * @param strLabel
	 * @param blnEnabled
	 * @param blnMandatoryField
	 */
	public LTComboBoxField(String strLabel, boolean blnEnabled, boolean blnMandatoryField) {
		this.strLabel = strLabel;
		this.blnEnabled = blnEnabled;
		this.blnMandatoryField = blnMandatoryField;
		
		lblLabel = new JLabel(strLabel);
		lblLabel.setFont(LTSwing.getInstance().getFontComponentLabel());
		
		ImageIcon iconAlert = new ImageIcon(LTParameters.getInstance().getResourcesFolder() + "images/alert.png");
		lblAlert = new JLabel(iconAlert, JLabel.CENTER);
		lblAlert.setToolTipText(LTParameters.getInstance().getBundle().getString("mandatory_field"));
		lblAlert.setVisible(false);
		
		comboBox = new JComboBox<String>();
		comboBox.setMinimumSize(LTSwing.getInstance().getDimensionComponentMinimumSize());
		comboBox.setMaximumSize(LTSwing.getInstance().getDimensionComponentMaximumSize());
		comboBox.setUI(new BasicComboBoxUI());
		comboBox.setEnabled(blnEnabled);
		
		if (!blnEnabled) {
			comboBox.setBackground(LTSwing.getInstance().getColorComponentBackgroundDisabled());
		} else {
			comboBox.setBackground(LTSwing.getInstance().getColorComponentBackground());
		}
		
		comboBox.setForeground(LTSwing.getInstance().getColorComponentForeground());
		comboBox.setFont(LTSwing.getInstance().getFontComponentTextField());
		
		comboBox.addMouseListener(this);
		comboBox.addFocusListener(this);
		comboBox.addKeyListener(this);
		comboBox.addItemListener(this);
		
		setLayout(new MigLayout("insets 0", "[grow]", "[] 0 [grow]"));
		setBackground(LTSwing.getInstance().getColorComponentPanelBackground());
		
		add(lblLabel, "cell 0 0, grow");
		add(comboBox, "cell 0 1, grow");
		add(lblAlert, "cell 0 1, gap 0, hidemode 3");
		
		lstComboBoxValues = new ArrayList<LTComboBoxFieldValues>();
		
		addValues("", "");
	}
	
	/**
	 * Adiciona valores ao campo.
	 * 
	 * @param strKeyValue
	 * @param strKeyValueDescription
	 */
	public void addValues(String strKeyValue, String strKeyValueDescription) {
		try {
			if (strKeyValue.equals(CLEAN_LT_COMBOBOX) || strKeyValueDescription.equals(CLEAN_LT_COMBOBOX)) {
				throw new Exception("Cannot add value: " + strKeyValue + " - " + strKeyValueDescription + ". Try another value.");
			} else {
				lstComboBoxValues.add(new LTComboBoxFieldValues(strKeyValue, strKeyValueDescription));
				
				comboBox.addItem(strKeyValueDescription);
				comboBox.setSelectedItem(null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Remove todos os valores do campo.
	 */
	public void removeValues() {
		comboBox.removeAllItems();
		
		lstComboBoxValues = new ArrayList<LTComboBoxFieldValues>();
		
		addValues("", "");
	}
	
	/**
	 * Renderer inicial do campo.
	 */
	private void setDefaultRenderer() {
		comboBox.setRenderer(new DefaultListCellRenderer() {
			private static final long serialVersionUID = -3834160234417617219L;
			
			@Override
			public void paint(Graphics g) {
			comboBox.setBackground(LTSwing.getInstance().getColorComponentBackground());
			comboBox.setForeground(LTSwing.getInstance().getColorComponentForeground());

			super.paint(g);
		    }
		});
	}
	
	/**
	 * Renderer do campo ao receber foco.
	 */
	private void setFocusRenderer() {
		comboBox.setRenderer(new DefaultListCellRenderer() {
			private static final long serialVersionUID = 3198399646742098137L;

			@Override
			public void paint(Graphics g) {
			comboBox.setBackground(LTSwing.getInstance().getColorComponentBackgroundFocus());
			comboBox.setForeground(LTSwing.getInstance().getColorComponentForeground());

			super.paint(g);
		    }
		});
	}

	// *********************************************************************
	// LTComboBoxFieldInterface
	@Override
	public String getLabel() {
		return strLabel;
	}
	
	@Override
	public void setLabel(String strLabel) {
		lblLabel.setText(strLabel);
	}

	@Override
	public boolean getIsMandatoryFieldEmpty() {
		if ((strValue == null || strValue.toString().length() == 0) && blnMandatoryField) {
			lblAlert.setVisible(true);
			return true;
		} else {
			lblAlert.setVisible(false);
			return false;
		}
	}
	
	@Override
	public String getValue() {
		for (int intIndex = 0; intIndex < lstComboBoxValues.size(); intIndex++) {
			if (lstComboBoxValues.get(intIndex).getKeyValue().equals(strValue)) {
				return lstComboBoxValues.get(intIndex).getKeyValue();
			}
		}
		
		comboBox.setSelectedItem(null);
		
		return "";
	}
	
	@Override
	public String getValueDescription() {
		for (int intIndex = 0; intIndex < lstComboBoxValues.size(); intIndex++) {
			if (lstComboBoxValues.get(intIndex).getKeyValue().equals(strValue)) {
				return lstComboBoxValues.get(intIndex).getKeyValueDescription();
			}
		}
		
		return "";
	}
	
	@Override
	public void setValue(int intKeyValue) {
		setValue("" + intKeyValue);
	}
	
	@Override
	public void setValue(Long lgnKeyValue) {
		setValue("" + lgnKeyValue);
	}
	
	@Override
	public void setValue(String strKeyValue) {
		try {
			if (strKeyValue != null && strKeyValue.length() > 0) {
				boolean blnValueFound = false;
				
				for (int intIndex = 0; intIndex < lstComboBoxValues.size(); intIndex++) {
					if (lstComboBoxValues.get(intIndex).getKeyValue().equals(strKeyValue) || lstComboBoxValues.get(intIndex).getKeyValueDescription().equals(strKeyValue)) {
						blnValueFound = true;
						
						this.strValue = lstComboBoxValues.get(intIndex).getKeyValue();
						
						comboBox.setSelectedIndex(intIndex);
						
						break;
					}
				}
				
				// Não foi encontrado nenhum valor na lista de valores para o valor alterado
				if (!blnValueFound) {
					this.strValue = "";
					
					comboBox.setSelectedItem(null);
					
					blnValueEmpty = true;
				}
				
			} else {
				blnValueEmpty = true;
				
				this.strValue = "";
			}
			
		} catch (Exception e) {
			blnValueEmpty = true;
			
			comboBox.setSelectedItem(null);
			
			this.strValue = "";
		}
	}
	
	@Override
	public void setValueByDescription(String strKeyValueDescription) {
		try {
			if (strKeyValueDescription != null && strKeyValueDescription.length() > 0) {
				boolean blnValueFound = false;
				
				for (int intIndex = 0; intIndex < lstComboBoxValues.size(); intIndex++) {
					if (lstComboBoxValues.get(intIndex).getKeyValueDescription().equals(strKeyValueDescription)) {
						blnValueFound = true;
						
						this.strValue = lstComboBoxValues.get(intIndex).getKeyValue();
						
						comboBox.setSelectedIndex(intIndex);
						
						break;
					}
				}
				
				// Não foi encontrado nenhum valor na lista de valores para o valor alterado
				if (!blnValueFound) {
					this.strValue = "";
					
					comboBox.setSelectedItem(null);
					
					blnValueEmpty = true;
				}
				
			} else {
				blnValueEmpty = true;
				
				comboBox.setSelectedItem(null);
				
				this.strValue = "";
			}
			
		} catch (Exception e) {
			blnValueEmpty = true;
			
			comboBox.setSelectedItem(null);
			
			this.strValue = "";
		}
	}
	
	@Override
	public void cleanValue() {
		setValue(CLEAN_LT_COMBOBOX);
	}
		
	@Override
	public void setEnabled(boolean blnEnabled) {
		this.blnEnabled = blnEnabled;
		
		comboBox.setEnabled(blnEnabled);
		
		comboBox.setBackground(LTSwing.getInstance().getColorComponentBackground());
		comboBox.setForeground(LTSwing.getInstance().getColorComponentForeground());
		
		if (!blnEnabled) {
			comboBox.setBackground(LTSwing.getInstance().getColorComponentBackgroundDisabled());
			
			lblAlert.setVisible(false);
		}
	}

	@Override
	public void setFocus() {
		comboBox.requestFocus();
	}
	
	@Override
	public void addActionListener(ActionListener actionListener) {
		comboBox.addActionListener(actionListener);
	}
	
	@Override
	public void addKeyListener(KeyListener keyListener) {
		comboBox.addKeyListener(keyListener);
	}

	@Override
	public void addFocusListener(FocusListener focusListener) {
		comboBox.addFocusListener(focusListener);
	}
	
	@Override
	public void addItemListener(ItemListener itemListener) {
		comboBox.addItemListener(itemListener);
	}
	
	// *************************************************************************
	// MouseListener
	@Override
	public void mouseClicked(MouseEvent event) {
		
	}

	@Override
	public void mouseEntered(MouseEvent event) {
		
	}

	@Override
	public void mouseExited(MouseEvent event) {
		
	}

	@Override
	public void mousePressed(MouseEvent event) {

	}

	@Override
	public void mouseReleased(MouseEvent event) {
		if (blnEnabled) {
			comboBox.setRenderer(new ComboBoxCellRenderer());
			comboBox.showPopup();
		}		
	}
	
	// *************************************************************************
	// FocusListener
	@Override
	public void focusGained(FocusEvent event) {
		if (blnEnabled) {
			comboBox.setOpaque(true);
			comboBox.setRenderer(new ComboBoxCellRenderer());
			comboBox.showPopup();
		} else {
			comboBox.setOpaque(false);
			lblAlert.setVisible(false);
		}
	}

	@Override
	public void focusLost(FocusEvent event) {
		setDefaultRenderer();
		
		comboBox.setOpaque(false);
		
		if (comboBox.getSelectedItem() == null) {
			lblAlert.setVisible(true);
		} else {
			lblAlert.setVisible(false);
		}
	}
	
	// *************************************************************************
	// KeyListener
	@Override
	public void keyPressed(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.VK_ENTER) {
        	setFocusRenderer();
        }
	}

	@Override
	public void keyReleased(KeyEvent event) {
				
	}

	@Override
	public void keyTyped(KeyEvent event) {
				
	}
	
	// *************************************************************************
	// ItemListener
	@Override
	public void itemStateChanged(ItemEvent event) {
		if (event.getStateChange() == ItemEvent.SELECTED) {
			setValue((String) event.getItem());
		} else if (event.getStateChange() == ItemEvent.DESELECTED) {
			if (!blnValueEmpty) {
				setValue("");
			}
		}
		
		blnValueEmpty = false;
	}
		
	/**
	 * Cria um renderizador o popup com os valores atribuídos ao campo.
	 */
	private class ComboBoxCellRenderer extends DefaultListCellRenderer {
		private static final long serialVersionUID = -1553302519977810202L;
		
		@Override
	    public Component getListCellRendererComponent(final JList<?> list, final Object value, final int index, final boolean isSelected, final boolean cellHasFocus) {
	    	this.setText((String) value);
	    	
	    	UIManager.put("ComboBox.background", LTSwing.getInstance().getColorComponentBackgroundFocus());
	    	
	    	setFont(LTSwing.getInstance().getFontComponentTextField());
	    	
	        if (isSelected) {
	        	setBackground(LTSwing.getInstance().getColorComboBoxRowSelected());
	        } else {
	            setBackground(LTSwing.getInstance().getColorComponentBackgroundFocus());
	            setForeground(LTSwing.getInstance().getColorComponentForeground());
	        }
	        
	        list.setSelectionBackground(LTSwing.getInstance().getColorComponentBackgroundFocus());
	        list.setSelectionForeground(LTSwing.getInstance().getColorComponentForeground());
	        
	        return this;
	    }
	}
}

/**
* Modelo para os valores inseridos na LTComboBoxField.
*/
class LTComboBoxFieldValues {
	private String strKeyValue;
	private String strKeyValueDescription;
	
	// Getters
	public String getKeyValue() {
		return strKeyValue;
	}

	public String getKeyValueDescription() {
		return strKeyValueDescription;
	}
	
	// Constructor
	public LTComboBoxFieldValues(String strKeyValue, String strKeyValueDescription) {
		this.strKeyValue = strKeyValue;
		this.strKeyValueDescription = strKeyValueDescription;
	}
}