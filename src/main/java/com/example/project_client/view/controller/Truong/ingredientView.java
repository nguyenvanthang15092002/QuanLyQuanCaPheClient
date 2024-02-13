package com.example.project_client.view.controller.Truong;

import com.example.project_client.model.Ingredient;
import com.example.project_client.model.Product;
import com.example.project_client.repository.IngredientRepository;
import com.example.project_client.router.Pages;
import com.example.project_client.router.Router;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.Getter;

import java.io.IOException;
import java.text.Normalizer;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;


public final class ingredientView {
    private static Ingredient ingredient;
    @FXML
    TableColumn<Ingredient, Integer> id;
    @FXML
    TableColumn<Ingredient, String> name;
    @FXML
    TableColumn<Ingredient, String> unit;
    @FXML
    TableColumn<Ingredient, Integer> unitPrice;
    @FXML
    TextField searchTextField;
    @Getter
    private List<Ingredient> ingredients;
    @FXML
    ObservableList<Ingredient> tableList;
    @FXML
    TableView<Ingredient> tableView;

    public void initialize(){
        try {
            setTableView();
            setColumn();
            searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                if(newValue.toString().length() > 0){
                    searchTextField.setStyle("-fx-background-color:  white;");
                }
                else {
                    searchTextField.setStyle("-fx-background-color:  #f9d388;");
                }
                filterTable(convertToString(newValue));
            });
        }
        catch (Exception e) {
            System.out.println("ERROR");
        }
    }
    @FXML
    public void cancel() throws IOException {
        Router.switchTo(Pages.ADMIN_VIEW);
    }
    @FXML
    private void readIngredient() {
        try {
            ingredient = tableView.getSelectionModel().getSelectedItem();
            if(ingredient == null) {
                throw new Exception("Please choose Ingredient to view information");
            }
            Router.switchTo(Pages.READ_INGREDIENT);
        }
        catch (Exception e) {
            raiseAlert(e.getMessage());
        }
    }
    @FXML
    public void addIngredient() throws IOException {
        Router.switchTo(Pages.ADD_INGREDIENT);
    }
    @FXML
    public void changeIngredient() {
        try {
            ingredient = tableView.getSelectionModel().getSelectedItem();
            if(ingredient == null) {
                throw new Exception("Please choose Ingredient to change");
            }
            Router.switchTo(Pages.CHANGE_INGREDIENT);
        }
        catch (Exception e) {
            raiseAlert(e.getMessage());
        }
    }
    @FXML
    public void deleteIngredient() {
        try {
            ingredient = tableView.getSelectionModel().getSelectedItem();
            if(ingredient == null) {
                throw new Exception("Please choose Ingredient to delete");
            }
            askDelete();
        }
        catch (Exception e){
            raiseAlert(e.getMessage());
        }
    }
    private void askDelete() throws Exception {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Do you want to delete Ingredient");
        alert.setContentText("choose your option");

        ButtonType buttonTypeYes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType buttonTypeNo = new ButtonType("No", ButtonBar.ButtonData.NO);

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        Optional<ButtonType> result = alert.showAndWait();

        String message;
        if(result.get().getButtonData() == ButtonBar.ButtonData.YES){
            try {
                IngredientRepository.deleteIngredient(ingredient.getId().toString());
                tableList.remove(ingredient);
                message = "Deleted";
            }
            catch (Exception e) {
                message = "Cannot delete";
            }
        } else {
            message = "Cancel delete";
        }
    }
    private void filterTable(String keyword) {
        ObservableList<Ingredient> filteredList = tableList.filtered(staff ->
                        convertToString(staff.getName()).toLowerCase().contains(keyword.toLowerCase())
        );
        tableView.setItems(filteredList);
    }
    public static String convertToString(String value) {
        try {
            String temp = Normalizer.normalize(value, Normalizer.Form.NFD);
            Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
            return pattern.matcher(temp).replaceAll("");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    private void raiseAlert(String alertText){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText("Notification");
        alert.setContentText(alertText);
        alert.show();
    }
    private void setTableView() throws IOException {
        ingredients = IngredientRepository.getIngredientsApi();
        tableList = FXCollections.observableArrayList(ingredients);
        tableView.setItems(tableList);
        tableView.setRowFactory(tv -> {
            TableRow<Ingredient> row = new TableRow<>();
            row.selectedProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal) {
                    row.setStyle("-fx-background-color: #c0b9bf;");
                } else {
                    row.setStyle("");
                }
            });
            return row;
        });
    }
    private void setColumn() {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        unit.setCellValueFactory(new PropertyValueFactory<>("unit"));
        unitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
    }
    public static Ingredient getIngredient() {
        return ingredient;
    }

}
