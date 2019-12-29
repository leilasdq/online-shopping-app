package com.example.maktabproj.Controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.maktabproj.Controller.viewPagerAdapter.CategoryViewPager;
import com.example.maktabproj.Model.CategoriesItem;
import com.example.maktabproj.Network.FetchItems;
import com.example.maktabproj.R;
import com.google.android.material.tabs.TabLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SubCategoryActivity extends AppCompatActivity {
    public static final String EXTRA_CATEGORY_ID = "com.example.maktabproj.category id";
    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private List<CategoriesItem> mCategoriesItems = new ArrayList<>();
    private FetchItems mFetchItems = FetchItems.getInstance();
    private CategoryViewPager mPagerAdapter;
    private int id;

    public static Intent newIntent(Context context, int categoryId){
        Intent intent = new Intent(context, SubCategoryActivity.class);
        intent.putExtra(EXTRA_CATEGORY_ID, categoryId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category);

        initUi();
        setUpToolbar();
        setUpViewPager();
        id = getIntent().getIntExtra(EXTRA_CATEGORY_ID, 0);

        GetCategoryAsync async = new GetCategoryAsync();
        async.execute();
    }

    private void setUpViewPager() {
        mViewPager.setOffscreenPageLimit(1);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void initUi() {
        mTabLayout = findViewById(R.id.tab);
        mViewPager = findViewById(R.id.view_pager);
    }

    private void setUpToolbar() {
        mToolbar = findViewById(R.id.sub_category_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(getString(R.string.categories));
    }

    private class GetCategoryAsync extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                mCategoriesItems = mFetchItems.getCategories();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            setPagerAdapterTabs();
        }
    }

    private void setPagerAdapter(){
        if (mPagerAdapter==null){
            mPagerAdapter = new CategoryViewPager(getSupportFragmentManager(), mCategoriesItems);
            mViewPager.setAdapter(mPagerAdapter);
            mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        } else {
            mPagerAdapter.setCategoriesItems(mCategoriesItems);
            mPagerAdapter.notifyDataSetChanged();
        }
    }

    private void setPagerAdapterTabs(){
        for (int i = 0; i < mCategoriesItems.size() ; i++) {
            mTabLayout.addTab(mTabLayout.newTab().setText(mCategoriesItems.get(i).getName()));
        }

        if (mTabLayout.getTabCount() < 3) mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        else mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        setPagerAdapter();
    }
}