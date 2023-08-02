package com.leandrotacioli;

import com.leandrotacioli.libs.javafx.layout.GridColumn;
import com.leandrotacioli.libs.javafx.layout.GridRow;
import com.leandrotacioli.libs.javafx.layout.ResponsiveLayout;
import com.leandrotacioli.libs.javafx.layout.WindowSize;
import javafx.application.Application;
import javafx.geometry.Insets;
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
