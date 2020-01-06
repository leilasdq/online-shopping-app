package com.example.maktabproj.View.adapter.recycler.recyclerViewAdapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maktabproj.View.activity.DetailProductActivity;
import com.example.maktabproj.Model.ImagesItem;
import com.example.maktabproj.Model.Response;
import com.example.maktabproj.R;
import com.example.maktabproj.databinding.AllListItemsBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ListAllProductAdapter extends RecyclerView.Adapter<ListAllProductAdapter.ListAllProductViewHolder> {

    private List<Response> allProductList = new ArrayList<>();
    private Context mContext;

    public ListAllProductAdapter(List<Response> list, Context context) {
        allProductList = listChecker(list);
        mContext = context;
    }

    public void setAllProductList(List<Response> list) {
        allProductList = listChecker(list);
    }

    private List<Response> listChecker (List<Response> responses){
        List<Response> original = new ArrayList<>();
        for (int i = 0; i < responses.size() ; i++) {
            if (responses.get(i).getName().equalsIgnoreCase("تخفیفات")) continue;
            original.add(responses.get(i));
        }
        return original;
    }

    @NonNull
    @Override
    public ListAllProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        AllListItemsBinding binding = AllListItemsBinding.inflate(inflater, parent, false);
        return new ListAllProductViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAllProductViewHolder holder, int position) {
        holder.bind(allProductList.get(position));
    }

    @Override
    public int getItemCount() {
        return allProductList.size();
    }

    class ListAllProductViewHolder extends RecyclerView.ViewHolder {
        private Response mResponse;
        private AllListItemsBinding mBinding;

        public ListAllProductViewHolder(@NonNull AllListItemsBinding binding) {
            super(binding.getRoot());
            mBinding = binding;

            itemView.setOnClickListener(v -> {
                Intent intent = DetailProductActivity.newIntent(mContext, mResponse.getId());
                mContext.startActivity(intent);
            });
        }

        private void bind(Response response) {
            mResponse = response;

            mBinding.allListProductName.setText(mResponse.getName());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                mBinding.allListProductShortDes.setText(Html.fromHtml(mResponse.getShortDescription(), Html.FROM_HTML_MODE_LEGACY));
            } else
                mBinding.allListProductShortDes.setText(Html.fromHtml(mResponse.getShortDescription()));
            String original = response.getRegularPrice();
            String sale = response.getSalePrice();
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
            ImagesItem src = response.getImages().get(0);
            Picasso.with(mContext).load(Uri.parse(src.getSrc()))
                    .placeholder(R.drawable.image_loading).error(R.drawable.image_error)
                    .into(mBinding.allListProductImage);

        }
    }
}
