package com.example.project_client.view.controller.Thang;


import com.example.project_client.model.Staff;
import com.example.project_client.repository.StaffCalRepository;
import com.example.project_client.router.Pages;
import com.example.project_client.router.Router;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;
import javafx.scene.control.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;
import java.util.function.UnaryOperator;

public class ReadStaffView {
    private Staff staff;
    private final StaffCalRepository staffCalRepository = new StaffCalRepository();

    private final StaffView staffView = new StaffView();

    @FXML
    private GridPane formTable;
    @FXML
    private TextField emailField;

    @FXML
    private TextField phoneField;
    @FXML
    private TextField addressField;

    @FXML
    private TextField nameField;

    @FXML
    private DatePicker birthDatePicker;

    @FXML
    private TextField salaryField;

    @FXML
    private TextField roleField;


    @FXML
    private RadioButton male;

    @FXML
    private RadioButton female;

    @FXML
    private ToggleGroup genderToggleGroup;

    @FXML
    public void initialize() throws Exception {
        staff = staffCalRepository.getStaffByIdApi(staffView.getStaffId());
        nameField.setText(staff.getName());
        roleField.setText(staff.getRole());
        emailField.setText(staff.getEmail());
        salaryField.setText(String.valueOf(staff.getSalaryPerDay()));
        birthDatePicker.setValue(staff.getDob());
        phoneField.setText(staff.getPhoneNumber());
        addressField.setText(staff.getAddress());
        if(staff.getGender().equals("nam")) {
            male.setSelected(true);
        } else {
            female.setSelected(true);
        }
    }

    @FXML
    private void cancelUpdate() throws IOException {
        Router.switchTo(Pages.STAFF_VIEW);
    }
}
