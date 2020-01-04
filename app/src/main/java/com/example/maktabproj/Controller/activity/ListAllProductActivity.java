package com.example.maktabproj.Controller.activity;

import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.maktabproj.Controller.fragment.ListAllProductFragment;
import com.example.maktabproj.R;

import static com.example.maktabproj.Controller.fragment.FirstPageFragment.EXTRA_SEND_PRODUCT_TYPE;

public class ListAllProductActivity extends SingleFragmentActivity {

    public static final String EXTRA_TYPE_OF_DATA = "type of datas";
    String productType;

    @Override
    public Fragment createFragment() {
        return ListAllProductFragment.newInstance(getIntent().getStringExtra(EXTRA_TYPE_OF_DATA));
    }

    public static Intent newIntent(Context context, String type){
        Intent intent = new Intent(context, ListAllProductActivity.class);
        intent.putExtra(EXTRA_TYPE_OF_DATA, type);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_fragment);

        productType = getIntent().getStringExtra(EXTRA_SEND_PRODUCT_TYPE);
    }
}
