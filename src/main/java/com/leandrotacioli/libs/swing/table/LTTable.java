package com.leandrotacioli.libs.swing.table;

import java.awt.Color;
import java.awt.event.MouseAdapter;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;

import com.leandrotacioli.libs.LTDataTypes;
import com.leandrotacioli.libs.swing.LTSwing;

/**
 * Tabela customizável da bibliocateca LT Libraries.<br>
 * <br>
 * IMPORTANTE: É necessário chamar o método <i>showTable()</i> para finalizar a criação e habilitar a visualização da LTTable.<br>
 * <br>
 * A tabela cria automaticamente uma coluna chamada 'ID_ROW_LT_TABLE' que gerencia internamente as linhas da LTTable.
 * 
 * @author Leandro Tacioli
 */
public class LTTable extends JScrollPane implements TableInterface {
	private static final long serialVersionUID = -3832122577742129437L;
	
	private Table objTable;
	
	/**
	 * Tabela customizável da bibliocateca LT Libraries.<br>
	 * <br>
	 * IMPORTANTE: É necessário chamar o método <i>showTable()</i> para finalizar a criação e habilitar a visualização da LTTable.<br>
	 * <br>
	 * A tabela cria automaticamente uma coluna chamada 'ID_ROW_LT_TABLE' que gerencia internamente as linhas da LTTable.
	 * 
	 * @param blnReadOnly - Status de leitura/alteração da tabela
	 */
	public LTTable(boolean blnReadOnly) {
		this(blnReadOnly, false);
	}
	
