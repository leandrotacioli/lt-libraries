package com.leandrotacioli.libs.swing.table;

import java.awt.Color;

import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import com.leandrotacioli.libs.LTDataTypes;

/**
 * Estabelece as colunas que serão inseridas no LTTable.
 * 
 * @author Leandro Tacioli
 */
public class TableColumnParameters {
	private TableCellRenderer objTableCellRenderer;
	private TableCellEditor objTableCellEditor;
	private String strColumnName;
	private String strColumnDescription;
	private LTDataTypes objColumnDataType;
	private int intColumnWidth;
	private boolean blnColumnEditable;
	private boolean blnColumnShowZeroValues;
	private int intColumnHorizontalAlignment;
	private Color colorColumn;
	private int intColumnStringMaximumLength;
	private int intColumnDoubleFractionDigits;
	private boolean blnColumnDoubleShowAsPercentage;
	
	/**
	 * Retorna o 'TableCellRenderer' da coluna.
	 * 
	 * @return objTableCellRenderer
	 */
	protected TableCellRenderer getTableCellRenderer() {
		return objTableCellRenderer;
	}
	
	/**
	 * Altera o 'TableCellRenderer' da coluna.
	 * 
	 * @param objTableCellRenderer
	 */
	protected void setTableCellRenderer(TableCellRenderer objTableCellRenderer) {
		this.objTableCellRenderer = objTableCellRenderer;
	}
	
	/**
	 * Retorna o 'TableCellEditor' da coluna.
	 * 
	 * @return objTableCellEditor
	 */
	protected TableCellEditor getTableCellEditor() {
		return objTableCellEditor;
	}
	
	/**
	 * Altera o 'TableCellEditor' da coluna.
	 * 
	 * @param objTableCellEditor
	 */
	protected void setTableCellRenderer(TableCellEditor objTableCellEditor) {
		this.objTableCellEditor = objTableCellEditor;
	}
	
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
	 * Altera o comprimento da coluna na visualização da tabela.
	 *
	 * @param intColumnWidth
	 */
	protected void setColumnWidth(int intColumnWidth) {
		this.intColumnWidth = intColumnWidth;
	}
	
	/**
	 * Retorna o status das células da coluna. 
	 * <br>
	 * <i>True</i> - Editável 
	 * <br>
	 * <i>False</i> - Não Editável
	 * 
	 * @return blnColumnEditable
	 */
	protected boolean getColumnEditable() {
		return blnColumnEditable;
	}
	
	/**
	 * Retorna o status se a coluna exibe valores zerados para campos numéricos (INTEGER, LONG, DOUBLE) 
	 * 
	 * @return blnColumnShowZeroValues
	 */
	protected boolean getColumnShowZeroValues() {
		return blnColumnShowZeroValues;
	}
	
	/**
	 * Retorna a cor da coluna.
	 * 
	 * @return intColumnWidth
	 */
	protected Color getColumnColor() {
		return colorColumn;
	}
	
	/**
	 * Altera a cor da coluna.
	 * 
	 * @param colorColumn
	 */
	protected void setColumnColor(Color colorColumn) {
		this.colorColumn = colorColumn;
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
	 * Retorna o status de exibiçao do campo com uma máscara de porcentagem (Tipo de dados DOUBLE).
	 * 
	 * @return blnColumnDoubleShowAsPercentage
	 */
	protected boolean getColumnDoubleShowAsPercentage() {
		return blnColumnDoubleShowAsPercentage;
	}
	
	/**
	 * Altera o status de exibição do campo com uma máscara de porcentagem (Tipo de dados DOUBLE).
	 * 
	 * @param blnColumnDoubleShowAsPercentage
	 */
	protected void setColumnDoubleShowAsPercentage(boolean blnColumnDoubleShowAsPercentage) {
		this.blnColumnDoubleShowAsPercentage = blnColumnDoubleShowAsPercentage;
	}
	
	/**
	 * Retorna o alinhamento horizontal da coluna.
	 * 
	 * @return intColumnHorizontalAlignment
	 * <br>
	 * <i>0 - Centralizado | 2 - Esquerda | 4 - Direita</i>
	 */
	protected int getColumnHorizontalAlignment() {
		return intColumnHorizontalAlignment;
	}
	
	/**
	 * Altera o alinhamento horizontal da coluna
	 * 
	 * @param intColumnHorizontalAlignment
	 * <br>
	 * <i>0 - Centralizado | 2 - Esquerda | 4 - Direita</i>
	 */
	protected void setColumnHorizontalAlignment(int intColumnHorizontalAlignment) {
		this.intColumnHorizontalAlignment = intColumnHorizontalAlignment;
	}

	/**
	 * Estabelece as colunas que serão inseridas no LTTable.
	 *
	 * @param objTableCellRenderer         - Renderer das células da coluna
	 * @param objTableCellEditor           - Editor das células da coluna
	 * @param strColumnName                - Nome da coluna
	 * @param strColumnDescription         - Descrição da coluna que será apresentada ao usuário
	 * @param objColumnDataType            - Tipo de dado da coluna
	 * @param intColumnWidth               - Comprimento da coluna na visualização da tabela
	 * @param blnColumnEditable            - Status das células da coluna (True = Editável)
	 * @param blnColumnShowZeroValues      - Status das células da coluna para exibor valores zerados em campos numéricos
	 * @param intColumnHorizontalAlignment - Alinhamento horizontal da coluna
	 * @param colorColumn                  - Cor da coluna
	 */
	protected TableColumnParameters(TableCellRenderer objTableCellRenderer, TableCellEditor objTableCellEditor, String strColumnName, String strColumnDescription, LTDataTypes objColumnDataType, int intColumnWidth, boolean blnColumnEditable, boolean blnColumnShowZeroValues, int intColumnHorizontalAlignment, Color colorColumn) {
		this.objTableCellRenderer = objTableCellRenderer;
		this.objTableCellEditor = objTableCellEditor;
		this.strColumnName = strColumnName;
		this.strColumnDescription = strColumnDescription;
		this.objColumnDataType = objColumnDataType;
		this.intColumnWidth = intColumnWidth;
		this.blnColumnEditable = blnColumnEditable;
		this.blnColumnShowZeroValues = blnColumnShowZeroValues;
		this.intColumnHorizontalAlignment = intColumnHorizontalAlignment;
		this.colorColumn = colorColumn;
		
		// Campos DOUBLE tem 2 casas decimais como padrão
		if (objColumnDataType == LTDataTypes.DOUBLE) {
			this.intColumnDoubleFractionDigits = 2;
			this.blnColumnDoubleShowAsPercentage = false;
		}
	}
}