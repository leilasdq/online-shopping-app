package com.example.maktabproj.View.activity;

import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.maktabproj.R;
import com.example.maktabproj.View.fragment.LoginUserFragment;

public class LoginUserActivity extends SingleFragmentActivity {

    @Override
    public Fragment createFragment() {
        return LoginUserFragment.newInstance();
    }

    public static Intent newIntent(Context context){
        return new Intent(context, LoginUserActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_fragment);
    }
}
