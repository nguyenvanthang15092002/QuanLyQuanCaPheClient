package com.example.project_client.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Ingredient {
    private Integer id;
    private String name;
    private String unit;
    private Integer unitPrice;

    @JsonCreator
    public Ingredient(@JsonProperty("id") Integer id, @JsonProperty("name") String name, @JsonProperty("unit") String unit, @JsonProperty("unitPrice") Integer unitPrice){
        this.id = id;
        this.name = name;
        this.unit = unit;
        this.unitPrice = unitPrice;
    }

    public Ingredient(){
        this.id = null;
        this.name = null;
        this.unit = null;
        this.unitPrice = null;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUnit() {
        return unit;
    }

    public Integer getUnitPrice() {
        return unitPrice;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean setName(String name) {
        if(name.equals("")){
            return Boolean.FALSE;
        }
        else {
            this.name = name;
            return Boolean.TRUE;
        }
    }

    public Boolean setUnit(String unit) {
        if(unit.equals("")){
            return Boolean.FALSE;
        }
        else {
            this.unit = unit;
            return Boolean.TRUE;
        }
    }

    public Boolean setUnitPrice(Integer unitPrice) {
        if(unitPrice > 1000000 || unitPrice < 0){
            return Boolean.FALSE;
        }
        else {
            this.unitPrice = unitPrice;
            return Boolean.TRUE;
        }
    }
}