	/**
	 * Tabela customizável da bibliocateca LT Libraries.<br>
	 * <br>
	 * IMPORTANTE: É necessário chamar o método <i>showTable()</i> para finalizar a criação e habilitar a visualização da LTTable.<br>
	 * <br>
	 * A tabela cria automaticamente uma coluna chamada 'ID_ROW_LT_TABLE' que gerencia internamente as linhas da LTTable.
	 * 
	 * @param blnReadOnly       - Status de leitura/alteração da tabela
	 * @param blnAllowDeleteRow - Status de permissão para deleção de qualquer linha da tabela (válido apenas quando <i>blnReadOnly</i> for <i>FALSE</i>)
	 */
	public LTTable(boolean blnReadOnly, boolean blnAllowDeleteRow) {
		objTable = new Table(blnReadOnly, blnAllowDeleteRow);
		
		setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, LTSwing.getInstance().getColorTableGrid()));
	}
	
	/**
	 * Finaliza a criação e habilita a visualização da LTTable.
	 */
	public void showTable() {
		objTable.createTable();
		
		setFocusable(false);
		setViewportView(objTable.getTable());
		setRowHeaderView(objTable.getTableFixed());
		setCorner(JScrollPane.UPPER_LEFT_CORNER, objTable.getTableFixed().getTableHeader());
	}
	
	/**
	 * Altera a cor da borda da LTTable.
	 * 
	 * @param color
	 */
	public void setBorderColor(Color color) {
		setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, color));
	}

	// *********************************************************************
	// Implementa TableInterface
	@Override
	public void addColumn(String strColumnName, String strColumnDescription, LTDataTypes objColumnDataType, int intColumnWidth, boolean blnColumnEditable) {
		objTable.addColumn(strColumnName, strColumnDescription, objColumnDataType, intColumnWidth, blnColumnEditable);
	}
	
	@Override
	public void addColumn(String strColumnName, String strColumnDescription, LTDataTypes objColumnDataType, int intColumnWidth, boolean blnColumnEditable, boolean blnColumnShowZeroValues) {
		objTable.addColumn(strColumnName, strColumnDescription, objColumnDataType, intColumnWidth, blnColumnEditable, blnColumnShowZeroValues);
	}
	
	@Override
	public void addRow() {
		objTable.addRow();
	}
	
	@Override
	public void addRowData(String strColumnName, Object objValue) {
		objTable.addRowData(strColumnName, objValue);
	}

	@Override
	public void deleteRow(int intRowIndex) {
		objTable.deleteRow(intRowIndex);
	}
	
	@Override
	public void deleteRows() {
		objTable.deleteRows();
	}
	
	@Override
	public void deleteColumn(String strColumnName) {
		objTable.deleteColumn(strColumnName);
	}
	
	@Override
	public void deleteColumns() {
		objTable.deleteColumns();
	}
	
	@Override
	public int getColumnCount() {
		return objTable.getColumnCount();
	}
	
	@Override
	public String getColumnName(int intColumnIndex) {
		return objTable.getColumnName(intColumnIndex);
	}
	
	@Override
	public String getColumnDescription(int intColumnIndex) {
		return objTable.getColumnDescription(intColumnIndex);
	}
	
	@Override
	public void setColumnDescription(String strColumnName, String strColumnDescription) {
		objTable.setColumnDescription(strColumnName, strColumnDescription);
	}

	@Override
    public Class<?> getColumnClass(int intColumnIndex) {
		return objTable.getColumnClass(intColumnIndex);
	}
	
	@Override
	public void setColumnColor(int intColumnIndex, Color color) {
		objTable.setColumnColor(intColumnIndex, color);
	}
			
	@Override
	public void setColumnColor(String strColumnName, Color color) {
		objTable.setColumnColor(strColumnName, color);
	}
	
	@Override
	public void orderColumnData(int intColumnIndex, boolean blnAscending) {
		objTable.orderColumnData(intColumnIndex, blnAscending);
	}
	
	@Override
	public void orderColumnData(String strColumnName, boolean blnAscending) {
		objTable.orderColumnData(strColumnName, blnAscending);
	}
	
	@Override
	public int getRowCount() {
		return objTable.getRowCount();
	}
	
	@Override
	public int getSelectedRow() {
		return objTable.getSelectedRow();
	}
	
	@Override
	public int[] getSelectedRows() {
		return objTable.getSelectedRows();
	}
	
	@Override
	public int getSelectedColumn() {
		return objTable.getSelectedColumn();
	}
	
	@Override
	public int[] getSelectedColumns() {
		return objTable.getSelectedColumns();
	}
	
	@Override
	public void clearSelection() {
		objTable.clearSelection();
	}
	
	@Override
	public void setRowColor(int intRowIndex, Color color) {
		objTable.setRowColor(intRowIndex, color);
	}
	
	@Override
	public void setRowSelection(int intRowIndex) {
		objTable.setRowSelection(intRowIndex);
	}
	
	@Override
	public void setRowSelectionInterval(int intInitialRowIndex, int intFinalRowIndex) {
		objTable.setRowSelectionInterval(intInitialRowIndex, intFinalRowIndex);
	}
	
	@Override
	public Object getValue(int intRowIndex, int intColumnIndex) {
		return objTable.getValue(intRowIndex, intColumnIndex);
	}
	
	@Override
	public Object getValue(int intRowIndex, String strColumnName) {
		return objTable.getValue(intRowIndex, strColumnName);
	}
		
	@Override
	public void setValue(Object objValue, int intRowIndex, int intColumnIndex) {
		objTable.setValue(objValue, intRowIndex, intColumnIndex);
	}
	
	@Override
	public void setValue(Object objValue, int intRowIndex, String strColumnName) {
		objTable.setValue(objValue, intRowIndex, strColumnName);
	}
	
	@Override
	public boolean getColumnEditable(int intColumnIndex) {
		return objTable.getColumnEditable(intColumnIndex);
	}

	@Override
	public void addTableListener(TableListener objTableListener) {
		objTable.addTableListener(objTableListener);
	}

	@Override
	public void addMouseListener(MouseAdapter mouseAdapter) {
		objTable.addMouseListener(mouseAdapter);
	}
	
	@Override
	public void setColumnStringMaximumLength(String strColumnName, int intColumnStringMaximumLength) {
		objTable.setColumnStringMaximumLength(strColumnName, intColumnStringMaximumLength);
	}
	
	@Override
	public void setColumnDoubleFractionDigits(String strColumnName, int intColumnDoubleFractionDigits) {
		objTable.setColumnDoubleFractionDigits(strColumnName, intColumnDoubleFractionDigits);
	}
	
	@Override
	public void setColumnDoubleShowAsPercentage(String strColumnName, boolean blnShowAsPercentage) {
		objTable.setColumnDoubleShowAsPercentage(strColumnName, blnShowAsPercentage);
	}
	
	@Override
	public void setColumnHorizontalAlignment(String strColumnName, int intHorizontalAlignment) {
		objTable.setColumnHorizontalAlignment(strColumnName, intHorizontalAlignment);
	}
	
	@Override
    public void setAllowDeleteRow(boolean blnAllowDeleteRow) {
    	objTable.setAllowDeleteRow(blnAllowDeleteRow);
    }
	
	@Override
	public void setAllowSortedRows(boolean blnAllowSortedRows) {
		objTable.setAllowSortedRows(blnAllowSortedRows);
	}
	
	@Override
	public void updateTableData() {
		objTable.updateTableData();
	}
}