package com.example.maktabproj.View.adapter.recycler.recyclerViewAdapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maktabproj.View.activity.ProductPerCategoryActivity;
import com.example.maktabproj.Model.Category;
import com.example.maktabproj.R;
import com.example.maktabproj.databinding.SubCategoryListItemBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.SubCategoryViewHolder>{
    private List<Category> mCategoriesItems = new ArrayList<>();
    private Context mContext;

    public SubCategoryAdapter(List<Category> categoriesItems, Context context) {
        mCategoriesItems = categoriesItems;
        mContext = context;
    }

    public void setCategoriesItems(List<Category> categoriesItems) {
        mCategoriesItems = categoriesItems;
    }

    @NonNull
    @Override
    public SubCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        SubCategoryListItemBinding binding = SubCategoryListItemBinding.inflate(inflater, parent, false);
        return new SubCategoryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SubCategoryViewHolder holder, int position) {
        holder.bind(mCategoriesItems.get(position));
    }

    @Override
    public int getItemCount() {
        return mCategoriesItems.size();
    }

    class SubCategoryViewHolder extends RecyclerView.ViewHolder{
        private Category mCategoriesItem;
        private SubCategoryListItemBinding mBinding;

        public SubCategoryViewHolder(@NonNull SubCategoryListItemBinding binding) {
            super(binding.getRoot());
            mBinding = binding;

            itemView.setOnClickListener(v -> {
                Intent intent = ProductPerCategoryActivity.newIntent(mContext, mCategoriesItem.getId());
                mContext.startActivity(intent);
            });
        }

        private void bind(Category categoriesItem){
            mCategoriesItem = categoriesItem;
            mBinding.categoryName.setText(mCategoriesItem.getName());
            Picasso.with(mContext).load(mCategoriesItem.getImage().getSrc())
                    .placeholder(R.drawable.image_loading)
                    .into(mBinding.categoryImage);
        }
    }
}
