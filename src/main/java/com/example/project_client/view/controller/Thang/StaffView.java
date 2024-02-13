package com.example.project_client.view.controller.Thang;

import com.example.project_client.model.Staff;
import com.example.project_client.repository.StaffCalRepository;
import com.example.project_client.repository.TimekeepingRepository;
import com.example.project_client.router.Pages;
import com.example.project_client.router.Router;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.Getter;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class StaffView {
    private static Staff staff;
    private final StaffCalRepository staffCalRepository = new StaffCalRepository();
    private final TimekeepingRepository timekeepingRepository = new TimekeepingRepository();
    @FXML
    private TableColumn<Staff, String> id;
    @FXML
    private TableColumn<Staff, String> name;
    @FXML
    private TableColumn<Staff, String> gender;
    @FXML
    private TableColumn<Staff, LocalDate> dob;
    @FXML
    private TableColumn<Staff, String> phoneNumber;
    @FXML
    private TextField searchTextField;
    @FXML
    private TableColumn<Staff, String> email;
    @FXML
    private TableColumn<Staff, String> address;
    @FXML
    private TableColumn<Staff, String> role;
    @FXML
    private TableColumn<Staff, Integer> salaryPerDay;
    @Getter
    private List<Staff> staffs;
    @FXML
    private ObservableList<Staff> tableList;
    @FXML
    TableView<Staff> tableView;

    @FXML
    public void initialize() {
        try {
            setTableView();
            setColumn();
        }
        catch (Exception e){
            System.out.println("ERROR" + e.getMessage());
//            System.out.println(staffs);
        }
        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filterTable(newValue);
        });
    }
    @FXML
    private void addStaff() throws IOException {
        Router.goTo(Pages.ADD_STAFF_VIEW);
    }
    public static String idUpdate;
    @FXML
    private void updateStaff() {
        try {
            staff = tableView.getSelectionModel().getSelectedItem();
            if(staff == null) {
                StaffView.createAlert(Alert.AlertType.WARNING, "Bạn chưa chọn nhân viên", "", "Thông báo" ).show();
            }
            idUpdate = staff.getId();
            Router.switchTo(Pages.UPDATE_STAFF_VIEW);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    @FXML
    private void deleteStaff() {
        try {
            staff = tableView.getSelectionModel().getSelectedItem();
            if(staff == null) {
                StaffView.createAlert(Alert.AlertType.WARNING, "Bạn chưa chọn nhân viên", "", "Thông báo" ).show();
            } else {
                staffCalRepository.deleteStaffApi(staff.getId());
                tableList.remove(staff);
                StaffView.createAlert(Alert.AlertType.CONFIRMATION, "Bạn đã xóa thanh công nhân viên " + staff.getName(), "", "Thông báo" ).show();
            }
        }
        catch (Exception e){
            System.out.println("Please choose Staff to delete");
        }
    }

    @FXML
    private void cancelStaff() throws IOException {
       Router.switchTo(Pages.ADMIN_VIEW);
    }
    @FXML
    private void attendanceStaff(){
        Staff staff = tableView.getSelectionModel().getSelectedItem();
        if(staff == null){
            createAlert(Alert.AlertType.WARNING, "Bạn chưa chọn nhân viên", "", "Thông báo" ).showAndWait();
        }else{
            Map<String, String> input = new HashMap<>();
            input.put("staffId", staff.getId());
            input.put("workDate", LocalDate.now().toString());
            try {
                timekeepingRepository.saveTimekeeping(input);
                createAlert(Alert.AlertType.INFORMATION, "Điểm danh thành công", "", "Thông báo" ).showAndWait();
            } catch (Exception e) {
                createAlert(Alert.AlertType.WARNING, "Có lỗi xảy ra với máy chủ, thử lại sau!", "", "Thông báo" ).showAndWait();
                throw new RuntimeException(e);
            }

        }
    }
    @FXML
    private void readStaff() throws IOException {
        try {
            staff = tableView.getSelectionModel().getSelectedItem();
            if(staff == null) {
                StaffView.createAlert(Alert.AlertType.WARNING, "Bạn chưa chọn nhân viên", "", "Thông báo" ).show();
            }
            idUpdate = staff.getId();
            Router.switchTo(Pages.READ_STAFF_VIEW);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private void setTableView() throws IOException {
        staffs = staffCalRepository.getAllStaffApi();
        tableList = FXCollections.observableArrayList(staffs);
        tableView.setItems(tableList);
    }
    private void setColumn()  {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        dob.setCellValueFactory(new PropertyValueFactory<>("dob"));
        phoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        address.setCellValueFactory(new PropertyValueFactory<>("address"));
        role.setCellValueFactory(new PropertyValueFactory<>("role"));
        salaryPerDay.setCellValueFactory(new PropertyValueFactory<>("salaryPerDay"));
    }

    public static Alert createAlert(Alert.AlertType type, String content, String header, String title) {
        Alert alert = new Alert(type, content);
        alert.setHeaderText(header);
        alert.setTitle(title);
        return alert;
    }

    public String getStaffId() {
        return idUpdate;
    }
    private void filterTable(String keyword) {
        // Lọc và hiển thị chỉ các dòng phù hợp với từ khóa tìm kiếm
        ObservableList<Staff> filteredList = tableList.filtered(staff ->
                        staff.getName().toLowerCase().contains(keyword.toLowerCase())
//                                || staff.getPhoneNumber().toLowerCase().contains(keyword.toLowerCase())
//                                || staff.getRole().toLowerCase().contains(keyword.toLowerCase())
                 // ... Thêm các trường khác cần tìm kiếm ...
        );

        // Cập nhật TableView với danh sách đã lọc
        tableView.setItems(filteredList);
    }
}
