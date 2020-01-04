package com.example.maktabproj.Controller;


import android.content.Intent;
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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.airbnb.lottie.network.NetworkFetcher;
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
public class FirstPageFragment extends Fragment {

    public static final String RESPONSE_ID_EXTRA = "com.example.maktabproj.response id extra";
    public static final String EXTRA_SEND_PRODUCT_TYPE = "send type";

    private EndlessRecyclerView scrollListener;
    private int pageNumber = 1;

    private RecyclerView mRecyclerView;
    private RecyclerView mCategoryRecyclerView;
    private RecyclerView mPopularRecyclerView;
    private RecyclerView mRatingRecyclerView;
    private TextView newText;
    private TextView popularText;
    private TextView ratingText;
    private ImageView newIcon;
    private ImageView popularIcon;
    private ImageView ratedIcon;
    private TextView newAllLists;
    private TextView popularAllLists;
    private TextView ratedAllLists;
    private ProgressBar newProgress;
    private ProgressBar popularProgress;
    private ProgressBar ratedProgress;

    private List<Response> items;
    private List<Response> popularList;
    private List<Response> ratedList;
    private List<CategoriesItem> categories;
    private List<CategoriesItem> copyOfCategories = new ArrayList<>();

    private FetchItems fetchItems;
    private SliderLayout sliderLayout;

    private static final String TAG = "FirstPageFragment";
    private LinearLayoutManager manager;
    private CategoryAdapter categoryAdapter;

    public FirstPageFragment() {
        // Required empty public constructor
    }

    public static FirstPageFragment newInstance() {

        Bundle args = new Bundle();

        FirstPageFragment fragment = new FirstPageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        callAsyncs();
    }

    private void callAsyncs() {
        fetchItems = FetchItems.getInstance();
        items = new ArrayList<>();
        popularList = new ArrayList<>();
        ratedList = new ArrayList<>();

        CategoriesAsync categoriesAsync = new CategoriesAsync();
        categoriesAsync.execute();

        NewProductsAsync async = new NewProductsAsync();
        async.execute();

        PopularAsync popularAsync = new PopularAsync();
        popularAsync.execute();

        RatedAsync ratedAsync = new RatedAsync();
        ratedAsync.execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_item, container, false);

        initViews(view);
        initListeners();
        sliderSetup();
        setUpRecycles();
        setupAdapter();

