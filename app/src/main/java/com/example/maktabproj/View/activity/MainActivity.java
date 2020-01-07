package com.example.maktabproj.View.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;

import com.example.maktabproj.View.fragment.FirstPageFragment;
import com.example.maktabproj.Model.CategoriesItem;
import com.example.maktabproj.R;
import com.example.maktabproj.viewmodel.FirstPageViewModel;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class MainActivity extends SingleFragmentActivity implements NavigationView.OnNavigationItemSelectedListener {
    Toolbar mToolbar;
    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;

    private FirstPageViewModel mViewModel;
    private SubMenu submenu;

    @Override
    public Fragment createFragment() {
        return FirstPageFragment.newInstance();
    }

    public static Intent newIntent(Context context) {
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
        submenu = menu.addSubMenu("دسته بندی محصولات");
        submenu.setIcon(R.drawable.ic_menu_black_24dp);


        new Handler().postDelayed(() -> {
            for (int i = 0; i < items.size(); i++) {
                if (items.get(i).getName().equalsIgnoreCase("فروش ویژه")) continue;
                submenu.add(i, Menu.FIRST + i, Menu.FIRST, items.get(i).getName());
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
        mNavigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START))
            mDrawerLayout.closeDrawer(GravityCompat.START);
        else super.onBackPressed();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == submenu.getItem(0).getItemId()) {
            startActivity(SubCategoryActivity.newIntent(MainActivity.this));
        }

        return false;
    }
}
