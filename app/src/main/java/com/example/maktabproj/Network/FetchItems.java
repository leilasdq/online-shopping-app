package com.example.maktabproj.Network;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.maktabproj.Model.CategoriesItem;
import com.example.maktabproj.Model.Category;
import com.example.maktabproj.Model.Response;

import java.util.ArrayList;
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

    private List<Response> mAllProducts = new ArrayList<>();
    private List<Response> mAllPopulars = new ArrayList<>();
    private List<Response> mAllRateds = new ArrayList<>();
    private MutableLiveData<List<Response>> allProLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Response>> allPopLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Response>> allRateLiveData = new MutableLiveData<>();

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

    public MutableLiveData<Response> getSpecificProduct(int id) {
        Call<Response> call = mApiInterfaces.getSpecificProduct(String.valueOf(id), mQueries);
        MutableLiveData<Response> responseMutableLiveData = new MutableLiveData<>();

        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if (response.isSuccessful()){
                    Response responseModel = response.body();
                    responseMutableLiveData.setValue(responseModel);
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
        return responseMutableLiveData;
    }

    public MutableLiveData<List<Response>> getAllProductsPerPage(int pageNumber) {
        Map<String, String> copy = new HashMap<>();
        copy.putAll(mQueries);
        copy.put("page", String.valueOf(pageNumber));
        Call<List<Response>> call = mApiInterfaces.getProducts(copy);

        call.enqueue(new Callback<List<Response>>() {
            @Override
            public void onResponse(Call<List<Response>> call, retrofit2.Response<List<Response>> response) {
                if (response.isSuccessful()){
                    List<Response> responseList = response.body();
                    mAllProducts.addAll(responseList);
                    allProLiveData.setValue(mAllProducts);
                }
            }

            @Override
            public void onFailure(Call<List<Response>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
        return allProLiveData;
    }

    public MutableLiveData<List<Response>> getPopularProductsPerPage(int pageNumber) {
        Map<String, String> copy = new HashMap<>();
        copy.putAll(mQueries);
        copy.put(ORDERBY, "popularity");
        copy.put("page", String.valueOf(pageNumber));
        Call<List<Response>> call = mApiInterfaces.getOrderedProducts(copy);

        call.enqueue(new Callback<List<Response>>() {
            @Override
            public void onResponse(Call<List<Response>> call, retrofit2.Response<List<Response>> response) {
                if (response.isSuccessful()){
                    List<Response> responseList = response.body();
                    mAllPopulars.addAll(responseList);
                    allPopLiveData.setValue(mAllPopulars);
                }
            }

            @Override
            public void onFailure(Call<List<Response>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
        return allPopLiveData;
    }

    public MutableLiveData<List<Response>> getRatedProductsPerPage(int pageNumber) {
        Map<String, String> copy = new HashMap<>();
        copy.putAll(mQueries);
        copy.put(ORDERBY, "rating");
        copy.put("page", String.valueOf(pageNumber));
        Call<List<Response>> call = mApiInterfaces.getOrderedProducts(copy);

        call.enqueue(new Callback<List<Response>>() {
            @Override
            public void onResponse(Call<List<Response>> call, retrofit2.Response<List<Response>> response) {
                if (response.isSuccessful()){
                    List<Response> responseList = response.body();
                    mAllRateds.addAll(responseList);
                    allRateLiveData.setValue(mAllRateds);
                }
            }

            @Override
            public void onFailure(Call<List<Response>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
        return allRateLiveData;
    }

    public MutableLiveData<List<Category>> getSubCategory(int id) {
        Map<String, String> copy = new HashMap<>();
        copy.putAll(mQueries);
        copy.put("display", "subcategories");

        Call<List<Category>> call = mApiInterfaces.getSubCategories(copy, String.valueOf(id));
        MutableLiveData<List<Category>> categoryLiveData = new MutableLiveData<>();

        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, retrofit2.Response<List<Category>> response) {
                if (response.isSuccessful()){
                    List<Category> categoryList = response.body();
                    categoryLiveData.setValue(categoryList);
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
        return categoryLiveData;
    }

    public MutableLiveData<List<Response>> getProductPerCategory(int id) {
        Map<String, String> copy = new HashMap<>();
        copy.putAll(mQueries);
        copy.put("category", String.valueOf(id));

        Call<List<Response>> call = mApiInterfaces.getProductsPerCategories(copy);
        MutableLiveData<List<Response>> responseLiveData = new MutableLiveData<>();

        call.enqueue(new Callback<List<Response>>() {
            @Override
            public void onResponse(Call<List<Response>> call, retrofit2.Response<List<Response>> response) {
                if (response.isSuccessful()){
                    List<Response> responseList = response.body();
                    responseLiveData.setValue(responseList);
                }
            }

            @Override
            public void onFailure(Call<List<Response>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
        return responseLiveData;
    }
}
