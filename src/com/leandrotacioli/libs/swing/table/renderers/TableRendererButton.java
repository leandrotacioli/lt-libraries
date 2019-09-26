package com.leandrotacioli.libs.swing.table.renderers;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.AbstractCellEditor;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import com.leandrotacioli.libs.swing.table.TableExtension;

/**
 * Renderizador/Editor para botões da LTTable.<br>
 * <br>
 * Adaptado do código de Rob Camick, disponível em:<br>
 * 'https://tips4java.wordpress.com/2009/07/12/table-button-column/' e
 * 'http://www.camick.com/java/source/ButtonColumn.java'
 * 
 * @author Leandro Tacioli
 * @version 1.0 - 26/Ago/2018
 */
public class TableRendererButton extends AbstractCellEditor implements TableCellRenderer, TableCellEditor, ActionListener, MouseListener {
	private static final long serialVersionUID = 3542107700428307692L;
	
	private TableExtension objTable;
	private Action action;
	private Border originalBorder;
	private Border focusBorder;
	
	private JButton btnRenderer;
	private JButton btnEditor;
	private Object editorValue;
	private boolean isButtonColumnEditor;
	
	/**
	 *  Create the TableRendererButton to be used as a renderer and editor. The
	 *  renderer and editor will automatically be installed on the TableColumn
	 *  of the specified column.
	 *
	 *  @param objTable       - The table containing the button renderer/editor
	 *  @param intIndexColumn - The column to which the button renderer/editor is added
	 */
	public TableRendererButton(TableExtension objTable, int intIndexColumn) {
		this(objTable, intIndexColumn, null);
	}
	
	/**
	 *  Create the TableRendererButton to be used as a renderer and editor. The
	 *  renderer and editor will automatically be installed on the TableColumn
	 *  of the specified column.
	 *
	 *  @param objTable       - The table containing the button renderer/editor
	 *  @param intIndexColumn - The column to which the button renderer/editor is added
	 *  @param action         - The Action to be invoked when the button is invoked
	 */
	public TableRendererButton(TableExtension objTable, int intIndexColumn, Action action) {
		this.objTable = objTable;
		this.action = action;
		
		btnRenderer = new JButton();
		btnRenderer.setToolTipText("");
		
		btnEditor = new JButton();
		btnEditor.setToolTipText("");
		btnEditor.setFocusPainted(false);
		btnEditor.addActionListener(this);
		
		originalBorder = btnEditor.getBorder();
		
		objTable.getColumnModel().getColumn(intIndexColumn).setCellRenderer(this);
		objTable.getColumnModel().getColumn(intIndexColumn).setCellEditor(this);
		
		objTable.addMouseListener(this);
	}

	// ************************************************************************************
	//  Implementa 'TableCellRenderer'
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		if (isSelected) {
			btnRenderer.setForeground(table.getSelectionForeground());
			btnRenderer.setBackground(table.getSelectionBackground());
		} else {
			btnRenderer.setForeground(table.getForeground());
			btnRenderer.setBackground(UIManager.getColor("Button.background"));
		}

		if (hasFocus) {
			btnRenderer.setBorder(focusBorder);
		} else {
			btnRenderer.setBorder(originalBorder);
		}
		
		if (value == null) {
			btnRenderer.setText("");
			btnRenderer.setIcon(null);
			btnRenderer.setContentAreaFilled(true);
		} else if (value instanceof Icon) {
			btnRenderer.setText("");
			btnRenderer.setIcon((Icon) value);
			btnRenderer.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
			btnRenderer.setContentAreaFilled(false);
		} else {
			btnRenderer.setText(value.toString());
			btnRenderer.setIcon(null);
			btnRenderer.setContentAreaFilled(true);
		}
		
		return btnRenderer;
	}
	
	// ************************************************************************************
	//  Implementa 'TableCellEditor'
	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		if (value == null) {
			btnEditor.setText("");
			btnEditor.setIcon(null);
		} else if (value instanceof Icon) {
			btnEditor.setText("");
			btnEditor.setIcon((Icon) value);
		} else {
			btnEditor.setText(value.toString());
			btnEditor.setIcon(null);
		}

		this.editorValue = value;
		
		return btnEditor;
	}

	@Override
	public Object getCellEditorValue() {
		return editorValue;
	}

	// ************************************************************************************
	//  Implementa 'ActionListener'
	@Override
	public void actionPerformed(ActionEvent e) {
		if (action != null) {
			int row = objTable.convertRowIndexToModel(objTable.getEditingRow());
			
			fireEditingStopped();
	
			// Invoke the Action
			ActionEvent event = new ActionEvent(objTable, ActionEvent.ACTION_PERFORMED, "" + row);
			action.actionPerformed(event);
		}
	}

	// ************************************************************************************
	//  Implementa 'MouseListener'
	@Override
    public void mousePressed(MouseEvent e) {
		/*  When the mouse is pressed the editor is invoked. If you then drag
		 *  the mouse to another cell before releasing it, the editor is still
		 *  active. Make sure editing is stopped when the mouse is released. */
    	if (objTable.isEditing() && objTable.getCellEditor() == this) {
			isButtonColumnEditor = true;
    	}
    }

	@Override
    public void mouseReleased(MouseEvent e) {
    	if (isButtonColumnEditor && objTable.isEditing()) {
    		objTable.getCellEditor().stopCellEditing();
    	}
    	
		isButtonColumnEditor = false;
    }

	@Override
    public void mouseClicked(MouseEvent e) {
		
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		
	}
	
	@Override
    public void mouseExited(MouseEvent e) {
		
	}
}