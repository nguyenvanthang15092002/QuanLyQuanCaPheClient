package com.example.project_client.repository;

import com.example.project_client.data.Api;
import com.example.project_client.data.JsonUtils;
import com.example.project_client.data.Request;

import java.util.Map;
import java.util.Objects;

public class TimekeepingRepository {
    public void saveTimekeeping(Map<String, String> input) throws Exception {
        Request.sendPostRequest(Api.timeKeepingApi + "/save", Objects.requireNonNull(JsonUtils.toJson(input)));
    }
}
