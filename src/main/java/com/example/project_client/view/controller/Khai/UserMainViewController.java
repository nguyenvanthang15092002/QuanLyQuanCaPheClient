package com.example.project_client.view.controller.Khai;

import atlantafx.base.theme.Styles;
import atlantafx.base.theme.Tweaks;
import com.example.project_client.model.User;
import com.example.project_client.router.Pages;
import com.example.project_client.router.Router;
import com.example.project_client.viewModel.Khai.UserMainViewModel;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.material2.Material2AL;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserMainViewController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);
        Button returnBtn = new Button(null, new FontIcon(Material2AL.CLOSE));
        returnBtn.getStyleClass().addAll(Styles.BUTTON_ICON,Styles.FLAT, Styles.DANGER);
        returnHbox.getChildren().addAll(region, returnBtn);
        returnBtn.setOnAction(e -> {
            try {
                Router.switchTo(Pages.ADMIN_VIEW);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        userMainViewModel.initData();
        idUserColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        usernameUserColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        staffIdUserColumn.setCellValueFactory(new PropertyValueFactory<>("staffId"));
        initStyles();
        userTable.setItems(FXCollections.observableList(userMainViewModel.getUsers()));
        Router.setData(Pages.USER_MAIN_VIEW, userMainViewModel);
        parentRoot.setOnMouseClicked(mouseEvent -> {
            userTable.getSelectionModel().clearSelection();
            userMainViewModel.setSelectedUser(null);
        });
        userTable.getSelectionModel().selectedItemProperty().addListener((observableValue, user, t1) -> {
            if(t1 != null){
                userMainViewModel.setSelectedUser(t1);
            }
        });
    }
    @FXML
    private BorderPane parentRoot;
    @FXML
    private HBox returnHbox;
    @FXML
    private TableView<User> userTable;
    @FXML
    private TableColumn<User, Integer> idUserColumn;
    @FXML
    private TableColumn<User, String> usernameUserColumn;
    @FXML
    private TableColumn<User, String> staffIdUserColumn;
    @FXML
    private Button addUserBtn;
    @FXML
    private Button setUserBtn;
    @FXML
    private Button deleteUserBtn;
    @FXML
    private TextField searchTf;
    @FXML
    private Button searchBtn;
    private final UserMainViewModel userMainViewModel = new UserMainViewModel();
    private void initStyles(){
        searchTf.getStyleClass().add(Styles.TITLE_4);
        addUserBtn.getStyleClass().addAll(Styles.BUTTON_OUTLINED, Styles.ACCENT, Styles.LARGE);
        setUserBtn.getStyleClass().addAll(Styles.BUTTON_OUTLINED, Styles.ACCENT, Styles.LARGE);
        deleteUserBtn.getStyleClass().addAll(Styles.BUTTON_OUTLINED, Styles.ACCENT, Styles.LARGE);
        searchBtn.getStyleClass().addAll(Styles.BUTTON_OUTLINED, Styles.ACCENT, Styles.LARGE);
        userTable.getStyleClass().add(Tweaks.EDGE_TO_EDGE);
    }
    @FXML
    private void searchUser() throws IOException {
        userMainViewModel.searchUser(searchTf.getText());
        userTable.setItems(FXCollections.observableList(userMainViewModel.getUsers()));
    }
    @FXML
    private  void openWdCreateUser() throws IOException {
        Router.switchTo(Pages.CREATE_USER_VIEW);
    }
    @FXML
    private  void openWdUpdateUser() throws IOException {
        if(userMainViewModel.getSelectedUser() != null){
            Router.switchTo(Pages.UPDATE_USER_VIEW);
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Chưa chọn tài khoản để cập nhật!");
            alert.showAndWait();
        }

    }
    @FXML
    private void deleteUser(){
        if(userMainViewModel.getSelectedUser() != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Bạn có chắc chắn muốn xóa tài khoản này không?");
            alert.showAndWait().ifPresent(respon -> {
                if(respon == ButtonType.CANCEL){
                    System.out.println("Cancel");
                }else{
                    try {
                        userTable.getItems().remove(userMainViewModel.getSelectedUser());
                        userMainViewModel.deleteUser();
                    } catch (IOException e) {
                        Alert alert1 = new Alert(Alert.AlertType.ERROR);
                        alert1.setContentText("Đã có lỗi xảy ra với máy chủ, vui lòng thử lại sau!");
                        alert1.showAndWait();
                        throw new RuntimeException(e);
                    }
                }
            });
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Chưa chọn tài khoản để cập nhật!");
            alert.showAndWait();
        }
    }
}
