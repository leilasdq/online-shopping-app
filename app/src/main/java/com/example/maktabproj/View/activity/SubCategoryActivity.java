package com.example.maktabproj.View.activity;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.maktabproj.View.adapter.recycler.viewPagerAdapter.CategoryViewPager;
import com.example.maktabproj.Model.CategoriesItem;
import com.example.maktabproj.R;
import com.example.maktabproj.viewmodel.FirstPageViewModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class SubCategoryActivity extends NetworkCheckerActivity {
    public static final String EXTRA_CATEGORY_ID = "com.example.maktabproj.category id";
    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager2 mViewPager;

    private FirstPageViewModel mViewModel;
    private CategoryViewPager mPagerAdapter;

    public static Intent newIntent(Context context, int categoryId){
        Intent intent = new Intent(context, SubCategoryActivity.class);
        intent.putExtra(EXTRA_CATEGORY_ID, categoryId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category);

        mViewModel = ViewModelProviders.of(this).get(FirstPageViewModel.class);
        mViewModel.getCategoryLiveData().observe(this, this::setPagerAdapterTabs);

        initUi();
        setUpToolbar();
        setUpViewPager();
    }

    private void setUpViewPager() {
        mViewPager.setOffscreenPageLimit(1);
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

    private void setPagerAdapter(List<CategoriesItem> items){
        if (mPagerAdapter==null){
            mPagerAdapter = new CategoryViewPager(this, items);

            mViewPager.setAdapter(mPagerAdapter);
            TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(
                    mTabLayout, mViewPager, (tab, position) -> {
                        for (int i = 0; i < items.size() ; i++) {
                            tab.setText(items.get(position).getName());
                        }
                    });
            tabLayoutMediator.attach();
        }
    }

    private void setPagerAdapterTabs(List<CategoriesItem> items){
        List<CategoriesItem> categoriesItems = new ArrayList<>();
        for (int i = 0; i < items.size() ; i++) {
            if (items.get(i).getName().equalsIgnoreCase("فروش ویژه")) continue;
            categoriesItems.add(items.get(i));
        }

        for (int i = 0; i < categoriesItems.size() ; i++) {
            mTabLayout.addTab(mTabLayout.newTab().setText(categoriesItems.get(i).getName()));
        }

        if (mTabLayout.getTabCount() < 3) mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        else mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        setPagerAdapter(categoriesItems);
    }
}
