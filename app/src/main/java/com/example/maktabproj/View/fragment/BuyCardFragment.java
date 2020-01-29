package com.example.maktabproj.View.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.maktabproj.R;
import com.example.maktabproj.databinding.FragmentBuyCardBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class BuyCardFragment extends Fragment {
    private FragmentBuyCardBinding mBinding;

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
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_buy_card, container, false);

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
}
