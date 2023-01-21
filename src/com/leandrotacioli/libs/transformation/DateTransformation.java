package com.leandrotacioli.libs.transformation;

import com.leandrotacioli.libs.LTParameters;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Leandro Tacioli
 */
public class DateTransformation {

	/**
	 * Transformações de valores <i>Date</i>.
	 */
	private DateTransformation() {

	}

	/**
	 * Transforma uma <i>String</i> em um objeto <i>Date</i>.
	 *
	 * @param strDate - Data a ser transformada
	 *
	 * @return Date
	 */
	public static Date stringToDate(String strDate) {
		return stringToDate(strDate, LTParameters.getInstance().getDateFormat());
	}

	/**
	 * Transforma uma <i>String</i> em um objeto <i>Date</i>.
	 * 
	 * @param strDate - Data a ser transformada
	 * @param strDateFormat - Formato da data a ser transformada. Ex: dd/MM/yyyy
	 * 
	 * @return Date
	 */
	public static Date stringToDate(String strDate, String strDateFormat) {
		String strHour = null;
		
		if (strDate.length() > 10) {
			String[] arrayDate = strDate.split(" ");
			
			if (arrayDate.length > 1) {
				strDate = arrayDate[0];
				strHour = arrayDate[1].substring(0, 5);
			}
		}
		
		return stringToDate(strDate, strHour, strDateFormat);
	}
	
