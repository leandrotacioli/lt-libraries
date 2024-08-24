package com.leandrotacioli.libs.swing.table.renderers;

import java.awt.Color;
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
import javax.swing.border.Border;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import com.leandrotacioli.libs.swing.LTSwing;
import com.leandrotacioli.libs.swing.table.TableButton;
import com.leandrotacioli.libs.swing.table.TableExtension;

/**
 * Renderizador/Editor para botões da LTTable.<br>
 * <br>
 * Adaptado do código de Rob Camick, disponível em:<br>
 * 'https://tips4java.wordpress.com/2009/07/12/table-button-column/' e
 * 'http://www.camick.com/java/source/ButtonColumn.java'
 * 
 * @author Leandro Tacioli
 */
public class TableRendererButton extends AbstractCellEditor implements TableCellRenderer, TableCellEditor, ActionListener, MouseListener {

	private TableExtension objTable;
	private Action action;
	private Border originalBorder;
	
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
		btnRenderer.setToolTipText(null);
		
		btnEditor = new JButton();
		btnEditor.setToolTipText(null);
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
			btnRenderer.setBackground(LTSwing.getInstance().getColorTableRowSelected());
		} else {
			btnRenderer.setBackground(Color.WHITE);
		}
		
		if (!hasFocus) {
			btnRenderer.setBorder(originalBorder);
		}
		
		if (value == null) {
			btnRenderer.setText("");
			btnRenderer.setToolTipText(null);
			btnRenderer.setIcon(null);
			btnRenderer.setBorder(null);
			btnRenderer.setContentAreaFilled(true);
			btnRenderer.setVisible(false);
		} else if (value instanceof Icon) {
			btnRenderer.setText("");
			btnRenderer.setToolTipText(null);
			btnRenderer.setIcon((Icon) value);
			btnRenderer.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
			btnRenderer.setContentAreaFilled(false);
			btnRenderer.setVisible(true);
		} else if (value instanceof TableButton) {
			btnRenderer.setText("");
			btnRenderer.setToolTipText(((TableButton) value).getTooltip());
			btnRenderer.setIcon(((TableButton) value).getIcon());
			btnRenderer.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
			btnRenderer.setBorderPainted(((TableButton) value).isClickEnabled());
			btnRenderer.setFocusPainted(((TableButton) value).isClickEnabled());
			btnRenderer.setContentAreaFilled(false);
			btnRenderer.setVisible(true);
		} else {
			btnRenderer.setText(value.toString());
			btnRenderer.setToolTipText(value.toString());
			btnRenderer.setIcon(null);
			btnRenderer.setContentAreaFilled(true);
			btnRenderer.setVisible(true);
		}
		
		btnRenderer.setOpaque(true);
		
		return btnRenderer;
	}
	
	// ************************************************************************************
	//  Implementa 'TableCellEditor'
	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		if (value == null) {
			btnEditor.setText("");
			btnEditor.setToolTipText(null);
			btnEditor.setIcon(null);
			btnEditor.setVisible(false);
		} else if (value instanceof Icon) {
			btnEditor.setText("");
			btnEditor.setToolTipText(null);
			btnEditor.setIcon((Icon) value);
			btnEditor.setVisible(true);
		} else if (value instanceof TableButton) {
			btnEditor.setText("");
			btnEditor.setToolTipText(((TableButton) value).getTooltip());
			btnEditor.setIcon(((TableButton) value).getIcon());
			btnEditor.setBorderPainted(((TableButton) value).isClickEnabled());
			btnEditor.setFocusPainted(((TableButton) value).isClickEnabled());
			btnEditor.setVisible(true);
		} else {
			btnEditor.setText(value.toString());
			btnEditor.setToolTipText(value.toString());
			btnEditor.setIcon(null);
			btnEditor.setVisible(true);
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