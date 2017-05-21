package com.example.marcin.upc;

import android.app.Activity;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Marcin on 26.03.2017.
 */

public class ServerClient {

    Activity activity;

    EditText editText_UPC;
    EditText editText_Nazwa;
    EditText editText_IMEI;
    EditText editText_Ilosc;

    public ServerClient(Activity activity) {
        this.activity = activity;
        this.editText_UPC = (EditText) activity.findViewById(R.id.editText_UPC);
        this.editText_Nazwa = (EditText) activity.findViewById(R.id.editText_Nazwa);
        this.editText_IMEI = (EditText) activity.findViewById(R.id.editText_IMEI);
        this.editText_Ilosc = (EditText) activity.findViewById(R.id.editText_Ilosc);
    }



    boolean saveUPC(){

        Gson gson = new GsonBuilder().setLenient().create();

        // Retrofit - obsługuje zapytania HTTP
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ServerApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        ServerApi serverApi =
                retrofit.create(ServerApi.class);

        ProductPOJO product = new ProductPOJO();
        product.setName(editText_Nazwa.getText().toString());
        product.setUPC(editText_UPC.getText().toString());

        Call<ProductPOJO> call = serverApi.createProduct(product);
        call.enqueue(new Callback<ProductPOJO>() {
            @Override
            public void onResponse(Call<ProductPOJO> call, Response<ProductPOJO> response) {

            }

            @Override
            public void onFailure(Call<ProductPOJO> call, Throwable t) {

            }});

        return true;
    }

    boolean updateUPC(){
        Gson gson = new GsonBuilder().setLenient().create();

        // Retrofit - obsługuje zapytania HTTP
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ServerApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        ServerApi serverApi =
                retrofit.create(ServerApi.class);

        ProductPOJO product = new ProductPOJO();
        product.setName(editText_Nazwa.getText().toString());
        product.setUPC(editText_UPC.getText().toString());

        Call<ProductPOJO> call = serverApi.updateProduct(product.getUPC(), product);
        call.enqueue(new Callback<ProductPOJO>() {
            @Override
            public void onResponse(Call<ProductPOJO> call, Response<ProductPOJO> response) {

            }

            @Override
            public void onFailure(Call<ProductPOJO> call, Throwable t) {

            }});
        return true;
    }

    boolean deleteUPC(){
        Gson gson = new GsonBuilder().setLenient().create();

        // Retrofit - obsługuje zapytania HTTP
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ServerApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        ServerApi serverApi =
                retrofit.create(ServerApi.class);

        ProductPOJO product = new ProductPOJO();
        product.setName(editText_Nazwa.getText().toString());
        product.setUPC(editText_UPC.getText().toString());

        Call<Void> call = serverApi.deleteProduct(product.getUPC());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }});


        return true;
    }

    boolean addProduct(StockProductPOJO product) {

        Gson gson = new GsonBuilder().setLenient().create();

        // Retrofit - obsługuje zapytania HTTP
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ServerApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        ServerApi serverApi =
                retrofit.create(ServerApi.class);

        //StockProductPOJO product = new StockProductPOJO();
        //product.setName(editText_Nazwa.getText().toString());
        //product.setUPC(editText_UPC.getText().toString());
        //product.setIMEI(editText_IMEI.getText().toString().split(",")[0]);
        //product.setPrice(232.08);

        //Integer.parseInt(myEditText.getText().toString())


        Call<StockProductPOJO> call = serverApi.addToStock(product);
        call.enqueue(new Callback<StockProductPOJO>() {
            @Override
            public void onResponse(Call<StockProductPOJO> call, Response<StockProductPOJO> response) {

            }

            @Override
            public void onFailure(Call<StockProductPOJO> call, Throwable t) {

            }
        });

        return true;
    }
}
