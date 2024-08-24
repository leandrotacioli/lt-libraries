package com.leandrotacioli;

import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.leandrotacioli.libs.swing.LTSwing;
import com.leandrotacioli.libs.swing.comboboxfield.LTComboBoxField;
import com.leandrotacioli.libs.swing.table.TableButton;
import com.leandrotacioli.libs.swing.textfield.LTTextField;
import net.miginfocom.swing.MigLayout;

import com.leandrotacioli.libs.swing.LTPanel;
import com.leandrotacioli.libs.swing.LTTitledBorder;
import com.leandrotacioli.libs.swing.table.LTTable;
import com.leandrotacioli.libs.swing.table.TableListener;
import com.leandrotacioli.libs.LTDataTypes;
import com.leandrotacioli.libs.LTParameters;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Testes contendo exemplos da LT Libraries - Swing.
 * 
 * @author Leandro Tacioli
 */
public class LTLibrariesSwing implements TableListener {
	private JFrame frame;
	
	private JPanel panelFields;
	private LTTextField txtString;
	private LTTextField txtStringRightAligned;
	private LTTextField txtInteger;
	private LTTextField txtLong;
	private LTTextField txtDouble;
	private LTTextField txtDoubleAsPercentage;
	private LTTextField txtDate;
	private LTTextField txtTime;
	private LTTextField txtText;
	private LTTextField txtBoolean;
	private LTComboBoxField comboBox;
	
	private JPanel panelTable;
	private LTTable objTable;
	
	private JButton btnPrintValues;
	private JButton btnChangeValues;
	private JButton btnEnableDisable;
	private JButton btnChangeTableStructure;
	private JButton btnChangeLocaleEnglishUs;
	private JButton btnChangeLocalePortuguesBr;
	
	private boolean blnHabilitado = true;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LTLibrariesSwing window = new LTLibrariesSwing();
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
	public LTLibrariesSwing() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		LTSwing.getInstance().setColorComponentPanelBackground(Color.GRAY);
		LTSwing.getInstance().setColorComponentPanelBackground(new Color(240, 240, 240));
		
