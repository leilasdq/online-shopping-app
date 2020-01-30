package com.example.maktabproj.View.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
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

import com.example.maktabproj.View.activity.BuyCardActivity;
import com.example.maktabproj.View.activity.SearchResultActivity;
import com.example.maktabproj.View.adapter.recycler.EndlessRecyclerView;
import com.example.maktabproj.View.adapter.recycler.recyclerViewAdapter.ListAllProductAdapter;
import com.example.maktabproj.Model.Response;
import com.example.maktabproj.R;
import com.example.maktabproj.databinding.FragmentListAllProductBinding;
import com.example.maktabproj.sharedprefs.BadgePrefs;
import com.example.maktabproj.viewmodel.ListAllProductsViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListAllProductFragment extends Fragment {

    public static final String GET_PRODUCT_TYPE_ARGS = "get product type";
    private String type;
    private int pageNumber = 1;

    private EndlessRecyclerView scrollListener;
    private LinearLayoutManager manager;

    private ListAllProductAdapter adapter;
    private FragmentListAllProductBinding mBinding;
    private ListAllProductsViewModel mViewModel;
    private SearchView searchView;

    private TextView badgeCount;

    public static ListAllProductFragment newInstance(String productType) {

        Bundle args = new Bundle();
        args.putString(GET_PRODUCT_TYPE_ARGS, productType);

        ListAllProductFragment fragment = new ListAllProductFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public ListAllProductFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        type = getArguments().getString(GET_PRODUCT_TYPE_ARGS);

        mViewModel = ViewModelProviders.of(this).get(ListAllProductsViewModel.class);
        mViewModel.sendRequest(pageNumber, type);
        mViewModel.getAllProductsLiveData().observe(this, responses -> setupAdapter(responses));

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_list_all_product, container, false);
        setupToolbar();
        setupRecycle();

        scrollListener = new EndlessRecyclerView(manager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                pageNumber++;
                mViewModel.sendRequest(pageNumber, type);
            }
        };
        mBinding.allProductRecycler.addOnScrollListener(scrollListener);

        return mBinding.getRoot();
    }

    private void setupRecycle() {
        manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mBinding.allProductRecycler.setLayoutManager(manager);
    }

    private void setupAdapter(List<Response> items) {
        if (isAdded()) {
            if (adapter==null) {
                adapter = new ListAllProductAdapter(items, getContext());
                mBinding.allProductRecycler.setAdapter(adapter);
            }
            else {
                adapter.setAllProductList(items);
                adapter.notifyDataSetChanged();
            }
        }
    }

    private void setupToolbar() {
        if (type.equalsIgnoreCase("date")) {
            mBinding.allListsToolbar.setTitle(getString(R.string.all_products));
        } else if (type.equalsIgnoreCase("popular")) {
            mBinding.allListsToolbar.setTitle(getString(R.string.all_popular));
        } else if (type.equalsIgnoreCase("rated")) {
            mBinding.allListsToolbar.setTitle(getString(R.string.all_rated));
        }
        mBinding.allListsToolbar.setTitleTextColor(getActivity().getResources().getColor(android.R.color.black));

        ((AppCompatActivity)getActivity()).setSupportActionBar(mBinding.allListsToolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.tab_menu, menu);
        Menu menuBar = mBinding.allListsToolbar.getMenu();
        MenuItem searchMenuItem = menuBar.findItem(R.id.app_bar_search);
        searchView = (SearchView) searchMenuItem.getActionView();
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

        final MenuItem menuItem = menuBar.findItem(R.id.app_bar_buy);
        View actionView = MenuItemCompat.getActionView(menuItem);
        badgeCount = actionView.findViewById(R.id.notification_badge);
        setupBadge();

        actionView.setOnClickListener(v -> {
            Intent intent = BuyCardActivity.newIntent(getActivity());
            startActivity(intent);
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            getActivity().finish();
            return true;
        } else return super.onOptionsItemSelected(item);
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
        getActivity().invalidateOptionsMenu();
    }
}
