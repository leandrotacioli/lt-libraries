import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import com.leandrotacioli.libs.swing.comboboxfield.LTComboBoxField;
import com.leandrotacioli.libs.swing.table.LTTable;
import com.leandrotacioli.libs.swing.table.TableListener;
import com.leandrotacioli.libs.swing.textfield.LTTextField;
import com.leandrotacioli.libs.LTDataTypes;
import com.leandrotacioli.libs.LTParameters;

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
 * @author Leandro
 *
 */
public class LTLibraries implements TableListener {
	private JFrame frame;
	private LTTextField txtString;
	private LTTextField txtInteger;
	private LTTextField txtLong;
	private LTTextField txtDouble;
	private LTTextField txtDate;
	private LTTextField txtText;
	private LTTextField txtBoolean;
	private LTComboBoxField comboBox;
	
	private JButton btnNewButton;
	private JButton btnNewButton2;
	private JButton btnNewButton3;
	private JButton btnNewButton4;
	private JButton btnNewButton5;
	
	private LTTable objTable;
	
	private boolean blnHabilitado = true;
	private JPanel panel;
	
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
		frame = new JFrame();
		frame.setBounds(100, 100, 1000, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new MigLayout("", "[grow]", "[grow]"));

		//LTParameters.getInstance().setColorComponentPanelBackground(new Color(10, 52, 10, 1));
		LTParameters.getInstance().setColorComponentPanelBackground(Color.GRAY);
		LTParameters.getInstance().setColorComponentPanelBackground(new Color(240, 240, 240));
		
