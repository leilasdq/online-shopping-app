package com.example.maktabproj.Controller.adapter.recycler.recyclerViewAdapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maktabproj.Controller.activity.ProductPerCategoryActivity;
import com.example.maktabproj.Model.Category;
import com.example.maktabproj.R;
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
        View view = LayoutInflater.from(mContext).inflate(R.layout.sub_category_list_item, parent, false);
        return new SubCategoryViewHolder(view);
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
        private ImageView categoryImage;
        private TextView categoryName;
        Category mCategoriesItem;

        public SubCategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            categoryImage = itemView.findViewById(R.id.category_image);
            categoryName = itemView.findViewById(R.id.category_name);

            itemView.setOnClickListener(v -> {
                Intent intent = ProductPerCategoryActivity.newIntent(mContext, mCategoriesItem.getId());
                mContext.startActivity(intent);
            });
        }

        private void bind(Category categoriesItem){
            mCategoriesItem = categoriesItem;
            categoryName.setText(mCategoriesItem.getName());
            Picasso.with(mContext).load(mCategoriesItem.getImage().getSrc()).into(categoryImage);
        }
    }
}
