package com.example.maktabproj.Network;

import android.content.Context;

import com.example.maktabproj.Model.CategoriesItem;
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
    public static final String ORDERBY = "orderby";

    private Retrofit mRetrofit;
    private ApiInterfaces mApiInterfaces;
    private Map<String, String> mQueries;

    private static FetchItems ourInstance = null;

    public static FetchItems getInstance() {
        if (ourInstance == null){
            ourInstance = new FetchItems();
        }
        return ourInstance;
    }

    private FetchItems() {
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

    public List<CategoriesItem> getCategories() throws IOException {
        if (mQueries.containsKey("orderby")){
            mQueries.remove("orderby");
        }
        Call<List<CategoriesItem>> call = mApiInterfaces.getProductCategory(mQueries);

        return call.execute().body();
    }

    public List<Response> getPopularProducts() throws IOException {
        mQueries.put(ORDERBY, "popularity");
        Call<List<Response>> call = mApiInterfaces.getOrderedProducts(mQueries);

        return call.execute().body();
    }

    public List<Response> getRatedProducts() throws IOException {
        mQueries.put(ORDERBY, "rating");
        Call<List<Response>> call = mApiInterfaces.getOrderedProducts(mQueries);

        return call.execute().body();
    }

    public Response getSpecificProduct(int id) throws IOException {
        mQueries.remove(ORDERBY);
        Call<Response> call = mApiInterfaces.getSpecificProduct(String.valueOf(id), mQueries);

        return call.execute().body();
    }

    public List<Response> getAllProductsPerPage(int pageNumber) throws IOException {
        mQueries.remove(ORDERBY);
        mQueries.put("page", String.valueOf(pageNumber));
        Call<List<Response>> call = mApiInterfaces.getProducts(mQueries);

        return call.execute().body();
    }

    public List<Response> getPopularProductsPerPage(int pageNumber) throws IOException {
        mQueries.put(ORDERBY, "popularity");
        mQueries.put("page", String.valueOf(pageNumber));
        Call<List<Response>> call = mApiInterfaces.getOrderedProducts(mQueries);

        return call.execute().body();
    }

    public List<Response> getRatedProductsPerPage(int pageNumber) throws IOException {
        mQueries.put(ORDERBY, "rating");
        mQueries.put("page", String.valueOf(pageNumber));
        Call<List<Response>> call = mApiInterfaces.getOrderedProducts(mQueries);

        return call.execute().body();
    }

    public List<CategoriesItem> getSubCategory(int id) throws IOException {
        Call<List<CategoriesItem>> call = mApiInterfaces.getSubCategories(mQueries, String.valueOf(id));
        return call.execute().body();
    }
}
