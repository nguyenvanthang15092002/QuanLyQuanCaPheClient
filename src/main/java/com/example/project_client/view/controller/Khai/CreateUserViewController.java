package com.example.project_client.view.controller.Khai;

import atlantafx.base.theme.Styles;
import com.example.project_client.model.User;
import com.example.project_client.router.Pages;
import com.example.project_client.router.Router;
import com.example.project_client.view.controller.Khai.Function.FunctionKhai;
import com.example.project_client.viewModel.Khai.CreateUserViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.material2.Material2AL;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CreateUserViewController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);
        Button returnBtn = new Button(null, new FontIcon(Material2AL.CLOSE));
        returnBtn.getStyleClass().addAll(Styles.BUTTON_ICON,Styles.FLAT, Styles.DANGER);
        returnHbox.getChildren().addAll(region, returnBtn);
        returnBtn.setOnAction(e -> {
            try {
                Router.switchTo(Pages.USER_MAIN_VIEW);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        initStyles();
    }
    @FXML
    private HBox returnHbox;
    @FXML
    private Label usernameLb;
    @FXML
    private Label passwordLb;
    @FXML
    private Label staffIdLb;
    @FXML
    private TextField usernameTf;
    @FXML
    private TextField staffIdTf;
    @FXML
    private TextField passwordTf;
    @FXML
    private Button createUserBtn;
    private final CreateUserViewModel createUserViewModel = new CreateUserViewModel();
    @FXML
    private void createUser() throws IOException {
        if(usernameTf.getText().isEmpty() || staffIdTf.getText().isEmpty() || passwordTf.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Các dữ liệu không được để trống!");
            alert.showAndWait();
        }else{
            if(!createUserViewModel.checkUsername(usernameTf.getText())){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Tên đăng nhập đã tồn tại!");
                alert.showAndWait();
            }else{
                try {
                    if(!createUserViewModel.checkStaffId(staffIdTf.getText())){
                        createUserViewModel.createUser(new User(null, usernameTf.getText(), passwordTf.getText(), staffIdTf.getText()));
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setContentText("Thêm mới thành công!");
                        Router.switchTo(Pages.USER_MAIN_VIEW);
                        alert.showAndWait();
                    }else{
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setContentText("Staff Id hiện không tồn tại, bạn vẫn muốn thêm mới chứ?");
                        alert.showAndWait().ifPresent(respon -> {
                            if(respon == ButtonType.CANCEL){
                                System.out.println("Cancel");
                            }else{
                                try {
                                    createUserViewModel.createUser(new User(null, usernameTf.getText(), passwordTf.getText(), staffIdTf.getText()));
                                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                                    alert1.setContentText("Thêm mới thành công!");
                                    Router.switchTo(Pages.USER_MAIN_VIEW);
                                    alert1.showAndWait();
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        });
                    }

                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Đã có lỗi xảy ra với máy chủ, vui lòng thử lại sau!");
                    alert.showAndWait();
                }
            }
        }

    }
    private void initStyles(){
        FunctionKhai.validLabel(usernameLb);
        FunctionKhai.validLabel(passwordLb);
        FunctionKhai.validLabel(staffIdLb);
        usernameTf.getStyleClass().add(Styles.TITLE_4);
        passwordTf.getStyleClass().add(Styles.TITLE_4);
        staffIdTf.getStyleClass().add(Styles.TITLE_4);
        createUserBtn.getStyleClass().addAll(Styles.LARGE, Styles.ACCENT, Styles.BUTTON_OUTLINED);
    }
}