        return view;
    }

    private void initListeners() {
        scrollListener = new EndlessRecyclerView(manager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                pageNumber++;
                CategoriesAsync async = new CategoriesAsync();
                async.execute();
            }
        };

        newAllLists.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = ListAllProductActivity.newIntent(getActivity(), "date");
                startActivity(intent);
            }
        });
        popularAllLists.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = ListAllProductActivity.newIntent(getActivity(), "popular");
                startActivity(intent);
            }
        });
        ratedAllLists.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = ListAllProductActivity.newIntent(getActivity(), "rated");
                startActivity(intent);
            }
        });

    }

    private void setUpRecycles() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        mPopularRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        mRatingRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));

        mCategoryRecyclerView.setLayoutManager(manager);
        mCategoryRecyclerView.addOnScrollListener(scrollListener);
    }

    private void initViews(View view) {
        manager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);

        newText = view.findViewById(R.id.new_product_text);
        popularText = view.findViewById(R.id.popular_product_text);
        ratingText = view.findViewById(R.id.most_rate_product_text);

        newIcon = view.findViewById(R.id.new_icon);
        popularIcon = view.findViewById(R.id.popular_icon);
        ratedIcon = view.findViewById(R.id.rated_icon);

        newProgress = view.findViewById(R.id.new_progress);
        popularProgress = view.findViewById(R.id.popular_progress);
        ratedProgress = view.findViewById(R.id.rated_progress);

        newAllLists = view.findViewById(R.id.new_product_all_lists);
        popularAllLists = view.findViewById(R.id.popular_product_all_lists);
        ratedAllLists = view.findViewById(R.id.rated_product_all_lists);

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
            textSliderView
                    .description(name)
                    .image(pics.get(name))
                    .setScaleType(BaseSliderView.ScaleType.CenterCrop);
            sliderLayout.addSlider(textSliderView);
        }
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderLayout.setDuration(5000);
    }

    private void setupAdapter() {
        if (isAdded()) {
            ProductAdapter adapter = new ProductAdapter();
            adapter.setList(items);
            mRecyclerView.setAdapter(adapter);

            ProductAdapter popular = new ProductAdapter();
            popular.setList(popularList);
            mPopularRecyclerView.setAdapter(popular);

            ProductAdapter rated = new ProductAdapter();
            rated.setList(ratedList);
            mRatingRecyclerView.setAdapter(rated);

            if (categoryAdapter == null) {
                categoryAdapter = new CategoryAdapter();
                categoryAdapter.setList(copyOfCategories);
                mCategoryRecyclerView.setAdapter(categoryAdapter);
            } else {
                categoryAdapter.setList(copyOfCategories);
                categoryAdapter.notifyDataSetChanged();
            }
        }
    }

    private class ProductViewHolder extends RecyclerView.ViewHolder {

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
                    Intent intent = DetailProductActivity.newIntent(getActivity(), mResponse.getId());
                    startActivity(intent);
                }
            });
        }

        public void bind(Response response){
            mResponse = response;
            productName.setText(response.getName());
            String original = response.getRegularPrice();
            String sale = response.getSalePrice();
            originalPrice.setText(original.concat(getString(R.string.Tooman)));
            if (!sale.equalsIgnoreCase("")){
                salePrice.setText(sale.concat(getString(R.string.Tooman)));
                originalPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                originalPrice.setPaintFlags( originalPrice.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
                salePrice.setVisibility(View.INVISIBLE);
                originalPrice.setGravity(Gravity.CENTER_VERTICAL);
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
        CategoriesItem mCategoriesItem;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryBtn = itemView.findViewById(R.id.category_btn);

            categoryBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = SubCategoryActivity.newIntent(getActivity(), mCategoriesItem.getId());
                    startActivity(intent);
                }
            });
        }

        public void bind(CategoriesItem categoriesItem){
            mCategoriesItem = categoriesItem;
            categoryBtn.setText(categoriesItem.getName());
            categoryBtn.setBackgroundColor(getActivity().getResources().getColor(R.color.categoryButtonColor));
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

    private class NewProductsAsync extends AsyncTask<Void, Void, List<Response>>{

        @Override
        protected List<Response> doInBackground(Void... voids) {
            try {
                items = fetchItems.getAllProducts();
            } catch (IOException e) {
                Log.e(TAG, "doInBackground: " + e.getMessage() );
            }
            return items;
        }

        @Override
        protected void onPostExecute(List<Response> responses) {
            super.onPostExecute(responses);
            newText.setVisibility(View.VISIBLE);
            newIcon.setVisibility(View.VISIBLE);
            newAllLists.setVisibility(View.VISIBLE);
            newProgress.setVisibility(View.GONE);
            setupAdapter();
            Log.e(TAG, "onPostExecute: items size" + items.size());
        }
    }

    private class PopularAsync extends AsyncTask<Void, Void, List<Response>>{

        @Override
        protected List<Response> doInBackground(Void... voids) {
            try {
                popularList = fetchItems.getPopularProducts();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return popularList;
        }

        @Override
        protected void onPostExecute(List<Response> responses) {
            super.onPostExecute(responses);
            popularText.setVisibility(View.VISIBLE);
            popularIcon.setVisibility(View.VISIBLE);
            popularAllLists.setVisibility(View.VISIBLE);
            popularProgress.setVisibility(View.GONE);
            setupAdapter();
        }
    }

    private class RatedAsync extends AsyncTask<Void, Void, List<Response>>{

        @Override
        protected List<Response> doInBackground(Void... voids) {
            try {
                ratedList = fetchItems.getRatedProducts();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return ratedList;
        }

        @Override
        protected void onPostExecute(List<Response> responses) {
            super.onPostExecute(responses);
            ratingText.setVisibility(View.VISIBLE);
            ratedIcon.setVisibility(View.VISIBLE);
            ratedAllLists.setVisibility(View.VISIBLE);
            ratedProgress.setVisibility(View.GONE);
            setupAdapter();
        }
    }

    private class CategoriesAsync extends AsyncTask<Void, Void, List<CategoriesItem>>{

        @Override
        protected List<CategoriesItem> doInBackground(Void... voids) {
            categories = new ArrayList<>();
            try {
                categories = fetchItems.getParentCategories(pageNumber);
                copyOfCategories.addAll(categories);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return copyOfCategories;
        }

        @Override
        protected void onPostExecute(List<CategoriesItem> items) {
            super.onPostExecute(items);
            setupAdapter();
            Log.e(TAG, "onPostExecute: categories size" + categories.size());
        }
    }

}
