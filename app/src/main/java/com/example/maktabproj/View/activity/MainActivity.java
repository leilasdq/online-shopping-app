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

        initViews();
        toolbarSetup();
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
        mDrawerLayout.closeDrawer(GravityCompat.START);
        switch (id) {
            case R.id.nav_home_page:
                return true;
            case R.id.nav_categories:
                startActivity(SubCategoryActivity.newIntent(this));
                return true;
            case R.id.nav_signup:
                startActivity(CreateUserActivity.newIntent(this));
                return true;
            case R.id.nav_login:
                startActivity(LoginUserActivity.newIntent(this));
                return true;
            case R.id.nav_shopping_card:
                startActivity(BuyCardActivity.newIntent(this));
                return true;
            case R.id.nav_new:
                startActivity(ListAllProductActivity.newIntent(this, "date"));
                return true;
            case R.id.nav_popular:
                startActivity(ListAllProductActivity.newIntent(this, "Popular"));
                return true;
            case R.id.nav_most_view:
                startActivity(ListAllProductActivity.newIntent(this, "rated"));
                return true;
            case R.id.nav_setting:
                return true;
            case R.id.nav_about:
                return true;
            default:
                return false;
        }
//        if (id == submenu.getItem(0).getItemId()) {
//            startActivity(SubCategoryActivity.newIntent(MainActivity.this));
//        }
//        if (id == R.id.signup){
//            LoginUserActivity.newIntent(this);
//        }
    }
}
