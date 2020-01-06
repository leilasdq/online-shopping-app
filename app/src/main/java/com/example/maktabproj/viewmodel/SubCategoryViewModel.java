package com.example.maktabproj.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.maktabproj.Model.Category;
import com.example.maktabproj.Network.FetchItems;

import java.util.List;

public class SubCategoryViewModel extends AndroidViewModel {
    private FetchItems mFetchItems = FetchItems.getInstance();
    private LiveData<List<Category>> mCategoryLiveData;

    public SubCategoryViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Category>> getCategoryLiveData(int id) {
        mCategoryLiveData = mFetchItems.getSubCategory(id);
        return mCategoryLiveData;
    }
}
