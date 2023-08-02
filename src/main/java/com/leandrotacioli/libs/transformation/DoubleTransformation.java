package com.leandrotacioli.libs.transformation;

import com.leandrotacioli.libs.LTParameters;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 *
 * @author Leandro Tacioli
 */
public class DoubleTransformation {

	/**
	 * Transformações de valores <i>double</i>.
	 */
	private DoubleTransformation() {

	}

	/**
	 * Transforma uma <i>String</i> em um valor <i>double</i>.
	 *
	 * @param strValue - Valor
	 *
	 * @return dblValue
	 */
	public static double stringToDouble(String strValue) {
		double dblValue = 0;

		try {
			dblValue = Double.parseDouble(strValue);
		} catch (Exception e) {
			dblValue = 0;
		}

		return dblValue;
	}

	/**
	 * Transforma um valor <i>double</i> em um objeto <i>String</i>.
	 * 
	 * @param dblValue - Valor a ser transformado
	 * @param intDecimalPlaces - Quantidade de casas decimais
	 * 
	 * @return strDouble - Ex: <i>120,63</i>
	 */
	public static String doubleToString(double dblValue, int intDecimalPlaces) {
		DecimalFormat decimalFormat = getDecimalFormat(intDecimalPlaces);
		
		return decimalFormat.format(dblValue);
	}
	
	/**
	 * Arredonda um valor <i>double</i> para a quantidade de casas decimais desejada.
	 *
	 * @param dblValue - Valor a ser transformado
	 * @param intDecimalPlaces - Quantidade de casas decimais
	 * 
	 * @return dblDouble
	 */
	public static double roundDouble(double dblValue, int intDecimalPlaces) {
		return roundDouble(dblValue, intDecimalPlaces, BigDecimal.ROUND_HALF_UP);
	}
	
	/**
	 * Arredonda um objeto <i>double</i> para a quantidade de casas decimais desejada.
	 *
	 * @param dblValue - Valor a ser transformado
	 * @param intDecimalPlaces - Quantidade de casas decimais
	 * @param intRoundingMode - Modo de Arredondamento
	 * 
	 * @return dblDouble
	 */
	public static double roundDouble(double dblValue, int intDecimalPlaces, int intRoundingMode) {
		return BigDecimal.valueOf(dblValue).setScale(intDecimalPlaces, intRoundingMode).doubleValue();
	}

	public static DecimalFormat getDecimalFormat(int intDecimalPlaces) {
		DecimalFormat decimalFormat = new DecimalFormat();
		decimalFormat.setMinimumFractionDigits(intDecimalPlaces);
		decimalFormat.setMaximumFractionDigits(intDecimalPlaces);
		decimalFormat.setDecimalFormatSymbols(LTParameters.getInstance().getDecimalFormatSymbols());

		return decimalFormat;
	}
}