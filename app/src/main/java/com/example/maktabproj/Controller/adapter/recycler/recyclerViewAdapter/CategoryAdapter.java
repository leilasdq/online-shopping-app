package com.example.maktabproj.Controller.adapter.recycler.recyclerViewAdapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maktabproj.Controller.activity.SubCategoryActivity;
import com.example.maktabproj.Model.CategoriesItem;
import com.example.maktabproj.R;
import com.example.maktabproj.databinding.CategoryListItemsBinding;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>{

    private List<CategoriesItem> mCategoriesItems;
    private Context mContext;

    public CategoryAdapter(List<CategoriesItem> categoriesItems, Context context) {
        mCategoriesItems = categoriesItems;
        mContext = context;
    }

    public void setList(List<CategoriesItem> categoriesItems) {
        mCategoriesItems = categoriesItems;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        CategoryListItemsBinding binding = CategoryListItemsBinding.inflate(inflater, parent, false);
        return new CategoryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        holder.bind(mCategoriesItems.get(position));
    }

    @Override
    public int getItemCount() {
        return mCategoriesItems.size();
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder {
        private CategoryListItemsBinding mBinding;
        private CategoriesItem mCategoriesItem;

        public CategoryViewHolder(@NonNull CategoryListItemsBinding binding) {
            super(binding.getRoot());
            mBinding = binding;

            mBinding.categoryBtn.setOnClickListener(v -> {
                Intent intent = SubCategoryActivity.newIntent(mContext, mCategoriesItem.getId());
                mContext.startActivity(intent);
            });
        }

        public void bind(CategoriesItem categoriesItem){
            mCategoriesItem = categoriesItem;
            mBinding.categoryBtn.setText(categoriesItem.getName());
            mBinding.categoryBtn.setBackgroundColor(mContext.getResources().getColor(R.color.categoryButtonColor));
        }
    }
}
