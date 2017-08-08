package com.leandrotacioli.libs.swing.table;

import java.awt.Color;
import java.awt.event.MouseAdapter;

import javax.swing.JScrollPane;

import com.leandrotacioli.libs.LTDataTypes;

/**
 * Tabela customizável da bibliocateca LT Libraries.<br>
 * <br>
 * IMPORTANTE: É necessário chamar o método <i>showTable()</i> 
 * para finalizar a criação e habilitar a visualização da LTTable.<br>
 * <br>
 * A tabela cria automaticamente uma coluna chamada 'ID_ROW_LT_TABLE'
 * que gerencia internamente as linhas da LTTable.
 * 
 * @author Leandro Tacioli
 * @version 3.2 - 14/Jul/2016
 */
public class LTTable extends JScrollPane implements TableInterface {
	private static final long serialVersionUID = -3832122577742129437L;
	
	private Table objTable;
	
	/**
	 * Tabela.<br>
	 * <br>
	 * IMPORTANTE: É necessário chamar o método <i>showTable()</i> 
	 * para finalizar a criação e habilitar a visualização da LTTable.
	 * 
	 * @param blnReadOnly - Status de leitura/alteração da tabela
	 */
	public LTTable(boolean blnReadOnly) {
		objTable = new Table(blnReadOnly, false);
	}
	
