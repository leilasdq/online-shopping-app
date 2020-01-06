package com.example.maktabproj.View.activity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.example.maktabproj.R;

public abstract class SingleFragmentActivity extends NetworkCheckerActivity {

        public abstract Fragment createFragment();

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_single_fragment);

            FragmentManager fragmentManager = getSupportFragmentManager();
            Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container);
            if (fragment == null)
                fragmentManager
                        .beginTransaction()
                        .add(R.id.fragment_container, createFragment())
                        .commit();
        }
}
