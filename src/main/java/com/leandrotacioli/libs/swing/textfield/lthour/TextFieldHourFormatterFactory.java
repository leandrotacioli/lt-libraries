package com.leandrotacioli.libs.swing.textfield.lthour;

import java.text.ParseException;

import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;

/**
 * 
 * @author Leandro Tacioli
 * @version 1.0 - 09/Jun/2020
 */
public class TextFieldHourFormatterFactory {
	
	/**
	 * Estabelece o formato padrão de hora. (HH:mm)
	 */
	public static DefaultFormatterFactory hourFormatterFactory() {
		DefaultFormatterFactory hourFormatterFactory = null;
		
		try {
			MaskFormatter maskFormatter = new HourMaskFormatter("##:##");
			hourFormatterFactory = new DefaultFormatterFactory(maskFormatter);
			
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		
		return hourFormatterFactory;
	}
}

/**
 * Permite que <i>MaskFormatter</i> aceite valores em branco.
 * 
 * @author Leandro Tacioli
 * @version 1.0 - 09/Jun/2020
 */
class HourMaskFormatter extends MaskFormatter {
	private static final long serialVersionUID = -147170285796703763L;
	
	private String strBlankRepresentation;
	
	private boolean blnAllowBlankField;
	
    /**
     * Atualiza a permissão de valores em branco.
     * 
     * @param blnAllowBlankField
     */
    protected void setAllowBlankField(boolean blnAllowBlankField) {
        this.blnAllowBlankField = blnAllowBlankField;
    }
    
    /**
     * Retorna a permissão de valores em branco.
     * 
     * @return blnAllowBlankField
     */
    protected boolean getAllowBlankField() {
        return blnAllowBlankField;
    }
    
    /**
     * Permite que <i>MaskFormatter</i> aceite valores em branco.
     */
    protected HourMaskFormatter(String strMask) throws ParseException {
        super(strMask);
    }

    // *************************************************************************
    // Atualiza a representação em branco toda a vez que 'strMask' for atualizado
    @Override 
    public void setMask(String strMask) throws ParseException {
        super.setMask(strMask);
        
        updateBlankRepresentation();
    }

	// *************************************************************************
    // Atualiza a representação em branco toda a vez que 'strMask' for atualizado 
    @Override 
    public void setPlaceholderCharacter(char charPlaceholder) {
        super.setPlaceholderCharacter(charPlaceholder);
        
        updateBlankRepresentation();
    }

	// *************************************************************************
    // Checa a representação em branco
    @Override 
    public Object stringToValue(String strValue) throws ParseException {
    	Object objValue = strValue;
        
        if (getAllowBlankField() && strBlankRepresentation != null && strBlankRepresentation.equals(strValue)) {
        	objValue = null;
        } else {
        	objValue = super.stringToValue(strValue);
        }
        
        return objValue;
    }

	// *************************************************************************
    // Chamando 'valueToString' da classe pai com um atributo 'null' irá pegar a representação em branco.
    protected void updateBlankRepresentation() {
    	try {
        	strBlankRepresentation = valueToString(null);
        } catch (ParseException e) {
        	strBlankRepresentation = null;
        }
    }
}