		txtInteger = new LTTextField("Integer", LTDataTypes.INTEGER, true, true);
		txtInteger.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				
			}
			
			@Override
			public void focusGained(FocusEvent arg0) {

			}
		});
		
		txtLong = new LTTextField("Long", LTDataTypes.LONG, true, true);
		txtDouble = new LTTextField("Double", LTDataTypes.DOUBLE, true, true);
		txtDouble.setFractionDigits(4);
		txtString = new LTTextField("String", LTDataTypes.STRING, false, true, 40);
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
		
		txtText = new LTTextField("Text", LTDataTypes.TEXT, true, true);
		txtDate = new LTTextField("Date", LTDataTypes.DATE, true, true);
		txtBoolean = new LTTextField("Boolean", LTDataTypes.BOOLEAN, true, true);
		
		comboBox = new LTComboBoxField("Combo Box", true, true);
		comboBox.addValues("ABC-1", "ABC");
		comboBox.addValues("DEF-2", "DEF");
		comboBox.addValues("GHI-3", "GHI");
		comboBox.setValue("GHI-3AA");
		//comboBox.setEnabled(false);

		panel = new JPanel();
		panel.setLayout(new MigLayout("insets 0", "[grow]", "[grow][][300.00][][grow][][]"));
		
		frame.getContentPane().add(panel, "cell 0 0, grow");
		
		panel.add(txtInteger, "cell 0 0, grow, width 200");
		panel.add(txtLong, "cell 0 0, grow, width 200");
		panel.add(txtDouble, "cell 0 0, grow, width 200");
	    
		panel.add(txtString, "cell 0 1, grow, width 400");
		panel.add(comboBox, "cell 0 1, grow, width 200");

		panel.add(txtText, "cell 0 2, grow");
		
		panel.add(txtDate, "cell 0 3, width 100, grow");
		panel.add(txtBoolean, "cell 0 3, width 200, aligny bottom");
	    
		btnNewButton = new JButton("Imprime Valores");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				System.out.println("Imprime Valores");
				System.out.println("Integer: " + txtInteger.getValue());
				System.out.println("Long: " + txtLong.getValue());
				System.out.println("Double: " + txtDouble.getValue());
				System.out.println("String: " + txtString.getValue());
				System.out.println("Text: " + txtText.getValue());
				System.out.println("Date: " + txtDate.getValue());
				System.out.println("Boolean: " + txtBoolean.getValue());
				System.out.println("Combo: " + comboBox.getValue());
				System.out.println("Combo Desc: " + comboBox.getValueDescription());
				
				// Tabela
				for (int indexRow = 0; indexRow < objTable.getRowCount(); indexRow++) {
					System.out.print("Row: " + indexRow + " | ");
					for (int indexColumn = 0; indexColumn < objTable.getColumnCount(); indexColumn++) {
						System.out.print(objTable.getColumnName(indexColumn) + ": " + objTable.getValue(indexRow, indexColumn) + " | ");
					}
					
					System.out.println("");
				}
			}
		});
		
		btnNewButton2 = new JButton("Altera Valores");
		btnNewButton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				txtInteger.setValue(Integer.MAX_VALUE - 200);
				txtLong.setValue(Long.MAX_VALUE - 150);
				txtDouble.setValue(12.23);
				txtDouble.setFractionDigits(6);
				txtDate.setValue("05/05/2015");
				txtString.setValue("String Alterado");
				txtText.setValue("Text Alterado");
				txtBoolean.setValue(true);
				comboBox.setValue("DEF");
			}
		});
		
		btnNewButton3 = new JButton("Habilita/Desabilita");
		btnNewButton3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if (blnHabilitado) {
					setComponentEnabled(frame, false);
					blnHabilitado = false;
				} else {
					setComponentEnabled(frame, true);
					blnHabilitado = true;
					txtString.setFocus();
				}
				
				btnNewButton.setEnabled(true);
				btnNewButton2.setEnabled(true);
				btnNewButton3.setEnabled(true);
				btnNewButton4.setEnabled(true);
				btnNewButton5.setEnabled(true);
			}
		});
		
		btnNewButton4 = new JButton("Deleta Colunas / Adiciona Novas");
		btnNewButton4.addActionListener(new ActionListener() {
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
		
		btnNewButton5 = new JButton("Testa tabela");
		btnNewButton5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				objTable.deleteRows();
				
				objTable.addRow();
				objTable.addRowData("nova_string", "AAA " + 1);
				objTable.addRowData("nova_integer", 1 * 100);
				objTable.addRowData("nova_double", 1 * 0.5);
				
				objTable.addRow();
				objTable.addRowData("nova_string", "BBB " + 1);
				objTable.addRowData("nova_integer", 1 * 25);
				objTable.addRowData("nova_double", 1 * 0.25);
			}
		});
		
		objTable = new LTTable(false, true);
		objTable.addColumn("integer", "Integer", LTDataTypes.INTEGER, 80, true);
		objTable.addColumn("long", "Long", LTDataTypes.LONG, 80, true);
		objTable.addColumn("double", "Double", LTDataTypes.DOUBLE, 80, true);
		objTable.addColumn("double_2", "Double 2", LTDataTypes.DOUBLE, 80, true);
		objTable.addColumn("string", "String", LTDataTypes.STRING, 80, true);
		objTable.addColumn("string_editable", "String Editable", LTDataTypes.STRING, 80, true);
		objTable.addColumn("string_not_editable", "String Not Editable", LTDataTypes.STRING, 80, false);
		objTable.addColumn("text", "Text", LTDataTypes.TEXT, 80, true);
		objTable.addColumn("date", "Date", LTDataTypes.DATE, 80, true);
		objTable.addColumn("boolean", "Boolean", LTDataTypes.BOOLEAN, 80, true);
		objTable.addMouseListener(new TableMouseAdapter());
		objTable.addTableListener(LTLibraries.this);
		objTable.setColumnStringMaximumLength("string", 10);
		objTable.setColumnDoubleFractionDigits("double", 1);
		objTable.setColumnDoubleFractionDigits("double_2", 3);
		//objTable.setAllowSortedRows(false);
		objTable.showTable();
		
		addRows();

		frame.getContentPane().add(objTable, "cell 0 6,grow");

		frame.getContentPane().add(btnNewButton, "flowx,cell 0 7");
		frame.getContentPane().add(btnNewButton2, "cell 0 7");
		frame.getContentPane().add(btnNewButton3, "cell 0 7");
		frame.getContentPane().add(btnNewButton4, "cell 0 7");
		frame.getContentPane().add(btnNewButton5, "cell 0 7");
		//frame.getContentPane().setBackground(Color.GREEN);
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
	 * Cria um <i>MouseAdapter</i> responsável pelo
	 * carregamento de uma biblioteca quando houver 
	 * duplo clique em um registro.
	 */
	private class TableMouseAdapter extends MouseAdapter {
		@Override
	    public void mouseClicked(MouseEvent event) {
			if (event.getClickCount() == 2) {
				if (objTable.getRowCount() > 0) {
					
				}
			}
	    }
	}
	
	private void addRows() {
		objTable.addRow();
		objTable.addRowData("integer", 100);
		objTable.addRowData("long", 1000);
		objTable.addRowData("double", 10.10);
		objTable.addRowData("string", "Primeiro");
		objTable.addRowData("string_not_editable", "Primeiro Fixo");
		objTable.addRowData("text", "Primeiro livre");
		objTable.addRowData("date", "01/01/2001");
		objTable.addRowData("boolean", true);
		
		objTable.addRow();
		objTable.addRowData("integer", 200);
		objTable.addRowData("long", 2000);
		objTable.addRowData("double", 20.20);
		objTable.addRowData("string", "Segundo");
		objTable.addRowData("string_not_editable", "Segundo Fixo");
		objTable.addRowData("text", "Segundo livre");
		objTable.addRowData("date", "02/02/2002");
		objTable.addRowData("boolean", false);
		
		objTable.addRow();
		objTable.addRowData("integer", 300);
		objTable.addRowData("long", 3000);
		objTable.addRowData("double", 30.30);
		objTable.addRowData("string", "Terceiro");
		objTable.addRowData("string_not_editable", "Terceiro Fixo");
		objTable.addRowData("text", "Terceiro livre");
		objTable.addRowData("date", "03/03/2003");
		objTable.addRowData("boolean", true);
		
		objTable.addRow();
		objTable.addRowData("integer", 400);
		objTable.addRowData("long", 4000);
		objTable.addRowData("double", 40.40);
		objTable.addRowData("string", "Quarto");
		objTable.addRowData("text", "Quarto livre");
		objTable.addRowData("date", "04/04/2004");
		objTable.addRowData("boolean", false);
		
		objTable.addRow();
		objTable.addRowData("integer", 500);
		objTable.addRowData("long", 5000);
		objTable.addRowData("double", 50.50);
		objTable.addRowData("string", "Quinto");
		objTable.addRowData("text", "Quinto livre");
		objTable.addRowData("date", "05/05/2005");
		objTable.addRowData("boolean", true);
		
		objTable.addRow();
		objTable.addRowData("integer", 600);
		objTable.addRowData("long", 6000);
		objTable.addRowData("double", 60.60);
		objTable.addRowData("string", "Sexto");
		objTable.addRowData("text", "Sexto livre");
		objTable.addRowData("date", "06/06/2006");
		objTable.addRowData("boolean", false);
		
		objTable.addRow();
		objTable.addRowData("integer", 700);
		objTable.addRowData("long", 7000);
		objTable.addRowData("double", 70.70);
		objTable.addRowData("string", "Sétimo");
		objTable.addRowData("text", "Sétimo livre");
		objTable.addRowData("date", "07/07/2007");
		objTable.addRowData("boolean", true);
		
		objTable.addRow();
		objTable.addRowData("integer", 800);
		objTable.addRowData("long", 8000);
		objTable.addRowData("double", 80.80);
		objTable.addRowData("string", "Oitavo");
		objTable.addRowData("text", "Oitavo livre");
		objTable.addRowData("date", "08/08/2008");
		objTable.addRowData("boolean", false);
		
		objTable.setRowSelection(objTable.getRowCount() - 1);
				
		objTable.addRow();
		objTable.addRowData("integer", 900);
		objTable.addRowData("long", 9000);
		objTable.addRowData("double", 90.90);
		objTable.addRowData("string", "Nono");
		objTable.addRowData("text", "Nono livre");
		objTable.addRowData("date", "09/09/2009");
		objTable.addRowData("boolean", false);
		
		//objTable.setRowSelection(objTable.getRowCount() - 1);
		
		objTable.addRow();
		objTable.addRowData("integer", 1000);
		objTable.addRowData("long", 10000);
		objTable.addRowData("double", 100.00);
		objTable.addRowData("string", "Décimo");
		objTable.addRowData("text", "Décimo livre");
		objTable.addRowData("date", "10/10/2010");
		objTable.addRowData("boolean", false);
		
		//objTable.setRowSelection(objTable.getRowCount() - 1);
		
		objTable.setRowSelection(3);
		
		//objTable.finishAddRows();
	}
	
	// Implementa TableListener
	@Override
	public void cellValueUpdated(Object objValue, int intRowIndex, int intColumnIndex) {
		
	}
}
