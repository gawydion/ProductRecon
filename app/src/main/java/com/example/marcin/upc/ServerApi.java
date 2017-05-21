package com.example.marcin.upc;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Marcin on 21.03.2017.
 */

public interface ServerApi {

    /*
     * dodaj BASE_URL
     */

    public static final String BASE_URL = "pusty!!";

    //oganięcie BC_tab - tabela z kodami
    @POST("BC_tab/")
    Call<ProductPOJO> createProduct(@Body ProductPOJO product);

    @POST("BC_tab/{upc}/PUT/")
    Call<ProductPOJO> updateProduct(@Path("upc") String upc, @Body ProductPOJO product);

    @GET("BC_tab/{upc}/DELETE/")
    Call<Void> deleteProduct(@Path("upc") String upc);

    //ogarnięcie S_tab - tabela ze stanem magazynowym

    @POST("S_tab/")
    Call<StockProductPOJO> addToStock(@Body StockProductPOJO product);

}