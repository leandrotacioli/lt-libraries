package com.leandrotacioli.libs;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.io.File;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Cria os parâmetros necessários para a execução do </i>LT Libraries</i>. 
 * 
 * @author Leandro Tacioli
 * @version 4.0 - 22/Mai/2020
 */
public class LTParameters {
	private static LTParameters objLTParameters;
	
	private String DEFAULT_DATE_FORMAT = "dd/MM/yyyy";
	private String DEFAULT_DECIMAL_MARK = "COMMA";
	private String DEFAULT_LOCALE_LANGUAGE = "en";
	private String DEFAULT_LOCALE_COUNTRY = "US";
	
	private String strDateFormat;
	private String strDecimalMark;
	private String strLocaleLanguage;
	private String strLocaleCountry;
	
	private Locale locale;
	
	private ResourceBundle rsBundle;
	
	private DecimalFormatSymbols decimalFormatSymbols;
	
	private int intTableRowHeight;
	
	private Dimension dimensionComponentMinimumSize;
	private Dimension dimensionComponentMaximumSize;
	
	private Font fontComponentLabel;
	private Font fontComponentTextField;
	private Font fontTableHeader;
	private Font fontTableTextField;
	
	private Color colorComponentPanelBackground;
	
	private Color colorComponentBorder;
	private Color colorComponentForeground;
	private Color colorComponentBackground;
	private Color colorComponentBackgroundFocus;
	private Color colorComponentBackgroundDisabled;
	
	private Color colorTable;
	private Color colorTableGrid;
	private Color colorTableGridDisabled;
	private Color colorTableRowSelected;
	
	private Color colorComboBoxRowSelected;
	
	private CompoundBorder borderComponent;
	private CompoundBorder borderComponentError;
	
	private CompoundBorder borderTableTextField;
	private CompoundBorder borderTableTextFieldFocus;
	private CompoundBorder borderTableTextFieldEditing;
	private CompoundBorder borderTableTextFieldError;

	/**
	 * Retorna o formato da data.
	 * 
	 * @return strDateFormat
	 */
	public String getDateFormat() {
		return strDateFormat;
	}
	
	/**
	 * Retorna o marcador decimal.
	 * 
	 * @return strDecimalMark
	 */
	public String getDecimalMark() {
		return strDecimalMark;
	}
	
	/**
	 * Retorna a linguagem do locale.
	 * 
	 * @return strLocaleLanguage
	 */
	public String getLocaleLanguage() {
		return strLocaleLanguage;
	}

	/**
	 * Retorna o país do locale.
	 * 
	 * @return strLocaleCountry
	 */
	public String getLocaleCountry() {
		return strLocaleCountry;
	}

	/**
	 * Retorna o locale.
	 * 
	 * @return locale
	 */
	public Locale getLocale() {
		return locale;
	}
	
	/**
	 * Altera o locale.
	 * 
	 * @param strLocaleLanguage
	 * @param strLocaleCountry
	 */
	public void setLocale(String strLocaleLanguage, String strLocaleCountry) {
		this.strLocaleLanguage = strLocaleLanguage;
		this.strLocaleCountry = strLocaleCountry;
				
		locale = new Locale(strLocaleLanguage, strLocaleCountry);
		
		rsBundle = ResourceBundle.getBundle("com.leandrotacioli.libs.internationalization.LabelBundles", locale);
	}
	
	/**
	 * Retorna o pacote de linguagens do sistema.
	 * 
	 * @return rsBundle
	 */
	public ResourceBundle getBundle() {
		return rsBundle;
	}
	
	/**
	 * Retorna os símbolos para os separadores decimais.<br>
	 * <br>
	 * Basicamente utilizados nos campos <i>DOUBLE</i>.
	 * 
	 * @return decimalFormatSymbols
	 */
	public DecimalFormatSymbols getDecimalFormatSymbols() {
		return decimalFormatSymbols;
	}
	
	/**
	 * Retorna a altura das linhas da tabela.
	 * 
	 * @return intTableCellHeight
	 */
	public int getTableRowHeight() {
		return intTableRowHeight;
	}
	
	/**
	 * Retorna o tamanho mínimo do componente.
	 * 
	 * @return dimensionComponentMinimumSize
	 */
	public Dimension getDimensionComponentMinimumSize() {
		return dimensionComponentMinimumSize;
	}
	
	/**
	 * Retorna o tamanho máximo do componente.
	 * 
	 * @return dimensionComponentMaximumSize
	 */
	public Dimension getDimensionComponentMaximumSize() {
		return dimensionComponentMaximumSize;
	}
	
	/**
	 * Retorna a fonte do texto da label do componente.
	 * 
	 * @return fontComponentLabel;
	 */
	public Font getFontComponentLabel() {
		return fontComponentLabel;
	}
	
	/**
	 * Altera a fonte do texto da label do componente.
	 * 
	 * @param fontComponentLabel
	 */
	public void setFontComponentLabel(Font fontComponentLabel) {
		this.fontComponentLabel = fontComponentLabel;
	}
	
