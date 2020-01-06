package com.example.maktabproj.Controller.activity;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;

import com.example.maktabproj.Controller.fragment.FirstPageFragment;
import com.example.maktabproj.Model.CategoriesItem;
import com.example.maktabproj.Network.FetchItems;
import com.example.maktabproj.R;
import com.example.maktabproj.viewmodel.FirstPageViewModel;
import com.google.android.material.navigation.NavigationView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends SingleFragmentActivity {
    Toolbar mToolbar;
    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;

    private FirstPageViewModel mViewModel;

    @Override
    public Fragment createFragment() {
        return FirstPageFragment.newInstance();
    }

    public static Intent newIntent(Context context){
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_layout);

        mViewModel = ViewModelProviders.of(this).get(FirstPageViewModel.class);
        mViewModel.getCategoryLiveData().observe(this, this::createDrawerMenu);

        initViews();
        toolbarSetup();
    }

    private void createDrawerMenu(List<CategoriesItem> items) {

        final Menu menu = mNavigationView.getMenu();
        final Menu submenu = menu.addSubMenu("دسته بندی محصولات");

        new Handler().postDelayed(() -> {
            for (int i = 0; i < items.size() ; i++) {
                if (items.get(i).getName().equalsIgnoreCase("فروش ویژه")) continue;
                submenu.add(items.get(i).getName());
            }

            mNavigationView.invalidate();
        }, 2000);
    }

    private void toolbarSetup() {
        setSupportActionBar(mToolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.start_drawer, R.string.end_drawer);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();
    }

    private void initViews() {
        mToolbar = findViewById(R.id.toolbar);
        mDrawerLayout = findViewById(R.id.nav_draw);
        mNavigationView = findViewById(R.id.nav_view);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START))  mDrawerLayout.closeDrawer(GravityCompat.START);
        else super.onBackPressed();
    }

}
