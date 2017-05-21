package com.example.marcin.upc;

/**
 * Created by Marcin on 26.03.2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductPOJO {

    @SerializedName("UPC")
    @Expose
    private String uPC;
    @SerializedName("Name")
    @Expose
    private String name;

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

}
