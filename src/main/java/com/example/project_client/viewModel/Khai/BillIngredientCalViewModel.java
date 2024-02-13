package com.example.project_client.viewModel.Khai;

import com.example.project_client.model.BillIngredientCal;
import com.example.project_client.model.NameAndCount;
import com.example.project_client.model.TimeRequest;
import com.example.project_client.repository.BillIngredientCalRepository;

import java.util.List;

public class BillIngredientCalViewModel {
    private final BillIngredientCalRepository billIngredientCalRepo = new BillIngredientCalRepository();

    public List<BillIngredientCal> getCount(TimeRequest timeRequest) throws Exception {
        return billIngredientCalRepo.getCountApi(timeRequest);
    }
    public List<NameAndCount> getPerDay(TimeRequest timeRequest) throws Exception {
        return billIngredientCalRepo.getPerDayApi(timeRequest);
    }
    public List<NameAndCount> getPerMonth(TimeRequest timeRequest) throws Exception {
        return billIngredientCalRepo.getPerMonthApi(timeRequest);
    }
}
