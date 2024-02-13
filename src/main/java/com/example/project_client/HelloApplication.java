package com.example.project_client;

import atlantafx.base.theme.*;
import com.example.project_client.router.Pages;

import com.example.project_client.router.Router;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;


public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Application.setUserAgentStylesheet(new PrimerLight().getUserAgentStylesheet());
        Router.bind(this, stage, "Coffee Management", 1366, 768);
        init();
        Router.goTo(Pages.LOGIN_VIEW    );
    }
    public void init(){
        //
        Router.setRouter(Pages.LOGIN_VIEW, "yin/login-view.fxml");
        Router.setRouter(Pages.ADMIN_VIEW, "yin/admin-view.fxml");
        Router.setRouter(Pages.CUSTOMER_VIEW, "yin/customer-view.fxml");
        Router.setRouter(Pages.ADD_CUSTOMER_VIEW, "yin/add-customer-view.fxml");
        Router.setRouter(Pages.UPDATE_CUSTOMER_VIEW, "yin/update-customer-view.fxml");
        Router.setRouter(Pages.READ_CUSTOMER_VIEW, "yin/read-customer-view.fxml");
        //
        Router.setRouter(Pages.MAIN_VIEW, "Quyen/main-view.fxml");
        Router.setRouter(Pages.CREATE_ORDER_VIEW, "Quyen/create-order-view.fxml");
        Router.setRouter(Pages.ORDER_BILL_VIEW, "Quyen/order-bill-view.fxml");
        Router.setRouter(Pages.PROMOTION_VIEW, "Quyen/promotion-view.fxml");
        Router.setRouter(Pages.CUSTOMER_INFORMATION_INPUT_VIEW, "Quyen/customer-information-input-view.fxml");
        //
        Router.setRouter(Pages.MAIN_VIEW_PROFIT, "Khai/main-view.fxml");
        Router.setRouter(Pages.SALARY_CAL_VIEW, "Khai/SalaryCalView.fxml");
        Router.setRouter(Pages.BILL_INGREDIENT_CAL_VIEW, "Khai/BillIngredientCalView.fxml");
        Router.setRouter(Pages.BILL_PRODUCT_CAL_VIEW, "Khai/BillProductCalView.fxml");
        Router.setRouter(Pages.PROFIT_CAL_VIEW, "Khai/ProfitCalView.fxml");
        Router.setRouter(Pages.USER_MAIN_VIEW, "Khai/User_Main_View.fxml");
        Router.setRouter(Pages.CREATE_USER_VIEW, "Khai/createUserView.fxml");
        Router.setRouter(Pages.UPDATE_USER_VIEW, "Khai/updateUserView.fxml");
        //
        Router.setRouter(Pages.INGREDIENT_VIEW, "Truong/ingredientScene.fxml");
        Router.setRouter(Pages.ADD_INGREDIENT, "Truong/addIngredient.fxml");
        Router.setRouter(Pages.CHANGE_INGREDIENT, "Truong/changeIngredient.fxml");
        Router.setRouter(Pages.PRODUCT_VIEW, "Truong/productScene1.fxml");
        Router.setRouter(Pages.ADD_PRODUCT, "Truong/addProduct1.fxml");
        Router.setRouter(Pages.CHANGE_PRODUCT, "Truong/changeProduct.fxml");
        Router.setRouter(Pages.READ_PRODUCT,"Truong/readProduct.fxml");
        Router.setRouter(Pages.READ_INGREDIENT,"Truong/readIngredient.fxml");
        //
        Router.setRouter(Pages.ADD_STAFF_VIEW, "Thang/addStaffScreen.fxml");
        Router.setRouter(Pages.STAFF_VIEW, "Thang/staffScreen.fxml");
        Router.setRouter(Pages.UPDATE_STAFF_VIEW, "Thang/updateStaffScreen.fxml");
        Router.setRouter(Pages.READ_STAFF_VIEW, "Thang/readStaff.fxml");

    }
    public static void main(String[] args) {
        launch();
    }
}