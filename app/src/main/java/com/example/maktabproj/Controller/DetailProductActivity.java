package com.example.maktabproj.Controller;

import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.example.maktabproj.R;

import static com.example.maktabproj.Controller.FirstPageFragment.RESPONSE_ID_EXTRA;

public class DetailProductActivity extends SingleFragmentActivity implements DetailProductFragment.ProductId {
    int productId;

    @Override
    public Fragment createFragment() {
        return DetailProductFragment.newInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_fragment);

        productId = getIntent().getIntExtra(RESPONSE_ID_EXTRA, 0);
    }

    @Override
    public int getProductId() {
        return productId;
    }
}