	/**
	 * Fonte do texto do componente.
	 * 
	 * @return fontComponentTextField
	 */
	public Font getFontComponentTextField() {
		return fontComponentTextField;
	}
	
	/**
	 * Fonte do cabeçalho da tabela.
	 * 
	 * @return fontTableHeader
	 */
	public Font getFontTableHeader() {
		return fontTableHeader;
	}
	
	/**
	 * Fonte do texto da tabela.
	 * 
	 * @return fontTableTextField
	 */
	public Font getFontTableTextField() {
		return fontTableTextField;
	}
	
	/**
	 * Cor de fundo do painel do componente.
	 * 
	 * @return colorComponentPanelBackground
	 */
	public Color getColorComponentPanelBackground() {
		return colorComponentPanelBackground;
	}
	
	/**
	 * Altera a cor de fundo do painel do componente.
	 * 
	 * @param colorComponentPanelBackground
	 */
	public void setColorComponentPanelBackground(Color colorComponentPanelBackground) {
		this.colorComponentPanelBackground = new Color(colorComponentPanelBackground.getRed(), colorComponentPanelBackground.getGreen(), colorComponentPanelBackground.getBlue());
	}
	
	/**
	 * Cor da borda do componente.
	 * 
	 * @return colorComponentBorder
	 */
	public Color getColorComponentBorder() {
		return colorComponentBorder;
	}
	
	/**
	 * Altera a cor da borda do componente.
	 * 
	 * @param colorComponentBorder
	 */
	public void setColorComponentBorder(Color colorComponentBorder) {
		this.colorComponentBorder = colorComponentBorder;
		this.borderComponent = new CompoundBorder(new LineBorder(colorComponentBorder), new EmptyBorder(1, 2, 1, 2));
	}
	
	/**
	 * Cor da fonte do componente.
	 * 
	 * @return colorComponentForeground
	 */
	public Color getColorComponentForeground() {
		return colorComponentForeground;
	}
	
	/**
	 * Cor de fundo do componente.
	 * 
	 * @return colorComponentBackground;
	 */
	public Color getColorComponentBackground() {
		return colorComponentBackground;
	}
	
	/**
	 * Altera a cor de fundo do componente.
	 * 
	 * @param colorComponentBackground
	 */
	public void setColorComponentBackground(Color colorComponentBackground) {
		this.colorComponentBackground = new Color(colorComponentBackground.getRed(), colorComponentBackground.getGreen(), colorComponentBackground.getBlue());
	}
	
	/**
	 * Cor de fundo do componente quando focado.
	 * 
	 * @return colorComponentBackgroundFocus
	 */
	public Color getColorComponentBackgroundFocus() {
		return colorComponentBackgroundFocus;
	}
	
	/**
	 * Cor de fundo do componente quando desabilitado.
	 * 
	 * @return colorComponentBackgroundDisabled
	 */
	public Color getColorComponentBackgroundDisabled() {
		return colorComponentBackgroundDisabled;
	}
	
	/**
	 * Cor da LTTable.
	 * 
	 * @return colorTable
	 */
	public Color getColorTable() {
		return colorTable;
	}
	
	/**
	 * Cor do grid da LTTable.
	 * 
	 * @return colorTableGrid
	 */
	public Color getColorTableGrid() {
		return colorTableGrid;
	}
	
	/**
	 * Cor do grid da LTTable quando desabilitado.
	 * 
	 * @return colorTableGridDisabled
	 */
	public Color getColorTableGridDisabled() {
		return colorTableGridDisabled;
	}
	
	/**
	 * Cor da linha do grid da LTTable quando selecionada.
	 * 
	 * @return colorTableRowSelected
	 */
	public Color getColorTableRowSelected() {
		return colorTableRowSelected;
	}
	
	/**
	 * Cor da linha popup da LTComboBoxField quando selecionada.
	 * 
	 * @return colorComboBoxRowSelected
	 */
	public Color getColorComboBoxRowSelected() {
		return colorComboBoxRowSelected;
	}
	
	/**
	 * Borda do componente.
	 * 
	 * @return borderComponent
	 */
	public CompoundBorder getBorderComponent() {
		return borderComponent;
	}
	
	/**
	 * Borda do componente quando houver erro.
	 * 
	 * @return borderComponentError
	 */
	public CompoundBorder getBorderComponentError() {
		return borderComponentError;
	}
	
	/**
	 * Borda do texto da tabela.
	 * 
	 * @return borderTableTextField
	 */
	public CompoundBorder getBorderTableTextField() {
		return borderTableTextField;
	}
	
	/**
	 * Borda do texto da tabela quando focado.
	 * 
	 * @return borderTableTextFieldFocus
	 */
	public CompoundBorder getBorderTableTextFieldFocus() {
		return borderTableTextFieldFocus;
	}
	
	/**
	 * Borda do texto da tabela quando está sendo editado.
	 * 
	 * @return borderTableTextFieldEditing
	 */
	public CompoundBorder getBorderTableTextFieldEditing() {
		return borderTableTextFieldEditing;
	}
	
