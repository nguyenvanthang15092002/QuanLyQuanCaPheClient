package com.example.project_client.view.controller.Khai;

import atlantafx.base.theme.Styles;
import com.example.project_client.model.NameAndCount;
import com.example.project_client.model.TimeRequest;
import com.example.project_client.view.controller.Khai.DataObj.ProfitLinechartData;
import com.example.project_client.view.controller.Khai.Function.FunctionKhai;
import com.example.project_client.viewModel.Khai.ProfitCalViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.material2.Material2AL;
import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ProfitCalViewController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        profitLinechart.setAnimated(false);
        FunctionKhai.validDatePicker(datePickEnd);
        FunctionKhai.validDatePicker(datePickStart);
        FunctionKhai.validNumberAxis(yAxisProfitLine);
        FunctionKhai.validCombobox(typeCbb);
        FunctionKhai.addBtnReturn(returnHbox);
        Button salaryCalBtn = new Button("Tính");
        salaryCalBtn.getStyleClass().addAll(Styles.BUTTON_OUTLINED, Styles.SUCCESS, Styles.LARGE);
        salaryCalBtn.setPrefWidth(100);
        salaryCalBtn.setOnAction(e -> {
            try {
                profitCal();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        inputHbox.getChildren().add(salaryCalBtn);
        initStyles();
        profitLinechart.setLegendVisible(false);
        validVboxInfor();
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
    private Label typeCbbLabel;
    @FXML
    private ComboBox<String> typeCbb;
    @FXML
    private VBox parentTotalVbox;
    @FXML
    private Label sumProfitLabel;
    @FXML
    private Label totalProfitLabel;
    @FXML
    private LineChart<String, Number> profitLinechart;
    @FXML
    private CategoryAxis xAxisProfitLine;
    @FXML
    private NumberAxis yAxisProfitLine;
    @FXML
    private VBox vBoxInfor;
    @FXML
    private HBox returnOfVboxInfor;
    @FXML
    private Label revenueLabel;
    @FXML
    private Label revenuePcLabel;
    @FXML
    private Label salaryLabel;
    @FXML
    private Label salaryPcLabel;
    @FXML
    private Label ingreLabel;
    @FXML
    private Label ingrePcLabel;
    @FXML
    private Label profitLabel;
    private final ProfitCalViewModel profitCalViewModel = new ProfitCalViewModel();
    private void settingOutTime(Integer totalProfit){
        sumProfitLabel.setText("Không thể thống kê!");
        totalProfitLabel.setText("Tổng: " + FunctionKhai.convertMoney(totalProfit));
        yAxisProfitLine.setUpperBound(500000);
        yAxisProfitLine.setLowerBound(0);
        yAxisProfitLine.setTickUnit(50000);
        profitLinechart.getData().clear();
    }
    private void popAlert(int type){
        profitLinechart.setVisible(false);
        parentTotalVbox.setVisible(false);
        Alert alert = new Alert(Alert.AlertType.ERROR);
        if(type == 1){
            alert.setContentText("Ngày bắt đầu không được phép lớn hơn ngày kết thúc!");
        }else{
            alert.setContentText("Đã có lỗi xảy ra với máy chủ, vui lòng thử lại sau!");
        }
        alert.showAndWait();
    }
    @SuppressWarnings("unchecked")
    private void handlePerDay(TimeRequest timeRequest) throws Exception {
        List<NameAndCount> billProducts = profitCalViewModel.getBillProductViewModel().getPerDay(timeRequest);
        List<NameAndCount> billIngredients = profitCalViewModel.getBillIngredientCalViewModel().getPerDay(timeRequest);
        List<NameAndCount> salarys = profitCalViewModel.getSalaryCalViewModel().getPerDay(timeRequest);
        int totalProfit = 0;
        for(NameAndCount b : billProducts) {
            totalProfit += b.getCount();
        }
        for(NameAndCount b : salarys) {
            totalProfit -= b.getCount();
        }
        for(NameAndCount b : billIngredients) {
            totalProfit -= b.getCount();
        }
        if(datePickStart.getValue().until(datePickEnd.getValue(), ChronoUnit.DAYS) >= 40) {
            settingOutTime(totalProfit);
        }else {
            sumProfitLabel.setText("Tổng lợi nhuận: ");
            totalProfitLabel.setText(FunctionKhai.convertMoney(totalProfit));
            int i, j1 = 0, j2 = 0, j3 = 0, sz = (int) datePickStart.getValue().until(datePickEnd.getValue(), ChronoUnit.DAYS), min = 0, max = 0;
            LocalDate st = datePickStart.getValue();

            List<ProfitLinechartData> profitLinechartDatas = new ArrayList<>();
            for (i = 0; i <= sz; i++) {
                int salary = 0, billPro = 0, billIngre = 0;
                if (j1 < billProducts.size() && st.plusDays(i).toString().equals(billProducts.get(j1).getName())) {
                    billPro += billProducts.get(j1).getCount();
                    j1++;
                }
                if (j2 < salarys.size() && st.plusDays(i).toString().equals(salarys.get(j2).getName())) {
                    salary += salarys.get(j2).getCount();
                    j2++;
                }
                if (j3 < billIngredients.size() && st.plusDays(i).toString().equals(billIngredients.get(j3).getName())) {
                    billIngre += billIngredients.get(j3).getCount();
                    j3++;
                }
                int total = billPro - salary - billIngre;
                if (min > total) min = total;
                if (max < total) max = total;
                profitLinechartDatas.add(new ProfitLinechartData(FunctionKhai.convertDate(st.plusDays(i).toString()), total, salary, billIngre, billPro));
            }

            if(profitLinechartDatas.size() == 1){
                profitLinechart.getData().clear();
                XYChart.Series<String, Number> series = new XYChart.Series<>();
                profitLinechart.getData().add(series);
                XYChart.Data<String, Number> dataNew = new XYChart.Data<>(profitLinechartDatas.get(0).getDateData(), profitLinechartDatas.get(0).getTotalData());
                series.getData().add(dataNew);
                dataNew.getNode().setOnMouseEntered(e -> dataNew.getNode().setCursor(Cursor.HAND));
                dataNew.getNode().setOnMousePressed(e -> {
                    vBoxInfor.setVisible(true);
                    revenueLabel.setText("Revenue: " + FunctionKhai.convertMoney(profitLinechartDatas.get(0).getBillPro()));
                    salaryLabel.setText("Salary: " + FunctionKhai.convertMoney(profitLinechartDatas.get(0).getSalary()));
                    ingreLabel.setText("Bill Ingredien: " + FunctionKhai.convertMoney(profitLinechartDatas.get(0).getBillIngre()));
                    profitLabel.setText("Profit: " + FunctionKhai.convertMoney((Integer) profitLinechartDatas.get(0).getTotalData()));
                    removePc();
                });
            }else{
                for(int id = 1; id < profitLinechartDatas.size(); id++){
                    XYChart.Series<String, Number> series1 = new XYChart.Series<>();
                    profitLinechart.getData().add(series1);
                    XYChart.Data<String, Number> dataNew0 = new XYChart.Data<>(profitLinechartDatas.get(id - 1).getDateData(), profitLinechartDatas.get(id - 1).getTotalData());
                    XYChart.Data<String, Number> dataNew1 = new XYChart.Data<>(profitLinechartDatas.get(id).getDateData(), profitLinechartDatas.get(id).getTotalData());
                    ProfitLinechartData profitLinechartData0 = profitLinechartDatas.get(id - 1);
                    series1.getData().addAll(dataNew0, dataNew1);
                    if((Integer)dataNew1.getYValue() >= (Integer) dataNew0.getYValue()){
                        series1.getNode().setStyle("-fx-stroke: green;");
                    }else{
                        series1.getNode().setStyle("-fx-stroke: red;");
                    }
                    dataNew0.getNode().setOnMouseEntered(e -> dataNew0.getNode().setCursor(Cursor.HAND));
                    if(id != 1){
                        ProfitLinechartData profitLinechartData = profitLinechartDatas.get(id - 2);
                        double revenuePc = FunctionKhai.calPc(profitLinechartData.getBillPro(), profitLinechartData0.getBillPro()),
                        salaryPc = FunctionKhai.calPc(profitLinechartData.getSalary(), profitLinechartData0.getSalary()),
                        ingrePc = FunctionKhai.calPc(profitLinechartData.getBillIngre(), profitLinechartData0.getBillIngre());
                        dataNew0.getNode().setOnMousePressed(e -> {
                            vBoxInfor.setVisible(true);
                            revenueLabel.setText("Revenue: " + FunctionKhai.convertMoney(profitLinechartData0.getBillPro()));
                            salaryLabel.setText("Salary: " + FunctionKhai.convertMoney(profitLinechartData0.getSalary()));
                            ingreLabel.setText("Bill Ingredien: " + FunctionKhai.convertMoney(profitLinechartData0.getBillIngre()));
                            profitLabel.setText("Profit: " + FunctionKhai.convertMoney((Integer) profitLinechartData0.getTotalData()));
                            validPcLabel(revenuePc, salaryPc, ingrePc);
                        });
                    }else{
                        dataNew0.getNode().setOnMousePressed(e -> {
                            vBoxInfor.setVisible(true);
                            revenueLabel.setText("Revenue: " + FunctionKhai.convertMoney(profitLinechartData0.getBillPro()));
                            salaryLabel.setText("Salary: " + FunctionKhai.convertMoney(profitLinechartData0.getSalary()));
                            ingreLabel.setText("Bill Ingredien: " + FunctionKhai.convertMoney(profitLinechartData0.getBillIngre()));
                            profitLabel.setText("Profit: " + FunctionKhai.convertMoney((Integer) profitLinechartData0.getTotalData()));
                            removePc();
                        });
                    }

                    if(id == profitLinechartDatas.size() - 1){
                        dataNew1.getNode().setOnMouseEntered(e -> dataNew1.getNode().setCursor(Cursor.HAND));
                        ProfitLinechartData profitLinechartData = profitLinechartDatas.get(id);
                        double revenuePc = FunctionKhai.calPc(profitLinechartData0.getBillPro(), profitLinechartData.getBillPro()),
                                salaryPc = FunctionKhai.calPc(profitLinechartData0.getSalary(), profitLinechartData.getSalary()),
                                ingrePc = FunctionKhai.calPc(profitLinechartData0.getBillIngre(), profitLinechartData.getBillIngre());
                        dataNew1.getNode().setOnMousePressed(e -> {
                            vBoxInfor.setVisible(true);
                            revenueLabel.setText("Revenue: " + FunctionKhai.convertMoney(profitLinechartData.getBillPro()));
                            salaryLabel.setText("Salary: " + FunctionKhai.convertMoney(profitLinechartData.getSalary()));
                            ingreLabel.setText("Bill Ingredien: " + FunctionKhai.convertMoney(profitLinechartData.getBillIngre()));
                            profitLabel.setText("Profit: " + FunctionKhai.convertMoney((Integer) profitLinechartData.getTotalData()));
                            validPcLabel(revenuePc, salaryPc, ingrePc);
                        });
                    }
                }
            }
            if (max == 0 && min == 0) {
                yAxisProfitLine.setUpperBound(250000);
                yAxisProfitLine.setLowerBound(-250000);
                yAxisProfitLine.setTickUnit(50000);
            } else {
                yAxisProfitLine.setUpperBound(max);
                yAxisProfitLine.setLowerBound(min);
                int diff = (max - min) / 10;
                yAxisProfitLine.setTickUnit(diff);
            }
        }
    }
    @SuppressWarnings("unchecked")
    private void handlePerMonth(TimeRequest timeRequest) throws Exception{
        List<NameAndCount> billProducts = profitCalViewModel.getBillProductViewModel().getPerMonth(timeRequest);
        List<NameAndCount> billIngredients = profitCalViewModel.getBillIngredientCalViewModel().getPerMonth(timeRequest);
        List<NameAndCount> salarys = profitCalViewModel.getSalaryCalViewModel().getPerMonth(timeRequest);
        int totalProfit = 0;
        for(NameAndCount b : billProducts) {
            totalProfit += b.getCount();
        }
        for(NameAndCount b : salarys) {
            totalProfit -= b.getCount();
        }
        for(NameAndCount b : billIngredients) {
            totalProfit -= b.getCount();
        }
        if(datePickStart.getValue().until(datePickEnd.getValue(), ChronoUnit.MONTHS) >= 40) {
            settingOutTime(totalProfit);
        }else{
            sumProfitLabel.setText("Tổng lợi nhuận: ");
            totalProfitLabel.setText(FunctionKhai.convertMoney(totalProfit));
            LocalDate st = datePickStart.getValue(), en = datePickEnd.getValue();
            int i, j1 = 0, j2 = 0, j3 = 0, sz = (en.getYear() - st.getYear()) * 12 + en.getMonthValue() - st.getMonthValue() , min = 0, max = 0;
            List<ProfitLinechartData> profitLinechartDatas = new ArrayList<>();
            for(i = 0; i <= sz; i++) {
                int salary = 0, billPro = 0, billIngre = 0;
                if(j1 < billProducts.size() && FunctionKhai.convertDatePerMonth(st.plusMonths(i)).equals(FunctionKhai.convertDate(billProducts.get(j1).getName()))) {
                    billPro += billProducts.get(j1).getCount();
                    j1++;
                }
                if(j2 < salarys.size() && FunctionKhai.convertDatePerMonth(st.plusMonths(i)).equals(FunctionKhai.convertDate(salarys.get(j2).getName()))) {
                    salary += salarys.get(j2).getCount();
                    j2++;
                }
                if(j3 < billIngredients.size() && FunctionKhai.convertDatePerMonth(st.plusMonths(i)).equals(FunctionKhai.convertDate(billIngredients.get(j3).getName()))) {
                    billIngre += billIngredients.get(j3).getCount();
                    j3++;
                }
                int total = billPro - salary - billIngre;
                if (min > total) min = total;
                if (max < total) max = total;
                profitLinechartDatas.add(new ProfitLinechartData(FunctionKhai.convertDatePerMonth(st.plusMonths(i)), total, salary, billIngre, billPro));
            }
            if(profitLinechartDatas.size() == 1){
                XYChart.Series<String, Number> series = new XYChart.Series<>();
                profitLinechart.getData().add(series);
                XYChart.Data<String, Number> dataNew = new XYChart.Data<>(profitLinechartDatas.get(0).getDateData(), profitLinechartDatas.get(0).getTotalData());
                series.getData().add(dataNew);
                dataNew.getNode().setOnMouseEntered(e -> dataNew.getNode().setCursor(Cursor.HAND));
                dataNew.getNode().setOnMousePressed(e -> {
                    vBoxInfor.setVisible(true);
                    revenueLabel.setText("Revenue: " + FunctionKhai.convertMoney(profitLinechartDatas.get(0).getBillPro()));
                    salaryLabel.setText("Salary: " + FunctionKhai.convertMoney(profitLinechartDatas.get(0).getSalary()));
                    ingreLabel.setText("Bill Ingredien: " + FunctionKhai.convertMoney(profitLinechartDatas.get(0).getBillIngre()));
                    profitLabel.setText("Profit: " + FunctionKhai.convertMoney((Integer) profitLinechartDatas.get(0).getTotalData()));
                    removePc();
                });
            }else{
                for(int id = 1; id < profitLinechartDatas.size(); id++){
                    XYChart.Series<String, Number> series1 = new XYChart.Series<>();
                    profitLinechart.getData().add(series1);
                    XYChart.Data<String, Number> dataNew0 = new XYChart.Data<>(profitLinechartDatas.get(id - 1).getDateData(), profitLinechartDatas.get(id - 1).getTotalData());
                    XYChart.Data<String, Number> dataNew1 = new XYChart.Data<>(profitLinechartDatas.get(id).getDateData(), profitLinechartDatas.get(id).getTotalData());
                    ProfitLinechartData profitLinechartData0 = profitLinechartDatas.get(id - 1);
                    series1.getData().addAll(dataNew0, dataNew1);
                    if((Integer)dataNew1.getYValue() >= (Integer) dataNew0.getYValue()){
                        series1.getNode().setStyle("-fx-stroke: green;");
                    }else{
                        series1.getNode().setStyle("-fx-stroke: red;");
                    }
                    dataNew0.getNode().setOnMouseEntered(e -> dataNew0.getNode().setCursor(Cursor.HAND));
                    if(id != 1){
                        ProfitLinechartData profitLinechartData = profitLinechartDatas.get(id - 2);
                        double revenuePc = FunctionKhai.calPc(profitLinechartData.getBillPro(), profitLinechartData0.getBillPro()),
                                salaryPc = FunctionKhai.calPc(profitLinechartData.getSalary(), profitLinechartData0.getSalary()),
                                ingrePc = FunctionKhai.calPc(profitLinechartData.getBillIngre(), profitLinechartData0.getBillIngre());
                        dataNew0.getNode().setOnMousePressed(e -> {
                            vBoxInfor.setVisible(true);
                            revenueLabel.setText("Revenue: " + FunctionKhai.convertMoney(profitLinechartData0.getBillPro()));
                            salaryLabel.setText("Salary: " + FunctionKhai.convertMoney(profitLinechartData0.getSalary()));
                            ingreLabel.setText("Bill Ingredien: " + FunctionKhai.convertMoney(profitLinechartData0.getBillIngre()));
                            profitLabel.setText("Profit: " + FunctionKhai.convertMoney((Integer) profitLinechartData0.getTotalData()));
                            validPcLabel(revenuePc, salaryPc, ingrePc);
                        });
                    }else{
                        dataNew0.getNode().setOnMousePressed(e -> {
                            vBoxInfor.setVisible(true);
                            revenueLabel.setText("Revenue: " + FunctionKhai.convertMoney(profitLinechartData0.getBillPro()));
                            salaryLabel.setText("Salary: " + FunctionKhai.convertMoney(profitLinechartData0.getSalary()));
                            ingreLabel.setText("Bill Ingredien: " + FunctionKhai.convertMoney(profitLinechartData0.getBillIngre()));
                            profitLabel.setText("Profit: " + FunctionKhai.convertMoney((Integer) profitLinechartData0.getTotalData()));
                            removePc();
                        });
                    }

                    if(id == profitLinechartDatas.size() - 1){
                        dataNew1.getNode().setOnMouseEntered(e -> dataNew1.getNode().setCursor(Cursor.HAND));
                        ProfitLinechartData profitLinechartData = profitLinechartDatas.get(id);
                        double revenuePc = FunctionKhai.calPc(profitLinechartData0.getBillPro(), profitLinechartData.getBillPro()),
                                salaryPc = FunctionKhai.calPc(profitLinechartData0.getSalary(), profitLinechartData.getSalary()),
                                ingrePc = FunctionKhai.calPc(profitLinechartData0.getBillIngre(), profitLinechartData.getBillIngre());
                        dataNew1.getNode().setOnMousePressed(e -> {
                            vBoxInfor.setVisible(true);
                            revenueLabel.setText("Revenue: " + FunctionKhai.convertMoney(profitLinechartData.getBillPro()));
                            salaryLabel.setText("Salary: " + FunctionKhai.convertMoney(profitLinechartData.getSalary()));
                            ingreLabel.setText("Bill Ingredien: " + FunctionKhai.convertMoney(profitLinechartData.getBillIngre()));
                            profitLabel.setText("Profit: " + FunctionKhai.convertMoney((Integer) profitLinechartData.getTotalData()));
                            validPcLabel(revenuePc, salaryPc, ingrePc);
                        });
                    }
                }
            }
            if(max == 0 && min == 0) {
                yAxisProfitLine.setUpperBound(250000);
                yAxisProfitLine.setLowerBound(-250000);
                yAxisProfitLine.setTickUnit(50000);
            }else {
                yAxisProfitLine.setUpperBound(max);
                yAxisProfitLine.setLowerBound(min);
                int diff = (max - min) / 10;
                yAxisProfitLine.setTickUnit(diff);
            }
        }
    }
    private void initStyles(){
        FunctionKhai.validLabel(dateStartLabel);
        FunctionKhai.validLabel(dateEndLabel);
        FunctionKhai.validLabel(sumProfitLabel);
        FunctionKhai.validLabel(totalProfitLabel);
        FunctionKhai.validLabel(typeCbbLabel);
        typeCbb.getStyleClass().add(Styles.TITLE_4);
        datePickStart.getStyleClass().add(Styles.TITLE_4);
        datePickEnd.getStyleClass().add(Styles.TITLE_4);
    }
    private void profitCal(){
        profitLinechart.getData().clear();
        if(datePickStart.getValue().isAfter(datePickEnd.getValue())){
            popAlert(1);
            return;
        }
        TimeRequest timeRequest = new TimeRequest(datePickStart.getValue(), datePickEnd.getValue());
        if(typeCbb.getValue().equals("Theo tháng")){
            xAxisProfitLine.setLabel("Tháng");
            try {
                handlePerMonth(timeRequest);
            } catch (Exception e) {
                popAlert(0);
                return;
            }
        }else{
            xAxisProfitLine.setLabel("Ngày");
            try {
                handlePerDay(timeRequest);
            } catch (Exception e) {
                popAlert(0);
                return;
            }
        }
        parentTotalVbox.setVisible(true);
        profitLinechart.setVisible(true);
    }
    private void validVboxInfor(){
        Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);
        Button returnBtn = new Button(null, new FontIcon(Material2AL.CLOSE));
        returnBtn.getStyleClass().addAll(Styles.BUTTON_ICON,Styles.FLAT, Styles.DANGER);
        returnOfVboxInfor.getChildren().addAll(region, returnBtn);
        returnBtn.setOnAction(e -> vBoxInfor.setVisible(false));
        FunctionKhai.validLabel(revenueLabel);
        FunctionKhai.validLabel(salaryLabel);
        FunctionKhai.validLabel(ingreLabel);
        FunctionKhai.validLabel(profitLabel);
        FunctionKhai.validLabel(revenuePcLabel);
        FunctionKhai.validLabel(salaryPcLabel);
        FunctionKhai.validLabel(ingrePcLabel);
    }
    private void removePc(){
        revenuePcLabel.setText("");
        salaryPcLabel.setText("");
        ingrePcLabel.setText("");
    }
    private void validPcLabel(double a, double b, double c){
        if (a > 0){
            revenuePcLabel.setText("Tăng " + a + "%");
        }else{
            if(a < 0){
                revenuePcLabel.setText("Giảm " + -a + "%");
            }else{
                revenuePcLabel.setText("");
            }
        }
        if (b > 0){
            salaryPcLabel.setText("Tăng " + b + "%");
        }else{
            if(b < 0){
                salaryPcLabel.setText("Giảm " + -b + "%");
            }else{
                salaryPcLabel.setText("");
            }
        }
        if (c > 0){
            ingrePcLabel.setText("Tăng " + c + "%");
        }else{
            if(c < 0){
                ingrePcLabel.setText("Giảm " + -c + "%");
            }else{
                ingrePcLabel.setText("");
            }
        }
    }
}