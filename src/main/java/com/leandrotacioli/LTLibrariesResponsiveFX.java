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
        primaryStage.setScene(new Scene(root, 1000, 600));
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
        fieldString1.addFocusListener((obs, oldVal, newVal) -> {
            if (newVal) System.out.println("String Focus: " + fieldString1.getValue());
            if (oldVal) System.out.println("String Lost Focus: " + fieldString1.getValue());
        });

        LTField fieldString2 = new LTField("String 2 - Disabled:", LTDataTypes.STRING, false, false);
        fieldString2.setMaximumLength(50);
        fieldString2.setHorizontalAlignment(Pos.CENTER);
        fieldString2.setValue("This String field is Disabled");

        LTField fieldString3 = new LTField("String 3 - Max Length 20 - Right Aligned:", LTDataTypes.STRING, true, false);
        fieldString3.setMaximumLength(20);
        fieldString3.setHorizontalAlignment(Pos.CENTER_RIGHT);

        LTField fieldInteger = new LTField("Integer:", LTDataTypes.INTEGER, true, false);
        fieldInteger.setValue(10);
        fieldInteger.setMinHeight(10);
        fieldInteger.setMaximumLength(50);

        LTField fieldLong = new LTField("Long:", LTDataTypes.LONG, true, false);
        fieldLong.setMinHeight(25);
        fieldLong.setFractionDigits(6);

        LTField fieldDouble = new LTField("Double:", LTDataTypes.DOUBLE, true, false);
        fieldDouble.setFractionDigits(4);
        fieldDouble.setValue(1234.25);
        fieldDouble.addFocusListener((obs, oldVal, newVal) -> {
            if (newVal) System.out.println("Double Focus: " + fieldDouble.getValue());
            if (oldVal) System.out.println("Double Lost Focus: " + fieldDouble.getValue());
        });

        LTField fieldText = new LTField("Text:", LTDataTypes.TEXT, true, false);
        fieldText.setMaximumLength(200);
        fieldText.setMinHeight(150);

        row.addColumn(new GridColumn(fieldString1, 4));
        row.addColumn(new GridColumn(fieldString2, 4));
        row.addColumn(new GridColumn(fieldString3, 4));
        row.addColumn(new GridColumn(fieldInteger, 4));
        row.addColumn(new GridColumn(fieldLong, 4));
        row.addColumn(new GridColumn(fieldDouble, 4));
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