		frame = new JFrame("LT Libraries");
		frame.setMinimumSize(new Dimension(1300, 800));
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new MigLayout("", "[grow]", "[][grow][]"));
		
		// *********************************************************************************************************************
		// Painel de Campos
		panelFields = new JPanel();
		panelFields.setLayout(new MigLayout("insets 5", "[grow]", "[][][100.00][]"));
		
		txtInteger = new LTTextField(getLabelFromBundle("field_integer"), LTDataTypes.INTEGER, true, true);
		txtInteger.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				System.out.println(getLabelFromBundle("field_integer") + " - " + getLabelFromBundle("focus_lost"));
			}
			
			@Override
			public void focusGained(FocusEvent arg0) {
				System.out.println(getLabelFromBundle("field_integer") + " - " + getLabelFromBundle("focus_gained"));
			}
		});
		
		txtLong = new LTTextField(getLabelFromBundle("field_long"), LTDataTypes.LONG, true, true);
		
		txtDouble = new LTTextField(getLabelFromBundle("field_double"), LTDataTypes.DOUBLE, true, true);
		txtDouble.setFractionDigits(4);
		
		txtDoubleAsPercentage = new LTTextField(getLabelFromBundle("field_double") + " - " + getLabelFromBundle("percentage"), LTDataTypes.DOUBLE, true, true);
		txtDoubleAsPercentage.setFractionDigits(2);
		txtDoubleAsPercentage.setShowAsPercentage(true);
		
		txtString = new LTTextField(getLabelFromBundle("field_string"), LTDataTypes.STRING, true, true, 40);
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
		
		txtStringRightAligned = new LTTextField(getLabelFromBundle("field_string") + " - " + getLabelFromBundle("alignment_right"), LTDataTypes.STRING, false, true, 100);
		txtStringRightAligned.setHorizontalAlignment(SwingConstants.RIGHT);
		
		txtText = new LTTextField(getLabelFromBundle("field_text"), LTDataTypes.TEXT, false, true);
		txtDate = new LTTextField(getLabelFromBundle("field_date"), LTDataTypes.DATE, true, true);
		txtTime = new LTTextField(getLabelFromBundle("field_time"), LTDataTypes.TIME, true, true);
		txtBoolean = new LTTextField(getLabelFromBundle("field_boolean"), LTDataTypes.BOOLEAN, true, true);
		
		comboBox = new LTComboBoxField(getLabelFromBundle("field_combo_box"), true, true);
		comboBox.addValues("1", "ABC-Z");
		comboBox.addValues("2", "DEF-Y");
		comboBox.addValues("3", "GHI-T");
		comboBox.addValues("4", "JKL-W");
		comboBox.addValues("5", "MNI-X");
		comboBox.setValue("4");
		
		panelFields.add(txtInteger, "cell 0 0, grow, width 200");
		panelFields.add(txtLong, "cell 0 0, grow, width 200");
		panelFields.add(txtDouble, "cell 0 0, grow, width 200");
		panelFields.add(txtDoubleAsPercentage, "cell 0 0, grow, width 200");
	    
		panelFields.add(txtString, "cell 0 1, grow, width 400");
		panelFields.add(txtStringRightAligned, "cell 0 1, grow, width 400");
		
		panelFields.add(txtText, "cell 0 2, grow");
		
		panelFields.add(comboBox, "cell 0 3, grow, width 200");
		panelFields.add(txtDate, "cell 0 3, width 100, grow");
		panelFields.add(txtTime, "cell 0 3, width 100, grow");
		panelFields.add(txtBoolean, "cell 0 3, width 200, aligny bottom");
	    
		// *********************************************************************************************************************
		// Painel da Tabela
		panelTable = new JPanel();
		panelTable.setLayout(new MigLayout("insets 5", "[grow]", "[grow]"));
		
		objTable = new LTTable(false, true);
		objTable.addColumn("image", getLabelFromBundle("image"), LTDataTypes.BUTTON, 90, true);
		objTable.addColumn("integer", getLabelFromBundle("field_integer"), LTDataTypes.INTEGER, 90, true, false);
		objTable.addColumn("integer_show_zero_values", getLabelFromBundle("field_integer") + " - " + getLabelFromBundle("show_zero_values"), LTDataTypes.INTEGER, 160, true, true);
		objTable.addColumn("long", getLabelFromBundle("field_long"), LTDataTypes.LONG, 90, true, false);
		objTable.addColumn("double", getLabelFromBundle("field_double"), LTDataTypes.DOUBLE, 90, true, false);
		objTable.addColumn("double_show_zero_values", getLabelFromBundle("field_double") + " - " + getLabelFromBundle("show_zero_values"), LTDataTypes.DOUBLE, 160, true, true);
		objTable.addColumn("double_percentage", getLabelFromBundle("field_double") + " - " + getLabelFromBundle("percentage"), LTDataTypes.DOUBLE, 160, true, true);
		objTable.addColumn("string", getLabelFromBundle("field_string"), LTDataTypes.STRING, 170, true);
		objTable.addColumn("string_right", getLabelFromBundle("field_string") + " - " + getLabelFromBundle("alignment_right"), LTDataTypes.STRING, 170, true);
		objTable.addColumn("string_read_only", getLabelFromBundle("field_string") + " - " + getLabelFromBundle("read_only"), LTDataTypes.STRING, 140, false);
		objTable.addColumn("text", getLabelFromBundle("field_text"), LTDataTypes.TEXT, 140, true);
		objTable.addColumn("date", getLabelFromBundle("field_date"), LTDataTypes.DATE, 100, true);
		objTable.addColumn("time", getLabelFromBundle("field_time"), LTDataTypes.TIME, 100, true);
		objTable.addColumn("boolean", getLabelFromBundle("field_boolean"), LTDataTypes.BOOLEAN, 80, true);
		objTable.addMouseListener(new TableMouseAdapter());
		objTable.addTableListener(LTLibrariesSwing.this);
		objTable.setColumnStringMaximumLength("string", 10);
		objTable.setColumnDoubleFractionDigits("double", 1);
		objTable.setColumnDoubleFractionDigits("double_show_zero_values", 4);
		objTable.setColumnDoubleFractionDigits("double_percentage", 3);
		objTable.setColumnDoubleShowAsPercentage("double_percentage", true);
		objTable.setColumnHorizontalAlignment("string_right", 4);
		objTable.setColumnHorizontalAlignment("double", 2);
		objTable.setColumnHorizontalAlignment("date", 0);
		objTable.setColumnColor("integer", Color.RED);
		objTable.showTable();
		
		panelTable.add(objTable, "cell 0 0, grow");
		
		// *********************************************************************************************************************
		btnPrintValues = new JButton("Imprime Valores");
		btnPrintValues.addActionListener(event -> {
			System.out.println("---------------------------------------------------------------------");
			System.out.println("Imprime Valores");
			System.out.println(getLabelFromBundle("field_integer") + ": " + txtInteger.getValue());
			System.out.println(getLabelFromBundle("field_long") + ": " + txtLong.getValue());
			System.out.println(getLabelFromBundle("field_double") + ": " + txtDouble.getValue());
			System.out.println(getLabelFromBundle("field_double") + " - " + getLabelFromBundle("percentage") + ": " + txtDoubleAsPercentage.getValue());
			System.out.println(getLabelFromBundle("field_string") + ": " + txtString.getValue());
			System.out.println(getLabelFromBundle("field_string") + " (" + getLabelFromBundle("alignment_right") + "): " + txtStringRightAligned.getValue());
			System.out.println(getLabelFromBundle("field_text") + ": " + txtText.getValue());
			System.out.println(getLabelFromBundle("field_date") + ": " + txtDate.getValue());
			System.out.println(getLabelFromBundle("field_time") + ": " + txtTime.getValue());
			System.out.println(getLabelFromBundle("field_boolean") + ": " + txtBoolean.getValue());
			System.out.println(getLabelFromBundle("field_combo_box") + " (" + getLabelFromBundle("value_select") + "): " + comboBox.getValue());
			System.out.println(getLabelFromBundle("field_combo_box") + " (" + getLabelFromBundle("value_select") + " " + getLabelFromBundle("description") + "): " + comboBox.getValueDescription());
			System.out.println();

			// Tabela
			System.out.println("Dados da Tabela - Total de Linhas: " + objTable.getRowCount());
			for (int indexRow = 0; indexRow < objTable.getRowCount(); indexRow++) {
				System.out.print("Row: " + indexRow + " | ");
				for (int indexColumn = 0; indexColumn < objTable.getColumnCount(); indexColumn++) {
					System.out.print(objTable.getColumnName(indexColumn) + ": " + objTable.getValue(indexRow, indexColumn) + " | ");
				}
				System.out.println();
			}
			System.out.println("---------------------------------------------------------------------");
		});
		
		btnChangeValues = new JButton("Altera Valores");
		btnChangeValues.addActionListener(event -> {
			txtInteger.setValue(Integer.MAX_VALUE - 200);

			txtLong.setValue(Long.MAX_VALUE - 150);

			txtDouble.setValue(-12.123456);
			txtDouble.setFractionDigits(6);

			txtDate.setValue("05/05/2015");

			txtTime.setValue("12:12");

			txtString.setValue("XPTO");

			txtStringRightAligned.setValue("XPTO - YYZ");

			txtText.setValue("In publishing and graphic design, Lorem ipsum is a placeholder text commonly used to demonstrate the visual form of a document or a typeface without relying on meaningful content. Lorem ipsum may be used as a placeholder before the final copy is available. It is also used to temporarily replace text in a process called greeking, which allows designers to consider the form of a webpage or publication, without the meaning of the text influencing the design." +
							 "\r\n" +
							 "\r\n" +
							 "Lorem ipsum is typically a corrupted version of De finibus bonorum et malorum, a 1st-century BC text by the Roman statesman and philosopher Cicero, with words altered, added, and removed to make it nonsensical and improper Latin.");

			txtBoolean.setValue(true);

			comboBox.removeValues();
			comboBox.addValues("JKL", "JKL-4");
			comboBox.addValues("MNO", "MNO-5");
			comboBox.addValues("PQR", "PQR-6");
			comboBox.setValue("MNO");
		});
		
		btnEnableDisable = new JButton("Habilita/Desabilita");
		btnEnableDisable.addActionListener(event -> {
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
			btnChangeLocaleEnglishUs.setEnabled(true);
			btnChangeLocalePortuguesBr.setEnabled(true);
		});
		
		btnChangeTableStructure = new JButton("Alteração Estrutura Tabela");
		btnChangeTableStructure.addActionListener(event -> {
			objTable.deleteRows();
			objTable.deleteColumns();

			objTable.addColumn("nova_string", "Nova String", LTDataTypes.STRING, 80, true);
			objTable.addColumn("nova_integer", "Nova Integer", LTDataTypes.INTEGER, 80, true);
			objTable.addColumn("nova_double", "Nova Double", LTDataTypes.DOUBLE, 80, true);

			objTable.addRow();
			objTable.addRowData("nova_string", "AAA " + 1);
			objTable.addRowData("nova_integer", 100);
			objTable.addRowData("nova_double", 1 * 0.5);

			objTable.addRow();
			objTable.addRowData("nova_string", "BBB " + 1);
			objTable.addRowData("nova_integer", 25);
			objTable.addRowData("nova_double", 1 * 0.25);

			objTable.addRow();
			objTable.addRowData("nova_string", "AAA " + 3);
			objTable.addRowData("nova_integer", 10);
			objTable.addRowData("nova_double", 1 * 10.5);
		});
		
		btnChangeLocaleEnglishUs = new JButton("Locale - English US");
		btnChangeLocaleEnglishUs.addActionListener(event -> {
			LTParameters.getInstance().setLocale(LTParameters.getInstance().LOCALE_LANGUAGE_ENGLISH, LTParameters.getInstance().LOCALE_COUNTRY_USA);
			setFieldDescriptions();
			JOptionPane.showMessageDialog(null, "Locale changed successfully!", "Locale: " + LTParameters.getInstance().LOCALE_LANGUAGE_ENGLISH + "-" + LTParameters.getInstance().LOCALE_COUNTRY_USA, JOptionPane.INFORMATION_MESSAGE);
		});
		
		btnChangeLocalePortuguesBr = new JButton("Locale - Português Brasil");
		btnChangeLocalePortuguesBr.addActionListener(event -> {
			LTParameters.getInstance().setLocale(LTParameters.getInstance().LOCALE_LANGUAGE_PORTUGUESE, LTParameters.getInstance().LOCALE_COUNTRY_BRAZIL);
			setFieldDescriptions();
			JOptionPane.showMessageDialog(null, "Locale alterado com sucesso!", "Locale: " + LTParameters.getInstance().LOCALE_LANGUAGE_PORTUGUESE + "-" + LTParameters.getInstance().LOCALE_COUNTRY_BRAZIL, JOptionPane.INFORMATION_MESSAGE);
		});
		
		// *********************************************************************************************************************
		// Inclusão dos Painéis e Botões no Frame
		frame.getContentPane().add(panelFields, "cell 0 0, grow");
		frame.getContentPane().add(panelTable, "cell 0 1, grow");

		frame.getContentPane().add(btnPrintValues, "cell 0 2");
		frame.getContentPane().add(btnChangeValues, "cell 0 2");
		frame.getContentPane().add(btnEnableDisable, "cell 0 2");
		frame.getContentPane().add(btnChangeTableStructure, "cell 0 2");
		frame.getContentPane().add(btnChangeLocaleEnglishUs, "cell 0 2");
		frame.getContentPane().add(btnChangeLocalePortuguesBr, "cell 0 2");
		
		setFieldDescriptions();
		
		addRows();
	}
	
	private void addRows() {
		ImageIcon imageExample = new ImageIcon(LTParameters.getInstance().getResourcesFolder() + "images/search.png");
		ImageIcon imageAlert = new ImageIcon(LTParameters.getInstance().getResourcesFolder() + "images/alert.png");
		ImageIcon imageSave = new ImageIcon(LTParameters.getInstance().getResourcesFolder() + "images/save.png");

		objTable.addRow();
		objTable.addRowData("image", imageExample);
		objTable.addRowData("integer", 100);
		objTable.addRowData("long", 1000);
		objTable.addRowData("double", 10.10);
		objTable.addRowData("double_percentage", 1.15);
		objTable.addRowData("string", "Primeiro");
		objTable.addRowData("string_read_only", "Primeiro Fixo");
		objTable.addRowData("text", "Primeiro livre");
		objTable.addRowData("date", "01/01/2001");
		objTable.addRowData("time", "08:00");
		objTable.addRowData("boolean", true);
		
		objTable.addRow();
		objTable.addRowData("image", imageExample);
		//objTable.addRowData("integer", 200);
		objTable.addRowData("integer_show_zero_values", 220);
		//objTable.addRowData("long", 2000);
		//objTable.addRowData("double", 20.20);
		objTable.addRowData("double_show_zero_values", 22.22);
		objTable.addRowData("double_percentage", 2.25);
		objTable.addRowData("string", "Segundo");
		objTable.addRowData("string_right", "Segundo Direita");
		objTable.addRowData("string_read_only", "Segundo Fixo");
		objTable.addRowData("text", "Segundo livre");
		objTable.addRowData("date", "02/02/2002");
		objTable.addRowData("time", "09:25");
		objTable.addRowData("boolean", false);
		
		objTable.addRow();
		objTable.addRowData("image", new TableButton(imageAlert, "Este é um alerta", false));
		objTable.addRowData("integer", 300);
		objTable.addRowData("long", 3000);
		objTable.addRowData("double", 30);
		objTable.addRowData("double_show_zero_values", -33.333);
		objTable.addRowData("string", "Terceiro");
		objTable.addRowData("string_read_only", "Terceiro Fixo");
		objTable.addRowData("text", "Terceiro livre");
		objTable.addRowData("date", "03/03/2003");
		objTable.addRowData("time", "12:30");
		objTable.addRowData("boolean", true);
		
		objTable.addRow();
		objTable.addRowData("image", imageExample);
		objTable.addRowData("integer", 400);
		objTable.addRowData("long", 4000);
		objTable.addRowData("double", 40.40);
		objTable.addRowData("double_percentage", -4.49);
		objTable.addRowData("string", "Quarto");
		objTable.addRowData("text", "Quarto livre");
		objTable.addRowData("date", "04/04/2004");
		objTable.addRowData("time", "17:00");
		objTable.addRowData("boolean", false);
		
		objTable.addRow();
		objTable.addRowData("image", imageExample);
		objTable.addRowData("integer", 500);
		objTable.addRowData("integer_show_zero_values", 550);
		objTable.addRowData("long", 5000);
		objTable.addRowData("double", 50.50);
		objTable.addRowData("double_percentage", 5.55);
		objTable.addRowData("string", "Quinto");
		objTable.addRowData("text", "Quinto livre");
		objTable.addRowData("date", "05/05/2005");
		objTable.addRowData("time", "00:00");
		objTable.addRowData("boolean", true);
		
		objTable.addRow();
		objTable.addRowData("image", new TableButton(imageSave, "Gravar", true));
		//objTable.addRowData("integer", 600);
		//objTable.addRowData("long", 6000);
		//objTable.addRowData("double", 60.60);
		objTable.addRowData("string", "Sexto");
		objTable.addRowData("text", "Sexto livre");
		objTable.addRowData("date", "06/06/2006");
		objTable.addRowData("time", "05:35");
		objTable.addRowData("boolean", false);
		
		objTable.addRow();
		objTable.addRowData("image", imageExample);
		objTable.addRowData("integer", 700);
		objTable.addRowData("long", 7000);
		objTable.addRowData("double", -70.70);
		objTable.addRowData("string", "Sétimo");
		objTable.addRowData("text", "Sétimo livre");
		objTable.addRowData("date", "07/07/2007");
		objTable.addRowData("time", "08:00");
		objTable.addRowData("boolean", true);
		
		objTable.addRow();
		objTable.addRowData("image", imageExample);
		//objTable.addRowData("integer", 800);
		//objTable.addRowData("long", 8000);
		//objTable.addRowData("double", 80.80);
		objTable.addRowData("double_percentage", -888.889);
		objTable.addRowData("string", "Oitavo");
		objTable.addRowData("text", "Oitavo livre");
		objTable.addRowData("date", "08/08/2008");
		objTable.addRowData("time", "08:15");
		objTable.addRowData("boolean", false);
				
		objTable.addRow();
		objTable.addRowData("image", getLabelFromBundle("button"));
		objTable.addRowData("integer", 900);
		objTable.addRowData("integer_show_zero_values", 990);
		objTable.addRowData("long", 9000);
		//objTable.addRowData("double", 90.90);
		objTable.addRowData("string", "Nono");
		objTable.addRowData("text", "Nono livre");
		objTable.addRowData("date", "09/09/2009");
		objTable.addRowData("time", "08:00");
		objTable.addRowData("boolean", false);
		
		objTable.addRow();
		objTable.addRowData("image", imageExample);
		//objTable.addRowData("integer", 1000);
		objTable.addRowData("long", 10000);
		objTable.addRowData("double", 100.00);
		objTable.addRowData("string", "Décimo");
		objTable.addRowData("text", "Décimo livre");
		objTable.addRowData("date", "10/10/2010");
		objTable.addRowData("time", "10:10");
		objTable.addRowData("boolean", false);
		
		objTable.setRowColor(1, Color.LIGHT_GRAY);
		objTable.setRowColor(7, Color.LIGHT_GRAY);
		
		objTable.setRowSelection(5);
	}
	
	private String getLabelFromBundle(String strLabel) {
		return LTParameters.getInstance().getBundle().getString(strLabel);
	}
	
	private void setFieldDescriptions() {
		LTTitledBorder borderFields = new LTTitledBorder(LTPanel.PANEL_BORDER);
		borderFields.setTitle(getLabelFromBundle("fields"));
		panelFields.setBorder(borderFields);
		
		LTTitledBorder borderTable = new LTTitledBorder(LTPanel.PANEL_BORDER);
		borderTable.setTitle(getLabelFromBundle("table"));
		panelTable.setBorder(borderTable);
		
		txtInteger.setLabel(getLabelFromBundle("field_integer"));
		txtLong.setLabel(getLabelFromBundle("field_long"));
		txtDouble.setLabel(getLabelFromBundle("field_double"));
		txtDoubleAsPercentage.setLabel(getLabelFromBundle("field_double") + " - " + getLabelFromBundle("percentage"));
		txtBoolean.setLabel(getLabelFromBundle("field_boolean"));
		txtString.setLabel(getLabelFromBundle("field_string"));
		txtStringRightAligned.setLabel(getLabelFromBundle("field_string") + " - " + getLabelFromBundle("alignment_right"));
		txtText.setLabel(getLabelFromBundle("field_text"));
		comboBox.setLabel(getLabelFromBundle("field_combo_box"));
		txtDate.setLabel(getLabelFromBundle("field_date"));
		txtTime.setLabel(getLabelFromBundle("field_time"));

		objTable.setColumnDescription("image", getLabelFromBundle("image"));
		objTable.setColumnDescription("integer", getLabelFromBundle("field_integer"));
		objTable.setColumnDescription("integer_show_zero_values", getLabelFromBundle("field_integer") + " - " + getLabelFromBundle("show_zero_values"));
		objTable.setColumnDescription("long", getLabelFromBundle("field_long"));
		objTable.setColumnDescription("double", getLabelFromBundle("field_double"));
		objTable.setColumnDescription("double_show_zero_values", getLabelFromBundle("field_double") + " - " + getLabelFromBundle("show_zero_values"));
		objTable.setColumnDescription("double_percentage", getLabelFromBundle("field_double") + " - " + getLabelFromBundle("percentage"));
		objTable.setColumnDescription("string", getLabelFromBundle("field_string"));
		objTable.setColumnDescription("string_right", getLabelFromBundle("field_string") + " - " + getLabelFromBundle("alignment_right"));
		objTable.setColumnDescription("string_read_only", getLabelFromBundle("field_string") + " - " + getLabelFromBundle("read_only"));
		objTable.setColumnDescription("text", getLabelFromBundle("field_text"));
		objTable.setColumnDescription("date", getLabelFromBundle("field_date"));
		objTable.setColumnDescription("time", getLabelFromBundle("field_time"));
		objTable.setColumnDescription("boolean", getLabelFromBundle("field_boolean"));
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