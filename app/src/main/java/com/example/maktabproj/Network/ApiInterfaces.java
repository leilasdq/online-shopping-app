package com.example.maktabproj.Network;

import com.example.maktabproj.Model.Response;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface ApiInterfaces {
    @GET("products")
    Call<List<Response>> getProducts(@QueryMap Map<String, String> queries);
}
