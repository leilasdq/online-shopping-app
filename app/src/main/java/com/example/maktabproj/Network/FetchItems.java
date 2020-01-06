package com.example.maktabproj.Network;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.maktabproj.Model.CategoriesItem;
import com.example.maktabproj.Model.Category;
import com.example.maktabproj.Model.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FetchItems {
    public static final String CONSUMER_KEY_VALUE = "ck_7276cad9e036d1139227ac59adff235d4215fd2f";
    public static final String CONSUMER_SECRET_VALUE = "cs_7c867db7cd424fe32614b92804eb4ffbe5c9c05f";
    public static final String BASE_URL = "https://woocommerce.maktabsharif.ir/wp-json/wc/v3/";
    private static final String TAG = "FetchItems";
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

    public MutableLiveData<List<Response>> getAllProducts() {
        Call<List<Response>> call = mApiInterfaces.getProducts(mQueries);
        MutableLiveData<List<Response>> responceMutableLiveData = new MutableLiveData<>();

        call.enqueue(new Callback<List<Response>>() {
            @Override
            public void onResponse(Call<List<Response>> call, retrofit2.Response<List<Response>> response) {
                if (response.isSuccessful()){
                    List<Response> responseList = response.body();
                    responceMutableLiveData.setValue(responseList);
                }
            }

            @Override
            public void onFailure(Call<List<Response>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
        return responceMutableLiveData;
    }

    public MutableLiveData<List<CategoriesItem>> getParentCategories() {
        Map<String, String> copy = new HashMap<>();
        copy.putAll(mQueries);
        copy.put("parent", String.valueOf(0));
        copy.put("display", "default");
        Call<List<CategoriesItem>> call = mApiInterfaces.getProductCategory(copy);
        MutableLiveData<List<CategoriesItem>> categoryMutableLiveData = new MutableLiveData<>();

        call.enqueue(new Callback<List<CategoriesItem>>() {
            @Override
            public void onResponse(Call<List<CategoriesItem>> call, retrofit2.Response<List<CategoriesItem>> response) {
                if (response.isSuccessful()){
                    List<CategoriesItem> responseList = response.body();
                    categoryMutableLiveData.setValue(responseList);
                }
            }

            @Override
            public void onFailure(Call<List<CategoriesItem>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
        return categoryMutableLiveData;
    }

    public MutableLiveData<List<Response>> getPopularProducts() {
        Map<String, String> copy = new HashMap<>();
        copy.putAll(mQueries);
        copy.put(ORDERBY, "popularity");
        Call<List<Response>> call = mApiInterfaces.getOrderedProducts(copy);
        MutableLiveData<List<Response>> responseMutableLiveData = new MutableLiveData<>();

        call.enqueue(new Callback<List<Response>>() {
            @Override
            public void onResponse(Call<List<Response>> call, retrofit2.Response<List<Response>> response) {
                if (response.isSuccessful()){
                    List<Response> responseList = response.body();
                    responseMutableLiveData.setValue(responseList);
                }
            }

            @Override
            public void onFailure(Call<List<Response>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
        return responseMutableLiveData;
    }

    public MutableLiveData<List<Response>> getRatedProducts() {
        Map<String, String> copy = new HashMap<>();
        copy.putAll(mQueries);
        copy.put(ORDERBY, "rating");
        Call<List<Response>> call = mApiInterfaces.getOrderedProducts(copy);
        MutableLiveData<List<Response>> responseMutableLiveData = new MutableLiveData<>();

        call.enqueue(new Callback<List<Response>>() {
            @Override
            public void onResponse(Call<List<Response>> call, retrofit2.Response<List<Response>> response) {
                if (response.isSuccessful()){
                    List<Response> responseList = response.body();
                    responseMutableLiveData.setValue(responseList);
                }
            }

            @Override
            public void onFailure(Call<List<Response>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
        return responseMutableLiveData;
    }

    public Response getSpecificProduct(int id) throws IOException {
        Call<Response> call = mApiInterfaces.getSpecificProduct(String.valueOf(id), mQueries);

        return call.execute().body();
    }

    public List<Response> getAllProductsPerPage(int pageNumber) throws IOException {
        Map<String, String> copy = new HashMap<>();
        copy.putAll(mQueries);
        copy.put("page", String.valueOf(pageNumber));
        Call<List<Response>> call = mApiInterfaces.getProducts(copy);

        return call.execute().body();
    }

    public List<Response> getPopularProductsPerPage(int pageNumber) throws IOException {
        Map<String, String> copy = new HashMap<>();
        copy.putAll(mQueries);
        copy.put(ORDERBY, "popularity");
        copy.put("page", String.valueOf(pageNumber));
        Call<List<Response>> call = mApiInterfaces.getOrderedProducts(copy);

        return call.execute().body();
    }

    public List<Response> getRatedProductsPerPage(int pageNumber) throws IOException {
        Map<String, String> copy = new HashMap<>();
        copy.putAll(mQueries);
        copy.put(ORDERBY, "rating");
        copy.put("page", String.valueOf(pageNumber));
        Call<List<Response>> call = mApiInterfaces.getOrderedProducts(copy);

        return call.execute().body();
    }

    public List<Category> getSubCategory(int id) throws IOException {
        Map<String, String> copy = new HashMap<>();
        copy.putAll(mQueries);
        copy.put("display", "subcategories");

        Call<List<Category>> call = mApiInterfaces.getSubCategories(copy, String.valueOf(id));

        return call.execute().body();
    }

    public List<Response> getProductPerCategory(int id) throws IOException {
        Map<String, String> copy = new HashMap<>();
        copy.putAll(mQueries);
        copy.put("category", String.valueOf(id));

        Call<List<Response>> call = mApiInterfaces.getProductsPerCategories(copy);

        return call.execute().body();
    }
}
