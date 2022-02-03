import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import net.miginfocom.swing.MigLayout;

import com.leandrotacioli.libs.swing.LTPanel;
import com.leandrotacioli.libs.swing.LTTitledBorder;
import com.leandrotacioli.libs.swing.comboboxfield.LTComboBoxField;
import com.leandrotacioli.libs.swing.table.LTTable;
import com.leandrotacioli.libs.swing.table.TableListener;
import com.leandrotacioli.libs.swing.textfield.LTTextField;
import com.leandrotacioli.libs.LTDataTypes;
import com.leandrotacioli.libs.LTParameters;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Testes contendo exemplos da LT Libraries.
 * 
 * @author Leandro Tacioli
 */
public class LTLibraries implements TableListener {
	private JFrame frame;
	
	private JPanel panelFields;
	private LTTextField txtString;
	private LTTextField txtStringDireita;
	private LTTextField txtInteger;
	private LTTextField txtLong;
	private LTTextField txtDouble;
	private LTTextField txtDate;
	private LTTextField txtHour;
	private LTTextField txtText;
	private LTTextField txtBoolean;
	private LTComboBoxField comboBox;
	
	private JPanel panelTable;
	private LTTable objTable;
	
	private JButton btnPrintValues;
	private JButton btnChangeValues;
	private JButton btnEnableDisable;
	private JButton btnChangeTableStructure;
	
	private boolean blnHabilitado = true;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LTLibraries window = new LTLibraries();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LTLibraries() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		LTParameters.getInstance().setColorComponentPanelBackground(Color.GRAY);
		LTParameters.getInstance().setColorComponentPanelBackground(new Color(240, 240, 240));
		
