package com.example.maktabproj.Controller;


import android.graphics.Paint;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.maktabproj.Model.CategoriesItem;
import com.example.maktabproj.Model.ImagesItem;
import com.example.maktabproj.Model.Response;
import com.example.maktabproj.Network.FetchItems;
import com.example.maktabproj.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewItemsFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView mCategoryRecyclerView;
    private RecyclerView mPopularRecyclerView;
    private RecyclerView mRatingRecyclerView;
    private TextView newText;
    private TextView popularText;
    private TextView ratingText;
    private List<Response> items;
    private List<Response> popularList;
    private List<Response> ratedList;
    private List<CategoriesItem> categories;

    private FetchItems fetchItems;
    private SliderLayout sliderLayout;

    private static final String TAG = "NewItemsFragment";
    private MainActivity activity;

    public NewItemsFragment() {
        // Required empty public constructor
    }

    public static NewItemsFragment newInstance() {

        Bundle args = new Bundle();

        NewItemsFragment fragment = new NewItemsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = (MainActivity) getActivity();
        ProductsAsync async = new ProductsAsync();
        async.execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_item, container, false);

        initViews(view);
        sliderSetup();
        setUpRecycles();
        setupAdapter();

        return view;
    }

    private void setUpRecycles() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        mPopularRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        mRatingRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));

        mCategoryRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
    }

    private void initViews(View view) {
        newText = view.findViewById(R.id.new_product_text);
        popularText = view.findViewById(R.id.popular_product_text);
        ratingText = view.findViewById(R.id.most_rate_product_text);

        sliderLayout = view.findViewById(R.id.slider);
        mRecyclerView = view.findViewById(R.id.recycle);
        mCategoryRecyclerView = view.findViewById(R.id.category_recycle);
        mPopularRecyclerView = view.findViewById(R.id.popular_recycle);
        mRatingRecyclerView = view.findViewById(R.id.rate_recycle);
    }

    private void sliderSetup() {
        HashMap<String, Integer> pics = new HashMap<>();
        pics.put("First Pic", R.drawable.one);
        pics.put("Second Pic", R.drawable.two);
        pics.put("Third Pic", R.drawable.three);
        pics.put("Fourth Pic", R.drawable.four);
        for (String name :
                pics.keySet()) {
            TextSliderView textSliderView = new TextSliderView(getContext());
            textSliderView.description(name)
                    .image(pics.get(name))
                    .setScaleType(BaseSliderView.ScaleType.CenterCrop);
            sliderLayout.addSlider(textSliderView);
        }
    }

    private void setupAdapter() {
        if (isAdded()) {
            ProductAdapter adapter = new ProductAdapter();
            adapter.setList(items);
            mRecyclerView.setAdapter(adapter);

            CategoryAdapter categoryAdapter = new CategoryAdapter();
            categoryAdapter.setList(categories);
            mCategoryRecyclerView.setAdapter(categoryAdapter);

            ProductAdapter popular = new ProductAdapter();
            popular.setList(popularList);
            mPopularRecyclerView.setAdapter(popular);

            ProductAdapter rated = new ProductAdapter();
            rated.setList(ratedList);
            mRatingRecyclerView.setAdapter(rated);
        }
    }

    private class ProductViewHolder extends RecyclerView.ViewHolder {

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
        }

        public void bind(Response response){
            productName.setText(response.getName());
            String original = response.getRegularPrice();
            String sale = response.getSalePrice();
            originalPrice.setText(original);
            if (!sale.equalsIgnoreCase("")){
                salePrice.setText(sale);
                originalPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                originalPrice.setPaintFlags( originalPrice.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
                salePrice.setText("");
            }
            ImagesItem src = response.getImages().get(0);
            Picasso.with(getContext()).load(Uri.parse(src.getSrc())).placeholder(R.drawable.image_loading).error(R.drawable.image_error).into(productImage);
        }
    }

    private class ProductAdapter extends RecyclerView.Adapter<ProductViewHolder>{

        private List<Response> mResponseList;

        public void setList(List responseList) {
            mResponseList = responseList;
        }

        @NonNull
        @Override
        public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.list_items_layout, parent, false);
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
    }

    private class CategoryViewHolder extends RecyclerView.ViewHolder {

        Button categoryBtn;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryBtn = itemView.findViewById(R.id.category_btn);
        }

        public void bind(CategoriesItem categoriesItem){
            categoryBtn.setText(categoriesItem.getName());
        }
    }

    private class CategoryAdapter extends RecyclerView.Adapter<CategoryViewHolder>{

        private List<CategoriesItem> mCategoriesItems;

        public void setList(List categoriesItems) {
            mCategoriesItems = categoriesItems;
        }

        @NonNull
        @Override
        public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.category_list_items, parent, false);
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
    }

    private class ProductsAsync extends AsyncTask<Void, Void, List<Response>>{

        @Override
        protected List<Response> doInBackground(Void... voids) {
            fetchItems = FetchItems.getInstance();
            items = new ArrayList<>();
            categories = new ArrayList<>();
            popularList = new ArrayList<>();
            ratedList = new ArrayList<>();
            try {
                items = fetchItems.getAllProducts();
                categories = fetchItems.getCategories();
               popularList = fetchItems.getPopularProducts();
               ratedList = fetchItems.getRatedProducts();
            } catch (IOException e) {
                Log.e(TAG, "doInBackground: " + e.getMessage() );
            }
            return items;
        }

        @Override
        protected void onPostExecute(List<Response> responses) {
            super.onPostExecute(responses);
            newText.setVisibility(View.VISIBLE);
            popularText.setVisibility(View.VISIBLE);
            ratingText.setVisibility(View.VISIBLE);
            setupAdapter();
            activity.setCategories(categories);
            Log.e(TAG, "onPostExecute: items size" + items.size());
            Log.e(TAG, "onPostExecute: categories size" + categories.size());
        }
    }

}
