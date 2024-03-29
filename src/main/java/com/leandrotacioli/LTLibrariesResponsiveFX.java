package com.leandrotacioli;

import com.leandrotacioli.libs.LTDataTypes;
import com.leandrotacioli.libs.javafx.field.LTField;
import com.leandrotacioli.libs.javafx.layout.GridColumn;
import com.leandrotacioli.libs.javafx.layout.GridRow;
import com.leandrotacioli.libs.javafx.layout.ResponsiveLayout;
import com.leandrotacioli.libs.javafx.layout.WindowSize;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class LTLibrariesResponsiveFX extends Application {

    @Override
    public void start(Stage primaryStage) {
        ResponsiveLayout root = new ResponsiveLayout();
        root.setGridLinesVisible(true);
        root.addRow(createGridRow1());
        root.addRow(createGridRow2());
        root.addRow(createGridRow3());
        root.setPadding(new Insets(25));
        root.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));

        primaryStage.setTitle("LT Libraries - JavaFX Responsive Layout");
        primaryStage.setScene(new Scene(root, 1200, 600));
        primaryStage.show();
    }

    private GridRow createGridRow1() {
        GridRow row = new GridRow();

        GridColumn columnRed = new GridColumn(createAnchorPane("Row 1 | Column 1 - Red", Color.RED));
        columnRed.setColumnWidth(12);

        row.addColumn(columnRed);

        return row;
    }

    private GridRow createGridRow2() {
        GridRow row = new GridRow();

        GridColumn columnBlue = new GridColumn(createAnchorPane("Row 2 | Column 1 - Blue", Color.BLUE));
        columnBlue.setColumnWidth(WindowSize.XSMALL, 12);
        columnBlue.setColumnWidth(WindowSize.LARGE, 8);
        columnBlue.setColumnWidth(WindowSize.XLARGE, 2);

        GridColumn columnYellow = new GridColumn(createAnchorPane("Row 2 | Column 2 - Yellow", Color.YELLOW));
        columnYellow.setColumnWidth(8, 6, 6, 4, 2);

        GridColumn columnGreen = new GridColumn(createAnchorPane("Row 2 | Column 3 - Green", Color.GREEN));
        columnGreen.setColumnWidth(WindowSize.XSMALL, 12);
        columnGreen.setColumnWidth(WindowSize.SMALL, 11);
        columnGreen.setColumnWidth(WindowSize.MEDIUM, 10);
        columnGreen.setColumnWidth(WindowSize.LARGE, 9);
        columnGreen.setColumnWidth(WindowSize.XLARGE, 2);

        GridColumn columnMagenta = new GridColumn(createAnchorPane("Row 2 | Column 4 - Magenta", Color.MAGENTA));
        columnMagenta.setColumnWidth(4, 4, 6, 6, 2);

        GridColumn columnOrange = new GridColumn(createAnchorPane("Row 2 | Column 5 - Orange", Color.ORANGE));
        columnOrange.setColumnWidth(4, 4, 6, 6, 2);

        GridColumn columnCyan = new GridColumn(createAnchorPane("Row 2 | Column 6 - Cyan", Color.CYAN));
        columnCyan.setColumnWidth(4, 4, 6, 6, 2);

        row.addColumn(columnBlue);
        row.addColumn(columnYellow);
        row.addColumn(columnGreen);
        row.addColumn(columnMagenta);
        row.addColumn(columnOrange);
        row.addColumn(columnCyan);

        return row;
    }

    private GridRow createGridRow3() {
        GridRow row = new GridRow();

        LTField fieldString1 = new LTField("String:", LTDataTypes.STRING, true, false);
        fieldString1.setMaximumLength(15);

        LTField fieldString2 = new LTField("String 2 - Disabled:", LTDataTypes.STRING, false, false);
        fieldString2.setMaximumLength(50);
        fieldString2.setHorizontalAlignment(Pos.CENTER);
        fieldString2.setValue("This String field is Disabled");

        LTField fieldString3 = new LTField("String 3 - Max Length 20 - Right Aligned:", LTDataTypes.STRING, true, false);
        fieldString3.setMaximumLength(20);
        fieldString3.setHorizontalAlignment(Pos.CENTER_RIGHT);
        fieldString3.setValue(null);

        LTField fieldBoolean = new LTField("Boolean", LTDataTypes.BOOLEAN, true, false);
        fieldBoolean.setValue("ABC");  // Error
        fieldBoolean.setValue(true);
        fieldBoolean.setHorizontalAlignment(Pos.CENTER_RIGHT);
        fieldBoolean.addFocusListener((obs, oldVal, newVal) -> {
            if (newVal) System.out.println("Boolean Focus: " + fieldBoolean.getValue());
            if (oldVal) System.out.println("Boolean Lost Focus: " + fieldBoolean.getValue());
        });

        LTField fieldBooleanDisabled = new LTField("Boolean Disabled", LTDataTypes.BOOLEAN, false, false);
        fieldBooleanDisabled.setValue(true);
        fieldBooleanDisabled.setHorizontalAlignment(Pos.CENTER_RIGHT);

        LTField fieldInteger = new LTField("Integer:", LTDataTypes.INTEGER, true, false);
        fieldInteger.setValue(10);
        fieldInteger.setMinHeight(10);
        fieldInteger.setMaximumLength(50);

        LTField fieldLong = new LTField("Long:", LTDataTypes.LONG, true, false);
        fieldLong.setMinHeight(25);
        fieldLong.setFractionDigits(6);
        fieldLong.setEnabled(false);

        LTField fieldDouble = new LTField("Double:", LTDataTypes.DOUBLE, true, false);
        fieldDouble.setFractionDigits(4);
        fieldDouble.setValue(1234.25);
        fieldDouble.addFocusListener((obs, oldVal, newVal) -> {
            if (newVal) System.out.println("Double Focus: " + fieldDouble.getValue());
            if (oldVal) System.out.println("Double Lost Focus: " + fieldDouble.getValue());
        });

        LTField fieldDate1 = new LTField("Date (dd/MM/yyyy):", LTDataTypes.DATE, true, false);
        fieldDate1.setDateFormat("dd/MM/yyyy");
        fieldDate1.addFocusListener((obs, oldVal, newVal) -> {
            if (newVal) System.out.println("Date Focus: " + fieldDate1.getValue());
            if (oldVal) System.out.println("Date Lost Focus: " + fieldDate1.getValue());
        });
        fieldDate1.setValue("15/01/2023");

        LTField fieldDate2 = new LTField("Date (MM/dd/yyyy):", LTDataTypes.DATE, false, false);
        fieldDate2.setDateFormat("MM/dd/yyyy");
        fieldDate2.addFocusListener((obs, oldVal, newVal) -> {
            if (newVal) System.out.println("Date Focus: " + fieldDate2.getValue());
            if (oldVal) System.out.println("Date Lost Focus: " + fieldDate2.getValue());
        });
        fieldDate2.setValue("02/28/2023");

        LTField fieldDate3 = new LTField("Date (yyyy-MM-dd):", LTDataTypes.DATE, true, false);
        fieldDate3.setDateFormat("yyyy-MM-dd");
        fieldDate3.addFocusListener((obs, oldVal, newVal) -> {
            if (newVal) System.out.println("Date Focus: " + fieldDate3.getValue());
            if (oldVal) System.out.println("Date Lost Focus: " + fieldDate3.getValue());
        });
        fieldDate3.setValue("2023-20-20");  // Error
        fieldDate3.setValue("2023-06-20");

        LTField fieldTime = new LTField("Time (HH:mm):", LTDataTypes.TIME, true, false);
        fieldTime.addFocusListener((obs, oldVal, newVal) -> {
            if (newVal) System.out.println("Time Focus: " + fieldTime.getValue());
            if (oldVal) System.out.println("Time Lost Focus: " + fieldTime.getValue());
        });
        fieldTime.setValue("25:15");  // Error
        fieldTime.setValue("12:15");

        LTField fieldComboBox = new LTField("Combo:", LTDataTypes.COMBOBOX, true, false);
        fieldComboBox.addValues("1", "Abacate");
        fieldComboBox.addValues("2", "Ameixa");
        fieldComboBox.addValues("2", "Amora");    // Error - Duplicated Key
        fieldComboBox.addValues("3", "Banana prata");
        fieldComboBox.addValues("4", "Banana maçã");
        fieldComboBox.addValues("5", "Caju");
        fieldComboBox.addValues("6", "Goiaba");
        fieldComboBox.addValues("7", "Laranja");
        fieldComboBox.addValues("8", "Laranja");  // Error - Duplicated description
        fieldComboBox.addValues("9", "Manga");
        fieldComboBox.addValues("10", "Maracujá");
        fieldComboBox.addValues("11", "Melão");
        fieldComboBox.addValues("12", "Uva");
        fieldComboBox.setValue(5);   // Caju
        fieldComboBox.setEnabled(true);

        fieldComboBox.addFocusListener((obs, oldVal, newVal) -> {
            if (newVal) System.out.println("Combo Focus: " + fieldComboBox.getValue());
            if (oldVal) System.out.println("Combo Lost Focus: " + fieldComboBox.getValue());
        });

        fieldString1.addFocusListener((obs, oldVal, newVal) -> {
            if (newVal) System.out.println("String Focus: " + fieldString1.getValue());
            if (oldVal) System.out.println("String Lost Focus: " + fieldString1.getValue());

            if (fieldString1.getValue().equals("ABC")) {
                fieldComboBox.setEnabled(false);
            } else {
                fieldComboBox.setEnabled(true);
            }
        });



        LTField fieldText = new LTField("Text:", LTDataTypes.TEXT, true, false);
        fieldText.setMaximumLength(200);
        fieldText.setMinHeight(150);
        fieldText.setValue("Lorem ipsum");

        row.addColumn(new GridColumn(fieldString1, 3));
        row.addColumn(new GridColumn(fieldString2, 3));
        row.addColumn(new GridColumn(fieldString3, 3));
        row.addColumn(new GridColumn(fieldBoolean, 1));
        row.addColumn(new GridColumn(fieldBooleanDisabled, 2));

        row.addColumn(new GridColumn(fieldInteger, 4));
        row.addColumn(new GridColumn(fieldLong, 4));
        row.addColumn(new GridColumn(fieldDouble, 4));

        row.addColumn(new GridColumn(fieldDate1, 3));
        row.addColumn(new GridColumn(fieldDate2, 3));
        row.addColumn(new GridColumn(fieldDate3, 3));
        row.addColumn(new GridColumn(fieldTime, 3));

        row.addColumn(new GridColumn(fieldComboBox, 6));

        row.addColumn(new GridColumn(fieldText, 12));

        return row;
    }

    private AnchorPane createAnchorPane(String paneLabel, Color color) {
        Label label = new Label(paneLabel);

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.minWidth(40);
        anchorPane.minHeight(40);
        anchorPane.setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));

        AnchorPane.setLeftAnchor(label, 5.0);
        AnchorPane.setRightAnchor(label, 5.0);
        AnchorPane.setTopAnchor(label, 5.0);

        anchorPane.getChildren().add(label);

        return anchorPane;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
