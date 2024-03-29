package com.leandrotacioli.libs.swing.table.editors;

import java.awt.Component;
import java.math.BigDecimal;

import javax.swing.DefaultCellEditor;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import com.leandrotacioli.libs.LTParameters;
import com.leandrotacioli.libs.swing.LTSwing;
import com.leandrotacioli.libs.swing.textfield.ltdouble.TextFieldDouble;
import com.leandrotacioli.libs.transformation.DoubleTransformation;

/**
 * 
 * @author Leandro Tacioli
 */
public class TableEditorDouble extends DefaultCellEditor {

	private int intHorizontalAlignment;
	private int intColumnDoubleFractionDigits;
	private boolean blnColumnDoubleShowAsPercentage;
	
	public TableEditorDouble() {
		this(SwingConstants.LEFT, 2, false);
	}
	
	public TableEditorDouble(int intHorizontalAlignment, int intColumnDoubleFractionDigits, boolean blnColumnDoubleShowAsPercentage) {
		super(new TextFieldDouble(intColumnDoubleFractionDigits, blnColumnDoubleShowAsPercentage));
		
		this.intHorizontalAlignment = intHorizontalAlignment;
		this.intColumnDoubleFractionDigits = intColumnDoubleFractionDigits;
		this.blnColumnDoubleShowAsPercentage = blnColumnDoubleShowAsPercentage;
	}
	
	//*************************************************************************
	@Override
	public Component getTableCellEditorComponent(JTable table, Object aValue, boolean isSelected, int rowIndex, int columnIndex) {
		TextFieldDouble txtFieldDouble = (TextFieldDouble) super.getTableCellEditorComponent(table, aValue, isSelected, rowIndex, columnIndex);
		txtFieldDouble.setHorizontalAlignment(intHorizontalAlignment);
		txtFieldDouble.setFractionDigits(intColumnDoubleFractionDigits);
		txtFieldDouble.setShowAsPercentage(blnColumnDoubleShowAsPercentage);
		txtFieldDouble.setFont(LTSwing.getInstance().getFontComponentTextField());
		txtFieldDouble.setBorder(LTSwing.getInstance().getBorderTableTextFieldEditing());

		String strValue = "";
		
		if (aValue != null && !aValue.equals("")) {
			strValue = DoubleTransformation.doubleToString(new BigDecimal(aValue.toString()), intColumnDoubleFractionDigits);
			strValue = DoubleTransformation.replaceDecimalSeparator(strValue);
		}
		
		txtFieldDouble.setText(strValue);
		
		return txtFieldDouble;
	}
}