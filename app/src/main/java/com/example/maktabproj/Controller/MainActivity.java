package com.example.maktabproj.Controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.maktabproj.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    Toolbar mToolbar;
    DrawerLayout mDrawerLayout;
    BottomNavigationView bottomNavigationView;
    SliderLayout sliderLayout;

    NewItemsFragment newItemsFragment;
    MostRatedFragment ratedFragment;
    MostVisitedFragment visitedFragment;
    private Fragment selectedFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_layout);

        initViews();
        sliderSetup();
        toolbarSetup();
        initListeners();
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

    private void sliderSetup() {
        HashMap<String, Integer> pics = new HashMap<>();
        pics.put("First Pic", R.drawable.one);
        pics.put("Second Pic", R.drawable.two);
        pics.put("Third Pic", R.drawable.three);
        pics.put("Fourth Pic", R.drawable.four);
        for (String name :
                pics.keySet()) {
            TextSliderView textSliderView = new TextSliderView(this);
            textSliderView.description(name)
                    .image(pics.get(name))
                    .setScaleType(BaseSliderView.ScaleType.CenterCrop);
            sliderLayout.addSlider(textSliderView);
        }
    }

    private void initViews() {
        sliderLayout = findViewById(R.id.slider);
        mToolbar = findViewById(R.id.toolbar);
        mDrawerLayout = findViewById(R.id.nav_draw);
        bottomNavigationView = findViewById(R.id.bottom_nav_view);

        newItemsFragment = NewItemsFragment.newInstance();
        ratedFragment = MostRatedFragment.newInstance();
        visitedFragment = MostVisitedFragment.newInstance();

        selectedFragment = newItemsFragment;
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START))  mDrawerLayout.closeDrawer(GravityCompat.START);
        else super.onBackPressed();
    }

}
