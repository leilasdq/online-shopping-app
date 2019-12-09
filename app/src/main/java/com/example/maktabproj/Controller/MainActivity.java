package com.example.maktabproj.Controller;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;

import com.example.maktabproj.Model.CategoriesItem;
import com.example.maktabproj.R;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends SingleFragmentActivity {
    Toolbar mToolbar;
    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;

    NewItemsFragment newItemsFragment;
    private Fragment selectedFragment;
    private List<CategoriesItem> categories;

    @Override
    public Fragment createFragment() {
        return NewItemsFragment.newInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_layout);

        initViews();
        toolbarSetup();
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
        }, 20000);
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

//        newItemsFragment = NewItemsFragment.newInstance();
//        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, newItemsFragment).commit();

        categories = new ArrayList<>();
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
