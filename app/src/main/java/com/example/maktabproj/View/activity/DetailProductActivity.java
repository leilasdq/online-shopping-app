package com.example.maktabproj.View.activity;

import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.maktabproj.View.fragment.DetailProductFragment;
import com.example.maktabproj.R;

public class DetailProductActivity extends SingleFragmentActivity {
    public static final String EXTRA_PRODUCT_ID = "com.example.maktabproj.product id";

    @Override
    public Fragment createFragment() {
        return DetailProductFragment.newInstance(getIntent().getIntExtra(EXTRA_PRODUCT_ID, 0));
    }

    public static Intent newIntent(Context context, int productId){
        Intent intent = new Intent(context, DetailProductActivity.class);
        intent.putExtra(EXTRA_PRODUCT_ID, productId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_fragment);
    }
}
