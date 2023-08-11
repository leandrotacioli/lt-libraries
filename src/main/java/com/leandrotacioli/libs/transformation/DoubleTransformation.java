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
	 * Transforma um valor <i>BigDecimal</i> em um objeto <i>String</i>.
	 * 
	 * @param value - Valor a ser transformado
	 * @param fractionDigits - Quantidade de casas decimais
	 * 
	 * @return strDouble - Ex: <i>120,63</i>
	 */
	public static String doubleToString(BigDecimal value, int fractionDigits) {
		DecimalFormat decimalFormat = getDecimalFormat(fractionDigits);
		
		return decimalFormat.format(value);
	}
	
	/**
	 * Arredonda um valor <i>BigDecimal</i> para a quantidade de casas decimais desejada.
	 *
	 * @param value - Valor a ser transformado
	 * @param fractionDigits - Quantidade de casas decimais
	 * 
	 * @return dblDouble
	 */
	public static BigDecimal roundDouble(BigDecimal value, int fractionDigits) {
		return roundDouble(value, fractionDigits, BigDecimal.ROUND_HALF_UP);
	}
	
	/**
	 * Arredonda um objeto <i>BigDecimal</i> para a quantidade de casas decimais desejada.
	 *
	 * @param value - Valor a ser transformado
	 * @param fractionDigits - Quantidade de casas decimais
	 * @param roundingMode - Modo de Arredondamento
	 * 
	 * @return bigDecimal
	 */
	public static BigDecimal roundDouble(BigDecimal value, int fractionDigits, int roundingMode) {
		return value.setScale(fractionDigits, roundingMode);
	}

	/**
	 * Returns a DecimalFormat object set with the fraction digits and its symbols.
	 *
	 * @param fractionDigits
	 *
	 * @return decimalFormat
	 */
	public static DecimalFormat getDecimalFormat(int fractionDigits) {
		DecimalFormat decimalFormat = new DecimalFormat();
		decimalFormat.setMinimumFractionDigits(fractionDigits);
		decimalFormat.setMaximumFractionDigits(fractionDigits);
		decimalFormat.setDecimalFormatSymbols(LTParameters.getInstance().getDecimalFormatSymbols());

		return decimalFormat;
	}

	/**
	 * Removes the grouping separator of a DOUBLE string.
	 *
	 * <p>Example: 1.000,123 -> 1000,123<p/>
	 *
	 * @param value
	 *
	 * @return
	 */
	public static String removeGroupingSeparator(String value) {
		if (LTParameters.getInstance().getDecimalFormatSymbols().getDecimalSeparator() == ',') {
			value = value.replace(".", "");
		} else if (LTParameters.getInstance().getDecimalFormatSymbols().getDecimalSeparator() == '.') {
			value = value.replace(",", "");
		}

		return value;
	}

	/**
	 * Replace the decimal separator to a valid DOUBLE string.
	 *
	 * <p>Example 1: 5.020.100,123 -> 502000.123 | When Decimal Separator = ','<p/>
	 * <p>Example 2: 5,020,100.123 -> 502000.123 | When Decimal Separator = '.'<p/>
	 *
	 * @param value
	 *
	 * @return
	 */
	public static String replaceDecimalSeparator(String value) {
		if (LTParameters.getInstance().getDecimalFormatSymbols().getDecimalSeparator() == ',') {
			value = value.replace(".", "");
			value = value.replace(",", ".");
		} else if (LTParameters.getInstance().getDecimalFormatSymbols().getDecimalSeparator() == '.') {
			value = value.replace(",", "");
		}

		return value;
	}
}