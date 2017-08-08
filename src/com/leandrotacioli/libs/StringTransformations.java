package com.leandrotacioli.libs;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Transformações de um valor <i>STRING</i> para outros campos e vice-versa.
 * 
 * @author Leandro Tacioli
 * @version 1.0 - 05/Abr/2015
 */
public class StringTransformations {
	
	/**
	 * Transformações de um valor <i>STRING</i> para outros campos e vice-versa.
	 */
	private StringTransformations() {
		
	}
	
	/**
	 * Transforma um valor <i>STRING</i> em <i>DATE</i>.
	 * 
	 * @param strDate
	 * 
	 * @return dataValue
	 */
	public static Date setStringToDate(String strDate) {
		Date date = null;
		 
		try {
			String strDateFormat = LTParameters.getInstance().getDateFormat();
			SimpleDateFormat dateFormat = new SimpleDateFormat(strDateFormat);
			date = dateFormat.parse(strDate);
		} catch (ParseException e) {
			date = null;
		}
		
		return date;
	}
	
	/**
	 * Transforma um valor <i>DOUBLE</i> em <i>STRING</i>.
	 * 
	 * @param dblValue          - Valor
	 * @param intFractionDigits - Quantidade de casas decimais
	 * 
	 * @return strValue
	 */
	public static String setDoubleToString(double dblValue, int intFractionDigits) {
		String strValue = "0";
		
		DecimalFormat decimalFormat = new DecimalFormat();
		decimalFormat.setMinimumFractionDigits(intFractionDigits);
		decimalFormat.setMaximumFractionDigits(intFractionDigits);
		decimalFormat.setDecimalFormatSymbols(LTParameters.getInstance().getDecimalFormatSymbols());

        try {
    		strValue = decimalFormat.format(dblValue);
        } catch (Exception e) {
        	strValue = decimalFormat.format(0);
        }
        
        return strValue;
	}
	
	/**
	 * Transforma um valor <i>STRING</i> em <i>DOUBLE</i>.
	 * 
	 * @param strValue - Valor
	 * 
	 * @return dblValue
	 */
	public static double setStringToDouble(String strValue) {
		double dblValue = 0;
		
		try {
			dblValue = Double.parseDouble(strValue);
		} catch (Exception e) {
			dblValue = 0;
		}
		
		return dblValue;
	}
}