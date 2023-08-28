package com.leandrotacioli.libs.swing;

import lombok.Getter;
import lombok.Setter;

import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

@Getter
public class LTSwing {

    private static LTSwing objLTSwing;

    /** Altura das linhas da LTTable. */
    private int tableRowHeight;

    /** Tamanho mínimo do componente. */
    private Dimension dimensionComponentMinimumSize;

    /** Tamanho máximo do componente. */
    private Dimension dimensionComponentMaximumSize;

    /** Fonte do texto da label do componente. */
    private Font fontComponentLabel;

    /** Fonte do texto do componente. */
    private Font fontComponentTextField;

    /** Fonte do cabeçalho da LTTable. */
    private Font fontTableHeader;

    /** Fonte do texto da LTTable. */
    private Font fontTableTextField;

    /** Cor de fundo do painel do componente. */
    @Setter
    private Color colorComponentPanelBackground;

    /** Cor da borda do componente. */
    private Color colorComponentBorder;

    /** Cor da fonte do componente. */
    private Color colorComponentForeground;

    /** Cor de fundo do componente. */
    private Color colorComponentBackground;

    /** Cor de fundo do componente quando focado. */
    private Color colorComponentBackgroundFocus;

    /** Cor de fundo do componente quando desabilitado. */
    private Color colorComponentBackgroundDisabled;

    /** Cor da LTTable. */
    private Color colorTable;

    /** Cor do grid da LTTable. */
    private Color colorTableGrid;

    /** Cor do grid da LTTable quando desabilitado. */
    private Color colorTableGridDisabled;

    /** Cor da linha do grid da LTTable quando selecionada. */
    private Color colorTableRowSelected;

    /** Cor da linha popup da LTComboBoxField quando selecionada. */
    private Color colorComboBoxRowSelected;

    /** Borda do componente. */
    private CompoundBorder borderComponent;

    /** Borda do componente quando houver erro. */
    private CompoundBorder borderComponentError;

    /** Borda do texto da tabela. */
    private CompoundBorder borderTableTextField;

    /** Borda do texto da tabela quando focado. */
    private CompoundBorder borderTableTextFieldFocus;

    /** Borda do texto da tabela quando está sendo editado. */
    private CompoundBorder borderTableTextFieldEditing;

    /** Borda do texto da tabela quando está com erro. */
    private CompoundBorder borderTableTextFieldError;

    /**
     * Cria uma nova instância para a classe ou retorna a instância previamente criada.
     *
     * @return objLTSwing
     */
    public static synchronized LTSwing getInstance() {
        if (objLTSwing == null) {
            objLTSwing = new LTSwing();
        }

        return objLTSwing;
    }

    /**
     * Cria os parâmetros necessários para a execução do </i>LT Libraries</i>.
     */
    private LTSwing() {
        loadComponentSettings();
    }

    /**
     * Carrega as propriedades/valores dos componentes.
     */
    private void loadComponentSettings() {
        tableRowHeight = 21;

        dimensionComponentMinimumSize = new Dimension(1, 21);
        dimensionComponentMaximumSize = new Dimension(10000, 21);

        fontComponentLabel = new Font("SansSerif", Font.BOLD, 12);
        fontComponentTextField = new Font("SansSerif", Font.PLAIN, 12);

        fontTableHeader = new Font("SansSerif", Font.ITALIC, 12);
        fontTableTextField = new Font("SansSerif", Font.PLAIN, 12);

        colorComponentPanelBackground = new Color(0, 0, 0);

        colorComponentBorder = new Color(180, 180, 180);
        colorComponentForeground = new Color(0, 0, 0);
        colorComponentBackground = new Color(255, 255, 254);
        colorComponentBackgroundFocus = new Color(255, 255, 190);
        colorComponentBackgroundDisabled = new Color(240, 240, 240);

        colorTable = new Color(255, 255, 255);
        colorTableGrid = new Color(200, 200, 200);
        colorTableGridDisabled = new Color(240, 240, 240);
        colorTableRowSelected = new Color(120, 200, 250);

        colorComboBoxRowSelected = new Color(120, 200, 250);

        borderComponent = new CompoundBorder(new LineBorder(colorComponentBorder), new EmptyBorder(1, 2, 1, 2));
        borderComponentError = new CompoundBorder(new LineBorder(new Color(255, 0, 0)), new EmptyBorder(1, 2, 1, 2));

        borderTableTextField = new CompoundBorder(new LineBorder(new Color(0, 0, 0, 0), 2), new EmptyBorder(1, 2, 1, 2));
        borderTableTextFieldFocus = new CompoundBorder(new LineBorder(new Color(0, 0, 0), 2), new EmptyBorder(1, 2, 1, 2));
        borderTableTextFieldEditing = new CompoundBorder(new LineBorder(new Color(0, 0, 0), 2), new EmptyBorder(2, 2, 2, 2));
        borderTableTextFieldError = new CompoundBorder(new LineBorder(new Color(255, 0, 0), 2), new EmptyBorder(2, 2, 2, 2));
    }

    /**
     * Altera a cor da borda do componente.
     *
     * @param colorComponentBorder - Color
     */
    public void setColorComponentBorder(Color colorComponentBorder) {
        this.colorComponentBorder = colorComponentBorder;
        this.borderComponent = new CompoundBorder(new LineBorder(colorComponentBorder), new EmptyBorder(1, 2, 1, 2));
    }
}