		frame = new JFrame("LT Libraries");
		frame.setMinimumSize(new Dimension(1300, 800));
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new MigLayout("", "[grow]", "[][grow][]"));
		
		// *********************************************************************************************************************
		// Painel de Campos
		LTTitledBorder borderFields = new LTTitledBorder(LTPanel.PANEL_BORDER);
		borderFields.setTitle("Campos:");
		
		panelFields = new JPanel();
		panelFields.setLayout(new MigLayout("insets 5", "[grow]", "[][][grow][]"));
		panelFields.setBorder(borderFields);
		
		txtInteger = new LTTextField("Integer", LTDataTypes.INTEGER, true, true);
		txtInteger.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				System.out.println("Integer - Foco perdido");
			}
			
			@Override
			public void focusGained(FocusEvent arg0) {
				System.out.println("Integer - Focado");
			}
		});
		
		txtLong = new LTTextField("Long", LTDataTypes.LONG, true, true);
		
		txtDouble = new LTTextField("Double", LTDataTypes.DOUBLE, true, true);
		txtDouble.setFractionDigits(4);
		
		txtString = new LTTextField("String", LTDataTypes.STRING, true, true, 40);
		txtString.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				
			}
		});
		
		txtString.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				
			}
		});
		
		txtStringDireita = new LTTextField("StringDireita", LTDataTypes.STRING, true, true, 100);
		txtStringDireita.setHorizontalAlignment(SwingConstants.RIGHT);
		
		txtText = new LTTextField("Text", LTDataTypes.TEXT, true, true);
		txtDate = new LTTextField("Date", LTDataTypes.DATE, true, true);
		txtHour = new LTTextField("Hour", LTDataTypes.HOUR, true, true);
		txtBoolean = new LTTextField("Boolean", LTDataTypes.BOOLEAN, true, true);
		
		comboBox = new LTComboBoxField("Combo Box", true, true);
		comboBox.addValues("ABC", "ABC-1");
		comboBox.addValues("DEF", "DEF-2");
		comboBox.addValues("GHI", "GHI-3");
		comboBox.setValue("DEF");
		
		panelFields.add(txtInteger, "cell 0 0, grow, width 200");
		panelFields.add(txtLong, "cell 0 0, grow, width 200");
		panelFields.add(txtDouble, "cell 0 0, grow, width 200");
	    
		panelFields.add(txtString, "cell 0 1, grow, width 400");
		panelFields.add(txtStringDireita, "cell 0 1, grow, width 400");
		
		panelFields.add(txtText, "cell 0 2, grow");
		
		panelFields.add(comboBox, "cell 0 3, grow, width 200");
		panelFields.add(txtDate, "cell 0 3, width 100, grow");
		panelFields.add(txtHour, "cell 0 3, width 100, grow");
		panelFields.add(txtBoolean, "cell 0 3, width 200, aligny bottom");
	    
		// *********************************************************************************************************************
		// Painel da Tabela
		LTTitledBorder borderTable = new LTTitledBorder(LTPanel.PANEL_BORDER);
		borderTable.setTitle("Tabela:");
		
		panelTable = new JPanel();
		panelTable.setLayout(new MigLayout("insets 5", "[grow]", "[grow]"));
		panelTable.setBorder(borderTable);
		
		objTable = new LTTable(false, true);
		objTable.addColumn("image", "Imagem", LTDataTypes.BUTTON, 90, true);
		objTable.addColumn("integer", "Integer", LTDataTypes.INTEGER, 90, true, false);
		objTable.addColumn("integer_show", "Integer Exibe Tudo", LTDataTypes.INTEGER, 90, true, true);
		objTable.addColumn("long", "Long", LTDataTypes.LONG, 90, true, false);
		objTable.addColumn("long_show", "Long Exibe Tudo", LTDataTypes.LONG, 90, true, true);
		objTable.addColumn("double", "Double", LTDataTypes.DOUBLE, 90, true, false);
		objTable.addColumn("double_show", "Double Exibe Tudo", LTDataTypes.DOUBLE, 90, true, true);
		objTable.addColumn("string", "String", LTDataTypes.STRING, 170, true);
		objTable.addColumn("string_right", "String Direita", LTDataTypes.STRING, 170, true);
		objTable.addColumn("string_editable", "String Editable", LTDataTypes.STRING, 140, true);
		objTable.addColumn("string_not_editable", "String Not Editable", LTDataTypes.STRING, 140, false);
		objTable.addColumn("text", "Text", LTDataTypes.TEXT, 140, true);
		objTable.addColumn("date", "Date", LTDataTypes.DATE, 100, true);
		objTable.addColumn("hour", "Hour", LTDataTypes.HOUR, 100, true);
		objTable.addColumn("boolean", "Boolean", LTDataTypes.BOOLEAN, 80, true);
		objTable.addMouseListener(new TableMouseAdapter());
		objTable.addTableListener(LTLibraries.this);
		objTable.setColumnStringMaximumLength("string", 10);
		objTable.setColumnDoubleFractionDigits("double", 1);
		//objTable.setColumnDoubleFractionDigits("double_show", 3);
		objTable.setColumnHorizontalAlignment("string_right", 4);
		objTable.setColumnHorizontalAlignment("double", 2);
		objTable.setColumnHorizontalAlignment("date", 0);
		objTable.setColumnColor("integer", Color.RED);
		//objTable.setBorderColor(Color.RED);
		//objTable.setAllowSortedRows(false);
		objTable.showTable();
		
		panelTable.add(objTable, "cell 0 0, grow");
		
		// *********************************************************************************************************************
		// Botões
		btnPrintValues = new JButton("Imprime Valores");
		btnPrintValues.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				System.out.println("---------------------------------------------------------------------");
				System.out.println("Imprime Valores");
				System.out.println("Integer: " + txtInteger.getValue());
				System.out.println("Long: " + txtLong.getValue());
				System.out.println("Double: " + txtDouble.getValue());
				System.out.println("String: " + txtString.getValue());
				System.out.println("String Direita: " + txtStringDireita.getValue());
				System.out.println("Text: " + txtText.getValue());
				System.out.println("Date: " + txtDate.getValue());
				System.out.println("Hour: " + txtHour.getValue());
				System.out.println("Boolean: " + txtBoolean.getValue());
				System.out.println("Valor do Combo: " + comboBox.getValue());
				System.out.println("Descrição do Combo: " + comboBox.getValueDescription());
				System.out.println("");
				
				// Tabela
				System.out.println("Dados da Tabela - Total de Linhas: " + objTable.getRowCount());
				for (int indexRow = 0; indexRow < objTable.getRowCount(); indexRow++) {
					System.out.print("Row: " + indexRow + " | ");
					for (int indexColumn = 0; indexColumn < objTable.getColumnCount(); indexColumn++) {
						System.out.print(objTable.getColumnName(indexColumn) + ": " + objTable.getValue(indexRow, indexColumn) + " | ");
					}
					
					System.out.println("");
				}
				System.out.println("---------------------------------------------------------------------");
			}
		});
		
		btnChangeValues = new JButton("Altera Valores");
		btnChangeValues.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				txtInteger.setValue(Integer.MAX_VALUE - 200);
				txtInteger.setLabel("Integer 2:");
				
				txtLong.setValue(Long.MAX_VALUE - 150);
				txtLong.setLabel("Long 2:");
				
				txtDouble.setValue(12);
				txtDouble.setFractionDigits(6);
				txtDouble.setLabel("Double 2:");
				
				txtDate.setValue("05/05/2015");
				txtDate.setLabel("Date 2:");
				
				txtHour.setValue("12:12");
				txtHour.setLabel("Hora 2:");
				
				txtString.setValue("String Alterado");
				txtString.setLabel("String 2:");
				
				txtStringDireita.setValue("String Direita Alterado");
				txtStringDireita.setLabel("String 2 Direita:");
				
				txtText.setValue("Text Alterado");
				txtText.setLabel("Text 2:");
				
				txtBoolean.setValue(true);
				txtBoolean.setLabel("Boolean 2:");
				
				comboBox.setLabel("Combo 2:");
				comboBox.removeValues();
				comboBox.addValues("JKL", "JKL-4");
				comboBox.addValues("MNO", "MNO-5");
				comboBox.addValues("PQR", "PQR-6");
				comboBox.setValue("MNO");
			}
		});
		
		btnEnableDisable = new JButton("Habilita/Desabilita");
		btnEnableDisable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if (blnHabilitado) {
					setComponentEnabled(frame, false);
					blnHabilitado = false;
				} else {
					setComponentEnabled(frame, true);
					blnHabilitado = true;
					txtString.setFocus();
				}
				
				btnPrintValues.setEnabled(true);
				btnChangeValues.setEnabled(true);
				btnEnableDisable.setEnabled(true);
				btnChangeTableStructure.setEnabled(true);
			}
		});
		
		btnChangeTableStructure = new JButton("Alteração Estrutura Tabela");
		btnChangeTableStructure.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				objTable.deleteRows();
				objTable.deleteColumns();
				
				objTable.addColumn("nova_string", "Nova String", LTDataTypes.STRING, 80, true);
				objTable.addColumn("nova_integer", "Nova Integer", LTDataTypes.INTEGER, 80, true);
				objTable.addColumn("nova_double", "Nova Double", LTDataTypes.DOUBLE, 80, true);

				objTable.addRow();
				objTable.addRowData("nova_string", "AAA " + 1);
				objTable.addRowData("nova_integer", 1 * 100);
				objTable.addRowData("nova_double", 1 * 0.5);
				//objTable.setRowColor(objTable.getRowCount() - 1, Color.LIGHT_GRAY);
				
				objTable.addRow();
				objTable.addRowData("nova_string", "BBB " + 1);
				objTable.addRowData("nova_integer", 1 * 25);
				objTable.addRowData("nova_double", 1 * 0.25);
				
				objTable.addRow();
				objTable.addRowData("nova_string", "AAA " + 3);
				objTable.addRowData("nova_integer", 1 * 10);
				objTable.addRowData("nova_double", 1 * 10.5);
			}
		});
		
		// *********************************************************************************************************************
		// Inclusão dos Painéis e Botões no Frame
		frame.getContentPane().add(panelFields, "cell 0 0, grow");
		frame.getContentPane().add(panelTable, "cell 0 1, grow");

		frame.getContentPane().add(btnPrintValues, "cell 0 2");
		frame.getContentPane().add(btnChangeValues, "cell 0 2");
		frame.getContentPane().add(btnEnableDisable, "cell 0 2");
		frame.getContentPane().add(btnChangeTableStructure, "cell 0 2");
		
		addRows();
	}
	
	private void addRows() {
		objTable.addRow();
		objTable.addRowData("image", new ImageIcon("res/images/search.png"));
		objTable.addRowData("integer", 100);
		objTable.addRowData("long", 1000);
		objTable.addRowData("double", 10.10);
		objTable.addRowData("string", "Primeiro");
		objTable.addRowData("string_not_editable", "Primeiro Fixo");
		objTable.addRowData("text", "Primeiro livre");
		objTable.addRowData("date", "01/01/2001");
		objTable.addRowData("hour", "08:00");
		objTable.addRowData("boolean", true);
		
		objTable.addRow();
		objTable.addRowData("image", new ImageIcon("res/images/search.png"));
		//objTable.addRowData("integer", 200);
		objTable.addRowData("integer_show", 220);
		//objTable.addRowData("long", 2000);
		objTable.addRowData("long_show", 2200);
		//objTable.addRowData("double", 20.20);
		objTable.addRowData("double_show", 22.22);
		objTable.addRowData("string", "Segundo");
		objTable.addRowData("string_right", "Segundo Direita");
		objTable.addRowData("string_not_editable", "Segundo Fixo");
		objTable.addRowData("text", "Segundo livre");
		objTable.addRowData("date", "02/02/2002");
		objTable.addRowData("hour", "09:25");
		objTable.addRowData("boolean", false);
		
		objTable.addRow();
		objTable.addRowData("image", new ImageIcon("res/images/search.png"));
		objTable.addRowData("integer", 300);
		objTable.addRowData("long", 3000);
		objTable.addRowData("double", 30);
		objTable.addRowData("string", "Terceiro");
		objTable.addRowData("string_not_editable", "Terceiro Fixo");
		objTable.addRowData("text", "Terceiro livre");
		objTable.addRowData("date", "03/03/2003");
		objTable.addRowData("hour", "12:30");
		objTable.addRowData("boolean", true);
		
		objTable.addRow();
		objTable.addRowData("image", new ImageIcon("res/images/search.png"));
		objTable.addRowData("integer", 400);
		objTable.addRowData("long", 4000);
		objTable.addRowData("double", 40.40);
		objTable.addRowData("string", "Quarto");
		objTable.addRowData("text", "Quarto livre");
		objTable.addRowData("date", "04/04/2004");
		objTable.addRowData("hour", "17:00");
		objTable.addRowData("boolean", false);
		
		objTable.addRow();
		objTable.addRowData("image", new ImageIcon("res/images/search.png"));
		objTable.addRowData("integer", 500);
		objTable.addRowData("long", 5000);
		objTable.addRowData("double", 50.50);
		objTable.addRowData("string", "Quinto");
		objTable.addRowData("text", "Quinto livre");
		objTable.addRowData("date", "05/05/2005");
		objTable.addRowData("hour", "00:00");
		objTable.addRowData("boolean", true);
		
		objTable.addRow();
		objTable.addRowData("image", new ImageIcon("res/images/search.png"));
		//objTable.addRowData("integer", 600);
		//objTable.addRowData("long", 6000);
		//objTable.addRowData("double", 60.60);
		objTable.addRowData("string", "Sexto");
		objTable.addRowData("text", "Sexto livre");
		objTable.addRowData("date", "06/06/2006");
		objTable.addRowData("hour", "05:35");
		objTable.addRowData("boolean", false);
		
		objTable.addRow();
		objTable.addRowData("image", new ImageIcon("res/images/search.png"));
		objTable.addRowData("integer", 700);
		objTable.addRowData("long", 7000);
		objTable.addRowData("double", 70.70);
		objTable.addRowData("string", "Sétimo");
		objTable.addRowData("text", "Sétimo livre");
		objTable.addRowData("date", "07/07/2007");
		objTable.addRowData("hour", "08:00");
		objTable.addRowData("boolean", true);
		
		objTable.addRow();
		objTable.addRowData("image", new ImageIcon("res/images/search.png"));
		//objTable.addRowData("integer", 800);
		//objTable.addRowData("long", 8000);
		//objTable.addRowData("double", 80.80);
		objTable.addRowData("string", "Oitavo");
		objTable.addRowData("text", "Oitavo livre");
		objTable.addRowData("date", "08/08/2008");
		objTable.addRowData("hour", "08:15");
		objTable.addRowData("boolean", false);
				
		objTable.addRow();
		objTable.addRowData("image", "Botão X");
		objTable.addRowData("integer", 900);
		objTable.addRowData("long", 9000);
		//objTable.addRowData("double", 90.90);
		objTable.addRowData("string", "Nono");
		objTable.addRowData("text", "Nono livre");
		objTable.addRowData("date", "09/09/2009");
		objTable.addRowData("hour", "08:00");
		objTable.addRowData("boolean", false);
		
		objTable.addRow();
		objTable.addRowData("image", new ImageIcon("res/images/search.png"));
		//objTable.addRowData("integer", 1000);
		objTable.addRowData("long", 10000);
		objTable.addRowData("double", 100.00);
		objTable.addRowData("string", "Décimo");
		objTable.addRowData("text", "Décimo livre");
		objTable.addRowData("date", "10/10/2010");
		objTable.addRowData("hour", "10:10");
		objTable.addRowData("boolean", false);
		
		objTable.setRowColor(1, Color.LIGHT_GRAY);
		objTable.setRowColor(7, Color.LIGHT_GRAY);
		
		objTable.setRowSelection(5);
	}
	
	private void setComponentEnabled(Container container, boolean blnEnabled) {
		try {
			Component[] components = container.getComponents();
			
	        for (Component component : components) {
	        	component.setEnabled(blnEnabled);
	        	
	        	if (component instanceof LTTextField) {
	            	((LTTextField) component).setEnabled(blnEnabled);
	        	} else if (component instanceof LTComboBoxField) {
	            	((LTComboBoxField) component).setEnabled(blnEnabled);
	            } else if (component instanceof Container) {
	            	setComponentEnabled((Container) component, blnEnabled);
	            }
	        }
	        
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Cria um <i>MouseAdapter</i> para quando clicar em alguma célula da tabela.
	 */
	private class TableMouseAdapter extends MouseAdapter {
		@Override
	    public void mouseClicked(MouseEvent event) {
			if (objTable.getRowCount() > 0) {
				int intIndexRow = objTable.getSelectedRow();
				int intIndexColumn = objTable.getSelectedColumn();
				
				System.out.println("Célula clicada - " + intIndexRow + " | Coluna " + intIndexColumn + " - Valor: " + objTable.getValue(intIndexRow, intIndexColumn));
			}
	    }
	}
	
	// Implementa TableListener
	@Override
	public void cellValueUpdated(Object objValue, int intRowIndex, int intColumnIndex) {
		
	}
}