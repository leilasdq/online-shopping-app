package com.example.maktabproj.Controller.adapter.recycler.recyclerViewAdapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maktabproj.Controller.activity.DetailProductActivity;
import com.example.maktabproj.Model.Response;
import com.example.maktabproj.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProductsOfSubCategoryAdapter extends RecyclerView.Adapter<ProductsOfSubCategoryAdapter.SubCategoryViewHolder>{
    List<Response> mCategoriesItems = new ArrayList<>();
    private Context mContext;

    public ProductsOfSubCategoryAdapter(List<Response> categoriesItems, Context context) {
        mCategoriesItems = categoriesItems;
        mContext = context;
    }

    public void setCategoriesItems(List<Response> categoriesItems) {
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
        private Response mResponse;

        public SubCategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            categoryImage = itemView.findViewById(R.id.category_image);
            categoryName = itemView.findViewById(R.id.category_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = DetailProductActivity.newIntent(mContext, mResponse.getId());
                    mContext.startActivity(intent);
                }
            });
        }

        private void bind(Response categoriesItem){
            mResponse = categoriesItem;
            categoryName.setText(mResponse.getName());
            Picasso.with(mContext).load(mResponse.getImages().get(0).getSrc()).into(categoryImage);
        }
    }
}
