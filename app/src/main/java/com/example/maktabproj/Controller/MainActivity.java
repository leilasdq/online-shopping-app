package com.example.maktabproj.Controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.maktabproj.Model.CategoriesItem;
import com.example.maktabproj.Model.Response;
import com.example.maktabproj.Network.FetchItems;
import com.example.maktabproj.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Toolbar mToolbar;
    DrawerLayout mDrawerLayout;
    BottomNavigationView bottomNavigationView;
    NavigationView mNavigationView;

    NewItemsFragment newItemsFragment;
    MostRatedFragment ratedFragment;
    MostVisitedFragment visitedFragment;
    private Fragment selectedFragment;
    private List<CategoriesItem> categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_layout);

        initViews();
        toolbarSetup();
        initListeners();
        createDrawerMenu();

    }

    private void createDrawerMenu() {

        final Menu menu = mNavigationView.getMenu();
        final Menu submenu = menu.addSubMenu("Category");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (categories != null)
                for (int i = 0; i < categories.size() ; i++) {
                    submenu.add(categories.get(i).getName());
                }

                mNavigationView.invalidate();
            }
        }, 6000);
    }

    private void initListeners() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()){
                    case R.id.new_product:
                        selectedFragment = newItemsFragment;
                        break;
                    case R.id.most_rate:
                        selectedFragment = ratedFragment;
                        break;
                    case R.id.most_visit:
                        selectedFragment = visitedFragment;
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                return true;
            }
        });
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
        bottomNavigationView = findViewById(R.id.bottom_nav_view);
        mNavigationView = findViewById(R.id.nav_view);

        newItemsFragment = NewItemsFragment.newInstance();
        ratedFragment = MostRatedFragment.newInstance();
        visitedFragment = MostVisitedFragment.newInstance();

        categories = new ArrayList<>();

        selectedFragment = newItemsFragment;
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START))  mDrawerLayout.closeDrawer(GravityCompat.START);
        else super.onBackPressed();
    }

    public void setCategories(List<CategoriesItem> list) {
        categories = list;
    }

}
