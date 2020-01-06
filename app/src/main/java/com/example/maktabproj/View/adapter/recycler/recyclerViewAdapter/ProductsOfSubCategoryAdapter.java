package com.example.maktabproj.View.adapter.recycler.recyclerViewAdapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Build;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maktabproj.View.activity.DetailProductActivity;
import com.example.maktabproj.Model.Response;
import com.example.maktabproj.R;
import com.example.maktabproj.databinding.AllListItemsBinding;
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
        LayoutInflater inflater = LayoutInflater.from(mContext);
        AllListItemsBinding binding = AllListItemsBinding.inflate(inflater, parent, false);
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
        private Response mResponse;
        private AllListItemsBinding mBinding;

        public SubCategoryViewHolder(@NonNull AllListItemsBinding binding) {
            super(binding.getRoot());
            mBinding = binding;

            itemView.setOnClickListener(v -> {
                Intent intent = DetailProductActivity.newIntent(mContext, mResponse.getId());
                mContext.startActivity(intent);
            });
        }

        private void bind(Response categoriesItem){
            mResponse = categoriesItem;
            mBinding.allListProductName.setText(mResponse.getName());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                mBinding.allListProductShortDes.setText(Html.fromHtml(mResponse.getShortDescription(), Html.FROM_HTML_MODE_LEGACY));
            } else
                mBinding.allListProductShortDes.setText(Html.fromHtml(mResponse.getShortDescription()));
            String original = mResponse.getRegularPrice();
            String sale = mResponse.getSalePrice();
            mBinding.allListProductRealPrice.setText(original.concat(mContext.getString(R.string.Tooman)));
            if (!sale.equalsIgnoreCase("")) {
                mBinding.allListProductSalePrice.setText(sale.concat(mContext.getString(R.string.Tooman)));
                mBinding.allListProductSalePrice.setVisibility(View.VISIBLE);
                mBinding.allListProductRealPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                mBinding.allListProductRealPrice.setPaintFlags(mBinding.allListProductRealPrice.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                mBinding.allListProductSalePrice.setVisibility(View.INVISIBLE);
                mBinding.allListProductRealPrice.setGravity(Gravity.CENTER_VERTICAL);
            }
            Picasso.with(mContext).load(mResponse.getImages().get(0).getSrc()).into(mBinding.allListProductImage);
        }
    }
}
