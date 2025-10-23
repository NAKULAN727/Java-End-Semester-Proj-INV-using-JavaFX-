package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Database;
import model.Item;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    @FXML private TableView<Item> itemTable;
    @FXML private TableColumn<Item, Integer> idColumn;
    @FXML private TableColumn<Item, String> nameColumn;
    @FXML private TableColumn<Item, String> categoryColumn;
    @FXML private TableColumn<Item, Integer> quantityColumn;
    @FXML private TableColumn<Item, Double> priceColumn;
    
    @FXML private TextField nameField;
    @FXML private TextField categoryField;
    @FXML private TextField quantityField;
    @FXML private TextField priceField;
    @FXML private TextField searchField;

    private ObservableList<Item> itemList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Database.createTable();
        setupTableColumns();
        loadItems();
        setupSearchFilter();
    }

    private void setupTableColumns() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        itemTable.setItems(itemList);
    }

    private void setupSearchFilter() {
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.isEmpty()) {
                loadItems();
            } else {
                itemList.setAll(Database.searchItems(newValue));
            }
        });
    }

    @FXML
    private void handleAddItem() {
        if (validateInput()) {
            Item item = new Item(
                nameField.getText(),
                categoryField.getText(),
                Integer.parseInt(quantityField.getText()),
                Double.parseDouble(priceField.getText())
            );
            
            if (Database.addItem(item)) {
                showAlert("Success", "Item added successfully!", Alert.AlertType.INFORMATION);
                clearFields();
                loadItems();
            } else {
                showAlert("Error", "Failed to add item!", Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    private void handleUpdateItem() {
        Item selected = itemTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("No Selection", "Please select an item to update!", Alert.AlertType.WARNING);
            return;
        }

        if (validateInput()) {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Confirm Update");
            confirm.setHeaderText("Update Item");
            confirm.setContentText("Are you sure you want to update this item?");
            
            Optional<ButtonType> result = confirm.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                selected.setName(nameField.getText());
                selected.setCategory(categoryField.getText());
                selected.setQuantity(Integer.parseInt(quantityField.getText()));
                selected.setPrice(Double.parseDouble(priceField.getText()));
                
                if (Database.updateItem(selected)) {
                    showAlert("Success", "Item updated successfully!", Alert.AlertType.INFORMATION);
                    clearFields();
                    loadItems();
                } else {
                    showAlert("Error", "Failed to update item!", Alert.AlertType.ERROR);
                }
            }
        }
    }

    @FXML
    private void handleDeleteItem() {
        Item selected = itemTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("No Selection", "Please select an item to delete!", Alert.AlertType.WARNING);
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirm Delete");
        confirm.setHeaderText("Delete Item");
        confirm.setContentText("Are you sure you want to delete this item?");
        
        Optional<ButtonType> result = confirm.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (Database.deleteItem(selected.getId())) {
                showAlert("Success", "Item deleted successfully!", Alert.AlertType.INFORMATION);
                loadItems();
            } else {
                showAlert("Error", "Failed to delete item!", Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    private void handleSelectItem() {
        Item selected = itemTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            nameField.setText(selected.getName());
            categoryField.setText(selected.getCategory());
            quantityField.setText(String.valueOf(selected.getQuantity()));
            priceField.setText(String.valueOf(selected.getPrice()));
        }
    }

    @FXML
    private void handleClearFields() {
        clearFields();
    }

    private void loadItems() {
        itemList.setAll(Database.getAllItems());
    }

    private void clearFields() {
        nameField.clear();
        categoryField.clear();
        quantityField.clear();
        priceField.clear();
        itemTable.getSelectionModel().clearSelection();
    }

    private boolean validateInput() {
        if (nameField.getText().trim().isEmpty()) {
            showAlert("Validation Error", "Name cannot be empty!", Alert.AlertType.ERROR);
            return false;
        }
        if (categoryField.getText().trim().isEmpty()) {
            showAlert("Validation Error", "Category cannot be empty!", Alert.AlertType.ERROR);
            return false;
        }
        try {
            int quantity = Integer.parseInt(quantityField.getText());
            if (quantity < 0) {
                showAlert("Validation Error", "Quantity cannot be negative!", Alert.AlertType.ERROR);
                return false;
            }
        } catch (NumberFormatException e) {
            showAlert("Validation Error", "Invalid quantity format!", Alert.AlertType.ERROR);
            return false;
        }
        try {
            double price = Double.parseDouble(priceField.getText());
            if (price < 0) {
                showAlert("Validation Error", "Price cannot be negative!", Alert.AlertType.ERROR);
                return false;
            }
        } catch (NumberFormatException e) {
            showAlert("Validation Error", "Invalid price format!", Alert.AlertType.ERROR);
            return false;
        }
        return true;
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}