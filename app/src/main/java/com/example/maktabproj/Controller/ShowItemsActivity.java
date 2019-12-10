package com.example.maktabproj.Controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.example.maktabproj.R;

import static com.example.maktabproj.Controller.NewItemsFragment.RESPONSE_ID_EXTRA;

public class ShowItemsActivity extends SingleFragmentActivity implements ShowItemsFragment.ProductId {
    int productId;

    @Override
    public Fragment createFragment() {
        return ShowItemsFragment.newInstance();
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
