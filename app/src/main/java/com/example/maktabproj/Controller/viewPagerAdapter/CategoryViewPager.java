package com.example.maktabproj.Controller.viewPagerAdapter;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.maktabproj.Controller.SubCategoryFragment;
import com.example.maktabproj.Model.CategoriesItem;

import java.util.List;

public class CategoryViewPager extends FragmentPagerAdapter {
    private int categoryId;
    private List<CategoriesItem> mCategoriesItems;

    public CategoryViewPager(FragmentManager fm, List<CategoriesItem> categoriesItems) {
        super(fm);
        mCategoriesItems = categoriesItems;
    }
    public void setCategoriesItems(List<CategoriesItem> categoriesItems) {
        mCategoriesItems = categoriesItems;
    }

    @Override
    public Fragment getItem(int position) {
        return SubCategoryFragment.newInstance(mCategoriesItems.get(position).getId());
    }

    @Override
    public int getCount() {
        return mCategoriesItems.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mCategoriesItems.get(position).getName();
    }

}
