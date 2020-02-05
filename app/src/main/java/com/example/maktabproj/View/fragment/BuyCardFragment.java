package com.example.maktabproj.View.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.maktabproj.Model.Response;
import com.example.maktabproj.Model.entities.Products;
import com.example.maktabproj.Network.FetchItems;
import com.example.maktabproj.R;
import com.example.maktabproj.View.adapter.recycler.recyclerViewAdapter.BuyCardAdapter;
import com.example.maktabproj.databinding.FragmentBuyCardBinding;
import com.example.maktabproj.repository.BuyingCardRepository;
import com.example.maktabproj.viewmodel.DetailViewModel;
import com.example.maktabproj.viewmodel.FirstPageViewModel;
import com.example.maktabproj.viewmodel.ListAllProductsViewModel;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BuyCardFragment extends Fragment {
    private FragmentBuyCardBinding mBinding;
    private BuyCardAdapter mAdapter;
    private DetailViewModel mViewModel;
    private List<Products> mProducts;
    private List<Response> mResponseList;
    private int mAllPrice = 0;
    private int mNumOfItems;
    NumberFormat formatter = new DecimalFormat("#,###");

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

        mProducts = BuyingCardRepository.getInstance(getActivity()).getAllBuyingProducts();
        mResponseList = new ArrayList<>();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_buy_card, container, false);
        mBinding.addToCartBtn.setBackgroundColor(getActivity().getResources().getColor(R.color.categoryButtonColor));

        mViewModel = ViewModelProviders.of(this).get(DetailViewModel.class);

        if (mProducts.size() == 0){
            mBinding.cardRecycle.setVisibility(View.INVISIBLE);
            mBinding.card.setVisibility(View.INVISIBLE);
        } else {
            mBinding.cardRecycle.setVisibility(View.VISIBLE);
            mBinding.card.setVisibility(View.VISIBLE);
            for (int i = 0; i < mProducts.size() ; i++) {
                mNumOfItems = 1;
                mNumOfItems = mProducts.get(i).getCounts();
                mViewModel.getResponseLiveData(mProducts.get(i).getIdInSite()).observe(this, new Observer<Response>() {
                    @Override
                    public void onChanged(Response response) {
                        mResponseList.add(response);
                        mAllPrice += (Integer.valueOf(response.getPrice())*mNumOfItems);
                        setupAdapter(mResponseList);
                    }
                });
            }
        }

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
        mBinding.cardRecycle.setLayoutManager(new LinearLayoutManager(getActivity()));
        String formattedOriginal = formatter.format(Long.parseLong(String.valueOf(mAllPrice)));
        mBinding.allPriceCount.setText(formattedOriginal.concat(getActivity().getString(R.string.Tooman)));
        if (mAdapter == null){
            mAdapter = new BuyCardAdapter(items, getContext());
            mBinding.cardRecycle.setAdapter(mAdapter);
        } else {
            mAdapter.setBuyProducts(items);
            mAdapter.notifyDataSetChanged();
        }
    }
}
