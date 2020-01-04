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
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_items_layout, parent, false);
        return new ProductViewHolder(view);
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

        Response mResponse;
        ImageView productImage;
        TextView productName;
        TextView originalPrice;
        TextView salePrice;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.pro_img);
            productName = itemView.findViewById(R.id.pro_name);
            originalPrice = itemView.findViewById(R.id.original_price);
            salePrice = itemView.findViewById(R.id.sale_price);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = DetailProductActivity.newIntent(mContext, mResponse.getId());
                    mContext.startActivity(intent);
                }
            });
        }

        public void bind(Response response){
            mResponse = response;
            productName.setText(response.getName());
            String original = response.getRegularPrice();
            String sale = response.getSalePrice();
            originalPrice.setText(original.concat(mContext.getString(R.string.Tooman)));
            if (!sale.equalsIgnoreCase("")){
                salePrice.setText(sale.concat(mContext.getString(R.string.Tooman)));
                originalPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                originalPrice.setPaintFlags( originalPrice.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
                salePrice.setVisibility(View.INVISIBLE);
                originalPrice.setGravity(Gravity.CENTER_VERTICAL);
            }
            ImagesItem src = response.getImages().get(0);
            Picasso.with(mContext).load(Uri.parse(src.getSrc())).placeholder(R.drawable.image_loading).error(R.drawable.image_error).into(productImage);
        }
    }
}
