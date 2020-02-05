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
import com.example.maktabproj.View.adapter.recycler.recyclerViewAdapter.BuyCardAdapter;
import com.example.maktabproj.databinding.FragmentBuyCardBinding;
import com.example.maktabproj.viewmodel.FirstPageViewModel;
import com.example.maktabproj.viewmodel.ListAllProductsViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BuyCardFragment extends Fragment {
    private FragmentBuyCardBinding mBinding;
    private BuyCardAdapter mAdapter;
    private FirstPageViewModel mViewModel;

    public static BuyCardFragment newInstance() {
        
        Bundle args = new Bundle();
        
        BuyCardFragment fragment = new BuyCardFragment();
        fragment.setArguments(args);
        return fragment;
    }


    public BuyCardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        mViewModel = ViewModelProviders.of(this).get(FirstPageViewModel.class);
        mViewModel.getAllProductsLiveData().observe(this, new Observer<List<Response>>() {
            @Override
            public void onChanged(List<Response> responses) {
                setupAdapter(responses);
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_buy_card, container, false);

        mBinding.cardRecycle.setLayoutManager(new LinearLayoutManager(getActivity()));

        setupToolbar();

        return mBinding.getRoot();
    }

    private void setupToolbar() {
        ((AppCompatActivity)getActivity()).setSupportActionBar(mBinding.cardToolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            getActivity().finish();
            return true;
        } else return super.onOptionsItemSelected(item);
    }

    private void setupAdapter(List<Response> items){
        if (items.size() == 0){
            mBinding.cardRecycle.setVisibility(View.INVISIBLE);
        } else {
            mBinding.cardRecycle.setVisibility(View.VISIBLE);
            mAdapter = new BuyCardAdapter(items, getContext());
            mBinding.cardRecycle.setAdapter(mAdapter);
        }
    }
}
