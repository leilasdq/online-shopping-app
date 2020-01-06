package com.example.maktabproj.Controller.adapter.recycler.recyclerViewAdapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maktabproj.Controller.activity.DetailProductActivity;
import com.example.maktabproj.Model.ImagesItem;
import com.example.maktabproj.Model.Response;
import com.example.maktabproj.R;
import com.example.maktabproj.databinding.ListItemsLayoutBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder>{

    private List<Response> mResponseList;
    private Context mContext;

    public void setList(List<Response> responseList, Context context) {
        mResponseList = responseList;
        mContext = context;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ListItemsLayoutBinding binding = ListItemsLayoutBinding.inflate(inflater, parent, false);
        return new ProductViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        holder.bind(mResponseList.get(position));
    }

    @Override
    public int getItemCount() {
        return mResponseList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

        private Response mResponse;
        private ListItemsLayoutBinding mBinding;

        public ProductViewHolder(@NonNull ListItemsLayoutBinding binding) {
            super(binding.getRoot());
            mBinding = binding;

            itemView.setOnClickListener(v -> {
                Intent intent = DetailProductActivity.newIntent(mContext, mResponse.getId());
                mContext.startActivity(intent);
            });
        }

        public void bind(Response response){
            mResponse = response;
            mBinding.proName.setText(response.getName());
            String original = response.getRegularPrice();
            String sale = response.getSalePrice();
            mBinding.originalPrice.setText(original.concat(mContext.getString(R.string.Tooman)));
            if (!sale.equalsIgnoreCase("")){
                mBinding.salePrice.setText(sale.concat(mContext.getString(R.string.Tooman)));
                mBinding.originalPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                mBinding.originalPrice.setPaintFlags( mBinding.originalPrice.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
                mBinding.salePrice.setVisibility(View.INVISIBLE);
                mBinding.originalPrice.setGravity(Gravity.CENTER_VERTICAL);
            }
            ImagesItem src = response.getImages().get(0);
            Picasso.with(mContext).load(Uri.parse(src.getSrc()))
                    .placeholder(R.drawable.image_loading).error(R.drawable.image_error)
                    .into(mBinding.proImg);
        }
    }
}
