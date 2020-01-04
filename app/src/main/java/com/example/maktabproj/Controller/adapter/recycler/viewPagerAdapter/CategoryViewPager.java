package com.example.maktabproj.Controller.adapter.recycler.viewPagerAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.maktabproj.Controller.fragment.SubCategoryFragment;
import com.example.maktabproj.Model.CategoriesItem;

import java.util.List;

public class CategoryViewPager extends FragmentStateAdapter {
    private List<CategoriesItem> mCategoriesItems;

    public CategoryViewPager(@NonNull FragmentActivity fragmentActivity, List<CategoriesItem> categoriesItems) {
        super(fragmentActivity);

        mCategoriesItems = categoriesItems;
    }

    public List<CategoriesItem> getCategoriesItems() {
        return mCategoriesItems;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return SubCategoryFragment.newInstance(mCategoriesItems.get(position).getId());
    }

    @Override
    public int getItemCount() {
        return mCategoriesItems.size();
    }

//    public CategoryViewPager(FragmentManager fm, List<CategoriesItem> categoriesItems) {
//        super(fm);
//        mCategoriesItems = categoriesItems;
//    }
//    public void setCategoriesItems(List<CategoriesItem> categoriesItems) {
//        mCategoriesItems = categoriesItems;
//    }
//
//    @Override
//    public Fragment getItem(int position) {
//        return SubCategoryFragment.newInstance(mCategoriesItems.get(position).getId());
//    }
//
//    @Override
//    public int getCount() {
//        return mCategoriesItems.size();
//    }
//
//    @Nullable
//    @Override
//    public CharSequence getPageTitle(int position) {
//        return mCategoriesItems.get(position).getName();
//    }

}
