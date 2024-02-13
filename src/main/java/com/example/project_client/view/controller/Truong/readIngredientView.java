package com.example.project_client.view.controller.Truong;

import com.example.project_client.model.Ingredient;
import com.example.project_client.router.Pages;
import com.example.project_client.router.Router;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class readIngredientView {
    @FXML
    private Label id, name, unit, unitPrice;
    private Ingredient ingredient;
    @FXML
    private void initialize() throws Exception {
        ingredient = ingredientView.getIngredient();
        System.out.println("Read Ingredient: "+ingredient.getName());
        getInformation();
    }
    @FXML
    private void cancel() throws Exception {
        Router.switchTo(Pages.INGREDIENT_VIEW);
    }
    private void getInformation() {
        id.setText(ingredient.getId().toString());
        name.setText(ingredient.getName());
        unit.setText(ingredient.getUnit());
        unitPrice.setText(ingredient.getUnitPrice().toString());
    }
}
