package com.example.project_client.viewModel.Khai;

import lombok.Getter;

@Getter
public class ProfitCalViewModel {
    private final BillProductViewModel billProductViewModel = new BillProductViewModel();
    private final BillIngredientCalViewModel billIngredientCalViewModel = new BillIngredientCalViewModel();
    private final SalaryCalViewModel salaryCalViewModel = new SalaryCalViewModel();
}
