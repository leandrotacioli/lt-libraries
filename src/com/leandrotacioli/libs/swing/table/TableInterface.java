package com.leandrotacioli.libs.swing.table;

import java.awt.Color;
import java.awt.event.MouseAdapter;

import com.leandrotacioli.libs.LTDataTypes;

/**
 * Interface com os métodos da LTTable.
 * 
 * @author Leandro Tacioli
 * @version 4.0 - 26/Ago/2018
 */
public interface TableInterface {
	
	/**
	 * Adiciona uma nova coluna à LTTable.
	 * 
     * @param strColumnName        - Nome da coluna
     * @param strColumnDescription - Descrição da coluna que será apresentada ao usuário
     * @param objColumnDataType    - Tipo de dado da coluna
     * @param intColumnWidth       - Comprimento da coluna na visualização da tabela
     * @param blnColumnEditable    - Status das células da coluna (True = Editável)
	 */
	public void addColumn(String strColumnName, String strColumnDescription, LTDataTypes objColumnDataType, int intColumnWidth, boolean blnColumnEditable);
	
	/**
	 * Adiciona uma nova coluna à LTTable.
	 * 
     * @param strColumnName           - Nome da coluna
     * @param strColumnDescription    - Descrição da coluna que será apresentada ao usuário
     * @param objColumnDataType       - Tipo de dado da coluna
     * @param intColumnWidth          - Comprimento da coluna na visualização da tabela
     * @param blnColumnEditable       - Status das células da coluna (True = Editável)
     * @param blnColumnShowZeroValues - Exibe valores zerados para campos numéricos (INTEGER, LONG, DOUBLE)
	 */
	public void addColumn(String strColumnName, String strColumnDescription, LTDataTypes objColumnDataType, int intColumnWidth, boolean blnColumnEditable, boolean blnColumnShowZeroValues);
	
	/**
	 * Adiciona uma nova linha à LTTable.<br>
	 * <br>
	 * IMPORTANTE: É necessário chamar o método <i>finishAddRow</i>
	 * após a inclusão de todas as linhas para habilitar os
	 * editores e renderizadores das células.
	 */
	public void addRow();
	
	/**
	 * Adiciona um valor à coluna parametrizada da última linha criada na LTTable.
	 * 
	 * @param strColumnName - Nome da coluna
	 * @param objValue      - Valor
	 */
	public void addRowData(String strColumnName, Object objValue);
	
	/**
	 * Exclui uma linha da LTTable. 
	 * <br>
	 * Obs: Se mais de uma linha está sendo removida, é necessário remover da última para a primeira.
	 * 
	 * @param intRowIndex
	 */
	public void deleteRow(int intRowIndex);
	
	/**
	 * Exclui todos as linhas da LTTable.
	 */
	public void deleteRows();
	
	/**
	 * Exclui uma coluna da LTTable. 
	 * 
	 * @param strColumnName - Nome da coluna
	 */
	public void deleteColumn(String strColumnName);
	
	/**
	 * Exclui todos as colunas da LTTable.
	 */
	public void deleteColumns();
	
	/**
	 * Retorna a quantidade de colunas da LTTable.<br>
	 * <br>
	 * OBS: A coluna 'ID_ROW_LT_TABLE' também é considerada.
	 * 
	 * @return intColumnCount
	 */
	public int getColumnCount();
	
	/**
	 * Retorna o nome da coluna da LTTable.
	 * 
	 * @param intColumnIndex
	 * 
	 * @return strColumnName
	 */
	public String getColumnName(int intColumnIndex);
	
	/**
	 * Retorna a descrição da coluna que será apresentada ao usuário.
	 * 
	 * @param intColumnIndex - Índice da coluna
	 * 
	 * @return strColumnDescription
	 */
	public String getColumnDescription(int intColumnIndex);
	
	/**
	 * Altera a descrição da coluna que será apresentada ao usuário.
	 * 
	 * @param strColumnName        - Nome da coluna
	 * @param strColumnDescription - Nova descrição
	 */
	public void setColumnDescription(String strColumnName, String strColumnDescription);
	
	/**
	 * Retorna o tipo de classe da coluna de acordo com tipo de dado atribuído.
	 * 
	 * @param intColumnIndex - Índice da coluna
	 * 
	 * @return classType
	 */
	public Class<?> getColumnClass(int intColumnIndex);
	
	/**
	 * Altera a cor de uma coluna da LTTable.
	 * 
	 * @param intColumnIndex - Índice da coluna
	 * @param color          - Cor
	 */
	public void setColumnColor(int intColumnIndex, Color color);
	
	/**
	 * Altera a cor de uma coluna da LTTable.
	 * 
	 * @param strColumnName - Nome da coluna
	 * @param color         - Cor
	 */
	public void setColumnColor(String strColumnName, Color color);
	
	/**
	 * Ordena os dados de uma coluna da LTTable.<br>
	 * <br>
	 * Obs: Caso uma nova linha seja incluída/excluída, é necessário chamar este método novamente.
	 * 
	 * @param strColumnName - Nome da coluna
	 * @param blnAscending  - Ascendente
	 */
	public void orderColumnData(String strColumnName, boolean blnAscending);
	
