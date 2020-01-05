package com.example.maktabproj.Controller.fragment;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.maktabproj.Controller.activity.ListAllProductActivity;
import com.example.maktabproj.Controller.adapter.recycler.EndlessRecyclerView;
import com.example.maktabproj.Controller.adapter.recycler.recyclerViewAdapter.CategoryAdapter;
import com.example.maktabproj.Controller.adapter.recycler.recyclerViewAdapter.ProductAdapter;
import com.example.maktabproj.Model.CategoriesItem;
import com.example.maktabproj.Model.Response;
import com.example.maktabproj.Network.FetchItems;
import com.example.maktabproj.R;
import com.example.maktabproj.databinding.FragmentNewItemBinding;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FirstPageFragment extends Fragment {

    public static final String EXTRA_SEND_PRODUCT_TYPE = "send type";

    private EndlessRecyclerView scrollListener;
    private int pageNumber = 1;

    private List<Response> items;
    private List<Response> popularList;
    private List<Response> ratedList;
    private List<CategoriesItem> categories;
    private List<CategoriesItem> copyOfCategories = new ArrayList<>();

    private FetchItems fetchItems;

    private static final String TAG = "FirstPageFragment";
    private LinearLayoutManager manager;
    private CategoryAdapter categoryAdapter;
    private FragmentNewItemBinding mBinding;

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
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_new_item, container, false);

        manager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
        initListeners();
        sliderSetup();
        setUpRecycles();
        setupAdapter();

        return mBinding.getRoot();
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

        mBinding.newProductAllLists.setOnClickListener(v -> {
            Intent intent = ListAllProductActivity.newIntent(getActivity(), "date");
            startActivity(intent);
        });
        mBinding.popularProductAllLists.setOnClickListener(v -> {
            Intent intent = ListAllProductActivity.newIntent(getActivity(), "popular");
            startActivity(intent);
        });
        mBinding.ratedProductAllLists.setOnClickListener(v -> {
            Intent intent = ListAllProductActivity.newIntent(getActivity(), "rated");
            startActivity(intent);
        });

    }

    private void setUpRecycles() {
        mBinding.recycle.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        mBinding.popularRecycle.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        mBinding.rateRecycle.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));

        mBinding.categoryRecycle.setLayoutManager(manager);
        mBinding.categoryRecycle.addOnScrollListener(scrollListener);
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
            mBinding.slider.addSlider(textSliderView);
        }
        mBinding.slider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mBinding.slider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mBinding.slider.setDuration(5000);
    }

    private void setupAdapter() {
        if (isAdded()) {
            ProductAdapter adapter = new ProductAdapter();
            adapter.setList(items, getContext());
            mBinding.recycle.setAdapter(adapter);

            ProductAdapter popular = new ProductAdapter();
            popular.setList(popularList, getContext());
            mBinding.popularRecycle.setAdapter(popular);

            ProductAdapter rated = new ProductAdapter();
            rated.setList(ratedList, getContext());
            mBinding.rateRecycle.setAdapter(rated);

            if (categoryAdapter == null) {
                categoryAdapter = new CategoryAdapter(copyOfCategories, getContext());
                categoryAdapter.setList(copyOfCategories);
                mBinding.categoryRecycle.setAdapter(categoryAdapter);
            } else {
                categoryAdapter.setList(copyOfCategories);
                categoryAdapter.notifyDataSetChanged();
            }
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
            mBinding.newProductText.setVisibility(View.VISIBLE);
            mBinding.newIcon.setVisibility(View.VISIBLE);
            mBinding.newProductAllLists.setVisibility(View.VISIBLE);
            mBinding.newProgress.setVisibility(View.GONE);
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
            mBinding.popularProductText.setVisibility(View.VISIBLE);
            mBinding.popularIcon.setVisibility(View.VISIBLE);
            mBinding.popularProductAllLists.setVisibility(View.VISIBLE);
            mBinding.popularProgress.setVisibility(View.GONE);
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
            mBinding.mostRateProductText.setVisibility(View.VISIBLE);
            mBinding.ratedIcon.setVisibility(View.VISIBLE);
            mBinding.ratedProductAllLists.setVisibility(View.VISIBLE);
            mBinding.ratedProgress.setVisibility(View.GONE);
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
