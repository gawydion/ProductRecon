package com.example.marcin.upc;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Marcin on 05.01.2017.
 */

public class Product {
    @SerializedName("Name")
    @Expose
    private String productName;
    @SerializedName("UPC")
    @Expose
    private String productNumber;
    @SerializedName("LP")
    @Expose
    private int lp;

    private String IMEI;
    private double price;

    String productDescription;

    String temp;


    public Product(String productName, String productNumber, String productDescription, double price) {
        this.productName = productName;
        this.productNumber = productNumber;
        this.productDescription = productDescription;
        this.price = price;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductNumber() {
        return productNumber;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductNumber(String productNumber) {
        this.productNumber = productNumber;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getProductDescription() {

        return productDescription;
    }

    public String getIMEI() {
        return IMEI;
    }

    public void setIMEI(String IMEI) {
        this.IMEI = IMEI;
    }

    public int getLp() {
        return lp;
    }

    public void setLp(int lp) {
        this.lp = lp;
    }

    JSONObject productToJSON(){

        //{"LP":"1","UPC":"190198067302","Name":"Apple iPhone 7 silver 32gb"}

        JSONObject productJSON = new JSONObject();

        try {
            productJSON.put("LP", "");
            productJSON.put("UPC", this.productNumber);
            productJSON.put("Name", this.productName);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return productJSON;
    }
}
