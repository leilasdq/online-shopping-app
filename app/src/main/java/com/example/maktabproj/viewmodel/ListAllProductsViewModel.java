package com.example.maktabproj.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.maktabproj.Model.Response;
import com.example.maktabproj.Network.FetchItems;

import java.util.List;

public class ListAllProductsViewModel extends AndroidViewModel {
    private LiveData<List<Response>> mAllProductsLiveData;

    public ListAllProductsViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Response>> getAllProductsLiveData() {
        return mAllProductsLiveData;
    }

    public void sendRequest(int pageNumber, String type){
        switch (type){
            case "date":
                mAllProductsLiveData = FetchItems.getInstance().getAllProductsPerPage(pageNumber);
                break;
            case "popular":
                mAllProductsLiveData = FetchItems.getInstance().getPopularProductsPerPage(pageNumber);
                break;
            case "rated":
                mAllProductsLiveData = FetchItems.getInstance().getRatedProductsPerPage(pageNumber);
                break;
        }
    }
}
