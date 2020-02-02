package com.example.maktabproj.View.activity;

import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.maktabproj.R;
import com.example.maktabproj.View.fragment.CreateUserFragment;

public class CreateUserActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context context){
        return new Intent(context, CreateUserActivity.class);
    }

    @Override
    public Fragment createFragment() {
        return CreateUserFragment.newInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_fragment);
    }
}
