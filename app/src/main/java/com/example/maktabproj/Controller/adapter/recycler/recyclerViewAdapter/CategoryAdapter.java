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
        View view = LayoutInflater.from(mContext).inflate(R.layout.category_list_items, parent, false);
        return new CategoryViewHolder(view);
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

        Button categoryBtn;
        CategoriesItem mCategoriesItem;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryBtn = itemView.findViewById(R.id.category_btn);

            categoryBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = SubCategoryActivity.newIntent(mContext, mCategoriesItem.getId());
                    mContext.startActivity(intent);
                }
            });
        }

        public void bind(CategoriesItem categoriesItem){
            mCategoriesItem = categoriesItem;
            categoryBtn.setText(categoriesItem.getName());
            categoryBtn.setBackgroundColor(mContext.getResources().getColor(R.color.categoryButtonColor));
        }
    }
}
