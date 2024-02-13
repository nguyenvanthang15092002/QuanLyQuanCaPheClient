package com.example.project_client.viewModel.Quyen;

import com.example.project_client.event.Data;
import com.example.project_client.model.OrderBill;
import com.example.project_client.repository.OrderBillRepository;

import com.example.project_client.view.controller.Quyen.components.PDFExporter;
import lombok.Getter;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class OrderBillViewModel {
    private final OrderBillRepository orderBillRepository = new OrderBillRepository();
    @Getter
    private OrderBill data;

    public void initData(OrderBill orderBill, Boolean isCreate) throws Exception {
        this.data = orderBill;
        if (isCreate) {
            data.setId("OB" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("ssmmHHddMMyyyy")));
            data.setUserStaffId(Data.getUser().getStaffId());
            data.setChangeMoney(data.getReceived() - data.getTotal());
            orderBillRepository.saveOrderBillApi(data);
            export(orderBill);
        }
    }

    private void export(OrderBill orderBill) {


        PDFExporter.exportToPDF(orderBill, Data.getBillPath()+"\\" + orderBill.getId() + ".pdf");


    }

}
