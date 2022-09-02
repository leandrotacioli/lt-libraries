package com.leandrotacioli.libs.swing.table.editors;

import java.awt.Component;

import javax.swing.DefaultCellEditor;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import com.leandrotacioli.libs.LTParameters;
import com.leandrotacioli.libs.StringTransformations;
import com.leandrotacioli.libs.swing.textfield.ltdouble.TextFieldDouble;

/**
 * 
 * @author Leandro Tacioli
 */
public class TableEditorDouble extends DefaultCellEditor {
	private static final long serialVersionUID = 8304313692833448805L;
	
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
		txtFieldDouble.setFont(LTParameters.getInstance().getFontComponentTextField());
		txtFieldDouble.setBorder(LTParameters.getInstance().getBorderTableTextFieldEditing());
		
		String strValue = "";
		
		if (aValue != null && !aValue.equals("")) {
			strValue = StringTransformations.setDoubleToString((double) aValue, intColumnDoubleFractionDigits);
			
			// Retira os caracteres de separador de milhares e altera o separador para o padrão configurado nos parâmetros
			if (LTParameters.getInstance().getDecimalMark().equals("COMMA")) {
				strValue = strValue.replace(".", "");
				strValue.replace(".", ",");
			} else if (LTParameters.getInstance().getDecimalMark().equals("PERIOD")) {
				strValue = strValue.replace(",", "");
			}
		}
		
		txtFieldDouble.setText(strValue);
		
		return txtFieldDouble;
	}
}