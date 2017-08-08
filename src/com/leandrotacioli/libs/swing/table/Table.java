package com.leandrotacioli.libs.swing.table;

import java.awt.Color;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.RowSorter;
import javax.swing.RowSorter.SortKey;
import javax.swing.SortOrder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.leandrotacioli.libs.LTDataTypes;
import com.leandrotacioli.libs.LTParameters;
import com.leandrotacioli.libs.swing.table.editors.TableEditorDate;
import com.leandrotacioli.libs.swing.table.editors.TableEditorDouble;
import com.leandrotacioli.libs.swing.table.editors.TableEditorInteger;
import com.leandrotacioli.libs.swing.table.editors.TableEditorLong;
import com.leandrotacioli.libs.swing.table.editors.TableEditorString;
import com.leandrotacioli.libs.swing.table.editors.TableEditorText;
import com.leandrotacioli.libs.swing.table.renderers.TableRendererBoolean;
import com.leandrotacioli.libs.swing.table.renderers.TableRendererDefault;
import com.leandrotacioli.libs.swing.table.renderers.TableRendererDouble;
import com.leandrotacioli.libs.swing.table.renderers.TableRendererFixed;

/**
 * Cria uma extensão de <i>AbstractTableModel</i>.
 * 
 * @author Leandro Tacioli
 * @version 4.0 - 11/Jul/2016
 */
public class Table extends AbstractTableModel implements TableInterface, ActionListener {	
	private static final long serialVersionUID = 755268795533847516L;
	
	private TableExtension objTable; 
	private TableExtension objTableFixed;
	
	private TableColumnModelFixed objTableFixedModel;
	
	private List<TableColumnModel> lstColumnModel;
	private List<TableColumnParameters> lstColumnParameters;
	
	// Ler a descrição do método 'setTableRendererEditor()' para entender
	// a criação dos Renderer e Editors abaixo
	private TableRendererDefault objTableRendererInteger;
	private TableRendererDefault objTableRendererLong;
	private TableRendererDefault objTableRendererString;
	private TableRendererDefault objTableRendererText;
	private TableRendererDefault objTableRendererDate;
	private TableRendererBoolean objTableRendererBoolean;
	
	private List<TableRendererDouble> lstTableRendererDouble;
	
	private TableEditorInteger objTableEditorInteger;
	private TableEditorLong objTableEditorLong;
	private TableEditorText objTableEditorText;
	private TableEditorDate objTableEditorDate;

	private TableRowSorter<TableModel> sorter;
	
	private Collection<Object> collectionListener;
	
	private MouseAdapter mouseAdapter;

	private boolean blnReadOnly;
	private boolean blnAllowDeleteRow;
	private boolean blnAllowSortedRows;
	
	private boolean blnFullRowSelection;
	
	private boolean blnSetTableColumnsFormat;    // Determina se as colunas da tabela devem ser reformatadas
	private boolean blnCellValueUpdated;         // Utilizado exclusivamente para notificar o 'TableAbstractListener' de uma atualização do valor em alguma célula
	private boolean blnTableRowSelection;        // A seleção foi feita através da tabela normal
	private boolean blnTableFixedRowSelection;   // A seleção foi feita através da tabela fixa
	
	private int[] rowsSelectionFixed;
	
	private SimpleDateFormat dateFormat;          // Formata de data padrão do LTLibraries
	private SimpleDateFormat dateDatabaseFormat;  // Formato de data armazenado em bancos de dados
	
	private final String ID_ROW_LT_TABLE = "ID_ROW_LT_TABLE";  // Nome da coluna de ID da LTTable
	private int intIdRowTable;                                 // Última ID das linhas da LTTable
	private boolean blnSetIdRowTable;                          // Permissão para alterar ID das linhas da LTTable
	
