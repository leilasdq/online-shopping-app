package com.example.maktabproj.View.adapter.recycler.recyclerViewAdapter;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maktabproj.Model.ImagesItem;
import com.example.maktabproj.Model.Response;
import com.example.maktabproj.Model.entities.Products;
import com.example.maktabproj.R;
import com.example.maktabproj.View.activity.DetailProductActivity;
import com.example.maktabproj.databinding.ShopCardListItemBinding;
import com.example.maktabproj.repository.BuyingCardRepository;
import com.example.maktabproj.sharedprefs.BadgePrefs;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class BuyCardAdapter extends RecyclerView.Adapter<BuyCardAdapter.BuyCardViewHolder> {
    private List<Response> mBuyProducts = new ArrayList<>();
    private Context mContext;
    private int numberOfProducts = 1;
    String[] count = {"1", "2", "3", "4", "5"};
    ArrayAdapter<String> adapter;

    public BuyCardAdapter(List<Response> buyProducts, Context context) {
        mBuyProducts = buyProducts;
        mContext = context;
    }

    public void setBuyProducts(List<Response> buyProducts) {
        mBuyProducts = buyProducts;
    }

    @NonNull
    @Override
    public BuyCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ShopCardListItemBinding binding = ShopCardListItemBinding.inflate(inflater, parent, false);
        adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, count);
        return new BuyCardViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BuyCardViewHolder holder, int position) {
        holder.bind(mBuyProducts.get(position));
    }

    @Override
    public int getItemCount() {
        return mBuyProducts.size();
    }

    class BuyCardViewHolder extends RecyclerView.ViewHolder{
        private Response mResponse;
        private ShopCardListItemBinding mBinding;

        public BuyCardViewHolder(@NonNull ShopCardListItemBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mBinding.countSpinner.setAdapter(adapter);
        }

        private void bind(Response response){
            mResponse = response;
            NumberFormat formatter = new DecimalFormat("#,###");

            mBinding.countSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    switch (position){
                        case 0:
                            numberOfProducts = 1;
                            mBinding.countSpinner.setSelection(0);
//                            BuyCardAdapter.this.notifyDataSetChanged();
                            break;
                        case 1:
                            numberOfProducts = 2;
                            mBinding.countSpinner.setSelection(1);
                            BuyCardAdapter.this.notifyDataSetChanged();
                            break;
                        case 2:
                            numberOfProducts = 3;
                            mBinding.countSpinner.setSelection(2);
                            BuyCardAdapter.this.notifyDataSetChanged();
                            break;
                        case 3:
                            numberOfProducts = 4;
                            mBinding.countSpinner.setSelection(3);
                            BuyCardAdapter.this.notifyDataSetChanged();
                            break;
                        case 4:
                            numberOfProducts = 5;
                            mBinding.countSpinner.setSelection(4);
                            BuyCardAdapter.this.notifyDataSetChanged();
                            break;
                    }
                    BuyingCardRepository.getInstance(mContext).editItem(mResponse.getId(), numberOfProducts);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            ImagesItem src = response.getImages().get(0);
            Picasso.with(mContext).load(Uri.parse(src.getSrc()))
                    .placeholder(R.drawable.image_loading).error(R.drawable.image_error)
                    .into(mBinding.proImg);

            mBinding.proName.setText(mResponse.getName());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                mBinding.proShortDes.setText(Html.fromHtml(mResponse.getShortDescription(), Html.FROM_HTML_MODE_LEGACY));
            } else
                mBinding.proShortDes.setText(Html.fromHtml(mResponse.getShortDescription()));

            String formattedOriginal = formatter.format(Long.parseLong(mResponse.getRegularPrice())*numberOfProducts);
            mBinding.fullPriceCount.setText(formattedOriginal.concat(mContext.getString(R.string.Tooman)));
            String sale = mResponse.getSalePrice();
            if (sale.equals("")){
                mBinding.salePrice.setVisibility(View.GONE);
                mBinding.salePriceCount.setVisibility(View.GONE);
                mBinding.line2.setVisibility(View.GONE);
                mBinding.allPriceCount.setText(formattedOriginal.concat(mContext.getString(R.string.Tooman)));
            } else {
                mBinding.salePrice.setVisibility(View.VISIBLE);
                mBinding.salePriceCount.setVisibility(View.VISIBLE);
                mBinding.line2.setVisibility(View.VISIBLE);
                String price = String.valueOf(Integer.valueOf(mResponse.getPrice())*numberOfProducts);
                String formattedSale = formatter.format(Long.parseLong(price));
                mBinding.allPriceCount.setText(formattedSale.concat(mContext.getString(R.string.Tooman)));
                String saleCount = String.valueOf((Integer.valueOf(mResponse.getRegularPrice()) - Integer.valueOf(mResponse.getPrice()))*numberOfProducts);
                String formattedSaleCount = formatter.format(Long.parseLong(saleCount));
                mBinding.salePriceCount.setText(formattedSaleCount.concat(mContext.getString(R.string.Tooman)));
            }

            mBinding.proImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mContext.startActivity(DetailProductActivity.newIntent(mContext, mResponse.getId()
                    ));
                }
            });
            mBinding.deleteItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BuyingCardRepository.getInstance(mContext).deleteItem(mResponse.getId());
                    BadgePrefs.setBadgeCount(mContext, BadgePrefs.getBadgeCount(mContext)-1);
                    BuyCardAdapter.this.notifyDataSetChanged();
                }
            });
        }
    }
}
