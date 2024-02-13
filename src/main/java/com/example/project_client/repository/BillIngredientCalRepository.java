package com.example.project_client.repository;

import com.example.project_client.data.Api;
import com.example.project_client.data.JsonUtils;
import com.example.project_client.data.Request;
import com.example.project_client.model.BillIngredientCal;
import com.example.project_client.model.NameAndCount;
import com.example.project_client.model.TimeRequest;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;
import java.util.Objects;

public class BillIngredientCalRepository {
    public List<BillIngredientCal> getCountApi(TimeRequest timeRequest) throws Exception{
        return JsonUtils.fromJson(Request.sendPostRequestGettingData(Api.billIngredientApi + "/getCount", Objects.requireNonNull(JsonUtils.toJson(timeRequest))), new TypeReference<>() {
        });
    }
    public List<NameAndCount> getPerDayApi(TimeRequest timeRequest) throws Exception{
        return JsonUtils.fromJson(Request.sendPostRequestGettingData(Api.billIngredientApi + "/getPerDay", Objects.requireNonNull(JsonUtils.toJson(timeRequest))), new TypeReference<>() {
        });
    }
    public List<NameAndCount> getPerMonthApi(TimeRequest timeRequest) throws Exception{
        return JsonUtils.fromJson(Request.sendPostRequestGettingData(Api.billIngredientApi + "/getPerMonth", Objects.requireNonNull(JsonUtils.toJson(timeRequest))), new TypeReference<>() {
        });
    }

}
