package com.leandrotacioli.libs.swing.table;

import java.awt.Color;
import java.awt.Component;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.RowSorter.SortKey;
import javax.swing.SortOrder;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.leandrotacioli.libs.LTDataTypes;
import com.leandrotacioli.libs.LTParameters;
import com.leandrotacioli.libs.swing.LTSwing;
import com.leandrotacioli.libs.swing.table.renderers.TableRendererBoolean;
import com.leandrotacioli.libs.swing.table.renderers.TableRendererButton;
import com.leandrotacioli.libs.swing.table.renderers.TableRendererDefault;
import com.leandrotacioli.libs.swing.table.renderers.TableRendererFixed;
import com.leandrotacioli.libs.swing.table.editors.TableEditorDate;
import com.leandrotacioli.libs.swing.table.editors.TableEditorDouble;
import com.leandrotacioli.libs.swing.table.editors.TableEditorHour;
import com.leandrotacioli.libs.swing.table.editors.TableEditorInteger;
import com.leandrotacioli.libs.swing.table.editors.TableEditorLong;
import com.leandrotacioli.libs.swing.table.editors.TableEditorString;
import com.leandrotacioli.libs.swing.table.editors.TableEditorText;

/**
 * Cria uma extensão de <i>AbstractTableModel</i> responsável pela LTTable.
 * 
 * @author Leandro Tacioli
 */
public class Table extends AbstractTableModel implements TableInterface, ActionListener {	
	private static final long serialVersionUID = 755268795533847516L;
	
	private TableExtension objTable; 
	private TableExtension objTableFixed;
	
	private TableColumnModelFixed objTableFixedModel;
	
	private List<TableColumnModel> lstColumnModel;
	private List<TableColumnParameters> lstColumnParameters;
	
	private Collection<Object> collectionListener;
	
	private MouseAdapter mouseAdapter;

	private boolean blnReadOnly;
	private boolean blnAllowDeleteRow;
	private boolean blnAllowSortedRows;
	
	private boolean blnFullRowSelection;
	
	private boolean blnCellValueUpdated;         // Utilizado exclusivamente para notificar o 'TableAbstractListener' de uma atualização do valor em alguma célula
	private boolean blnTableRowSelection;        // A seleção foi feita através da tabela normal
	private boolean blnTableFixedRowSelection;   // A seleção foi feita através da tabela fixa
	
	private int[] rowsSelectionFixed;
	
	private final String ID_ROW_LT_TABLE = "ID_ROW_LT_TABLE";  // Nome da coluna de ID da LTTable
	private int intIdRowTable;                                 // Última ID das linhas da LTTable
	private boolean blnSetIdRowTable;                          // Permissão para alterar ID das linhas da LTTable
	
	/**
	 * Retorna a LTTable.
	 * 
	 * @return objTable
	 */
	protected TableExtension getTable() {
		return objTable;
	}
	
	/**
	 * Retorna a LTTable fixa.
	 * 
	 * @return objTableFixed
	 */
	protected TableExtension getTableFixed() {
		return objTableFixed;
	}
	
	/**
	 * Cria uma extensão de <i>AbstractTableModel</i>.
	 * 
	 * @param blnReadOnly
	 * @param blnAllowDeleteRow
	 */
	protected Table(boolean blnReadOnly, boolean blnAllowDeleteRow) {
		this.blnReadOnly = blnReadOnly;
		this.blnAllowDeleteRow = blnAllowDeleteRow;
		
		lstColumnModel = new ArrayList<TableColumnModel>();
		lstColumnParameters = new ArrayList<TableColumnParameters>();

		collectionListener = new ArrayList<Object>();
		
		// Adiciona uma coluna que servirá de ID para manipulação das linhas da tabela
		addColumn(ID_ROW_LT_TABLE, ID_ROW_LT_TABLE, LTDataTypes.INTEGER, 0, false);
		
		intIdRowTable = 0;
		blnSetIdRowTable = false;
		blnAllowSortedRows = true;
	}
	
	/**
	 * Cria a tabela.
	 */
	protected void createTable() {
		setTableProperties();
		setTableFixedProperties();
		setTableColumnDescriptions();
		setTableColumnWidth();
		setTableRenderersEditors();
	}
	
	/**
	 * Estabele as propriedades principais da tabela.
	 */
	private void setTableProperties() {	
		objTable = new TableExtension(this, blnReadOnly);
		objTable.setAutoCreateRowSorter(blnAllowSortedRows);
		objTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		objTable.setCellSelectionEnabled(false);
		objTable.setRowHeight(LTSwing.getInstance().getTableRowHeight());
		objTable.setFont(LTSwing.getInstance().getFontTableTextField());
		objTable.setBorder(LTSwing.getInstance().getBorderTableTextField());
		objTable.setGridColor(LTSwing.getInstance().getColorTableGrid());
		objTable.getTableHeader().setReorderingAllowed(blnAllowSortedRows);
		objTable.getTableHeader().setFont(LTSwing.getInstance().getFontTableHeader());
		objTable.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
		
		// Configura ordenação da LTTable
		setAllowSortedRows(blnAllowSortedRows);
		
		// Irá alterar o foco da célula quando uma tecla específica for pressionada
		objTable.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent event) {
				
			}
			
			@Override
			public void keyReleased(KeyEvent event) {
				
			}
			
