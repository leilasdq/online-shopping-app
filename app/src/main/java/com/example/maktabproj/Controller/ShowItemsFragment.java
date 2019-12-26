package com.example.maktabproj.Controller;


import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.maktabproj.Controller.viewPagerAdapter.ImageViewAdapter;
import com.example.maktabproj.Model.ImagesItem;
import com.example.maktabproj.Model.Response;
import com.example.maktabproj.Network.FetchItems;
import com.example.maktabproj.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShowItemsFragment extends Fragment {

    private FetchItems mFetchItems;
    private ProductId productId;
    private Response product;
    private ImageViewAdapter mViewAdapter;

    private ViewPager proImage;
    private TextView proName;
    private TextView proDetail;
    private TextView realPrice;
    private TextView salePrice;
    private Button addToCard;
    private ProgressBar mShowItemsProgress;
    private Toolbar mToolbar;

    public ShowItemsFragment() {
        // Required empty public constructor
    }

    public static ShowItemsFragment newInstance() {
        Bundle args = new Bundle();

        ShowItemsFragment fragment = new ShowItemsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ProductId){
            productId = (ProductId) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        productId = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFetchItems = FetchItems.getInstance();
        product = new Response();
//        productId = getArguments().getInt(ARGS_PRODUCT_ID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_show_items, container, false);

        initUI(view);
        GetProductAsync async = new GetProductAsync();
        async.execute();

        return view;
    }

    private void initUI(View view) {
        proImage = view.findViewById(R.id.product_images);
        proName = view.findViewById(R.id.product_name);
        proDetail = view.findViewById(R.id.product_detail);
        realPrice = view.findViewById(R.id.product_real_price);
        salePrice = view.findViewById(R.id.product_sale_price);
        addToCard = view.findViewById(R.id.add_to_cart_btn);
        mShowItemsProgress = view.findViewById(R.id.show_product_progress);
        mToolbar = view.findViewById(R.id.show_product_toolbar);
    }

    private class GetProductAsync extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                product = mFetchItems.getSpecificProduct(productId.getProductId());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            addToCard.setVisibility(View.VISIBLE);
            mShowItemsProgress.setVisibility(View.INVISIBLE);

            mToolbar.setTitle(product.getName());
            proName.setText(product.getName());
            proDetail.setText(product.getDescription());

            List<ImagesItem> images = product.getImages();
            List<String> urls = new ArrayList<>();
            for (int i = 0; i < images.size(); i++) {
                urls.add(images.get(i).getSrc());
            }
            mViewAdapter = new ImageViewAdapter(getActivity(), urls);
            proImage.setAdapter(mViewAdapter);

            String original = product.getRegularPrice();
            String sale = product.getSalePrice();
            realPrice.setText(original.concat(getString(R.string.Tooman)));
            if (!sale.equalsIgnoreCase("")){
                salePrice.setText(sale.concat(getString(R.string.Tooman)));
                realPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                realPrice.setPaintFlags( realPrice.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
                salePrice.setVisibility(View.INVISIBLE);
                realPrice.setGravity(Gravity.CENTER_VERTICAL);
            }
        }
    }

    public interface ProductId{
        int getProductId();
    }

}
