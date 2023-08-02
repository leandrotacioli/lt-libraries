package com.leandrotacioli.libs;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import com.leandrotacioli.libs.internationalization.Locales;
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
	private String DEFAULT_LOCALE_LANGUAGE = Locales.LOCALE_COUNTRY_USA;
	private String DEFAULT_LOCALE_COUNTRY = Locales.LOCALE_COUNTRY_USA;
	
	private String strDateFormat;
	private String strDecimalMark;
	private String strLocaleLanguage;
	private String strLocaleCountry;
	
	private Locale locale;
	
	private ResourceBundle rsBundle;
	
	private DecimalFormatSymbols decimalFormatSymbols;

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
	 * Retorna a pasta de arquivos "resources".
	 *
	 * @return resourcePath
	 */
	public String getResourcesFolder() {
		Path resourceDirectory = Paths.get("src","main", "resources");
		String resourcePath = resourceDirectory.toFile().getAbsolutePath();

		return resourcePath + "\\";
	}
}