	/**
	 * Transforma uma <i>String</i> em um objeto <i>Date</i>.
	 * 
	 * @param strDate - Parte da Data a ser transformada
	 * @param strHour - Parte da Hora a ser transformada
	 * @param strDateFormat - Formato da data a ser transformada. Ex: dd/MM/yyyy
	 * 
	 * @return Date
	 */
	public static Date stringToDate(String strDate, String strHour, String strDateFormat) {
		Date dteFinal = null;
		
		try {
			if (strHour == null || strHour.equals("")) {
				strHour = "00:00";
			}
			
			dteFinal = new SimpleDateFormat(strDateFormat + " HH:mm").parse(strDate + " " + strHour);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return dteFinal;
	}
	
	/**
	 * Transforma a parte de data de um objeto <i>Date</i> em um objeto <i>String</i>.
	 * 
	 * @param dteDate - Data a ser transformada
	 * @param blnShowHour - Exibe a hora
	 * 
	 * @return strDate - Ex: <i>dd/MM/yyyy</i> ou <i>dd/MM/yyyy HH:mm</i>
	 */
	public static String dateToString(Date dteDate, boolean blnShowHour) {
		String strDate = "";
		
		if (dteDate != null) {
			try {
				DateFormat dateFormat = new SimpleDateFormat(LTParameters.getInstance().getDateFormat());
				
				// Exibe horário apenas se for diferente de 00:00
				if (blnShowHour) {
					String strHours = new SimpleDateFormat("HH").format(dteDate);
					String strMinutes = new SimpleDateFormat("mm").format(dteDate);
					
					if (!strHours.equals("00") || !strMinutes.equals("00")) {
						dateFormat = new SimpleDateFormat(LTParameters.getInstance().getDateFormat() + " HH:mm");
					}
				}
				
				strDate = dateFormat.format(dteDate);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return strDate;
	}
	
	/**
	 * Transforma a parte de hora de um objeto <i>Date</i> em um objeto <i>String</i>.
	 * 
	 * @param dteDate - Data a ser transformada
	 * 
	 * @return Date - HH:mm
	 */
	public static String hourToString(Date dteDate) {
		String strHour = "";
		
		if (dteDate != null) {
			try {
				DateFormat hourFormat = new SimpleDateFormat("HH:mm");
				strHour = hourFormat.format(dteDate);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return strHour;
	}
	
	/**
	 * Retorna o dia de uma data.
	 * 
	 * @param dteDate - Data
	 * 
	 * @return strDia (dd)
	 */
	public static String getDay(Date dteDate) {
		String strDia = "";
		
		if (dteDate != null) {
			try {
				DateFormat dateFormat = new SimpleDateFormat("dd");
				strDia = dateFormat.format(dteDate);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return strDia;
	}
	
	/**
	 * Retorna o mês de uma data.
	 * 
	 * @param dteDate - Data
	 * 
	 * @return strMes (MM)
	 */
	public static String getMonth(Date dteDate) {
		String strMes = "";
		
		if (dteDate != null) {
			try {
				DateFormat dateFormat = new SimpleDateFormat("MM");
				strMes = dateFormat.format(dteDate);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return strMes;
	}
	
	/**
	 * Retorna o nome de uma mês de uma data.
	 * 
	 * @param dteDate
	 * 
	 * @return strMonthName
	 */
	public static String getMonthName(Date dteDate) {
		return getMonthName(Integer.parseInt(getMonth(dteDate)));
	}
	
	/**
	 * Retorna o nome de uma mês.
	 * 
	 * @param intMonth
	 * 
	 * @return strMonthName
	 */
	public static String getMonthName(int intMonth) {
		String strMonthName = "";
		
        switch(intMonth) {
            case 1:
            	strMonthName = LTParameters.getInstance().getBundle().getString("month_01");
                break;
            case 2:
				strMonthName = LTParameters.getInstance().getBundle().getString("month_02");
                break;
            case 3:
				strMonthName = LTParameters.getInstance().getBundle().getString("month_03");
                break;
            case 4:
				strMonthName = LTParameters.getInstance().getBundle().getString("month_04");
                break;
            case 5:
				strMonthName = LTParameters.getInstance().getBundle().getString("month_05");
                break;
            case 6:
				strMonthName = LTParameters.getInstance().getBundle().getString("month_06");
                break;
            case 7:
				strMonthName = LTParameters.getInstance().getBundle().getString("month_07");
                break;
            case 8:
				strMonthName = LTParameters.getInstance().getBundle().getString("month_08");
                break;
            case 9:
				strMonthName = LTParameters.getInstance().getBundle().getString("month_09");
                break;
            case 10:
				strMonthName = LTParameters.getInstance().getBundle().getString("month_10");
                break;
            case 11:
				strMonthName = LTParameters.getInstance().getBundle().getString("month_11");
                break;
            case 12:
				strMonthName = LTParameters.getInstance().getBundle().getString("month_12");
                break;
            default:
            	strMonthName = "";
                break;
        } 
        
        return strMonthName;
    }
	
	/**
	 * Retorna o ano de uma data.
	 * 
	 * @param dteDate - Data
	 * 
	 * @return strAno (yyyy)
	 */
	public static String getYear(Date dteDate) {
		String strAno = "";
		
		if (dteDate != null) {
			try {
				DateFormat dateFormat = new SimpleDateFormat("yyyy");
				strAno = dateFormat.format(dteDate);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return strAno;
	}
	
	/**
	 * Calcula a quantidade de dias entre 2 datas.
	 * 
	 * @param dteDateInitial
	 * @param dteDateFinal
	 * 
	 * @return lgnDays
	 */
	public static long getDaysBetweenDates(Date dteDateInitial, Date dteDateFinal) {
		long lgnDays = 0;
		
		dteDateInitial = getDateWithoutTime(dteDateInitial);
		dteDateFinal = getDateWithoutTime(dteDateFinal);
		
		LocalDate dateInitial = Instant.ofEpochMilli(dteDateInitial.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate dateFinal = Instant.ofEpochMilli(dteDateFinal.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
		
		lgnDays = ChronoUnit.DAYS.between(dateInitial, dateFinal);
		
		return lgnDays;
	}
	
	/**
	 * Retorna uma data apenas com as informações de dia/mês/ano, desconsiderando os horários
	 * 
	 * @param dteDate
	 * 
	 * @return dteDateWithoutTime
	 */
	public static Date getDateWithoutTime(Date dteDate) {
		return setDateTime(dteDate, 0, 0, 0);
	}
	
	/**
	 * Atualiza uma data com horas/minutos/segundos passados como parâmetros.
	 * 
	 * @param dteDate
	 * 
	 * @param intHours
	 * @param intMinutes
	 * @param intSeconds
	 * 
	 * @return dteDateTime
	 */
	public static Date setDateTime(Date dteDate, int intHours, int intMinutes, int intSeconds) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dteDate);
		cal.set(Calendar.HOUR_OF_DAY, intHours);
		cal.set(Calendar.MINUTE, intMinutes);
		cal.set(Calendar.SECOND, intSeconds);
		cal.set(Calendar.MILLISECOND, 0);
		
		Date dteDateTime = cal.getTime();
		
		return dteDateTime;
	}
	
	/**
	 * Verifica se uma data é válida.
	 * 
	 * @param intDay
	 * @param intMonth
	 * @param intYear
	 * 
	 * @return blnValidDate
	 */
	public static boolean isValidDate(int intDay, int intMonth, int intYear) {
		boolean blnValidDate = false;
		
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			dateFormat.setLenient(false);
			dateFormat.parse(String.format("%04d", intYear) + "-" + String.format("%02d", intMonth) + "-" + String.format("%02d", intDay));
			
			blnValidDate = true;
			
		} catch (Exception e) {
			blnValidDate = false;
		}
		
		return blnValidDate;
	}
}