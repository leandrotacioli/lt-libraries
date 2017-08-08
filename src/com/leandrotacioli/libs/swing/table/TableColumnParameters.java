package com.leandrotacioli.libs.swing.table;

import com.leandrotacioli.libs.LTDataTypes;

/**
 * Estabelece as colunas que serão inseridas no LTTable.
 * 
 * @author Leandro Tacioli
 * @version 3.1 - 07/Jun/2016
 */
public class TableColumnParameters {
	private String strColumnName;
	private String strColumnDescription;
	private LTDataTypes objColumnDataType;
	private int intColumnWidth;
	private boolean blnColumnEditable;
	private int intColumnStringMaximumLength;
	private int intColumnDoubleFractionDigits;

	/**
	 * Retorna o nome da coluna.
	 * 
	 * @return strName
	 */
	protected String getColumnName() {
		return strColumnName;
	}
	
	/**
	 * Retorna a descrição da coluna que será apresentada ao usuário.
	 * 
	 * @return strColumnDescription
	 */
	protected String getColumnDescription() {
		return strColumnDescription;
	}
	
	/**
	 * Altera a descrição da coluna que será apresentada ao usuário.
	 * 
	 * @param strColumnDescription - Nova descrição
	 */
	protected void setColumnDescription(String strColumnDescription) {
		this.strColumnDescription = strColumnDescription;
	}
	
	/**
	 * Retorna o tipo de dado da coluna.
	 * 
	 * @return Tipo de dado
	 */
	protected LTDataTypes getColumnDataType() {
		return objColumnDataType;
	}
	
	/**
	 * Retorna o comprimento da coluna na visualização da tabela.
	 * 
	 * @return intColumnWidth
	 */
	protected int getColumnWidth() {
		return intColumnWidth;
	}
	
	/**
	 * Retorna o status das células da coluna. <br>
	 * <i>True</i> - Editável <br>
	 * <i>False</i> - Não Editável
	 * 
	 * @return blnColumnEditable
	 */
	protected boolean getColumnEditable() {
		return blnColumnEditable;
	}

	/**
	 * Retorna o tamanho máximo do campo em caracteres da coluna (Tipo de dados STRING).
	 * 
	 * @return intColumnMaximumLength
	 */
	protected int getColumnStringMaximumLength() {
		return intColumnStringMaximumLength;
	}
	
	/**
	 * Altera o tamanho máximo do campo em caracteres da coluna (Tipo de dados STRING).
	 * 
	 * @param intColumnStringMaximumLength
	 */
	protected void setColumnMaximumLength(int intColumnStringMaximumLength) {
		this.intColumnStringMaximumLength = intColumnStringMaximumLength;
	}
	
	/**
	 * Retorna a quantidade de casas decimais (Tipo de dados DOUBLE).
	 * 
	 * @return intColumnDoubleFractionDigits
	 */
	protected int getColumnDoubleFractionDigits() {
		return intColumnDoubleFractionDigits;
	}
	
	/**
	 * Altera a quantidade de casas decimais (Tipo de dados DOUBLE).
	 * 
	 * @param intColumnDoubleFractionDigits
	 */
	protected void setColumnDoubleFractionDigits(int intColumnDoubleFractionDigits) {
		this.intColumnDoubleFractionDigits = intColumnDoubleFractionDigits;
	}
	
	/**
	 * Estabelece as colunas que serão inseridas no LTTable.
	 * 
	 * @param strColumnName                 - Nome da coluna 
	 * @param strColumnDescription          - Descrição da coluna que será apresentada ao usuário
	 * @param objColumnDataType             - Tipo de dado da coluna
	 * @param intColumnWidth                - Comprimento da coluna na visualização da tabela
	 * @param blnColumnEditable             - Status das células da coluna (True = Editável)
	 */
	protected TableColumnParameters(String strColumnName, String strColumnDescription, LTDataTypes objColumnDataType, int intColumnWidth, boolean blnColumnEditable) {
		this(strColumnName, strColumnDescription, objColumnDataType, intColumnWidth, blnColumnEditable, 20, 2);
	}
	
	/**
	 * Estabelece as colunas que serão inseridas no LTTable.
	 * 
	 * @param strColumnName                 - Nome da coluna 
	 * @param strColumnDescription          - Descrição da coluna que será apresentada ao usuário
	 * @param objColumnDataType             - Tipo de dado da coluna
	 * @param intColumnWidth                - Comprimento da coluna na visualização da tabela
	 * @param blnColumnEditable             - Status das células da coluna (True = Editável)
	 * @param intColumnStringMaximumLength  - Quantidade máxima de caracteres (Válido apenas para colunas do tipo <i>STRING</i>).
	 * @param intColumnDoubleFractionDigits - Quantidade de casas decimais (Válido apenas para colunas do tipo <i>DOUBLE</i>).
	 */
	private TableColumnParameters(String strColumnName, String strColumnDescription, LTDataTypes objColumnDataType, int intColumnWidth, boolean blnColumnEditable, int intColumnStringMaximumLength, int intColumnDoubleFractionDigits) {
		this.strColumnName = strColumnName;
		this.strColumnDescription = strColumnDescription;
		this.objColumnDataType = objColumnDataType;
		this.intColumnWidth = intColumnWidth;
		this.blnColumnEditable = blnColumnEditable;
		this.intColumnStringMaximumLength = intColumnStringMaximumLength;
		this.intColumnDoubleFractionDigits = intColumnDoubleFractionDigits;
	}
}