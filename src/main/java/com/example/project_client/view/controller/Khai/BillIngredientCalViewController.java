package com.example.project_client.view.controller.Khai;

import atlantafx.base.theme.Styles;
import atlantafx.base.theme.Tweaks;
import com.example.project_client.model.BillIngredientCal;
import com.example.project_client.model.TimeRequest;
import com.example.project_client.view.controller.Khai.Function.FunctionKhai;
import com.example.project_client.viewModel.Khai.BillIngredientCalViewModel;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.net.URL;
import java.util.ResourceBundle;

public class BillIngredientCalViewController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nameIngeredientColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        countIngeredientColumn.setCellValueFactory(new PropertyValueFactory<>("count"));
        unitIngeredientColumn.setCellValueFactory(new PropertyValueFactory<>("unit"));
        totalIngeredientColumn.setCellValueFactory(new PropertyValueFactory<>("total"));
        FunctionKhai.validColumnMoney(totalIngeredientColumn);
        FunctionKhai.validDatePicker(datePickStart);
        FunctionKhai.validDatePicker(datePickEnd);
        FunctionKhai.addBtnReturn(returnHbox);
        Button salaryCalBtn = new Button("Tính");
        salaryCalBtn.getStyleClass().addAll(Styles.BUTTON_OUTLINED, Styles.SUCCESS, Styles.LARGE);
        salaryCalBtn.setPrefWidth(100);
        salaryCalBtn.setOnAction(e -> {
            try {
                billIngredientCal();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        inputHbox.getChildren().add(salaryCalBtn);
        initStyles();
    }
    @FXML
    private HBox returnHbox;
    @FXML
    private HBox inputHbox;
    @FXML
    private Label dateStartLabel;
    @FXML
    private DatePicker datePickStart;
    @FXML
    private Label dateEndLabel;
    @FXML
    private DatePicker datePickEnd;
    @FXML
    private VBox parentTotalVbox;
    @FXML
    private Label sumBillIngLabel;
    @FXML
    private Label totalBillIngredientLabel;
    @FXML
    private TableView<BillIngredientCal> tableIngredient;
    @FXML
    private TableColumn<BillIngredientCal, String> nameIngeredientColumn;
    @FXML
    private TableColumn<BillIngredientCal, Integer> countIngeredientColumn;
    @FXML
    private TableColumn<BillIngredientCal, String> unitIngeredientColumn;
    @FXML
    private TableColumn<BillIngredientCal, Integer> totalIngeredientColumn;

    private final BillIngredientCalViewModel billIngredientCalViewModel = new BillIngredientCalViewModel();
    private void popAlert(int type){
        tableIngredient.setVisible(false);
        parentTotalVbox.setVisible(false);
        Alert alert = new Alert(Alert.AlertType.ERROR);
        if(type == 1){
            alert.setContentText("Ngày bắt đầu không được phép lớn hơn ngày kết thúc!");
        }else{
            alert.setContentText("Đã có lỗi xảy ra, vui lòng thử lại sau!");
        }
        alert.showAndWait();
    }
    private void billIngredientCal(){
        if(datePickStart.getValue().isAfter(datePickEnd.getValue())){
            popAlert(1);
            return;
        }
        try {
            ScrollBar vScrollBar = (ScrollBar) tableIngredient.lookup(".scroll-bar:vertical");
            vScrollBar.setValue(vScrollBar.getMin());
            tableIngredient.setItems(FXCollections.observableList(billIngredientCalViewModel.getCount(new TimeRequest(datePickStart.getValue(), datePickEnd.getValue()))));
            tableIngredient.setVisible(true);
            int totalBillIngredient = 0;
            for(BillIngredientCal b : tableIngredient.getItems()) {
                totalBillIngredient += b.getTotal();
            }
            totalBillIngredientLabel.setText(FunctionKhai.convertMoney(totalBillIngredient));
            parentTotalVbox.setVisible(true);
        } catch (Exception e) {
            popAlert(0);
            throw new RuntimeException(e);
        }
    }
    private void initStyles(){
        FunctionKhai.validLabel(dateStartLabel);
        FunctionKhai.validLabel(dateEndLabel);
        FunctionKhai.validLabel(sumBillIngLabel);
        FunctionKhai.validLabel(totalBillIngredientLabel);
        datePickStart.getStyleClass().add(Styles.TITLE_4);
        datePickEnd.getStyleClass().add(Styles.TITLE_4);
        tableIngredient.getStyleClass().add(Tweaks.EDGE_TO_EDGE);
    }
}
