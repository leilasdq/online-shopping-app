package com.example.maktabproj.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.maktabproj.Model.Response;
import com.example.maktabproj.Network.FetchItems;

import java.util.List;

public class SearchViewModel extends AndroidViewModel {
    private LiveData<List<Response>> mSearchLiveData;

    public SearchViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Response>> getSearchList(String query) {
        mSearchLiveData = FetchItems.getInstance().getSearchedProducrs(query);
        return mSearchLiveData;
    }
}
