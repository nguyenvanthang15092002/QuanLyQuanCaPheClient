package com.example.project_client.view.controller.Truong;

import com.example.project_client.model.Ingredient;
import com.example.project_client.repository.IngredientRepository;
import com.example.project_client.router.Pages;
import com.example.project_client.router.Router;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class changeIngredientView {
    @FXML
    private TextField name, unitPrice, unit;
    @FXML
    private Label id, nameAlert, unitAlert, unitPriceAlert;
    private Boolean[] check = {true, true, true, true};
    private Ingredient ingredient;
    @FXML
    private void initialize() throws Exception {
        ingredient = ingredientView.getIngredient();
        System.out.println(ingredient.getName());
        setField();
    }
    @FXML
    private void cancel() throws IOException {
        raiseAlert("Cancel change ingredient");
        Router.switchTo(Pages.INGREDIENT_VIEW);
    }
    @FXML
    private void confirm() throws IOException {
        try {
            for (int i = 0; i < 4; ++i) {
                if (!check[i]) {
                    throw new Exception("Cannot change product");
                }
            }
            System.out.println(ingredient);
            IngredientRepository.updateIngredient(ingredient);
            raiseAlert("Changed Ingredient");
            Router.switchTo(Pages.INGREDIENT_VIEW);
        }
        catch (Exception e) {
            raiseAlert(e.getMessage());
        }
    }
    private void raiseAlert(String alertText){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText("Notification");
        alert.setContentText(alertText);
        alert.show();
    }
    private void setField() {
        id.setText(ingredient.getId().toString());
        setName();
        setUnitPrice();
        setUnit();
    }
    private void setName() {
        name.setPromptText(ingredient.getName());
        name.setStyle("-fx-text-fill: white;" + "-fx-background-color: black;");
        name.textProperty().addListener((observableValue, oldValue, newValue) -> {
            try{
                if(!ingredient.setName(newValue)) {
                    throw new Exception("Invalid name, name must be a String");
                }
                check[1] = true;
                nameAlert.setText("");
            }
            catch (Exception e) {
                check[1] = false;
                ingredient.setName(name.getPromptText());
                nameAlert.setText(e.getMessage());
            }
        });
    }
    private void setUnitPrice() {
        unitPrice.setPromptText(ingredient.getUnitPrice().toString());
        unitPrice.setStyle("-fx-text-fill: white;" + "-fx-background-color: black;");
        unitPrice.textProperty().addListener((observableValue, oldValue, newValue) -> {
            try{
                try {
                    if (!ingredient.setUnitPrice(Integer.parseInt(newValue))) {
                        throw new Exception();
                    }
                }
                catch (Exception e) {
                    throw new Exception("Invalid unit price, unit price must be a number from 0 to 1000000");
                }
                check[2] = true;
                unitPriceAlert.setText("");
            }
            catch (Exception e) {
                check[2] = false;
                ingredient.setUnitPrice(Integer.parseInt(unitPrice.getPromptText()));
                unitPriceAlert.setText(e.getMessage());
            }
        });
    }

    private void setUnit() {
        unit.setPromptText(ingredient.getUnit());
        unit.setStyle("-fx-text-fill: white;" + "-fx-background-color: black;");
        unit.textProperty().addListener((observableValue, oldValue, newValue) -> {
            try{
                if(!ingredient.setUnit(newValue)){
                    throw new Exception("Invalid unit, unit must be a String");
                }
                check[3] = true;
                unitAlert.setText("");
            }
            catch (Exception e) {
                check[3] = false;
                ingredient.setUnit(unit.getPromptText());
                unitAlert.setText(e.getMessage());
            }
        });
    }
}