	/**
	 * Retorna a quantidade de linhas da LTTable.
	 * 
	 * @return intRowCount
	 */
	public int getRowCount();
	
	/**
	 * Retorna a linha selecionada na LTTable.
	 * 
	 * @return intSelectedRow
	 */
	public int getSelectedRow();
	
	/**
	 * Retorna as linhas selecionadas na LTTable.
	 * 
	 * @return selectedRows
	 */
	public int[] getSelectedRows();
	
	/**
	 * Retorna a coluna selecionada na LTTable.
	 * 
	 * @return intSelectedColumn
	 */
	public int getSelectedColumn();
	
	/**
	 * Retorna as colunas selecionadas na LTTable.
	 * 
	 * @return selectedColumns
	 */
	public int[] getSelectedColumns();
	
	/**
	 * Altera a cor de uma linha da LTTable.
	 * 
	 * @param intRowIndex - Índice da linha
	 * @param color       - Cor
	 */
	public void setRowColor(int intRowIndex, Color color);
	
	/**
	 * Marcar linha como selecionada.
	 * 
	 * @param intRowIndex - Índice da linha
	 */
	public void setRowSelection(int intRowIndex);
	
	/**
	 * Marcar um intervalo de linhas como selecionadas.
	 * 
	 * @param intInitialRowIndex - Índice inicial da linha
	 * @param intFinalRowIndex   - Índice final da linha 
	 */
	public void setRowSelectionInterval(int intInitialRowIndex, int intFinalRowIndex);
	
	/**
	 * Limpa as seleções da LTTable.
	 */
	public void clearSelection();
	
	/**
	 * Retorna o valor de uma célula da LTTable.
	 * 
	 * @param intRowIndex    - Índice da linha
	 * @param intColumnIndex - Índice da coluna
	 * 
	 * @return objValue
	 */
	public Object getValue(int intRowIndex, int intColumnIndex);
	
	/**
	 * Retorna o valor de uma célula da LTTable.
	 * 
	 * @param intRowIndex   - Índice da linha
	 * @param strColumnName - Nome da coluna
	 * 
	 * @return objValue
	 */
	public Object getValue(int intRowIndex, String strColumnName);
		
	/**
	 * Altera o valor de uma célula da LTTable.
	 * 
	 * @param objValue       - Valor
	 * @param intRowIndex    - Índice da linha
	 * @param intColumnIndex - Índice da coluna
	 */
	public void setValue(Object objValue, int intRowIndex, int intColumnIndex);
	
	/**
	 * Altera o valor de uma célula da LTTable.
	 * 
	 * @param objValue      - Valor
	 * @param intRowIndex   - Índice da linha
	 * @param strColumnName - Nome da coluna
	 */
	public void setValue(Object objValue, int intRowIndex, String strColumnName);
	
	/**
	 * Retorna o status de edição de uma coluna da LTTable.
	 * 
	 * @param intColumnIndex - Índice da coluna 
	 * 
	 * @return blnColumnEditable 
	 */
	public boolean getColumnEditable(int intColumnIndex);
	
	/**
     * Adiciona um <i>TableAbstractListener</i> à LTTable.
     * 
     * @param objTableListener
     */
    public void addTableListener(TableListener objTableListener);
    
    /**
	 * Adiciona um <i>MouseListener</i> à LTTable.
	 * 
	 * @param mouseAdapter
	 */
	public void addMouseListener(MouseAdapter mouseAdapter);
	
	/**
	 * Altera a quantidade máxima de caracteres do campo STRING.
	 * 
	 * @param strColumnName          - Nome da coluna
	 * @param intColumnMaximumLength - Quantidade máxima de caracteres
	 */
	public void setColumnStringMaximumLength(String strColumnName, int intColumnMaximumLength);
	
	/**
	 * Altera a quantidade de casas decimais do campo DOUBLE.
	 * 
	 * @param strColumnName                 - Nome da coluna
	 * @param intColumnDoubleFractionDigits - Quantidade de casas decimais
	 */
	public void setColumnDoubleFractionDigits(String strColumnName, int intColumnDoubleFractionDigits);
	
	/**
	 * Altera o alinhamento horizonal de uma coluna da tabela.
	 * 
	 * @param strColumnName          - Nome da coluna
	 * @param intHorizontalAlignment - Alinhamento Horizonal
	 * <br>
	 * <i>0 - Centralizado | 2 - Esquerda | 4 - Direita</i>
	 */
	public void setColumnHorizontalAlignment(String strColumnName, int intHorizontalAlignment);
	
	/**
	 * Permite a remoção de linhas.
	 * 
	 * @param blnAllowDeleteRow
	 */
	public void setAllowDeleteRow(boolean blnAllowDeleteRow);
	
	/**
	 * Permite a ordenação das linhas.
	 * 
	 * @param blnAllowSortedRows
	 */
	public void setAllowSortedRows(boolean blnAllowSortedRows);
	
	/**
	 * Atualiza a LTTable.<br>
	 * <br>
	 * NOTA: Quando inserimos registros em background, pode ser
	 * necessário executar este método para atualizar os renderizadores
	 * e editores da LTTable.
	 */
	public void updateTableData();
}