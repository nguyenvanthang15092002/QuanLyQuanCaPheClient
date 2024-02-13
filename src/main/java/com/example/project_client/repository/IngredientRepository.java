package com.example.project_client.repository;

import com.example.project_client.data.Api;
import com.example.project_client.data.JsonUtils;
import com.example.project_client.data.Request;
import com.example.project_client.model.Ingredient;

import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public final class IngredientRepository {
    public static List<Ingredient> getIngredientsApi() throws IOException {
        String jsonObject = Request.sendGetRequest(Api.ingredientApi+"/getAll");
        return JsonUtils.fromJson(jsonObject, new TypeReference<>() {
        });
    }
    public static void saveIngredient(Ingredient ingredient) throws Exception {
        Request.sendPostRequest(Api.ingredientApi+"/save", Objects.requireNonNull(JsonUtils.toJson(ingredient)));
    }
    public static void updateIngredient(Ingredient ingredient) throws Exception {
        Request.sendPutRequest(Api.ingredientApi+"/update", Objects.requireNonNull(JsonUtils.toJson(ingredient)));
    }
    public static void deleteIngredient(String id) throws Exception {
        Request.sendDeleteRequest(Api.ingredientApi+"/delete", id);
    }
}