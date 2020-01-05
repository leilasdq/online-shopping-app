package com.example.maktabproj.Controller.adapter.recycler.recyclerViewAdapter;

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
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maktabproj.Controller.activity.DetailProductActivity;
import com.example.maktabproj.Model.ImagesItem;
import com.example.maktabproj.Model.Response;
import com.example.maktabproj.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ListAllProductAdapter extends RecyclerView.Adapter<ListAllProductAdapter.ListAllProductViewHolder> {

    private List<Response> allProductList = new ArrayList<>();
    private Context mContext;

    public ListAllProductAdapter(List<Response> allProductList, Context context) {
        this.allProductList = allProductList;
        mContext = context;
    }

    public void setAllProductList(List<Response> allProductList) {
        this.allProductList = allProductList;
    }

    @NonNull
    @Override
    public ListAllProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.all_list_items, parent, false);
        return new ListAllProductViewHolder(view);
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
        private ImageView proImage;
        private TextView name;
        private TextView shortDes;
        private TextView realPrice;
        private TextView salePrice;

        public ListAllProductViewHolder(@NonNull View itemView) {
            super(itemView);

            proImage = itemView.findViewById(R.id.all_list_product_image);
            name = itemView.findViewById(R.id.all_list_product_name);
            shortDes = itemView.findViewById(R.id.all_list_product_short_des);
            realPrice = itemView.findViewById(R.id.all_list_product_real_price);
            salePrice = itemView.findViewById(R.id.all_list_product_sale_price);

            itemView.setOnClickListener(v -> {
                Intent intent = DetailProductActivity.newIntent(mContext, mResponse.getId());
                mContext.startActivity(intent);
            });
        }

        private void bind(Response response) {
            mResponse = response;

            name.setText(mResponse.getName());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                shortDes.setText(Html.fromHtml(mResponse.getShortDescription(), Html.FROM_HTML_MODE_LEGACY));
            } else  shortDes.setText(Html.fromHtml(mResponse.getShortDescription()));
            String original = response.getRegularPrice();
            String sale = response.getSalePrice();
            realPrice.setText(original.concat(mContext.getString(R.string.Tooman)));
            if (!sale.equalsIgnoreCase("")) {
                salePrice.setText(sale.concat(mContext.getString(R.string.Tooman)));
                salePrice.setVisibility(View.VISIBLE);
                realPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                realPrice.setPaintFlags(realPrice.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                salePrice.setVisibility(View.INVISIBLE);
                realPrice.setGravity(Gravity.CENTER_VERTICAL);
            }
            ImagesItem src = response.getImages().get(0);
            Picasso.with(mContext).load(Uri.parse(src.getSrc())).placeholder(R.drawable.image_loading).error(R.drawable.image_error).into(proImage);

        }
    }
}