	/**
	 * Tabela customizável da bibliocateca LT Libraries.<br>
	 * <br>
	 * IMPORTANTE: É necessário chamar o método <i>showTable()</i> 
	 * para finalizar a criação e habilitar a visualização da LTTable.<br>
	 * <br>
	 * A tabela cria automaticamente uma coluna chamada 'ID_ROW_LT_TABLE'
	 * que gerencia internamente as linhas da LTTable.
	 * 
	 * @param blnReadOnly       - Status de leitura/alteração da tabela
	 * @param blnAllowDeleteRow - Status de permissão para deleção de qualquer linha da tabela (válido apenas quando <i>blnReadOnly</i> for <i>FALSE</i>)
	 */
	public LTTable(boolean blnReadOnly, boolean blnAllowDeleteRow) {
		objTable = new Table(blnReadOnly, blnAllowDeleteRow);
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

	// *********************************************************************
	// Implementa TableInterface
	/**
	 * Adiciona uma nova coluna à LTTable.
	 */
	public void addColumn(String strColumnName, String strColumnDescription, LTDataTypes objColumnDataType, int intColumnWidth, boolean blnColumnEditable) {
		objTable.addColumn(strColumnName, strColumnDescription, objColumnDataType, intColumnWidth, blnColumnEditable);
	}
	
	/**
	 * Adiciona uma nova linha à LTTable.
	 */
	public void addRow() {
		objTable.addRow();
	}
	
	/**
	 * Adiciona um valor à coluna parametrizada da última linha criada na LTTable.
	 * 
	 * @param strColumnName - Nome da coluna
	 * @param objValue      - Valor
	 */
	public void addRowData(String strColumnName, Object objValue) {
		objTable.addRowData(strColumnName, objValue);
	}

	/**
	 * Exclui uma linha da LTTable. 
	 * <br>
	 * Obs: Se mais de uma linha está sendo removida, é necessário remover da última para a primeira.
	 * 
	 * @param intRowIndex
	 */
	public void deleteRow(int intRowIndex) {
		objTable.deleteRow(intRowIndex);
	}
	
	/**
	 * Exclui todos as linhas da LTTable.
	 */
	public void deleteRows() {
		objTable.deleteRows();
	}
	
	/**
	 * Exclui uma coluna da LTTable. 
	 * 
	 * @param strColumnName - Nome da coluna
	 */
	public void deleteColumn(String strColumnName) {
		objTable.deleteColumn(strColumnName);
	}
	
	/**
	 * Exclui todos as colunas da LTTable.
	 */
	public void deleteColumns() {
		objTable.deleteColumns();
	}
	
	/**
	 * Retorna a quantidade de colunas da LTTable.<br>
	 * <br>
	 * OBS: A coluna 'ID_ROW_LT_TABLE' também é considerada.
	 * 
	 * @return intColumnCount
	 */
	public int getColumnCount() {
		return objTable.getColumnCount();
	}
	
	/**
	 * Retorna o nome da coluna da LTTable que será apresentado para o usuário.
	 * 
	 * @param intColumnIndex
	 * 
	 * @return strColumnName
	 */
	public String getColumnName(int intColumnIndex) {
		return objTable.getColumnName(intColumnIndex);
	}
	
	/**
	 * Retorna o título da coluna da LTTable.
	 * 
	 * @param intColumnIndex - Índice da coluna
	 * 
	 * @return strColumnDescription
	 */
	public String getColumnDescription(int intColumnIndex) {
		return objTable.getColumnDescription(intColumnIndex);
	}
	
	/**
	 * Altera a descrição da coluna que será apresentada ao usuário.
	 * 
	 * @param strColumnName        - Nome da coluna
	 * @param strColumnDescription - Nova descrição
	 */
	public void setColumnDescription(String strColumnName, String strColumnDescription) {
		objTable.setColumnDescription(strColumnName, strColumnDescription);
	}

	/**
	 * Retorna o tipo de classe da coluna de acordo com tipo de dado atribuído.
	 * 
	 * @param intColumnIndex - Índice da coluna
	 * 
	 * @return classType
	 */
    public Class<?> getColumnClass(int intColumnIndex) {
		return objTable.getColumnClass(intColumnIndex);
	}
	
	/**
	 * Altera a cor de uma coluna da LTTable.
	 * 
	 * @param intColumnIndex - Índice da coluna
	 * @param color          - Cor
	 */
	public void setColumnColor(int intColumnIndex, Color color) {
		objTable.setColumnColor(intColumnIndex, color);
	}
			
	/**
	 * Altera a cor de uma coluna da LTTable.
	 * 
	 * @param strColumnName - Nome da coluna
	 * @param color         - Cor
	 */
	public void setColumnColor(String strColumnName, Color color) {
		objTable.setColumnColor(strColumnName, color);
	}
	
	/**
	 * Ordena os dados de uma coluna da LTTable.
	 * 
	 * @param strColumnName - Nome da coluna
	 * @param blnAscending  - Ascendente
	 */
	public void orderColumnData(String strColumnName, boolean blnAscending) {
		objTable.orderColumnData(strColumnName, blnAscending);
	}
	
	/**
	 * Retorna a quantidade de linhas da LTTable.
	 * 
	 * @return intRowCount
	 */
	public int getRowCount() {
		return objTable.getRowCount();
	}
	
	/**
	 * Retorna a linha selecionada na LTTable.
	 * 
	 * @return intSelectedRow
	 */
	public int getSelectedRow() {
		return objTable.getSelectedRow();
	}
	
	/**
	 * Retorna a linhas selecionadas na LTTable.
	 * 
	 * @return selectedRows
	 */
	public int[] getSelectedRows() {
		return objTable.getSelectedRows();
	}
	
	/**
	 * Limpa as seleções da LTTable.
	 */
	public void clearSelection() {
		objTable.clearSelection();
	}
	
	/**
	 * Altera a cor de uma linha da LTTable.
	 * 
	 * @param intRowIndex - Índice da linha
	 * @param color       - Cor
	 */
	public void setRowColor(int intRowIndex, Color color) {
		objTable.setRowColor(intRowIndex, color);
	}
	
	/**
	 * Marcar linha como selecionada.
	 * 
	 * @param intRowIndex - Índice da linha
	 */
	public void setRowSelection(int intRowIndex) {
		objTable.setRowSelection(intRowIndex);
	}
	
	/**
	 * Marcar um intervalo de linhas como selecionadas.
	 * 
	 * @param intInitialRowIndex - Índice inicial da linha
	 * @param intFinalRowIndex   - Índice final da linha 
	 */
	public void setRowSelectionInterval(int intInitialRowIndex, int intFinalRowIndex) {
		objTable.setRowSelectionInterval(intInitialRowIndex, intFinalRowIndex);
	}
	
	/**
	 * Retorna o valor de uma célula da LTTable.
	 * 
	 * @param intRowIndex    - Índice da linha
	 * @param intColumnIndex - Índice da coluna
	 * 
	 * @return objValue
	 */
	public Object getValue(int intRowIndex, int intColumnIndex) {
		return objTable.getValue(intRowIndex, intColumnIndex);
	}
	
	/**
	 * Retorna o valor de uma célula da LTTable.
	 * 
	 * @param intRowIndex   - Índice da linha
	 * @param strColumnName - Nome da coluna
	 * 
	 * @return objValue
	 */
	public Object getValue(int intRowIndex, String strColumnName) {
		return objTable.getValue(intRowIndex, strColumnName);
	}
		
	/**
	 * Altera o valor de uma célula da LTTable.
	 * 
	 * @param objValue       - Valor
	 * @param intRowIndex    - Índice da linha
	 * @param intColumnIndex - Índice da coluna
	 */
	public void setValue(Object objValue, int intRowIndex, int intColumnIndex) {
		objTable.setValue(objValue, intRowIndex, intColumnIndex);
	}
	
	/**
	 * Altera o valor de uma célula da LTTable.
	 * 
	 * @param objValue      - Valor
	 * @param intRowIndex   - Índice da linha
	 * @param strColumnName - Nome da coluna
	 */
	public void setValue(Object objValue, int intRowIndex, String strColumnName) {
		objTable.setValue(objValue, intRowIndex, strColumnName);
	}
	
	/**
	 * Retorna o status de edição de uma coluna da LTTable.
	 * 
	 * @param intColumnIndex - Índice da coluna 
	 * 
	 * @return blnColumnEditable 
	 */
	public boolean getColumnEditable(int intColumnIndex) {
		return objTable.getColumnEditable(intColumnIndex);
	}

	/**
     * Adiciona um <i>TableAbstractListener</i> à LTTable.
     * 
     * @param objTableListener
     */
	public void addTableListener(TableListener objTableListener) {
		objTable.addTableListener(objTableListener);
	}

    /**
	 * Adiciona um <i>MouseListener</i> à LTTable.
	 * 
	 * @param mouseAdapter
	 */
	public void addMouseListener(MouseAdapter mouseAdapter) {
		objTable.addMouseListener(mouseAdapter);
	}
	
	/**
	 * Altera a quantidade máxima de caracteres do campo STRING.
	 * 
	 * @param strColumnName          - Nome da coluna
	 * @param intColumnMaximumLength - Quantidade máxima de caracteres
	 */
	public void setColumnStringMaximumLength(String strColumnName, int intColumnStringMaximumLength) {
		objTable.setColumnStringMaximumLength(strColumnName, intColumnStringMaximumLength);
	}
	
	/**
	 * Altera a quantidade de casas decimais do campo DOUBLE.
	 * 
	 * @param strColumnName                 - Nome da coluna
	 * @param intColumnDoubleFractionDigits - Quantidade de casas decimais
	 */
	public void setColumnDoubleFractionDigits(String strColumnName, int intColumnDoubleFractionDigits) {
		objTable.setColumnDoubleFractionDigits(strColumnName, intColumnDoubleFractionDigits);
	}
	
	/**
	 * Permite a ordenação das linhas.
	 * 
	 * @param blnAllowSortedRows
	 */
	public void setAllowSortedRows(boolean blnAllowSortedRows) {
		objTable.setAllowSortedRows(blnAllowSortedRows);
	}
	
	/**
	 * Atualiza a LTTable.<br>
	 * <br>
	 * NOTA: Quando inserimos registros em background, pode ser
	 * necessário executar este método para atualizar os renderizadores
	 * e editores da LTTable.
	 */
	public void updateTableData() {
		objTable.updateTableData();
	}
}