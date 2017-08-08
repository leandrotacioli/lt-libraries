package com.leandrotacioli.libs.swing.table;

import java.util.ArrayList;
import java.util.List;

/**
 * Estabelece o modelo da LTTable. <br>
 * Como os objetos da tabela serão dinâmicos,
 * o desenvolvedor que irá estabelecer que 
 * tipo de dado será atribuído à cada coluna.
 * 
 * @author Leandro Tacioli
 * @version 3.0 - 05/Set/2015
 */
public class TableColumnModel {
	private List<Object> lstValues;
	
	/**
	 * Retorna o valor do objeto.
	 * 
	 * @param intColumnIndex
	 * 
	 * @return objValue
	 */
	protected Object getValue(int intColumnIndex) {
		return lstValues.get(intColumnIndex);
	}

	/**
	 * Altera o valor do objeto.
	 * 
	 * @param intPosition
	 * @param objValue
	 */
	protected void setValue(int intColumnIndex, Object objValue) {
		lstValues.set(intColumnIndex, objValue);
	}
	
	/**
	 * Estabelece o modelo da LTTable. <br>
	 * Como os objetos da tabela serão dinâmicos,
	 * o desenvolvedor que irá estabelecer que 
	 * tipo de dado será atribuído à cada coluna.
	 * 
	 * @param intTotalColumns - Total de colunas da tabela
	 */
	protected TableColumnModel(int intTotalColumns) {
		lstValues = new ArrayList<Object>();
		
		for (int indexColumns = 0; indexColumns < intTotalColumns; indexColumns++) {
			lstValues.add(new Object());
		}
	}
	
	/**
	 * Adiciona uma nova coluna ao modelo.
	 */
	protected void addColumn() {
		lstValues.add(new Object());
	}
	
	/**
	 * Exclui uma coluna do modelo.
	 * 
	 * @param intColumnIndex - Índice da coluna
	 */
	protected void deleteColumn(int intColumnIndex) {
		lstValues.remove(intColumnIndex);
	}
}