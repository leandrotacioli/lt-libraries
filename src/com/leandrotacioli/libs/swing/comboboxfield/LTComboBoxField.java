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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;

import net.miginfocom.swing.MigLayout;

import com.leandrotacioli.libs.LTParameters;

/**
 * Combobox.
 * 
 * @author Leandro Tacioli
 * @version 2.3 - 24/Mai/2016
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
		lblLabel.setFont(LTParameters.getInstance().getFontComponentLabel());
		
		ImageIcon iconAlert = new ImageIcon("res/images/alert.png");
		lblAlert = new JLabel(iconAlert, JLabel.CENTER);
		lblAlert.setToolTipText(LTParameters.getInstance().getBundle().getString("text_field_panel_mandatory_field"));
		lblAlert.setVisible(false);
		
		comboBox = new JComboBox<String>();
		comboBox.setMinimumSize(LTParameters.getInstance().getDimensionComponentMinimumSize());
		comboBox.setMaximumSize(LTParameters.getInstance().getDimensionComponentMaximumSize());
		comboBox.setUI(new BasicComboBoxUI());
		comboBox.setEnabled(blnEnabled);
		
		if (!blnEnabled) {
			comboBox.setBackground(LTParameters.getInstance().getColorComponentBackgroundDisabled());
		} else {
			comboBox.setBackground(LTParameters.getInstance().getColorComponentBackground());
		}
		
		comboBox.setForeground(LTParameters.getInstance().getColorComponentForeground());
		comboBox.setFont(LTParameters.getInstance().getFontComponentTextField());
		
		comboBox.addMouseListener(this);
		comboBox.addFocusListener(this);
		comboBox.addKeyListener(this);
		comboBox.addItemListener(this);
		
		setLayout(new MigLayout("insets 0", "[grow]", "[] 0 [grow]"));
		setBackground(LTParameters.getInstance().getColorComponentPanelBackground());
		
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
	 * @param strLabel
	 */
	public void addValues(String strKeyValue, String strKeyValueDescription) {
		try {
			if (strKeyValue.equals(CLEAN_LT_COMBOBOX) || strKeyValueDescription.equals(CLEAN_LT_COMBOBOX)) {
				throw new Exception("Cannot add value: " + strKeyValue + " - " + strKeyValueDescription + ". Try another value.");
			} else {
				lstComboBoxValues.add(new LTComboBoxFieldValues(strKeyValue, strKeyValueDescription));
				
				comboBox.addItem(lstComboBoxValues.get(lstComboBoxValues.size() - 1).getKeyValueDescription());
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
				comboBox.setBackground(LTParameters.getInstance().getColorComponentBackground());
				comboBox.setForeground(LTParameters.getInstance().getColorComponentForeground());
				
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
				comboBox.setBackground(LTParameters.getInstance().getColorComponentBackgroundFocus());
				comboBox.setForeground(LTParameters.getInstance().getColorComponentForeground());

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
		
		comboBox.setBackground(LTParameters.getInstance().getColorComponentBackground());
		comboBox.setForeground(LTParameters.getInstance().getColorComponentForeground());
		
		if (!blnEnabled) {
			comboBox.setBackground(LTParameters.getInstance().getColorComponentBackgroundDisabled());
			
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
			addPopupMouseListener();
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
			addPopupMouseListener();
			comboBox.showPopup();
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
	 * Cria uma popup ao clicar com o mouse. 
	 * 
	 * @param comboBox
	 */
	private void addPopupMouseListener() {  
        try {
            Field popupInBasicComboBoxUI = BasicComboBoxUI.class.getDeclaredField("popup");  
            popupInBasicComboBoxUI.setAccessible(true);
            
            BasicComboPopup popup = (BasicComboPopup) popupInBasicComboBoxUI.get(comboBox.getUI());  

            Field scrollerInBasicComboPopup = BasicComboPopup.class.getDeclaredField("scroller");  
            scrollerInBasicComboPopup.setAccessible(true);  
            
            JScrollPane scroller = (JScrollPane) scrollerInBasicComboPopup.get(popup);  

            scroller.getViewport().getView().addMouseListener(new MouseAdapter() {
    			@Override
    			public void mouseClicked(MouseEvent event) {  
                    
                }

    			@Override
                public void mousePressed(MouseEvent event) {  
                      
                }  

				@Override
                public void mouseReleased(MouseEvent event) {
					setFocusRenderer();
                }

    			@Override
                public void mouseEntered(MouseEvent event) {  
                      
                }  

    			@Override
                public void mouseExited(MouseEvent mouseEvent) {  
                      
                }
            });
                            		    
        } catch (Exception e) {  
            e.printStackTrace();  
        }
    }
		
	/**
	 * Cria um renderizador o popup com os valores atribuídos ao campo.
	 */
	private class ComboBoxCellRenderer extends DefaultListCellRenderer {
		private static final long serialVersionUID = -1553302519977810202L;
		
		@Override
	    public Component getListCellRendererComponent(final JList<?> list, final Object value, final int index, final boolean isSelected, final boolean cellHasFocus) {
	    	this.setText((String) value);
	    	
	    	UIManager.put("ComboBox.background", LTParameters.getInstance().getColorComponentBackgroundFocus());
	    	
	    	setFont(LTParameters.getInstance().getFontComponentTextField());
	    	
	        if (isSelected) {
	        	setBackground(LTParameters.getInstance().getColorComboBoxRowSelected());
	        } else {
	            setBackground(LTParameters.getInstance().getColorComponentBackgroundFocus());
	            setForeground(LTParameters.getInstance().getColorComponentForeground());
	        }
	        
	        list.setSelectionBackground(LTParameters.getInstance().getColorComponentBackgroundFocus());
	        list.setSelectionForeground(LTParameters.getInstance().getColorComponentForeground());
	        
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