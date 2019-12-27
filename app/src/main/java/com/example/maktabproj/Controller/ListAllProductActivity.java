package com.example.maktabproj.Controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.maktabproj.R;

import static com.example.maktabproj.Controller.NewItemsFragment.EXTRA_SEND_PRODUCT_TYPE;

public class ListAllProductActivity extends SingleFragmentActivity {

    public static final String EXTRA_GET_TYPE_OF_PRODUCT = "com.example.maktabproj.get Type of product";
    String productType;

    @Override
    public Fragment createFragment() {
        return ListAllProductFragment.newInstance(getIntent().getStringExtra(EXTRA_SEND_PRODUCT_TYPE));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_fragment);

        productType = getIntent().getStringExtra(EXTRA_SEND_PRODUCT_TYPE);
    }
}
