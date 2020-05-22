package com.leandrotacioli.libs.swing;

import java.awt.Component;
import java.awt.Container;

import javax.swing.BorderFactory;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;

import com.leandrotacioli.libs.swing.comboboxfield.LTComboBoxField;
import com.leandrotacioli.libs.swing.table.LTTable;
import com.leandrotacioli.libs.swing.textfield.LTTextField;

public class LTPanel {
	
	/**
	 * Borda padrão de painéis.
	 */
	public static final CompoundBorder PANEL_BORDER = BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED), 
			 																			 BorderFactory.createEmptyBorder(0, 0, 0, 0));

	/**
	 * Painel.
	 */
	private LTPanel() {
		
	}
	
	/**
	 * Limpa o conteúdo dos campos de um painel.
	 * 
	 * @param container - Painel
	 */
	public static void cleanComponents(Container container) {
		try {
			Component[] components = container.getComponents();
			
	        for (Component component : components) {
	        	if (component instanceof LTTextField) {
	            	((LTTextField) component).setValue("");
	            } else if (component instanceof LTComboBoxField) {
	            	((LTComboBoxField) component).cleanValue();
	            } else if (component instanceof LTTable) {
	            	((LTTable) component).deleteRows();
	            }
	        }
	        
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Habilita/desabilita os componentes de um painel.
	 * 
	 * @param container  - Painel
	 * @param blnEnabled - <i>True</i>: Habilita - <i>False</i>: Desabilita
	 */
	public static void setComponentEnabled(Container container, boolean blnEnabled) {
		try {
			Component[] components = container.getComponents();
			
	        for (Component component : components) {
	        	component.setEnabled(blnEnabled);
	        	
	        	if (component instanceof LTTextField) {
	            	((LTTextField) component).setEnabled(blnEnabled);
	        	} else if (component instanceof LTComboBoxField) {
	            	((LTComboBoxField) component).setEnabled(blnEnabled);
	        	//} else if (component instanceof LTTable) {
	            //	((LTTable) component).setEnabled(blnEnabled);
	            } else if (component instanceof Container) {
	            	setComponentEnabled((Container) component, blnEnabled);
	            }
	        }
	        
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Verifica se o painel contém registros obrigatórios não preenchidos.
	 * 
	 * @param container  - Painel
	 * 
	 * @return blnReturn - <i>True</i> - Campos não preenchidos
	 */
	public static boolean checkMandatoryFields(Container container) {
		boolean blnReturn = false;
		
		try {
			Component[] components = container.getComponents();
			
	        for (Component component : components) {
	        	if (component instanceof LTTextField) {
	            	if (((LTTextField) component).getIsMandatoryFieldEmpty()) {
	            		blnReturn = true;
	            	}
	            } else if (component instanceof LTComboBoxField) {
	            	if (((LTComboBoxField) component).getIsMandatoryFieldEmpty()) {
	            		blnReturn = true;
	            	}
	            }
	        }
	        
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return blnReturn;
	}
}