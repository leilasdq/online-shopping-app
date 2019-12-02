package com.example.maktabproj.Network;

import com.example.maktabproj.Model.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FetchItems {
    public static final String CONSUMER_KEY_VALUE = "ck_7276cad9e036d1139227ac59adff235d4215fd2f";
    public static final String CONSUMER_SECRET_VALUE = "cs_7c867db7cd424fe32614b92804eb4ffbe5c9c05f";
    public static final String BASE_URL = "https://woocommerce.maktabsharif.ir/wp-json/wc/v3/";

    private Retrofit mRetrofit;
    private ApiInterfaces mApiInterfaces;
    private Map<String, String> mQueries;

    public FetchItems() {
        mQueries = new HashMap<>();
        mQueries.put("consumer_key", CONSUMER_KEY_VALUE);
        mQueries.put("consumer_secret", CONSUMER_SECRET_VALUE);

        mRetrofit =  new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mApiInterfaces = mRetrofit.create(ApiInterfaces.class);
    }

    public List<Response> getAllProducts() throws IOException {
        Call<List<Response>> call = mApiInterfaces.getProducts(mQueries);

        return call.execute().body();
    }

}