	/**
	 * Retorna a LTTable.
	 * 
	 * @return jtbTable
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
		
		dateFormat = new SimpleDateFormat(LTParameters.getInstance().getDateFormat());
		dateDatabaseFormat = new SimpleDateFormat("yyyy-MM-dd");
		
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
		setTableRendererEditor();
		setTableColumnDescriptions();
		setTableColumnWidth();
	}
	
	/**
	 * Estabele as propriedades principais da tabela.
	 */
	private void setTableProperties() {	
		objTable = new TableExtension(this, blnReadOnly);
		objTable.setAutoCreateRowSorter(blnAllowSortedRows);
		objTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		objTable.setCellSelectionEnabled(false);
		objTable.setRowHeight(LTParameters.getInstance().getTableRowHeight());
		objTable.setFont(LTParameters.getInstance().getFontTableTextField());
		objTable.setBorder(LTParameters.getInstance().getBorderTableTextField());
		objTable.setGridColor(LTParameters.getInstance().getColorTableGrid());
		objTable.getTableHeader().setReorderingAllowed(blnAllowSortedRows);
		objTable.getTableHeader().setFont(LTParameters.getInstance().getFontTableHeader());
		objTable.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
		
		objTable.getTableHeader().addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		    	int intIndexColumn = objTable.getTableHeader().columnAtPoint(e.getPoint());
		    	intIndexColumn = objTable.convertColumnIndexToModel(intIndexColumn);
		        
		    	ArrayList<SortKey> sortKeys = new ArrayList<>(objTable.getRowSorter().getSortKeys());
		    	
		        if (sorter != null) {
		        	sorter.setSortKeys(sortKeys);
		        	sorter.sort();
		        }
		    }
		});
		
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
				objTableRendererString.setFullRowSelection(false);
				
				allowFullRowSelection(blnReadOnly);
		    }

			@Override
			public void mousePressed(MouseEvent event) {
				objTableRendererString.setFullRowSelection(false);
				
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
		objTableFixed.setGridColor(LTParameters.getInstance().getColorTableGrid());
		objTableFixed.setRowHeight(LTParameters.getInstance().getTableRowHeight());
        objTableFixed.getColumnModel().getColumn(0).setPreferredWidth(LTParameters.getInstance().getTableRowHeight());
		objTableFixed.getColumnModel().getColumn(0).setMinWidth(LTParameters.getInstance().getTableRowHeight());
		objTableFixed.getColumnModel().getColumn(0).setMaxWidth(LTParameters.getInstance().getTableRowHeight());
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
	 * Estabelece os renderizadores e editores das colunas da tabela.<br>
	 * <br>
	 * Alguns tipos de dados podem utilizar o mesmo objeto <i>renderer</i>/<i>editor</i> para todos seus campos na tabela.<br>
	 * <br>
	 * <b>No caso do <i>renderer</i></b>: <br>
	 * Apenas para o tipo de dado <i>DOUBLE</i> é criada uma lista com os objetos de cada coluna, pois a quantidade de casas decimais pode variar de coluna para coluna.<br>
	 * <br>
	 * <b>No caso do <i>editor</i></b>: <br>
	 * O tipo de dado <i>DOUBLE</i> cria um novo objeto devido a quantidade de casas decimais;<br>
	 * O tipo de dado <i>STRING</i> cria um novo objeto devido a quantidade máxima de caracteres;<br>
	 * O tipo de dado <i>BOOLEAN</i> não utiliza nenhum objeto para o <i>editor</i>.<br>
	 * <br>
	 * Quando há a necessidade de novos objetos, serão criados diretamente no método <i>setTableColumnRendererEditor()</i>
	 */
	private void setTableRendererEditor() {
		objTableRendererInteger = new TableRendererDefault(LTDataTypes.INTEGER, blnReadOnly, blnFullRowSelection, objTable.getBackground());
		objTableRendererLong = new TableRendererDefault(LTDataTypes.LONG, blnReadOnly, blnFullRowSelection, objTable.getBackground());
		objTableRendererString = new TableRendererDefault(LTDataTypes.STRING, blnReadOnly, blnFullRowSelection, objTable.getBackground());
		objTableRendererText = new TableRendererDefault(LTDataTypes.TEXT, blnReadOnly, blnFullRowSelection, objTable.getBackground());
		objTableRendererDate = new TableRendererDefault(LTDataTypes.DATE, blnReadOnly, blnFullRowSelection, objTable.getBackground());
		objTableRendererBoolean = new TableRendererBoolean(blnReadOnly, blnFullRowSelection, objTable.getBackground());
		
		lstTableRendererDouble = new ArrayList<TableRendererDouble>();
		
		objTableEditorInteger = new TableEditorInteger();
		objTableEditorLong = new TableEditorLong();
		objTableEditorText = new TableEditorText();
		objTableEditorDate = new TableEditorDate();
	}
	
	/**
	 * Estabelece a renderização de uma coluna da tabela.
	 * 
	 * @param intColumnIndex - Índice da coluna
	 * @param color          - Cor
	 */
	private void setTableColumnRendererEditor(int intColumnIndex, Color color) {
		LTDataTypes objDataType = lstColumnParameters.get(intColumnIndex).getColumnDataType();
		
		if (objDataType == LTDataTypes.INTEGER) {
			objTableRendererInteger.setColorBackground(color);
			objTable.getColumnModel().getColumn(intColumnIndex).setCellRenderer(objTableRendererInteger);
			objTable.getColumnModel().getColumn(intColumnIndex).setCellEditor(objTableEditorInteger);
			
		} else if (objDataType == LTDataTypes.LONG) {
			objTableRendererLong.setColorBackground(color);
			objTable.getColumnModel().getColumn(intColumnIndex).setCellRenderer(objTableRendererLong);
			objTable.getColumnModel().getColumn(intColumnIndex).setCellEditor(objTableEditorLong);
		
		} else if (objDataType == LTDataTypes.DOUBLE) {
			boolean blnHasRenderer = false;
			
			// Cria o 'renderer' da coluna
			TableRendererDouble objTableRendererDouble = new TableRendererDouble(lstColumnParameters.get(intColumnIndex).getColumnName(), blnReadOnly, blnFullRowSelection, lstColumnParameters.get(intColumnIndex).getColumnDoubleFractionDigits(), color);
			
			// Verifica se já existe o 'renderer' para o coluna
			for (int indexRendererDouble = 0; indexRendererDouble < lstTableRendererDouble.size(); indexRendererDouble++) {
				if (lstTableRendererDouble.get(indexRendererDouble).getColumnName() == lstColumnParameters.get(intColumnIndex).getColumnName()) {
					lstTableRendererDouble.get(indexRendererDouble).setColumnDoubleFractionDigits(lstColumnParameters.get(intColumnIndex).getColumnDoubleFractionDigits());
					objTableRendererDouble = lstTableRendererDouble.get(indexRendererDouble);
					blnHasRenderer = true;
					
					break;
				}
			}
			
			// Caso não haja nenhum renderer atribuído à coluna, adiciona a lista de renderers 'DOUBLE'
			if (!blnHasRenderer) {
				lstTableRendererDouble.add(objTableRendererDouble);
			}
			
			objTable.getColumnModel().getColumn(intColumnIndex).setCellRenderer(objTableRendererDouble);
			objTable.getColumnModel().getColumn(intColumnIndex).setCellEditor(new TableEditorDouble(lstColumnParameters.get(intColumnIndex).getColumnDoubleFractionDigits()));

		} else if (objDataType == LTDataTypes.STRING) {
			objTableRendererString.setColorBackground(color);
			objTable.getColumnModel().getColumn(intColumnIndex).setCellRenderer(objTableRendererString);
			objTable.getColumnModel().getColumn(intColumnIndex).setCellEditor(new TableEditorString(lstColumnParameters.get(intColumnIndex).getColumnStringMaximumLength()));
			
		} else if (objDataType == LTDataTypes.TEXT) {
			objTableRendererText.setColorBackground(color);
			objTable.getColumnModel().getColumn(intColumnIndex).setCellRenderer(objTableRendererText);
			objTable.getColumnModel().getColumn(intColumnIndex).setCellEditor(objTableEditorText);
		
		} else if (objDataType == LTDataTypes.DATE) {
			objTableRendererDate.setColorBackground(color);
			objTable.getColumnModel().getColumn(intColumnIndex).setCellRenderer(objTableRendererDate);
			objTable.getColumnModel().getColumn(intColumnIndex).setCellEditor(objTableEditorDate); 
			
		} else if (objDataType == LTDataTypes.BOOLEAN) {
			objTableRendererBoolean.setColorBackground(color);
			objTable.getColumnModel().getColumn(intColumnIndex).setCellRenderer(objTableRendererBoolean);
		}
	}
	
	/**
	 * Estabelece a renderização / editor de todas as colunas da tabela.
	 */
	private void setTableColumnFormat() {
		for (int intColumnIndex = 0; intColumnIndex < getColumnCount(); intColumnIndex++) {
			setTableColumnRendererEditor(intColumnIndex, objTable.getBackground());
		}
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
		
		objTableRendererInteger.setFullRowSelection(blnFullRowSelection);
		objTableRendererLong.setFullRowSelection(blnFullRowSelection);
		objTableRendererString.setFullRowSelection(blnFullRowSelection);
		objTableRendererText.setFullRowSelection(blnFullRowSelection);
		objTableRendererDate.setFullRowSelection(blnFullRowSelection);
		objTableRendererBoolean.setFullRowSelection(blnFullRowSelection);
		
		for (int indexRendererDouble = 0; indexRendererDouble < lstTableRendererDouble.size(); indexRendererDouble++) {
			lstTableRendererDouble.get(indexRendererDouble).setFullRowSelection(blnFullRowSelection);
		}
	}
	
	// *********************************************************************
	// Implementa TableInterface
	@Override
	public void addColumn(String strColumnName, String strColumnDescription, LTDataTypes objColumnDataType, int intColumnWidth, boolean blnColumnEditable) {
		try {
			if (strColumnName.equals(ID_ROW_LT_TABLE) && getColumnCount() != 0) {
				 throw new Exception("Cannot create column named: " + ID_ROW_LT_TABLE + ". Try another column name.");
			} else {
				lstColumnParameters.add(new TableColumnParameters(strColumnName, strColumnDescription, objColumnDataType, intColumnWidth, blnColumnEditable));
			
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
		
					setTableColumnFormat();
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
		sorter = new TableRowSorter<>(objTable.getModel());
		objTable.setRowSorter(sorter);
		
		lstColumnModel.add(new TableColumnModel(getColumnCount()));
		
		fireTableRowsInserted(getRowCount() - 1, getRowCount() - 1);
		
		// Insere valores default para os colunas
		blnSetIdRowTable = true;
		
		for (int columnIndex = 0; columnIndex < getColumnCount(); columnIndex++) {
			setValueAt(null, getRowCount() - 1, columnIndex);
		}
		
		objTableFixedModel.setRowCount(getRowCount());
		
		if (!blnSetTableColumnsFormat) {
			setTableColumnFormat();
			
			blnSetTableColumnsFormat = true;
		}
		
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
						
						setTableColumnFormat();
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
		lstTableRendererDouble = new ArrayList<TableRendererDouble>();
		
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
	  			classType = String.class;
	  		
	    	} else if (objDataType == LTDataTypes.BOOLEAN) {
	  			classType = Boolean.class;
	  		}
    	}
  		
    	return classType;
    }
	
	@Override
	public void setColumnColor(int intColumnIndex, Color color) {
		setTableColumnRendererEditor(intColumnIndex, color);
		
		fireTableDataChanged();
	}
			
	@Override
	public void setColumnColor(String strColumnName, Color color) {
		int intColumnIndex = -1;
		
		for (int columnIndex = 0; columnIndex < getColumnCount(); columnIndex++) {
			if (lstColumnParameters.get(columnIndex).getColumnName().equals(strColumnName)) {
				intColumnIndex = columnIndex; 
				break;
			}
		}

		if (intColumnIndex != -1) {
			setColumnColor(intColumnIndex, color);
		}
	}
	
	@Override
	public void orderColumnData(String strColumnName, boolean blnAscending) {
		int intColumnIndex = 0;
		
		for (int indexColumn = 0; indexColumn < getColumnCount(); indexColumn++) {
			if (lstColumnParameters.get(indexColumn).getColumnName().equals(strColumnName)) {
				intColumnIndex = indexColumn;
				break;
			}
		}
		
		sorter = new TableRowSorter<>(objTable.getModel());
		objTable.setRowSorter(sorter);
		
		List<RowSorter.SortKey> sortKeys = new ArrayList<>();
		
		if (blnAscending) {
			sortKeys.add(new RowSorter.SortKey(intColumnIndex, SortOrder.ASCENDING));
		} else {
			sortKeys.add(new RowSorter.SortKey(intColumnIndex, SortOrder.DESCENDING));
		}
		
		sorter.setSortKeys(sortKeys);
		sorter.sort();
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
	public void setRowColor(int intRowIndex, Color color) {
		objTable.setRowColor(intRowIndex, color);
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
				
				if (objDataType == LTDataTypes.INTEGER) {
					if (objValue == null || objValue.equals("")) {
						objValue = 0;
					}
					
					lstColumnModel.get(intRowIndex).setValue(intColumnIndex, Integer.parseInt(objValue.toString()));
					
				} else if (objDataType == LTDataTypes.LONG) {
					if (objValue == null || objValue.equals("")) {
						objValue = 0;
					}
					
					lstColumnModel.get(intRowIndex).setValue(intColumnIndex, Long.parseLong(objValue.toString()));
			    	
			    } else if (objDataType == LTDataTypes.DOUBLE) {
			    	if (objValue == null || objValue.equals("")) {
			    		objValue = 0;
			    	} else {
		    			String strDouble = objValue.toString();
		    			strDouble = strDouble.replace(",", ".");
		    			objValue = strDouble;
			    	}
			    	
			    	lstColumnModel.get(intRowIndex).setValue(intColumnIndex, Double.parseDouble(objValue.toString()));
			    	
			    } else if (objDataType == LTDataTypes.STRING) {
			    	if (objValue == null || objValue.equals("")) {
			    		lstColumnModel.get(intRowIndex).setValue(intColumnIndex, "");
			    	} else {
			    		lstColumnModel.get(intRowIndex).setValue(intColumnIndex, (String) objValue);
			    	}
			    	
			    } else if (objDataType == LTDataTypes.TEXT) {
			    	if (objValue == null || objValue.equals("")) {
			    		lstColumnModel.get(intRowIndex).setValue(intColumnIndex, "");
			    	} else {
			    		lstColumnModel.get(intRowIndex).setValue(intColumnIndex, (String) objValue);
			    	}
					
			    } else if (objDataType == LTDataTypes.DATE) {
			    	if (objValue == null || objValue.equals("")) {
			    		lstColumnModel.get(intRowIndex).setValue(intColumnIndex, "");
			    		
			    	} else {
			    		try {
				    		// Se o formato padrão da data for "dd/MM/yyyy"
				    		if (objValue.toString().substring(2, 3).equals("/") && objValue.toString().substring(5, 6).equals("/")) {
				    			lstColumnModel.get(intRowIndex).setValue(intColumnIndex, (String) objValue);
								
							// Se o formato padrão da data for "yyyy-MM-dd" (armazenado no banco de dados)
							} else if (objValue.toString().substring(4, 5).equals("-") && objValue.toString().substring(7, 8).equals("-")) {
								String strValue = (String) objValue;
								
								Date date = dateDatabaseFormat.parse(strValue);
								strValue = dateFormat.format(date);
								
								lstColumnModel.get(intRowIndex).setValue(intColumnIndex, "" + strValue);
							}
				    		
			    		} catch (ParseException e) {
			    			lstColumnModel.get(intRowIndex).setValue(intColumnIndex, "");
			    		}
			    	}
					
				} else if (objDataType == LTDataTypes.BOOLEAN) {
					if (objValue == null || objValue.equals("")) {
						lstColumnModel.get(intRowIndex).setValue(intColumnIndex, (boolean) false);
					} else {
						lstColumnModel.get(intRowIndex).setValue(intColumnIndex, (boolean) objValue);
					}
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
    		if (lstColumnParameters.get(indexColumn).getColumnName().equals(strColumnName)) {
    			lstColumnParameters.get(indexColumn).setColumnMaximumLength(intColumnStringMaximumLength);

    			if (objTable != null) {
    				setTableColumnRendererEditor(indexColumn, objTable.getBackground());
    			}
    			
    			break;
    		}
    	}
    }
    
    @Override
	public void setColumnDoubleFractionDigits(String strColumnName, int intColumnDoubleFractionDigits) {
    	for (int indexColumn = 0; indexColumn < getColumnCount(); indexColumn++) {
    		if (lstColumnParameters.get(indexColumn).getColumnName().equals(strColumnName)) {
    			lstColumnParameters.get(indexColumn).setColumnDoubleFractionDigits(intColumnDoubleFractionDigits);
    			 
    			if (objTable != null) {
    				setTableColumnRendererEditor(indexColumn, objTable.getBackground());
    			}
    			
    			break;
    		}
    	}
	}
    
    @Override
    public void setAllowSortedRows(boolean blnAllowSortedRows) {
    	this.blnAllowSortedRows = blnAllowSortedRows;
    	
    	if (objTable != null) {
	    	TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(objTable.getModel()) {
	    	    @Override
	    	    public boolean isSortable(int intColumn) {
	    	    	return blnAllowSortedRows;
	    	    };
	    	};
	    	
	    	objTable.setRowSorter(sorter);
    	}
    }
    
    @Override
	public void updateTableData() {
		fireTableDataChanged();
		setTableColumnFormat();
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
			
			int intNumColumns = objTable.getSelectedColumnCount();
			int intNumRows = objTable.getSelectedRowCount();
			
			int[] rowsSelected = objTable.getSelectedRows();
			int[] columnsSelected = objTable.getSelectedColumns();
			
			if (!((intNumRows - 1 == rowsSelected[rowsSelected.length - 1] - rowsSelected[0] && intNumRows == rowsSelected.length) &&
					(intNumColumns - 1 == columnsSelected[columnsSelected.length - 1] - columnsSelected[0] && intNumColumns == columnsSelected.length))) {
        	 		return;
			}
			
			for (int indexRow = 0; indexRow < intNumRows; indexRow++) {
				for (int indexColumn = 0; indexColumn < intNumColumns; indexColumn++) {
					stringBuffer.append(objTable.getValueAt(rowsSelected[indexRow], columnsSelected[indexColumn]));
					
					if (indexColumn < intNumColumns - 1) {
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