package com.example.maktabproj.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.maktabproj.Model.Response;
import com.example.maktabproj.Network.FetchItems;

public class DetailViewModel extends AndroidViewModel {
    private FetchItems mFetchItems = FetchItems.getInstance();
    private LiveData<Response> mResponseLiveData;

    public DetailViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Response> getResponseLiveData(int id) {
        mResponseLiveData = mFetchItems.getSpecificProduct(id);
        return mResponseLiveData;
    }
}
