package com.leandrotacioli.libs.swing.table;

public interface TableListener {

	/**
	 * 
	 * @param objValue
	 * @param intRowIndex
	 * @param intColumnIndex
	 */
	public void cellValueUpdated(Object objValue, int intRowIndex, int intColumnIndex);
}
