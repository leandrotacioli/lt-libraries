package com.leandrotacioli;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import com.leandrotacioli.libs.LTParameters;
import com.leandrotacioli.libs.swing.LTSwing;
import com.leandrotacioli.libs.swing.comboboxfield.LTComboBoxField;
import com.leandrotacioli.libs.swing.textfield.LTTextField;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.leandrotacioli.libs.swing.LTMenuButton;
import com.leandrotacioli.libs.swing.LTMenuButtonPanel;

import net.miginfocom.swing.MigLayout;
import com.leandrotacioli.libs.LTDataTypes;

/**
 * Cria os parâmetros necessários para a execução do </i>LT Libraries</i>.
 * 
 * @author Leandro Tacioli
 */
public class LTProperties {
	private JFrame frame;
	
	private LTMenuButtonPanel buttonPanel;
	private LTMenuButton btnSave;
	private LTComboBoxField cboDateFormat;
	private LTComboBoxField cboDecimalMark;
	private LTTextField txtLocaleLanguage;
	private LTTextField txtLocaleCountry;

	// Constructor
	public LTProperties() {
		frame = new JFrame("LT Config Properties");
		frame.setBounds(100, 100, 600, 125);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new MigLayout("insets 5", "[grow]", "[grow]"));

		LTSwing.getInstance().setColorComponentPanelBackground(Color.GRAY);
		LTSwing.getInstance().setColorComponentPanelBackground(new Color(240, 240, 240));
		
		// Centered frame position
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int intX = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
		int intY = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
		frame.setLocation(intX, intY);
		
		// Button Bar
		buttonPanel = new LTMenuButtonPanel();

	    btnSave = new LTMenuButton("Save", LTParameters.getInstance().getResourcesFolder() + "images/save.png");
	    btnSave.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		createPropertiesXML();
	    	}
	    });
	    
	    buttonPanel.add(btnSave, "cell 0 0, growx");

	    cboDateFormat = new LTComboBoxField("Date Format:", true, true);
	    cboDateFormat.addValues("dd/MM/yyyy", "dd/MM/yyyy");
	    cboDateFormat.addValues("MM/dd/yyyy", "MM/dd/yyyy");
	   
	    cboDecimalMark = new LTComboBoxField("Decimal Mark:", true, true);
	    cboDecimalMark.addValues("COMMA", "COMMA");
	    cboDecimalMark.addValues("PERIOD", "PERIOD");
	    	    
	    txtLocaleLanguage = new LTTextField("Locale Language:", LTDataTypes.STRING, true, true);
	    txtLocaleCountry = new LTTextField("Locale Country:", LTDataTypes.STRING, true, true);
	    
	    frame.getContentPane().add(buttonPanel, "north");
	    frame.getContentPane().add(cboDateFormat, "cell 0 0, growx");
	    frame.getContentPane().add(cboDecimalMark, "cell 1 0, growx");
	    frame.getContentPane().add(txtLocaleLanguage, "cell 2 0, growx");
	    frame.getContentPane().add(txtLocaleCountry, "cell 3 0, growx");
	    
	    readPropertiesXML();
	}
	
	//*************************************************************************
	// Main
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LTProperties window = new LTProperties();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	//*************************************************************************
	// Creates Properties.xml File
	private void createPropertiesXML() {
		try {	
			String strDateFormat = cboDateFormat.getValue().toString();
			String strDecimalMark = cboDecimalMark.getValue().toString();
			String strLocaleLanguage = txtLocaleLanguage.getValue().toString();
			String strLocaleCountry = txtLocaleCountry.getValue().toString();
			
			// XML
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			
			// Properties
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("Properties");
			doc.appendChild(rootElement);
	 
				// Config
				Element configElement = doc.createElement("Config");
				rootElement.appendChild(configElement);
				
					// Date Format
					Element eDateFormat = doc.createElement("DateFormat");
					eDateFormat.appendChild(doc.createTextNode(strDateFormat));
					configElement.appendChild(eDateFormat);
					
					// Decimal Mark
					Element eDecimalMark = doc.createElement("DecimalMark");
					eDecimalMark.appendChild(doc.createTextNode(strDecimalMark));
					configElement.appendChild(eDecimalMark);
					
					// Locale Language
					Element eLocaleLanguage = doc.createElement("LocaleLanguage");
					eLocaleLanguage.appendChild(doc.createTextNode(strLocaleLanguage));
					configElement.appendChild(eLocaleLanguage);
					
					// Locale Country
					Element eLocaleCountry = doc.createElement("LocaleCountry");
					eLocaleCountry.appendChild(doc.createTextNode(strLocaleCountry));
					configElement.appendChild(eLocaleCountry);

			// Saves XML file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			DOMSource domSource = new DOMSource(doc);
			StreamResult streamResult = new StreamResult(new File("LT-Properties.xml"));
			
			transformer.transform(domSource, streamResult);
	 
			JOptionPane.showMessageDialog(frame, "File Saved!", "LT Config Properties", JOptionPane.INFORMATION_MESSAGE);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//*************************************************************************
	// Reads Properties XML and sets the fields to the values
	private void readPropertiesXML() {
		try {
			// Reads XML
			File fileXML = new File("LT-Properties.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document document = dBuilder.parse(fileXML);
			
			NodeList nList = document.getElementsByTagName("Config");
			Node node;
			Element element;
			
			for (int temp = 0; temp < nList.getLength(); temp++) {
				node = nList.item(temp);
		  
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					element = (Element) node;
					
					cboDateFormat.setValue(element.getElementsByTagName("DateFormat").item(0).getTextContent());
					cboDecimalMark.setValue(element.getElementsByTagName("DecimalMark").item(0).getTextContent());
					txtLocaleLanguage.setValue(element.getElementsByTagName("LocaleLanguage").item(0).getTextContent());
					txtLocaleCountry.setValue(element.getElementsByTagName("LocaleCountry").item(0).getTextContent());
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}