package com.example.maktabproj.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.maktabproj.Model.Response;
import com.example.maktabproj.Network.FetchItems;

import java.util.List;

public class ProductsPerSubsViewModel extends AndroidViewModel {
    private LiveData<List<Response>> responseLiveData;

    public ProductsPerSubsViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Response>> getResponseLiveData(int id) {
        responseLiveData = FetchItems.getInstance().getProductPerCategory(id);
        return responseLiveData;
    }
}
