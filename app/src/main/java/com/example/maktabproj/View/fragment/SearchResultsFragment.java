package com.example.maktabproj.View.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.maktabproj.Model.Response;
import com.example.maktabproj.R;
import com.example.maktabproj.View.adapter.recycler.recyclerViewAdapter.ListAllProductAdapter;
import com.example.maktabproj.databinding.FragmentSearchResultsBinding;
import com.example.maktabproj.viewmodel.SearchViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchResultsFragment extends Fragment {
    public static final String ARGS_SEARCH_TEXT = "Search Text";
    private FragmentSearchResultsBinding mBinding;
    private SearchViewModel mViewModel;
    private ListAllProductAdapter mAdapter;
    private String mSearchText;

    public static SearchResultsFragment newInstance(String searchText) {

        Bundle args = new Bundle();
        args.putString(ARGS_SEARCH_TEXT, searchText);

        SearchResultsFragment fragment = new SearchResultsFragment();
        fragment.setArguments(args);
        return fragment;
    }


    public SearchResultsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
        mSearchText = getArguments().getString(ARGS_SEARCH_TEXT);
        mViewModel.getSearchList(mSearchText).observe(this, responses -> setUpAdapter(responses));

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_search_results, container, false);

        mBinding.searchRecycle.setLayoutManager(new LinearLayoutManager(getContext()));
        setupToolbar();

        return mBinding.getRoot();
    }

    private void setupToolbar() {
        mBinding.searchToolbar.setTitle(mSearchText);

        ((AppCompatActivity)getActivity()).setSupportActionBar(mBinding.searchToolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setUpAdapter(List<Response> items){
        if (items.size() == 0){
            mBinding.searchRecycle.setVisibility(View.INVISIBLE);
        } else {
            mBinding.searchRecycle.setVisibility(View.VISIBLE);
            mAdapter = new ListAllProductAdapter(items, getContext());
            mBinding.searchRecycle.setAdapter(mAdapter);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            getActivity().finish();
            return true;
        } else return super.onOptionsItemSelected(item);
    }

}
