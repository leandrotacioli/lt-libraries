package com.leandrotacioli.libs.javafx.field;

import java.util.*;
import java.util.stream.Collectors;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Window;

public class FieldComboBoxAutoComplete extends ComboBox {

    private Map<String, String> comboValues;
    private StringProperty filter = new SimpleStringProperty("");

    public FieldComboBoxAutoComplete() {
        comboValues = new LinkedHashMap<>();

        setTooltip(new Tooltip());
        getTooltip().textProperty().bind(filter);

        filter.addListener((observable, oldValue, newValue) -> handleFilterChanged(newValue));

        setOnKeyPressed(this::handleOnKeyPressed);
        setOnHidden(this::handleOnHiding);
    }

    protected void addValues(String key, String description) {
        for (Map.Entry<String, String> comboValue : comboValues.entrySet()) {
            if (comboValue.getKey().equals(key)) {
                throw new IllegalArgumentException("Unable to add new value to the ComboBox - Duplicated key (" + key + ").");
            }

            if (comboValue.getValue().equals(description)) {
                throw new IllegalArgumentException("Unable to add new value to the ComboBox - Duplicated description (" + description + ").");
            }
        }

        comboValues.put(key, description);

        getItems().setAll(comboValues.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList()));
    }

    protected String getSelectedValue() {
        if (this.getValue() != null) {
            Optional<String> selectedValue = comboValues.entrySet().stream().filter(entry -> entry.getValue().equals(this.getValue())).map(Map.Entry::getKey).findFirst();

            if (selectedValue.isPresent()) {
                return selectedValue.get();
            }
        }

        return null;
    }

    protected String getSelectedDescription() {
        if (this.getValue() != null) {
            Optional<String> selectedDescription = comboValues.entrySet().stream().filter(entry -> entry.getValue().equals(this.getValue())).map(Map.Entry::getValue).findFirst();

            if (selectedDescription.isPresent()) {
                return selectedDescription.get();
            }
        }

        return null;
    }

    protected void setSelectedValue(Object keyValue) {
        this.setValue(null);

        if (keyValue instanceof Integer || keyValue instanceof Long) {
            keyValue = keyValue.toString();
        }

        boolean valueAssigned = false;

        for (Map.Entry<String, String> comboValue : comboValues.entrySet()) {
            if (comboValue.getKey().equals(keyValue)) {
                this.setValue(comboValue.getValue());
                valueAssigned = true;
                break;
            }
        }

        if (!valueAssigned) {
            throw new IllegalArgumentException("Invalid value (" + keyValue + ").");
        }
    }

    private void handleOnKeyPressed(KeyEvent keyEvent) {
        KeyCode code = keyEvent.getCode();
        String filterValue = filter.get();

        if (code.isLetterKey()) {
            filterValue += keyEvent.getText();
        } else if (code == KeyCode.BACK_SPACE && filterValue.length() > 0) {
            filterValue = filterValue.substring(0, filterValue.length() - 1);
        } else if (code == KeyCode.ESCAPE) {
            filterValue = "";
        } else if (code == KeyCode.DOWN || code == KeyCode.UP) {
            show();
        }

        filter.setValue(filterValue);
    }

    private void handleOnHiding(Event e) {
        filter.setValue("");
        getTooltip().hide();
        restoreOriginalValues();
    }

    private void handleFilterChanged(String newValue) {
        if (newValue.length() > 0) {
            show();
            showTooltip();
            getItems().setAll(filterValues());
        } else {
            restoreOriginalValues();
            getTooltip().hide();
        }
    }

    private void showTooltip() {
        if (!getTooltip().isShowing()) {
            Window stage = getScene().getWindow();
            double posX = stage.getX() + localToScene(getBoundsInLocal()).getMinX() + 20;
            double posY = stage.getY() + localToScene(getBoundsInLocal()).getMinY() - 10;
            getTooltip().show(stage, posX, posY);
        }
    }

    private ObservableList filterValues() {
        List<String> filteredList = comboValues.entrySet().stream().filter(item -> item.getValue().toLowerCase().contains(filter.get().toLowerCase())).map(Map.Entry::getValue).collect(Collectors.toList());
        return FXCollections.observableArrayList(filteredList);
    }

    private void restoreOriginalValues() {
        String selectedItem = (String) getSelectionModel().getSelectedItem();
        getItems().setAll(comboValues.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList()));
        getSelectionModel().select(selectedItem);
    }

}