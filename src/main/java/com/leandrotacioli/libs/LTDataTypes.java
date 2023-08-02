package com.leandrotacioli.libs;

/**
 * Define os tipos de campos que podem ser utilizadas na LT Libraries.
 * 
 * @author Leandro Tacioli
 * @version 4.0 - 09/Jun/2020
 */
public enum LTDataTypes {
	INTEGER,
	LONG,
	DOUBLE,
	STRING,
	TEXT,       // Textos sem limite de caracteres
	DATE,
	HOUR,       // HH:mm:ss
	BOOLEAN,
	BUTTON      // Botões serão utilizadas apenas na LTTable
}