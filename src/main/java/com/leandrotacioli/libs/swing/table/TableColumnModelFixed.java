package com.leandrotacioli.libs.swing.table;

import javax.swing.table.AbstractTableModel;

/**
 * Cria um modelo de tabela que será utilizado, 
 * exclusivamente, para a tabela fixa.
 * 
 * @author Leandro Tacioli
 * @version 1.1 - 10/Set/2015
 */
public class TableColumnModelFixed extends AbstractTableModel {
	private static final long serialVersionUID = -7620090463201928209L;
	
	private int intRowCount;
	
	/**
	 * Atualiza o número de linhas da tabela
	 * 
	 * @param intRowCount
	 */
	protected void setRowCount(int intRowCount) {
		this.intRowCount = intRowCount;
		
		fireTableDataChanged();
	}
	
	/**
	 * Cria um modelo de tabela que será utilizado, 
	 * exclusivamente, para a tabela fixa.
	 * 
	 * @param intRowCount - Número de linhas da tabela
	 */
	protected TableColumnModelFixed(int intRowCount) {
		this.intRowCount = intRowCount;
	}
	
	@Override
    public String getColumnName(int intColumnIndex) {
    	return null;
    }

	@Override
	public int getColumnCount() {
		return 1;
	}

	@Override
	public int getRowCount() {
		return intRowCount;
	}

	@Override
	public Object getValueAt(int intRowIndex, int intColumnIndex) {
		return null;
	}
	
	@Override
    public boolean isCellEditable(int intRowIndex, int intColumnIndex) {
    	return false;
    }
}