	/**
	 * Borda do texto da tabela quando está com erro.
	 * 
	 * @return borderTableTextFieldError
	 */
	public CompoundBorder getBorderTableTextFieldError() {
		return borderTableTextFieldError;
	}
	
	/**
	 * Cria uma nova instância para a classe ou retorna a instância previamente criada.
	 * 
	 * @return objLTParameters
	 */
	public static synchronized LTParameters getInstance() {
		if (objLTParameters == null) {
			objLTParameters = new LTParameters();
		}
		
		return objLTParameters;
	}
	
	/**
	 * Cria os parâmetros necessários para a execução do </i>LT Libraries</i>. 
	 */
	private LTParameters() {
		loadPropertiesXML();
		loadComponentSettings();
	}
	
	/**
	 * Carrega o arquivo XML de propriedades e atribui valores aos parâmetros.
	 */
	private void loadPropertiesXML() {
		try {
			// Valores padrão
			strDateFormat     = DEFAULT_DATE_FORMAT;
			strDecimalMark    = DEFAULT_DECIMAL_MARK;
			strLocaleLanguage = DEFAULT_LOCALE_LANGUAGE;
			strLocaleCountry  = DEFAULT_LOCALE_COUNTRY;
			
			// Lê o XML
			File fileXML = new File("LT-Properties.xml");
			
			if (fileXML.exists() && fileXML.isFile())
		    {
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document document = dBuilder.parse(fileXML);
				
				NodeList nodeList = document.getElementsByTagName("Config");

				for (int intNode = 0; intNode < nodeList.getLength(); intNode++) {
					Node node = nodeList.item(intNode);
					
					if (node.getNodeType() == Node.ELEMENT_NODE) {
						Element element = (Element) node;

						strDateFormat = element.getElementsByTagName("DateFormat").item(0).getTextContent();
						strDecimalMark = element.getElementsByTagName("DecimalMark").item(0).getTextContent();
						strLocaleLanguage = element.getElementsByTagName("LocaleLanguage").item(0).getTextContent();
						strLocaleCountry = element.getElementsByTagName("LocaleCountry").item(0).getTextContent();
					}
				}
		    }
			
			locale = new Locale(strLocaleLanguage, strLocaleCountry);
			
			rsBundle = ResourceBundle.getBundle("com.leandrotacioli.libs.internationalization.LabelBundles", locale);
			
			decimalFormatSymbols = new DecimalFormatSymbols();
			
			if (strDecimalMark.equals("COMMA")) {
				decimalFormatSymbols.setDecimalSeparator(',');
				decimalFormatSymbols.setGroupingSeparator('.');
			} else if (strDecimalMark.equals("PERIOD")) {
				decimalFormatSymbols.setDecimalSeparator('.');
				decimalFormatSymbols.setGroupingSeparator(',');
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Carrega as propriedades/valores dos componentes.
	 */
	private void loadComponentSettings() {
		intTableRowHeight = 21;
		
		dimensionComponentMinimumSize = new Dimension(1, 21);
		dimensionComponentMaximumSize = new Dimension(10000, 21);
		
		fontComponentLabel = new Font("SansSerif", Font.BOLD, 12);
		fontComponentTextField = new Font("SansSerif", Font.PLAIN, 12);
		
		fontTableHeader = new Font("SansSerif", Font.ITALIC, 12);
		fontTableTextField = new Font("SansSerif", Font.PLAIN, 12);
		
		colorComponentPanelBackground = new Color(0, 0, 0);
		
		colorComponentBorder = new Color(180, 180, 180);
		colorComponentForeground = new Color(0, 0, 0);
		colorComponentBackground = new Color(255, 255, 254);
		colorComponentBackgroundFocus = new Color(255, 255, 125);
		colorComponentBackgroundDisabled = new Color(240, 240, 240);
		
		colorTable = new Color(255, 255, 255);
		colorTableGrid = new Color(200, 200, 200);
		colorTableGridDisabled = new Color(240, 240, 240);
		colorTableRowSelected = new Color(120, 200, 250);
		
		colorComboBoxRowSelected = new Color(120, 200, 250);
		
		borderComponent = new CompoundBorder(new LineBorder(colorComponentBorder), new EmptyBorder(1, 2, 1, 2));
		borderComponentError = new CompoundBorder(new LineBorder(new Color(255, 0, 0)), new EmptyBorder(1, 2, 1, 2));
		
		borderTableTextField = new CompoundBorder(new LineBorder(new Color(0, 0, 0, 0), 2), new EmptyBorder(1, 2, 1, 2));
		borderTableTextFieldFocus = new CompoundBorder(new LineBorder(new Color(0, 0, 0), 2), new EmptyBorder(1, 2, 1, 2));
		borderTableTextFieldEditing = new CompoundBorder(new LineBorder(new Color(0, 0, 0), 2), new EmptyBorder(2, 2, 2, 2));
		borderTableTextFieldError = new CompoundBorder(new LineBorder(new Color(255, 0, 0), 2), new EmptyBorder(2, 2, 2, 2));
	}
}