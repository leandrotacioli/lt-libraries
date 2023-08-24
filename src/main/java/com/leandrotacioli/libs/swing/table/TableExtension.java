package com.leandrotacioli.libs.swing.table;

import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.text.JTextComponent;

/**
 * Fornece uma extensão para a <i>JTable</i> padrão.
 * 
 * @author Leandro Tacioli
 */
public class TableExtension extends JTable {

	private boolean blnReadOnly;
	
	private List<TableExtensionRowColor> lstRowColor;

	/**
	 * Fornece uma extensão para a <i>JTable</i> padrão.
	 */
	protected TableExtension() {
        this(null, false);
    }

    /**
     * Fornece uma extensão para a <i>JTable</i> padrão.
     * 
     * @param objTableModel
     * @param blnReadOnly
     */
	protected TableExtension(TableModel objTableModel, boolean blnReadOnly) {
    	super(objTableModel);
    	
    	this.blnReadOnly = blnReadOnly;

    	this.lstRowColor = new ArrayList<TableExtensionRowColor>();
    }
	
	/**
	 * Limpa as cores das linhas da table.
	 */
	protected void clearRowColor() {
		lstRowColor = new ArrayList<TableExtensionRowColor>();
	}
    
    /**
     * Altera a cor de uma linha da table.
     * 
     * @param intRowId    - ID da linha
	 * @param color       - Cor
     */
    protected void setRowColor(int intRowId, Color color) {
    	lstRowColor.add(new TableExtensionRowColor(intRowId, color));
    }

    //***************************************************************************************************
    @Override
	protected JTableHeader createDefaultTableHeader() {
		return new JTableHeader(columnModel) {
			public String getToolTipText(MouseEvent e) {
				Point point = e.getPoint();
				int columnIndex = columnModel.getColumnIndexAtX(point.x);
	            
				return columnModel.getColumn(columnIndex).getHeaderValue().toString();
			}
		};
	}
    
    @Override
	public boolean editCellAt(int indexRow, int columnIndex, EventObject event) {
		boolean result = super.editCellAt(indexRow, columnIndex, event);
	
		selectAll(event);
		
		return result;
	}

    //***************************************************************************************************
    // Seleciona o texto quando houver edição de uma célula
	private void selectAll(EventObject event) {
		final Component editor = getEditorComponent();

		if (editor == null || !(editor instanceof JTextComponent)) {
			return;
		}

		if (event == null) {
			((JTextComponent) editor).selectAll();
			
			return;
		}

		// Digitar na célula foi usado para ativar o editor
		if (event instanceof KeyEvent) {
			((JTextComponent) editor).selectAll();
			
			return;
		}

		// F2 foi usado para ativar o editor
		if (event instanceof ActionEvent) {
			((JTextComponent) editor).selectAll();
			
			return;
		}

		// Um clique do mouse foi usado para ativar o editor
		// Geralmente, isto é um duplo clique clique e o segundo
		// clique é passado ao editor que removeria a seleção do texto
		if (event instanceof MouseEvent) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					((JTextComponent) editor).selectAll();
				}
			});
		}
	}
	
	//***************************************************************************************************
	// Estabelece um renderer que colore o background
	@Override
	public Component prepareRenderer(TableCellRenderer renderer, int rowIndex, int columnIndex) {
		Component component = null;
		
		try {
			component = super.prepareRenderer(renderer, rowIndex, columnIndex);
			
			// Altera a cor de background da linha
			if (blnReadOnly) {
				if (!isRowSelected(rowIndex)) {
					component.setBackground(rowIndex % 2 == 0 ? getBackground() : new Color(220, 220, 220));
				}
				
			} else {
				for (int indexRowColor = 0; indexRowColor < lstRowColor.size(); indexRowColor++) {
					if (lstRowColor.get(indexRowColor).getRowId() == (int) getValueAt(rowIndex, 0)) {
						component.setBackground(lstRowColor.get(indexRowColor).getColor());
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return component;
	}
}

/**
 *
 * @author Leandro Tacioli
 */
class TableExtensionRowColor {
	private int intRowId;
	private Color color;
	
	protected int getRowId() {
		return intRowId;
	}

	protected void setRowId(int intRowId) {
		this.intRowId = intRowId;
	}

	protected Color getColor() {
		return color;
	}

	protected void setColor(Color color) {
		this.color = color;
	}

	protected TableExtensionRowColor(int intRowId, Color color) {
		this.intRowId = intRowId;
		this.color = color;
	}
}