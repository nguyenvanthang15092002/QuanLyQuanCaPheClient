package com.example.project_client.view.controller.Truong;

import com.example.project_client.model.Ingredient;
import com.example.project_client.repository.IngredientRepository;
import com.example.project_client.router.Pages;
import com.example.project_client.router.Router;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lombok.Getter;

import java.io.IOException;

public class addIngredientView {
    @Getter
    private Ingredient ingredient = new Ingredient();
    @FXML
    private TextField name, unitPrice, unit;
    @FXML
    private Label nameAlert, unitPriceAlert, unitAlert;
    private Boolean[] check = {true, false, false, false};
    @FXML
    public void initialize() throws Exception {
        System.out.println("Add ingredient");
        setField();
    }
    @FXML
    public void cancel() throws IOException {
        raiseAlert("Cancel add ingredient");
        Router.switchTo(Pages.INGREDIENT_VIEW);
    }
    @FXML
    public void confirm() throws Exception {
        try {
            for(int i = 0; i < 4; ++i) {
                if (!check[i]) {
                    throw new Exception("Invalid Field");
                }
            }
            IngredientRepository.saveIngredient(ingredient);
            raiseAlert("Added Ingredient");
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
        setName();
        setUnitPrice();
        setUnit();
    }
    private void setName() {
        name.setPromptText("input name");
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
                nameAlert.setText(e.getMessage());
            }
        });
    }
    private void setUnitPrice() {
        unitPrice.setPromptText("input unit price");
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
                unitPriceAlert.setText(e.getMessage());
            }
        });
    }

    private void setUnit() {
        unit.setPromptText("input unit");
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
                unitAlert.setText(e.getMessage());
            }
        });
    }
}
