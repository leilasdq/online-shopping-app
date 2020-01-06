package com.example.maktabproj.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.maktabproj.Model.CategoriesItem;
import com.example.maktabproj.Model.Response;
import com.example.maktabproj.Network.FetchItems;

import java.util.List;

public class FirstPageViewModel extends AndroidViewModel {
    private FetchItems mFetchItems = FetchItems.getInstance();
    private LiveData<List<Response>> allProductsLiveData;
    private LiveData<List<Response>> popularProductsLiveData;
    private LiveData<List<Response>> ratedProductsLiveData;
    private LiveData<List<CategoriesItem>> categoryLiveData;

    public FirstPageViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Response>> getAllProductsLiveData() {
        allProductsLiveData = mFetchItems.getAllProducts();
        return allProductsLiveData;
    }

    public LiveData<List<Response>> getPopularProductsLiveData() {
        popularProductsLiveData = mFetchItems.getPopularProducts();
        return popularProductsLiveData;
    }

    public LiveData<List<Response>> getRatedProductsLiveData() {
        ratedProductsLiveData = mFetchItems.getRatedProducts();
        return ratedProductsLiveData;
    }

    public LiveData<List<CategoriesItem>> getCategoryLiveData() {
        categoryLiveData = mFetchItems.getParentCategories();
        return categoryLiveData;
    }
}
