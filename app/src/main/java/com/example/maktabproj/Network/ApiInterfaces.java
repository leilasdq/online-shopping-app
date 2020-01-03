package com.example.maktabproj.Network;

import com.example.maktabproj.Model.CategoriesItem;
import com.example.maktabproj.Model.Response;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ApiInterfaces {

    @GET("products")
    Call<List<Response>> getProducts(@QueryMap Map<String, String> queries);

    @GET("products/categories")
    Call<List<CategoriesItem>> getProductCategory(@QueryMap Map<String, String> queries);

    @GET("products")
    Call<List<Response>> getOrderedProducts(@QueryMap Map<String, String> queries);

    @GET("products/{id}")
    Call<Response> getSpecificProduct(@Path ("id") String productId,
                                      @QueryMap Map<String, String> queries);

    @GET("products/categories")
    Call<List<CategoriesItem>> getSubCategories(@QueryMap Map<String, String> queries,
                                          @Query("parent") String id);
}
