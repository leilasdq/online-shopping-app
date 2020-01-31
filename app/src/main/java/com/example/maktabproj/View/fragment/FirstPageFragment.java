package com.example.maktabproj.View.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.maktabproj.View.activity.BuyCardActivity;
import com.example.maktabproj.View.activity.ListAllProductActivity;
import com.example.maktabproj.View.activity.SearchResultActivity;
import com.example.maktabproj.View.adapter.recycler.recyclerViewAdapter.CategoryAdapter;
import com.example.maktabproj.View.adapter.recycler.recyclerViewAdapter.ProductAdapter;
import com.example.maktabproj.Model.CategoriesItem;
import com.example.maktabproj.Model.Response;
import com.example.maktabproj.R;
import com.example.maktabproj.databinding.FragmentNewItemBinding;
import com.example.maktabproj.sharedprefs.BadgePrefs;
import com.example.maktabproj.viewmodel.DetailViewModel;
import com.example.maktabproj.viewmodel.FirstPageViewModel;

import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FirstPageFragment extends Fragment {

    public static final String EXTRA_SEND_PRODUCT_TYPE = "send type";
    private CategoryAdapter categoryAdapter;
    private FragmentNewItemBinding mBinding;
    private FirstPageViewModel mViewModel;
    private DetailViewModel mDetailViewModel;
    private ProductAdapter mAdapter;
    private ProductAdapter mPopularAdapter;
    private ProductAdapter mRatedAdapter;

    private TextView badgeCount;

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

        setHasOptionsMenu(true);
        setupViewModel();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_new_item, container, false);

        initListeners();
        setUpRecycles();

        return mBinding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.tab_menu, menu);

        MenuItem searchMenuItem = menu.findItem(R.id.app_bar_search);
        final SearchView searchView = (SearchView) searchMenuItem.getActionView();
        searchView.setQueryHint(getString(R.string.search_hint));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//                searchView.setIconified(true);
                Intent intent = SearchResultActivity.newIntent(getActivity(), query);
                startActivity(intent);
                searchView.onActionViewCollapsed();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        final MenuItem menuItem = menu.findItem(R.id.app_bar_buy);
        View actionView = MenuItemCompat.getActionView(menuItem);
        badgeCount = actionView.findViewById(R.id.notification_badge);
        setupBadge();

        actionView.setOnClickListener(v -> {
            Intent intent = BuyCardActivity.newIntent(getActivity());
            startActivity(intent);
        });
    }

    private void initListeners() {
        mBinding.newProductAllLists.setOnClickListener(v -> {
            Intent intent = ListAllProductActivity.newIntent(getActivity(), "date");
            startActivity(intent);
        });
        mBinding.popularProductAllLists.setOnClickListener(v -> {
            Intent intent = ListAllProductActivity.newIntent(getActivity(), "Popular");
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
        mBinding.categoryRecycle.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
    }

    private void sliderSetup(Response response) {
        HashMap<String, String> pics = new HashMap<>();
        for (int i = 0; i < response.getImages().size() ; i++) {
            pics.put("pic " + i, response.getImages().get(i).getSrc());
        }
        for (String name :
                pics.keySet()) {
            TextSliderView textSliderView = new TextSliderView(getContext());
            textSliderView
                    .image(pics.get(name))
                    .setScaleType(BaseSliderView.ScaleType.CenterCrop);
            mBinding.slider.addSlider(textSliderView);
        }
        mBinding.slider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mBinding.slider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mBinding.slider.setDuration(5000);
    }

    private void setupViewModel() {
        mDetailViewModel = ViewModelProviders.of(this).get(DetailViewModel.class);
        mDetailViewModel.getResponseLiveData(608).observe(this, new Observer<Response>() {
            @Override
            public void onChanged(Response response) {
                sliderSetup(response);
            }
        });
        mViewModel = ViewModelProviders.of(this).get(FirstPageViewModel.class);
        mViewModel.getAllProductsLiveData().observe(this, responses -> {
            mBinding.newProductText.setVisibility(View.VISIBLE);
            mBinding.newIcon.setVisibility(View.VISIBLE);
            mBinding.newProductAllLists.setVisibility(View.VISIBLE);
            mBinding.newProgress.setVisibility(View.GONE);
            setupNewListAdapter(responses);
        });
        mViewModel.getPopularProductsLiveData().observe(this, responses -> {
            mBinding.popularProductText.setVisibility(View.VISIBLE);
            mBinding.popularIcon.setVisibility(View.VISIBLE);
            mBinding.popularProductAllLists.setVisibility(View.VISIBLE);
            mBinding.popularProgress.setVisibility(View.GONE);
            setupPopularListAdapter(responses);
        });
        mViewModel.getRatedProductsLiveData().observe(this, responses -> {
            mBinding.mostRateProductText.setVisibility(View.VISIBLE);
            mBinding.ratedIcon.setVisibility(View.VISIBLE);
            mBinding.ratedProductAllLists.setVisibility(View.VISIBLE);
            mBinding.ratedProgress.setVisibility(View.GONE);
            setupRatedListAdapter(responses);
        });
        mViewModel.getCategoryLiveData().observe(this, this::setupCategoryAdapter);
    }

    private void setupNewListAdapter(List<Response> items) {
        if (mAdapter == null) {
            mAdapter = new ProductAdapter(items, getContext());
            mBinding.recycle.setAdapter(mAdapter);
        } else {
            mAdapter.setList(items);
            mAdapter.notifyDataSetChanged();
        }
    }

    private void setupPopularListAdapter(List<Response> items) {
        if (mPopularAdapter == null) {
            mPopularAdapter = new ProductAdapter(items, getContext());
            mBinding.popularRecycle.setAdapter(mPopularAdapter);
        } else {
            mPopularAdapter.setList(items);
            mPopularAdapter.notifyDataSetChanged();
        }
    }

    private void setupRatedListAdapter(List<Response> items) {
        if (mRatedAdapter == null) {
            mRatedAdapter = new ProductAdapter(items, getContext());
            mBinding.rateRecycle.setAdapter(mRatedAdapter);
        } else {
            mRatedAdapter.setList(items);
            mRatedAdapter.notifyDataSetChanged();
        }
    }

    private void setupCategoryAdapter(List<CategoriesItem> items){
        if (categoryAdapter == null) {
            categoryAdapter = new CategoryAdapter(items, getContext());
            mBinding.categoryRecycle.setAdapter(categoryAdapter);
        } else {
            categoryAdapter.setList(items);
            categoryAdapter.notifyDataSetChanged();
        }
    }

    private void setupBadge() {
        if (BadgePrefs.getBadgeCount(getContext()) == 0){
            if (badgeCount.getVisibility() != View.GONE) {
                badgeCount.setVisibility(View.GONE);
            }
        } else {
            badgeCount.setVisibility(View.VISIBLE);
            badgeCount.setText(String.valueOf(BadgePrefs.getBadgeCount(getContext())));
        }
//        getActivity().invalidateOptionsMenu();
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().invalidateOptionsMenu();
    }
}
