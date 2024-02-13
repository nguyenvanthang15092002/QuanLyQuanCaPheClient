package com.example.project_client.view.controller.Thang;

import com.example.project_client.model.Staff;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class demo {
    @FXML
    private TableView<YourDataModelClass> tableView;

    @FXML
    private TableColumn<YourDataModelClass, Integer> idstaffColumn;

    @FXML
    private TableColumn<YourDataModelClass, Integer> day1Column;

    // Add TableColumn fields for Day 3 to Day 31 similarly

    // You can also add an ObservableList<YourDataModelClass> to store data for the table

    @FXML
    public void handleEditCommit(TableColumn.CellEditEvent<YourDataModelClass, Integer> event) {
        YourDataModelClass rowData = event.getRowValue();
        rowData.setDay1(event.getNewValue());
        // Handle other necessary logic here
    }
}