			@Override
			public void keyPressed(KeyEvent event) {
				int intKeyCode = event.getKeyCode();
				int intRowIndex = objTable.getSelectionModel().getLeadSelectionIndex();
				int intColumnIndex = objTable.getColumnModel().getSelectionModel().getLeadSelectionIndex();
				
				// Tecla TAB
				if (intKeyCode == KeyEvent.VK_TAB) {
					if (intRowIndex == getRowCount() - 1) { 
						if (intColumnIndex == getColumnCount() - 1) {
							objTable.changeSelection(-1, -1, false, false);
						}
					} else {
						if (intColumnIndex == getColumnCount() - 1) {
							objTable.changeSelection(intRowIndex, -1, false, false);
						}
					}
					
				// Barra de espaço
				} else if (intKeyCode == 32) {
					// Se a barra de espaço for pressionada, o valor muda apenas para coluna BOOLEAN
					if (lstColumnParameters.get(intColumnIndex).getColumnDataType() == LTDataTypes.BOOLEAN) {
						if (lstColumnParameters.get(intColumnIndex).getColumnEditable()) {
							if (!(boolean) objTable.getValueAt(intRowIndex, intColumnIndex)) {
								objTable.setValueAt(true, intRowIndex, intColumnIndex);
							} else {
								objTable.setValueAt(false, intRowIndex, intColumnIndex);
							}
						}
					}
				}
			}
		});
		
		// MouseListener
		objTable.addMouseListener(new MouseAdapter() {
			@Override
		    public void mouseClicked(MouseEvent event) {
				allowFullRowSelection(blnReadOnly);
		    }

			@Override
			public void mousePressed(MouseEvent event) {
				allowFullRowSelection(blnReadOnly);
			}
		});
		
		objTable.addMouseListener(mouseAdapter);
		
		objTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
		    @Override
		    public void valueChanged(ListSelectionEvent event) {
		    	if (!blnReadOnly && !blnTableFixedRowSelection) {
		    		allowFullRowSelection(false);
		    		
		    	} else if (blnReadOnly && !blnTableFixedRowSelection) {
			    	int[] rowsSelected = objTable.getSelectedRows();
			    	
			    	blnTableRowSelection = true;
			    	
			    	if (rowsSelected.length > 0) {
			    		objTableFixed.setRowSelectionAllowed(true);
			    		objTableFixed.setRowSelectionInterval(rowsSelected[0], rowsSelected[rowsSelected.length - 1]);
			    	}
			        
			        blnTableRowSelection = false;
		    	}
		    }
		});
		
		KeyStroke keyCopy = KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK, false);
		KeyStroke keyPaste = KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK, false);
		
		objTable.registerKeyboardAction(this, "Copy_Table", keyCopy, JComponent.WHEN_FOCUSED);
		objTable.registerKeyboardAction(this, "Paste_Table", keyPaste, JComponent.WHEN_FOCUSED);
	}

	/**
	 * Estabele as propriedades principais da tabela fixa.
	 * <br>
	 * Essa tabela fixa significa ter uma coluna adicional antes 
	 * de todas as linhas, mas a única funcionalidade permitida é selecionar essa coluna.
	 */
	private void setTableFixedProperties() {
		objTableFixedModel = new TableColumnModelFixed(getRowCount());
		
		objTableFixed = new TableExtension(objTableFixedModel, false);
		objTableFixed.setAutoCreateColumnsFromModel(false);
		objTableFixed.setGridColor(LTSwing.getInstance().getColorTableGrid());
		objTableFixed.setRowHeight(LTSwing.getInstance().getTableRowHeight());
        objTableFixed.getColumnModel().getColumn(0).setPreferredWidth(LTSwing.getInstance().getTableRowHeight());
		objTableFixed.getColumnModel().getColumn(0).setMinWidth(LTSwing.getInstance().getTableRowHeight());
		objTableFixed.getColumnModel().getColumn(0).setMaxWidth(LTSwing.getInstance().getTableRowHeight());
		objTableFixed.getColumnModel().getColumn(0).setCellRenderer(new TableRendererFixed());
		objTableFixed.setPreferredScrollableViewportSize(objTableFixed.getPreferredSize());

		// Remove alguma linha caso solicitado pelo usuário através do botão DELETE.
		objTableFixed.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent event) {
				
			}
			
			@Override
			public void keyReleased(KeyEvent event) {
				
			}
			
			@Override
			public void keyPressed(KeyEvent event) {
				if (!blnReadOnly && blnFullRowSelection) {
					int intKeyCode = event.getKeyCode();

					if (intKeyCode == KeyEvent.VK_DELETE) {
						// Se mais de uma linha está sendo removida, é necessário remover da última para a primeira linha
						if (rowsSelectionFixed.length > 0) {
							blnTableRowSelection = true;
							
							for (int rowIndex = rowsSelectionFixed.length - 1; rowIndex >= 0; rowIndex--) {
								deleteRow(rowsSelectionFixed[rowIndex]);
							}
							
							allowFullRowSelection(false);
							
							blnTableRowSelection = false;
						}
						
					} else if (intKeyCode == KeyEvent.VK_ESCAPE) {
						allowFullRowSelection(false);
					}
				}
			}
		});
		
		objTableFixed.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
		    @Override
		    public void valueChanged(ListSelectionEvent event) {
		    	if (!blnTableRowSelection) {
			    	allowFullRowSelection(true);
			    	
			    	blnTableFixedRowSelection = true;
			        
			        rowsSelectionFixed = objTableFixed.getSelectedRows();
			        
			        // Informa aos registros da tabela que eles foram selecionados
			        if (rowsSelectionFixed.length > 0) {
				    	objTable.setRowSelectionInterval(rowsSelectionFixed[0], rowsSelectionFixed[0]);
				    	
				    	for (int indexSelectionFixed = 1; indexSelectionFixed < rowsSelectionFixed.length; indexSelectionFixed++) {
				    		objTable.addRowSelectionInterval(rowsSelectionFixed[indexSelectionFixed], rowsSelectionFixed[indexSelectionFixed]);
				    	}
				    	
			        } else {
			        	allowFullRowSelection(false);
			        }

			    	blnTableFixedRowSelection = false;
		    	}
		    }
		});
	}
	
	/**
	 * Atualiza as descrições das colunas da tabela.
	 */
	private void setTableColumnDescriptions() {
		for (int indexColumn = 0; indexColumn < getColumnCount(); indexColumn++) {
			objTable.getColumnModel().getColumn(indexColumn).setHeaderValue(lstColumnParameters.get(indexColumn).getColumnDescription());
		}
		
		objTable.getTableHeader().repaint();
	}
	
	/**
	 * Estabelece o comprimento das colunas na visualização da tabela.
	 */
	private void setTableColumnWidth() {
		for (int columnIndex = 0; columnIndex < getColumnCount(); columnIndex++) {
			if (lstColumnParameters.get(columnIndex).getColumnWidth() == 0) {
				objTable.getColumnModel().getColumn(columnIndex).setPreferredWidth(0);
				objTable.getColumnModel().getColumn(columnIndex).setMinWidth(0);
				objTable.getColumnModel().getColumn(columnIndex).setMaxWidth(0);
			} else {
				objTable.getColumnModel().getColumn(columnIndex).setPreferredWidth(lstColumnParameters.get(columnIndex).getColumnWidth());
			}
		}
	}
	
	/**
	 * Estabelece os renderers e editors da tabela.
	 */
	private void setTableRenderersEditors() {
		for (int intColumnIndex = 0; intColumnIndex < getColumnCount(); intColumnIndex++) {
			TableCellRenderer objTableCellRenderer = setCellRenderer(lstColumnParameters.get(intColumnIndex));
			TableCellEditor objTableCellEditor = setCellEditor(lstColumnParameters.get(intColumnIndex));
			
			objTable.getColumnModel().getColumn(intColumnIndex).setCellRenderer(objTableCellRenderer);
			objTable.getColumnModel().getColumn(intColumnIndex).setCellEditor(objTableCellEditor);
			
			if (lstColumnParameters.get(intColumnIndex).getColumnDataType() == LTDataTypes.BUTTON) {
				TableRendererButton objTableRendererButton = new TableRendererButton(objTable, intColumnIndex);
				
				objTable.getColumnModel().getColumn(intColumnIndex).setCellRenderer(objTableRendererButton);
				objTable.getColumnModel().getColumn(intColumnIndex).setCellEditor(objTableRendererButton);
			}
    	}
	}

	/**
	 * Cria os renderizadores das colunas da tabela.
	 *
	 * @param objDataType
	 *
	 * @return objTableCellRenderer
	 */
	private TableCellRenderer setCellRenderer(LTDataTypes objDataType) {
		TableCellRenderer objTableCellRenderer = null;
		
		if (objDataType == LTDataTypes.INTEGER) {
			objTableCellRenderer = new TableRendererDefault(LTDataTypes.INTEGER, blnReadOnly, blnFullRowSelection, getColumnHorizontalAlignment(LTDataTypes.INTEGER), LTSwing.getInstance().getColorTable());
		} else if (objDataType == LTDataTypes.LONG) {
			objTableCellRenderer = new TableRendererDefault(LTDataTypes.LONG, blnReadOnly, blnFullRowSelection, getColumnHorizontalAlignment(LTDataTypes.LONG), LTSwing.getInstance().getColorTable());
		} else if (objDataType == LTDataTypes.DOUBLE) {
			objTableCellRenderer = new TableRendererDefault(LTDataTypes.DOUBLE, blnReadOnly, blnFullRowSelection, getColumnHorizontalAlignment(LTDataTypes.DOUBLE), LTSwing.getInstance().getColorTable());
		} else if (objDataType == LTDataTypes.STRING) {
			objTableCellRenderer = new TableRendererDefault(LTDataTypes.STRING, blnReadOnly, blnFullRowSelection, getColumnHorizontalAlignment(LTDataTypes.STRING), LTSwing.getInstance().getColorTable());
		} else if (objDataType == LTDataTypes.TEXT) {
			objTableCellRenderer = new TableRendererDefault(LTDataTypes.TEXT, blnReadOnly, blnFullRowSelection, getColumnHorizontalAlignment(LTDataTypes.TEXT), LTSwing.getInstance().getColorTable());
		} else if (objDataType == LTDataTypes.DATE) {
			objTableCellRenderer = new TableRendererDefault(LTDataTypes.DATE, blnReadOnly, blnFullRowSelection, getColumnHorizontalAlignment(LTDataTypes.DATE), LTSwing.getInstance().getColorTable());
		} else if (objDataType == LTDataTypes.HOUR) {
			objTableCellRenderer = new TableRendererDefault(LTDataTypes.HOUR, blnReadOnly, blnFullRowSelection, getColumnHorizontalAlignment(LTDataTypes.HOUR), LTSwing.getInstance().getColorTable());
		} else if (objDataType == LTDataTypes.BOOLEAN) {
			objTableCellRenderer = new TableRendererBoolean(blnReadOnly, blnFullRowSelection, LTSwing.getInstance().getColorTable());
		}
		
		return objTableCellRenderer;
	}

	/**
	 * Altera os renderizadores das colunas da tabela.
	 *
	 * @param objTableColumnParameters
	 *
	 * @return objTableCellRenderer
	 */
	private TableCellRenderer setCellRenderer(TableColumnParameters objTableColumnParameters) {
		TableCellRenderer objTableCellRenderer = null;
		
		if (objTableColumnParameters.getColumnDataType() == LTDataTypes.INTEGER) {
			objTableCellRenderer = new TableRendererDefault(LTDataTypes.INTEGER, blnReadOnly, blnFullRowSelection, objTableColumnParameters.getColumnHorizontalAlignment(), objTableColumnParameters.getColumnColor());
		} else if (objTableColumnParameters.getColumnDataType() == LTDataTypes.LONG) {
			objTableCellRenderer = new TableRendererDefault(LTDataTypes.LONG, blnReadOnly, blnFullRowSelection, objTableColumnParameters.getColumnHorizontalAlignment(), objTableColumnParameters.getColumnColor());
		} else if (objTableColumnParameters.getColumnDataType() == LTDataTypes.DOUBLE) {
			TableRendererDefault objTableRendererDefault = new TableRendererDefault(LTDataTypes.DOUBLE, blnReadOnly, blnFullRowSelection, objTableColumnParameters.getColumnHorizontalAlignment(), objTableColumnParameters.getColumnColor());
			objTableRendererDefault.setColumnDoubleFractionDigits(objTableColumnParameters.getColumnDoubleFractionDigits());
			objTableRendererDefault.setColumnDoubleShowAsPercentage(objTableColumnParameters.getColumnDoubleShowAsPercentage());
			objTableCellRenderer = objTableRendererDefault;
		} else if (objTableColumnParameters.getColumnDataType() == LTDataTypes.STRING) {
			objTableCellRenderer = new TableRendererDefault(LTDataTypes.STRING, blnReadOnly, blnFullRowSelection, objTableColumnParameters.getColumnHorizontalAlignment(), objTableColumnParameters.getColumnColor());
		} else if (objTableColumnParameters.getColumnDataType() == LTDataTypes.TEXT) {
			objTableCellRenderer = new TableRendererDefault(LTDataTypes.TEXT, blnReadOnly, blnFullRowSelection, objTableColumnParameters.getColumnHorizontalAlignment(), objTableColumnParameters.getColumnColor());
		} else if (objTableColumnParameters.getColumnDataType() == LTDataTypes.DATE) {
			objTableCellRenderer = new TableRendererDefault(LTDataTypes.DATE, blnReadOnly, blnFullRowSelection, objTableColumnParameters.getColumnHorizontalAlignment(), objTableColumnParameters.getColumnColor());
		} else if (objTableColumnParameters.getColumnDataType() == LTDataTypes.HOUR) {
			objTableCellRenderer = new TableRendererDefault(LTDataTypes.HOUR, blnReadOnly, blnFullRowSelection, objTableColumnParameters.getColumnHorizontalAlignment(), objTableColumnParameters.getColumnColor());
		} else if (objTableColumnParameters.getColumnDataType() == LTDataTypes.BOOLEAN) {
			objTableCellRenderer = new TableRendererBoolean(blnReadOnly, blnFullRowSelection, objTableColumnParameters.getColumnColor());
		}
		
		return objTableCellRenderer;
	}
	
	/**
	 * Cria os editores das colunas da tabela.
	 */
	private TableCellEditor setCellEditor(LTDataTypes objDataType) {
		TableCellEditor objTableCellEditor = null;
		
		if (objDataType == LTDataTypes.INTEGER) {
			objTableCellEditor = new TableEditorInteger();
		} else if (objDataType == LTDataTypes.LONG) {
			objTableCellEditor = new TableEditorLong();
		} else if (objDataType == LTDataTypes.DOUBLE) {
			objTableCellEditor = new TableEditorDouble();
		} else if (objDataType == LTDataTypes.STRING) {
			objTableCellEditor = new TableEditorString();
		} else if (objDataType == LTDataTypes.TEXT) {
			objTableCellEditor = new TableEditorText();
		} else if (objDataType == LTDataTypes.DATE) {
			objTableCellEditor = new TableEditorDate();
		} else if (objDataType == LTDataTypes.HOUR) {
			objTableCellEditor = new TableEditorHour();
		} else if (objDataType == LTDataTypes.BOOLEAN) {
			objTableCellEditor = null;
		}
		
		return objTableCellEditor;
	}
	
	/**
	 * Altera os editores de uma coluna da tabela.
	 */
	private TableCellEditor setCellEditor(TableColumnParameters objTableColumnParameters) {
		TableCellEditor objTableCellEditor = null;
		
		if (objTableColumnParameters.getColumnDataType() == LTDataTypes.INTEGER) {
			objTableCellEditor = new TableEditorInteger(objTableColumnParameters.getColumnHorizontalAlignment());
		} else if (objTableColumnParameters.getColumnDataType() == LTDataTypes.LONG) {
			objTableCellEditor = new TableEditorLong(objTableColumnParameters.getColumnHorizontalAlignment());
		} else if (objTableColumnParameters.getColumnDataType() == LTDataTypes.DOUBLE) {
			objTableCellEditor = new TableEditorDouble(objTableColumnParameters.getColumnHorizontalAlignment(), objTableColumnParameters.getColumnDoubleFractionDigits(), objTableColumnParameters.getColumnDoubleShowAsPercentage());
		} else if (objTableColumnParameters.getColumnDataType() == LTDataTypes.STRING) {
			objTableCellEditor = new TableEditorString(objTableColumnParameters.getColumnHorizontalAlignment(), objTableColumnParameters.getColumnStringMaximumLength());
		} else if (objTableColumnParameters.getColumnDataType() == LTDataTypes.TEXT) {
			objTableCellEditor = new TableEditorText(objTableColumnParameters.getColumnHorizontalAlignment());
		} else if (objTableColumnParameters.getColumnDataType() == LTDataTypes.DATE) {
			objTableCellEditor = new TableEditorDate(objTableColumnParameters.getColumnHorizontalAlignment());
		} else if (objTableColumnParameters.getColumnDataType() == LTDataTypes.HOUR) {
			objTableCellEditor = new TableEditorHour(objTableColumnParameters.getColumnHorizontalAlignment());
		} else if (objTableColumnParameters.getColumnDataType() == LTDataTypes.BOOLEAN) {
			objTableCellEditor = null;
		}
		
		return objTableCellEditor;
	}

	private TableRowSorter<TableModel> setTableSorter() {
		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(objTable.getModel());

		for (int intIndex = 0; intIndex < getColumnCount(); intIndex++) {
			Comparator<?> sorterComparator = setTableColumnSorterComparator(sorter, intIndex, lstColumnParameters.get(intIndex).getColumnDataType());

			if (sorterComparator != null) {
				sorter.setComparator(intIndex, sorterComparator);
			}
		}

		return sorter;
	}

	/**
	 * Cria um comparador para ordenar corretamente os valores das colunas.
	 *
	 * @param sorter
	 * @param intColumnIndex
	 * @param objDataType
	 *
	 * @return
	 */
	private Comparator<?> setTableColumnSorterComparator(TableRowSorter<TableModel> sorter, int intColumnIndex, LTDataTypes objDataType) {
		if (objDataType == LTDataTypes.INTEGER) {
			return new TableColumnComparator<Integer>(sorter, intColumnIndex);
		} else if (objDataType == LTDataTypes.LONG) {
			return new TableColumnComparator<Long>(sorter, intColumnIndex);
		} else if (objDataType == LTDataTypes.DOUBLE) {
			return new TableColumnComparator<Double>(sorter, intColumnIndex);
		} else if (objDataType == LTDataTypes.DATE) {
			return new TableColumnComparator<Date>(sorter, intColumnIndex);
		}

		return null;
	}
	
	/**
	 * Notifica o 'TableAbstractListener' de uma atualização do valor em alguma célula.
	 * 
	 * @param objValue       - Valor atualizado
	 * @param intRowIndex    - Linha atualizada
	 * @param intColumnIndex - Coluna atualizada
	 */
	private void updateCellValue(Object objValue, int intRowIndex, int intColumnIndex) {
        Iterator<Object> it = collectionListener.iterator();
        TableListener objTableListener;
        
        while (it.hasNext()) {
        	objTableListener = (TableListener) it.next();
        	objTableListener.cellValueUpdated(objValue, intRowIndex, intColumnIndex);
        }
	}
	
	/**
	 * Permite a seleção de linhas inteiras da tabela.
	 * 
	 * @param blnFullRowSelection
	 */
	private void allowFullRowSelection(boolean blnFullRowSelection) {
		this.blnFullRowSelection = blnFullRowSelection;
		
		objTable.setRowSelectionAllowed(blnFullRowSelection);
		objTableFixed.setRowSelectionAllowed(blnFullRowSelection);
		
		setTableRenderersEditors();
	}
	
	/**
	 * Retorna o alinhamento padrão para as colunas da tabela. 
	 * 
	 * @param objColumnDataType
	 * 
	 * @return intHorizontalAlignment
	 */
	private int getColumnHorizontalAlignment(LTDataTypes objColumnDataType) {
		int intHorizontalAlignment = SwingConstants.LEFT;
		
		if (objColumnDataType == LTDataTypes.INTEGER || objColumnDataType == LTDataTypes.LONG || objColumnDataType == LTDataTypes.DOUBLE) {
			intHorizontalAlignment = SwingConstants.RIGHT;
		}
		
		return intHorizontalAlignment;
	}
	
	// *********************************************************************
	// Implementa TableInterface
	public void addColumn(String strColumnName, String strColumnDescription, LTDataTypes objColumnDataType, int intColumnWidth, boolean blnColumnEditable) {
		addColumn(strColumnName, strColumnDescription, objColumnDataType, intColumnWidth, blnColumnEditable, true);
	}
	
	@Override
	public void addColumn(String strColumnName, String strColumnDescription, LTDataTypes objColumnDataType, int intColumnWidth, boolean blnColumnEditable, boolean blnColumnShowZeroValues) {
		try {
			if (strColumnName.equals(ID_ROW_LT_TABLE) && getColumnCount() != 0) {
				 throw new Exception("Cannot create column named: " + ID_ROW_LT_TABLE + ". Try another column name.");
			} else {
				TableCellRenderer objTableCellRenderer = setCellRenderer(objColumnDataType);
				TableCellEditor objTableCellEditor = setCellEditor(objColumnDataType);
				TableColumnParameters objTableColumnParameters = new TableColumnParameters(objTableCellRenderer, objTableCellEditor, strColumnName, strColumnDescription, objColumnDataType, intColumnWidth, blnColumnEditable, blnColumnShowZeroValues, getColumnHorizontalAlignment(objColumnDataType), LTSwing.getInstance().getColorTable());
				
				lstColumnParameters.add(objTableColumnParameters);
				
				// Caso a tabela já tenha sido criada, faz os ajustes nos modelos e colunas
				if (objTable != null) {
					objTable.getColumnModel().addColumn(new TableColumn(getColumnCount() - 1));
					
					// Adiciona a nova coluna a todos os registros do modelo
					for (int indexModel = 0; indexModel < lstColumnModel.size(); indexModel++) {
						lstColumnModel.get(indexModel).addColumn();
					}
		
					// Insere valores default para a nova coluna
					for (int indexRow = 0; indexRow < getRowCount(); indexRow++) {
						setValueAt(null, indexRow, getColumnCount() - 1);
					}
		
					setTableColumnDescriptions();
					setTableColumnWidth();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void addRow() {
		if (blnAllowSortedRows) {
			TableRowSorter<TableModel> sorter = setTableSorter();

			objTable.setRowSorter(sorter);
		}
		
		lstColumnModel.add(new TableColumnModel(getColumnCount()));
		
		fireTableRowsInserted(getRowCount() - 1, getRowCount() - 1);
		
		// Insere valores default para os colunas
		blnSetIdRowTable = true;
		
		for (int columnIndex = 0; columnIndex < getColumnCount(); columnIndex++) {
			setValueAt(null, getRowCount() - 1, columnIndex);
		}
		
		objTableFixedModel.setRowCount(getRowCount());
		
		// Atribui um ID para a linha nova
		addRowData(ID_ROW_LT_TABLE, intIdRowTable);
		
		blnSetIdRowTable = false;
		
		intIdRowTable = intIdRowTable + 1;
	}
	
	@Override
	public void addRowData(String strColumnName, Object objValue) {
		for (int columnIndex = 0; columnIndex < getColumnCount(); columnIndex++) {
			if (lstColumnParameters.get(columnIndex).getColumnName().equals(strColumnName)) {
				blnCellValueUpdated = true;
				
				setValueAt(objValue, getRowCount() - 1, columnIndex);
				
				blnCellValueUpdated = false;
				break;
			}
		}
	}

	@Override
	public void deleteRow(int intRowIndex) {
		allowFullRowSelection(false);
		
		int intIdRowToDelete = (int) getValue(intRowIndex, ID_ROW_LT_TABLE);
		
		if (blnAllowDeleteRow) {
			if (lstColumnModel.size() > 0) {
				// Deleta a coluna do modelo
				for (int intColumnIndex = 0; intColumnIndex < lstColumnModel.size(); intColumnIndex++) {
					int intIdRowModel = (int) lstColumnModel.get(intColumnIndex).getValue(0);
					
					if (intIdRowToDelete == intIdRowModel) {
						lstColumnModel.remove(intColumnIndex);
					}
				}
				
				fireTableRowsDeleted(intRowIndex, intRowIndex);
				
				objTableFixedModel.setRowCount(getRowCount());
			}
		}
	}
	
	@Override
	public void deleteRows() {
		allowFullRowSelection(false);
		
		if (lstColumnModel.size() > 0) {
			fireTableRowsDeleted(0, lstColumnModel.size() - 1);
		}
		
		lstColumnModel = new ArrayList<TableColumnModel>();

		objTableFixedModel.setRowCount(getRowCount());
		
		objTable.clearRowColor();
		
		// Zera o ID das linhas
		intIdRowTable = 0;
		
		updateTableData();
	}
	
	@Override
	public void deleteColumn(String strColumnName) {
		try {
			if (strColumnName.equals(ID_ROW_LT_TABLE)) {
				 throw new Exception("Cannot delete column: " + ID_ROW_LT_TABLE + ".");
			} else {
				for (int columnIndex = 0; columnIndex < getColumnCount(); columnIndex++) {
					if (lstColumnParameters.get(columnIndex).getColumnName().equals(strColumnName)) {
						// Remove a coluna do modelo
						for (int indexModel = 0; indexModel < lstColumnModel.size(); indexModel++) {
							lstColumnModel.get(indexModel).deleteColumn(columnIndex);
						}
						
						// Remove a coluna da lista de parâmetros
						lstColumnParameters.remove(columnIndex);
						
						fireTableStructureChanged();
						
						setTableColumnDescriptions();
						setTableColumnWidth();
						
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void deleteColumns() {
		deleteRows();
		
		lstColumnModel = new ArrayList<TableColumnModel>();
		lstColumnParameters = new ArrayList<TableColumnParameters>();
		
		// Adiciona a coluna de ID novamente
		addColumn(ID_ROW_LT_TABLE, ID_ROW_LT_TABLE, LTDataTypes.INTEGER, 0, false);
		
		fireTableStructureChanged();
	}
	
	@Override
	public int getColumnCount() {
		return lstColumnParameters.size();   // A coluna 'ID_ROW_LT_TABLE' é considerada na contagem
	}
	
	@Override
	public String getColumnName(int intColumnIndex) {
		return lstColumnParameters.get(intColumnIndex).getColumnName();
	}
	
	@Override
	public String getColumnDescription(int intColumnIndex) {
		return lstColumnParameters.get(intColumnIndex).getColumnDescription();
	}
	
	@Override
	public void setColumnDescription(String strColumnName, String strColumnDescription) {
		for (int columnIndex = 0; columnIndex < getColumnCount(); columnIndex++) {
			if (lstColumnParameters.get(columnIndex).getColumnName().equals(strColumnName)) {
				lstColumnParameters.get(columnIndex).setColumnDescription(strColumnDescription);
				
				setTableColumnDescriptions();

				break;
			}
		}
	}
	
	@Override
    public Class<?> getColumnClass(int intColumnIndex) {
    	Class<?> classType = null;
    	
    	if (getColumnCount() > 0) {
	    	LTDataTypes objDataType = lstColumnParameters.get(intColumnIndex).getColumnDataType();

	    	if (objDataType == LTDataTypes.INTEGER) {
	    		classType = Integer.class;
	    	} else if (objDataType == LTDataTypes.LONG) {
	        	classType = Long.class;
	    	} else if (objDataType == LTDataTypes.DOUBLE) {
	  			classType = Double.class;
	    	} else if (objDataType == LTDataTypes.STRING) {
	  			classType = String.class;
	    	} else if (objDataType == LTDataTypes.TEXT) {
	  			classType = String.class;
	    	} else if (objDataType == LTDataTypes.DATE) {
	  			classType = Date.class;
	    	} else if (objDataType == LTDataTypes.HOUR) {
	  			classType = String.class;
	    	} else if (objDataType == LTDataTypes.BOOLEAN) {
	  			classType = Boolean.class;
	    	} else if (objDataType == LTDataTypes.BUTTON) {
	  			classType = JButton.class;
	  		}
    	}
  		
    	return classType;
    }
	
	@Override
	public void setColumnColor(int intColumnIndex, Color color) {
		lstColumnParameters.get(intColumnIndex).setColumnColor(color);
		
		if (objTable != null) {
			TableCellRenderer objTableCellRenderer = setCellRenderer(lstColumnParameters.get(intColumnIndex));
			
			objTable.getColumnModel().getColumn(intColumnIndex).setCellRenderer(objTableCellRenderer);
			
			fireTableDataChanged();	
		}
	}
			
	@Override
	public void setColumnColor(String strColumnName, Color color) {
		for (int intColumnIndex = 0; intColumnIndex < getColumnCount(); intColumnIndex++) {
			if (lstColumnParameters.get(intColumnIndex).getColumnName().equals(strColumnName)) {
				setColumnColor(intColumnIndex, color);
				
				break;
			}
		}
	}
	
	@Override
	public void orderColumnData(int intColumnIndex, boolean blnAscending) {
		if (blnAllowSortedRows) {
			List<SortKey> sortKeys = new ArrayList<SortKey>();

			if (blnAscending) {
				sortKeys.add(new SortKey(intColumnIndex, SortOrder.ASCENDING));
			} else {
				sortKeys.add(new SortKey(intColumnIndex, SortOrder.DESCENDING));
			}

			TableRowSorter<TableModel> sorter = setTableSorter();
			sorter.setSortKeys(sortKeys);
			sorter.sort();

			objTable.setRowSorter(sorter);
		}
	}
	
	@Override
	public void orderColumnData(String strColumnName, boolean blnAscending) {
		if (blnAllowSortedRows) {
			for (int indexColumn = 0; indexColumn < getColumnCount(); indexColumn++) {
				if (lstColumnParameters.get(indexColumn).getColumnName().equals(strColumnName)) {
					orderColumnData(indexColumn, blnAscending);
					
					break;
				}
			}
		}
	}
	
	@Override
	public int getRowCount() {
		return lstColumnModel.size();
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
	public void setRowColor(int intRowIndex, Color color) {
		int intRowId = (int) getValue(intRowIndex, "ID_ROW_LT_TABLE");
		
		objTable.setRowColor(intRowId, color);
	}
	
	@Override
	public void setRowSelection(int intRowIndex) {
    	objTable.setRowSelectionAllowed(true);
    	objTable.setRowSelectionInterval(intRowIndex, intRowIndex);
    	
    	objTableFixed.setRowSelectionAllowed(true);
    	objTableFixed.setRowSelectionInterval(intRowIndex, intRowIndex);
	}
	
	@Override
	public void setRowSelectionInterval(int intInitialRowIndex, int intFinalRowIndex) {
		objTable.setRowSelectionAllowed(true);
		objTable.setRowSelectionInterval(intInitialRowIndex, intFinalRowIndex);
		
		objTableFixed.setRowSelectionAllowed(true);
    	objTableFixed.setRowSelectionInterval(intInitialRowIndex, intFinalRowIndex);
	}
	
	@Override
	public void clearSelection() {
		objTable.clearSelection();
		objTableFixed.clearSelection();
	}
	
	@Override
	public Object getValue(int intRowIndex, int intColumnIndex) {
		return objTable.getModel().getValueAt(objTable.convertRowIndexToModel(intRowIndex), objTable.convertColumnIndexToModel(intColumnIndex));
	}
	
	@Override
	public Object getValue(int intRowIndex, String strColumnName) {
		Object objValue = null;
		
		for (int intColumnIndex = 0; intColumnIndex < getColumnCount(); intColumnIndex++) {
			if (lstColumnParameters.get(intColumnIndex).getColumnName().equals(strColumnName)) {
				objValue = getValue(intRowIndex, intColumnIndex);
				break;
			}
		}

		return objValue;
	}
	
	@Override
	public Object getValueAt(int intRowIndex, int intColumnIndex) {
		return lstColumnModel.get(intRowIndex).getValue(intColumnIndex);
	}
	
	@Override
	public void setValueAt(Object objValue, int intRowIndex, int intColumnIndex) {
		try {
			if (intColumnIndex == 0 && !blnSetIdRowTable) {
				throw new Exception("Cannot set values for column: " + ID_ROW_LT_TABLE + ".");
			} else {
				LTDataTypes objDataType = lstColumnParameters.get(intColumnIndex).getColumnDataType();
				boolean blnShowZeroValues = lstColumnParameters.get(intColumnIndex).getColumnShowZeroValues();
				
				lstColumnModel.get(intRowIndex).setValue(intColumnIndex, "");
				
				if (objDataType == LTDataTypes.INTEGER) {
					if (blnShowZeroValues) {
						if (objValue == null || objValue.equals("")) {
							objValue = 0;
						}
						
						lstColumnModel.get(intRowIndex).setValue(intColumnIndex, Integer.parseInt(objValue.toString()));
					} else {
						if (objValue != null && !objValue.equals("")) {
							lstColumnModel.get(intRowIndex).setValue(intColumnIndex, Integer.parseInt(objValue.toString()));
						}
					}
					
				} else if (objDataType == LTDataTypes.LONG) {
					if (blnShowZeroValues) {
						if (objValue == null || objValue.equals("")) {
							objValue = 0;
						}
						
						lstColumnModel.get(intRowIndex).setValue(intColumnIndex, Long.parseLong(objValue.toString()));
					} else {
						if (objValue != null && !objValue.equals("")) {
							lstColumnModel.get(intRowIndex).setValue(intColumnIndex, Long.parseLong(objValue.toString()));
						}
					}
			    	
			    } else if (objDataType == LTDataTypes.DOUBLE) {
			    	if (blnShowZeroValues) {
				    	if (objValue == null || objValue.equals("")) {
				    		objValue = 0;
				    	} else {
			    			String strDouble = objValue.toString();
			    			strDouble = strDouble.replace(",", ".");
			    			strDouble = strDouble.replace("%", "");
			    			objValue = strDouble;
				    	}
				    	
				    	lstColumnModel.get(intRowIndex).setValue(intColumnIndex, new BigDecimal(objValue.toString()));
			    	} else {
			    		if (objValue != null && !objValue.equals("")) {
			    			String strDouble = objValue.toString();
			    			strDouble = strDouble.replace(",", ".");
			    			strDouble = strDouble.replace("%", "");
			    			objValue = strDouble;
			    			
			    			lstColumnModel.get(intRowIndex).setValue(intColumnIndex, new BigDecimal(objValue.toString()));
						}
			    	}
			    	
			    } else if (objDataType == LTDataTypes.STRING) {
			    	if (objValue != null && !objValue.equals("")) {
			    		lstColumnModel.get(intRowIndex).setValue(intColumnIndex, (String) objValue);
			    	}
			    	
			    } else if (objDataType == LTDataTypes.TEXT) {
			    	if (objValue != null && !objValue.equals("")) {
			    		lstColumnModel.get(intRowIndex).setValue(intColumnIndex, (String) objValue);
			    	}
					
			    } else if (objDataType == LTDataTypes.DATE) {
			    	if (objValue != null && !objValue.equals("")) {
			    		try {
				    		// Se o formato padrão da data for "dd/MM/yyyy"
				    		if (objValue.toString().substring(2, 3).equals("/") && objValue.toString().substring(5, 6).equals("/")) {
				    			lstColumnModel.get(intRowIndex).setValue(intColumnIndex, (String) objValue);
								
							// Se o formato padrão da data for "yyyy-MM-dd" (armazenado no banco de dados)
							} else if (objValue.toString().substring(4, 5).equals("-") && objValue.toString().substring(7, 8).equals("-")) {
								String strValue = (String) objValue;
								
								SimpleDateFormat dateFormat = new SimpleDateFormat(LTParameters.getInstance().getDateFormat());
								SimpleDateFormat dateDatabaseFormat = new SimpleDateFormat("yyyy-MM-dd");
								
								Date date = dateDatabaseFormat.parse(strValue);
								strValue = dateFormat.format(date);
								
								lstColumnModel.get(intRowIndex).setValue(intColumnIndex, "" + strValue);
							}
				    		
			    		} catch (ParseException e) {
			    			lstColumnModel.get(intRowIndex).setValue(intColumnIndex, "");
			    		}
			    	}
			    	
			    } else if (objDataType == LTDataTypes.HOUR) {
			    	if (objValue != null && !objValue.equals("")) {
			    		lstColumnModel.get(intRowIndex).setValue(intColumnIndex, (String) objValue);
			    	}
			    	
				} else if (objDataType == LTDataTypes.BOOLEAN) {
					if (objValue == null || objValue.equals("")) {
						lstColumnModel.get(intRowIndex).setValue(intColumnIndex, (boolean) false);
					} else {
						lstColumnModel.get(intRowIndex).setValue(intColumnIndex, (boolean) objValue);
					}
					
				} else if (objDataType == LTDataTypes.BUTTON) {
					lstColumnModel.get(intRowIndex).setValue(intColumnIndex, objValue);
				}
				
				fireTableCellUpdated(intRowIndex, intColumnIndex);
				
				if (!blnCellValueUpdated) {
					updateCellValue(objValue, intRowIndex, intColumnIndex);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void setValue(Object objValue, int intRowIndex, int intColumnIndex) {
		setValueAt(objValue, intRowIndex, intColumnIndex);
	}
	
	@Override
	public void setValue(Object objValue, int intRowIndex, String strColumnName) {
		int intColumnIndex = 0;
		boolean blnColumnFound = false;
		
		for (int indexColumn = 0; indexColumn < getColumnCount(); indexColumn++) {
			if (lstColumnParameters.get(indexColumn).getColumnName().equals(strColumnName)) {
				intColumnIndex = indexColumn;
				blnColumnFound = true;
				break;
			}
		}
		
		if (blnColumnFound) {
			setValue(objValue, intRowIndex, intColumnIndex);
		}
	}
	
	@Override
	public boolean isCellEditable(int intRowIndex, int intColumnIndex) {
		if (blnReadOnly) {
			return false;
		} else {
			return lstColumnParameters.get(intColumnIndex).getColumnEditable();
		}
	}
	
	@Override
	public boolean getColumnEditable(int intColumnIndex) {
		if (blnReadOnly) {
			return false;
		} else {
			return lstColumnParameters.get(intColumnIndex).getColumnEditable();
		}
	}
	
	@Override
    public void addTableListener(TableListener objTableListener) {
    	collectionListener.add(objTableListener);
    }
    
    @Override
	public void addMouseListener(MouseAdapter mouseAdapter) {
		this.mouseAdapter = mouseAdapter;
		
		if (objTable != null) {
			objTable.addMouseListener(mouseAdapter);
		}
	}
    
    @Override
    public void setColumnStringMaximumLength(String strColumnName, int intColumnStringMaximumLength) {
    	for (int indexColumn = 0; indexColumn < getColumnCount(); indexColumn++) {
    		if (lstColumnParameters.get(indexColumn).getColumnDataType() == LTDataTypes.STRING && lstColumnParameters.get(indexColumn).getColumnName().equals(strColumnName)) {
    			lstColumnParameters.get(indexColumn).setColumnMaximumLength(intColumnStringMaximumLength);
    			
				if (objTable != null) {
					TableCellEditor objTableCellEditor = setCellEditor(lstColumnParameters.get(indexColumn));
					
					objTable.getColumnModel().getColumn(indexColumn).setCellEditor(objTableCellEditor);
				}
				
    			break;
    		}
    	}
    }
    
    @Override
	public void setColumnDoubleFractionDigits(String strColumnName, int intColumnDoubleFractionDigits) {
    	for (int indexColumn = 0; indexColumn < getColumnCount(); indexColumn++) {
    		if (lstColumnParameters.get(indexColumn).getColumnDataType() == LTDataTypes.DOUBLE && lstColumnParameters.get(indexColumn).getColumnName().equals(strColumnName)) {
    			lstColumnParameters.get(indexColumn).setColumnDoubleFractionDigits(intColumnDoubleFractionDigits);
    			
				if (objTable != null) {
					TableRendererDefault objTableRendererDefault = new TableRendererDefault(LTDataTypes.DOUBLE, blnReadOnly, blnFullRowSelection, lstColumnParameters.get(indexColumn).getColumnHorizontalAlignment(), lstColumnParameters.get(indexColumn).getColumnColor());
					objTableRendererDefault.setColumnDoubleFractionDigits(lstColumnParameters.get(indexColumn).getColumnDoubleFractionDigits());
					objTableRendererDefault.setColumnDoubleShowAsPercentage(lstColumnParameters.get(indexColumn).getColumnDoubleShowAsPercentage());
					
					TableCellEditor objTableCellEditor = setCellEditor(lstColumnParameters.get(indexColumn));
					
					objTable.getColumnModel().getColumn(indexColumn).setCellRenderer(objTableRendererDefault);
					objTable.getColumnModel().getColumn(indexColumn).setCellEditor(objTableCellEditor);
				}
    			
    			break;
    		}
    	}
	}
    
    @Override
    public void setColumnDoubleShowAsPercentage(String strColumnName, boolean blnShowAsPercentage) {
    	for (int indexColumn = 0; indexColumn < getColumnCount(); indexColumn++) {
    		if (lstColumnParameters.get(indexColumn).getColumnDataType() == LTDataTypes.DOUBLE && lstColumnParameters.get(indexColumn).getColumnName().equals(strColumnName)) {
    			lstColumnParameters.get(indexColumn).setColumnDoubleShowAsPercentage(blnShowAsPercentage);
    			
    			if (objTable != null) {
					TableRendererDefault objTableRendererDefault = new TableRendererDefault(LTDataTypes.DOUBLE, blnReadOnly, blnFullRowSelection, lstColumnParameters.get(indexColumn).getColumnHorizontalAlignment(), lstColumnParameters.get(indexColumn).getColumnColor());
					objTableRendererDefault.setColumnDoubleFractionDigits(lstColumnParameters.get(indexColumn).getColumnDoubleFractionDigits());
					objTableRendererDefault.setColumnDoubleShowAsPercentage(lstColumnParameters.get(indexColumn).getColumnDoubleShowAsPercentage());
					
					TableCellEditor objTableCellEditor = setCellEditor(lstColumnParameters.get(indexColumn));
					
					objTable.getColumnModel().getColumn(indexColumn).setCellRenderer(objTableRendererDefault);
					objTable.getColumnModel().getColumn(indexColumn).setCellEditor(objTableCellEditor);				
				}
    			
    			break;
    		}
    	}
	}
    
    @Override
	public void setColumnHorizontalAlignment(String strColumnName, int intHorizontalAlignment) {
    	for (int intColumnIndex = 0; intColumnIndex < getColumnCount(); intColumnIndex++) {
    		if (lstColumnParameters.get(intColumnIndex).getColumnName().equals(strColumnName)) {
    			lstColumnParameters.get(intColumnIndex).setColumnHorizontalAlignment(intHorizontalAlignment);
    			 
    			if (objTable != null) {
    				TableCellRenderer objTableCellRenderer = setCellRenderer(lstColumnParameters.get(intColumnIndex));
    				
    				objTable.getColumnModel().getColumn(intColumnIndex).setCellRenderer(objTableCellRenderer);
    			}
    			
    			break;
    		}
    	}
	}
    
    @Override
    public void setAllowDeleteRow(boolean blnAllowDeleteRow) {
    	this.blnAllowDeleteRow = blnAllowDeleteRow;
    }
    
    @Override
    public void setAllowSortedRows(boolean blnAllowSortedRows) {
    	this.blnAllowSortedRows = blnAllowSortedRows;
    	
    	if (blnAllowSortedRows) {
			objTable.getTableHeader().addMouseListener(new MouseAdapter() {
			    @Override
			    public void mouseClicked(MouseEvent e) {
			    	int intIndexColumn = objTable.getTableHeader().columnAtPoint(e.getPoint());
			    	intIndexColumn = objTable.convertColumnIndexToModel(intIndexColumn);
			        
			    	ArrayList<SortKey> sortKeys = new ArrayList<>(objTable.getRowSorter().getSortKeys());

					TableRowSorter<TableModel> sorter = setTableSorter();
					sorter.setSortKeys(sortKeys);
					sorter.sort();

					objTable.setRowSorter(sorter);
			    }
			});
		}
    }
    
    @Override
	public void updateTableData() {
		fireTableDataChanged();

		setTableColumnDescriptions();
		setTableColumnWidth();
	}

    // *********************************************************************
    // Implementa ActionListener
	@Override
	public void actionPerformed(ActionEvent event) {
		// Copia conteúdo da LTTable
		if (event.getActionCommand().compareTo("Copy_Table") == 0) {
			StringBuffer stringBuffer = new StringBuffer();

			int[] rowsSelected = objTable.getSelectedRows();
			int[] columnsSelected = objTable.getSelectedColumns();

			for (int indexRow = 0; indexRow < rowsSelected.length; indexRow++) {
				for (int indexColumn = 0; indexColumn < columnsSelected.length; indexColumn++) {
					TableCellRenderer renderer = objTable.getCellRenderer(rowsSelected[indexRow], columnsSelected[indexColumn]);
					Component component = objTable.prepareRenderer(renderer, rowsSelected[indexRow], columnsSelected[indexColumn]);
					
					if (component instanceof JLabel) {
						stringBuffer.append(((JLabel) component).getText());
					} else if (component instanceof JCheckBox) {
						stringBuffer.append(((JCheckBox) component).isSelected());
					}

					if (indexColumn < columnsSelected.length - 1) {
						stringBuffer.append("\t");
					}
				}

				stringBuffer.append("\n");
			}

			StringSelection stringSelection = new StringSelection(stringBuffer.toString());

			Clipboard systemClipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			systemClipboard.setContents(stringSelection, stringSelection);
			
		// Cola conteúdo na LTTable
		// Funciona para apenas dados de uma coluna por vez
		} else if (event.getActionCommand().compareTo("Paste_Table") == 0) {
          	try {
          		int intStartRow = (objTable.getSelectedRows())[0];
    			int intStartColumn = (objTable.getSelectedColumns())[0];
    			
    			if (getColumnEditable(intStartColumn)) {
	          		Clipboard systemClipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
	          		
	          		// *********************************************************************
	          		// Insere uma string antes e depois do conteúdo original (Necessário para saber exatamente quantas células foram copiadas)
	          		String strContentPaste = " \n" + (String) (systemClipboard.getContents(this).getTransferData(DataFlavor.stringFlavor)) + " ";
	          		
	          		String[] lineContentChanged = strContentPaste.split("\n");
	          		
	          		// *********************************************************************
	          		// Cria um array para o conteúdo da células copiadas, descartando a string inicial e final criadas acima
	          		String[] lineContent = new String[lineContentChanged.length - 2];    
	          		int intIndexLine = 0;
	          		
	          		for (int indexLineContentChanged = 1; indexLineContentChanged < lineContentChanged.length - 1; indexLineContentChanged++) {
	          			lineContent[intIndexLine++] = lineContentChanged[indexLineContentChanged];
	          		}
	          		
	          		// *********************************************************************
	          		// Atribui os valores das células copiadas à LTTable
	                for (int indexLineContent = 0; indexLineContent < lineContent.length; indexLineContent++) {
	                	String[] cells = lineContent[indexLineContent].split("\t");
	                    
	                	if (objTable.getRowCount() > (intStartRow + indexLineContent)) {
	            			objTable.setValueAt(cells[0].trim(), (intStartRow + indexLineContent), intStartColumn);
	                    }
	                }
    			}
          		
          	} catch(Exception e) {
          		e.printStackTrace();
          	}
		}
	}
}