package com.example.marcin.upc;

/**
 * Created by Marcin on 03.04.2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//http://www.jsonschema2pojo.org/ - GSON,

public class StockProductPOJO {

    @SerializedName("UPC")
    @Expose
    private String uPC;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("IMEI")
    @Expose
    private String iMEI;
    @SerializedName("Price")
    @Expose
    private Double price;

    public String getUPC() {
        return uPC;
    }

    public void setUPC(String uPC) {
        this.uPC = uPC;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIMEI() {
        return iMEI;
    }

    public void setIMEI(String iMEI) {
        this.iMEI = iMEI;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

}


