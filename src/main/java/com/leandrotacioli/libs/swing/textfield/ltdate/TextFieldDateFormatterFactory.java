package com.leandrotacioli.libs.swing.textfield.ltdate;

import java.text.ParseException;

import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;

/**
 * 
 * @author Leandro Tacioli
 */
public class TextFieldDateFormatterFactory {
	
	/**
	 * Estabelece o formato padrão de data. (##/##/####)
	 */
	public static DefaultFormatterFactory dateFormatterFactory() {
		DefaultFormatterFactory dateFormatterFactory = null;
		
		try {
			MaskFormatter maskFormatter = new DateMaskFormatter("##/##/####");
			dateFormatterFactory = new DefaultFormatterFactory(maskFormatter);
			
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		
		return dateFormatterFactory;
	}
}

/**
 * Permite que <i>MaskFormatter</i> aceite valores em branco.
 * 
 * @author Leandro Tacioli
 */
class DateMaskFormatter extends MaskFormatter {

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
    protected DateMaskFormatter(String strMask) throws ParseException {
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