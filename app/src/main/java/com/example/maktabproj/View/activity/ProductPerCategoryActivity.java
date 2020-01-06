package com.example.maktabproj.View.activity;

import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.maktabproj.View.fragment.ProductPerSubsFragment;
import com.example.maktabproj.R;

public class ProductPerCategoryActivity extends SingleFragmentActivity {

    public static final String EXTRA_CATEGORY_ID = "com.example.maktabproj.Category id";

    public static Intent newIntent (Context context, int id){
        Intent intent = new Intent(context, ProductPerCategoryActivity.class);
        intent.putExtra(EXTRA_CATEGORY_ID, id);
        return intent;
    }

    @Override
    public Fragment createFragment() {
        return ProductPerSubsFragment.newInstance(getIntent().getIntExtra(EXTRA_CATEGORY_ID, 0));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_fragment);
    }
}
