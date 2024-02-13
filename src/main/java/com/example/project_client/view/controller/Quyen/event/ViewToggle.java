package com.example.project_client.view.controller.Quyen.event;

import com.example.project_client.model.OrderBill;
import com.example.project_client.model.Promotion;
import lombok.Getter;
import lombok.Setter;

public class ViewToggle {
    @Getter
    @Setter
    private static Boolean isCreateBill = false;
    @Getter
    @Setter
    private static OrderBill orderBill ;
    @Getter
    @Setter
    private static Promotion promotion = new Promotion();
}
