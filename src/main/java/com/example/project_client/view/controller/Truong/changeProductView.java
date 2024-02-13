package com.example.project_client.view.controller.Truong;

import com.example.project_client.model.Product;
import com.example.project_client.repository.ProductRepository;
import com.example.project_client.router.Pages;
import com.example.project_client.router.Router;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class changeProductView {
    @FXML
    private ChoiceBox<Boolean> choiceBox;
    private final Boolean[] available = {Boolean.FALSE, Boolean.TRUE};
    private Boolean[] check = {true, true, true, true, true, true};
    @FXML
    private Button image;
    @FXML
    private TextField name, price;
    @FXML
    private Label id, nameAlert, priceAlert, imageAlert;
    private Product product;
    @FXML
    private void initialize() throws Exception {
        choiceBox.getItems().addAll(available);
        product = productView.getProduct();
        System.out.println("Change product: "+product.getName());
        setField();
    }
    @FXML
    private void cancel() throws IOException {
        raiseAlert("Cancel change product");
        Router.switchTo(Pages.PRODUCT_VIEW);
    }
    @FXML
    private void confirm() {
        try {
            for(int i = 0; i < 6; ++i){
                if(!check[i]){
                    throw new Exception("Cannot change product");
                }
            }
            product.setAvailable(choiceBox.getSelectionModel().getSelectedItem());
            ProductRepository.updateProduct(product);
            raiseAlert("Changed Product");
            Router.switchTo(Pages.PRODUCT_VIEW);
        }
        catch (Exception e){
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
    private void setField() throws Exception {
        id.setText(product.getId().toString());
        setName();
        setPrice();
        image.setText(product.getImage().replace("/com/example/project_client/images/", "").replace(".png", "").replace(".jpg", ""));
        choiceBox.setValue(Boolean.TRUE);
    }
    private void setName(){
        name.setPromptText(product.getName());
        name.setStyle("-fx-text-fill: white;" + "-fx-background-color: black;");
        name.textProperty().addListener((observableValue, oldValue, newValue) -> {
            try {
                if(!product.setName(newValue)){
                    throw new Exception();
                }
                check[1] = true;
                nameAlert.setText("");
            }
            catch (Exception e) {
                check[1] = false;
                product.setName(name.getPromptText());
                nameAlert.setText("Invalid name, name must be a String");
            }
        });
    }

    private void setPrice() {
        price.setPromptText(product.getPrice().toString());
        price.setStyle("-fx-text-fill: white;" + "-fx-background-color: black;");
        price.textProperty().addListener((observableValue, oldValue, newValue) -> {
            try {
                try {
                    if (!product.setPrice(Integer.parseInt(newValue))) {
                        throw new Exception();
                    }
                }
                catch (Exception e) {
                    throw new Exception("Invalid price, price must be a number from 0 to 1000000");
                }
                check[2] = true;
                priceAlert.setText("");
            }
            catch (Exception e){
                check[2] = false;
                product.setPrice(Integer.parseInt(price.getPromptText()));
                priceAlert.setText(e.getMessage());
            }
        });
    }
    @FXML
    private void chooseImage(){
        try {
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Image files", "png", "jpg");
            fileChooser.setFileFilter(filter);
            String directory = System.getProperty("user.dir") + "\\src\\main\\resources\\com\\example\\project_client\\images";
            System.out.println("direct: " + directory);
            fileChooser.setCurrentDirectory(new File(directory));
            int result = fileChooser.showOpenDialog(null);
            System.out.println("Result " + result);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = new File(fileChooser.getSelectedFile().getAbsolutePath());
                System.out.println("FilePath " + selectedFile);
                if (!Desktop.isDesktopSupported()) {
                    System.out.println("Not supported");
                }
                else {
                    Desktop desktop = Desktop.getDesktop();
                    product.setImage(selectedFile.toString().replace(System.getProperty("user.dir") + "\\src\\main\\resources", "").replace("\\", "/"));
                    image.setText(selectedFile.toString().replace(directory+"\\", ""));
                    imageAlert.setText("");
                    check[4] = true;
                }
            } else if (result == JFileChooser.CANCEL_OPTION) {
                System.out.println("Cancelled");
            }
        }
        catch (Exception e) {
            System.out.println("ERROR");
        }
    }
}
