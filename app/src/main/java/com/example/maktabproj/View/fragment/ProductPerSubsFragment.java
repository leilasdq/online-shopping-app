package com.example.maktabproj.View.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.maktabproj.View.adapter.recycler.recyclerViewAdapter.ListAllProductAdapter;
import com.example.maktabproj.Model.Response;
import com.example.maktabproj.R;
import com.example.maktabproj.databinding.FragmentProductPerSubsBinding;
import com.example.maktabproj.viewmodel.ProductsPerSubsViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductPerSubsFragment extends Fragment {
    public static final String ARGS_CATEGORY_ID = "category id";
    private int mCategoryId;
    private FragmentProductPerSubsBinding mBinding;
    private ListAllProductAdapter mAdapter;
    private ProductsPerSubsViewModel mViewModel;


    public ProductPerSubsFragment() {
        // Required empty public constructor
    }

    public static ProductPerSubsFragment newInstance(int id) {

        Bundle args = new Bundle();
        args.putInt(ARGS_CATEGORY_ID, id);

        ProductPerSubsFragment fragment = new ProductPerSubsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCategoryId = getArguments().getInt(ARGS_CATEGORY_ID);

        mViewModel = ViewModelProviders.of(this).get(ProductsPerSubsViewModel.class);
        mViewModel.getResponseLiveData(mCategoryId).observe(this, this::setUpAdapter);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_product_per_subs, container, false);
        setupRecycle();
        setupToolbar();
        return mBinding.getRoot();
    }

    private void setupToolbar() {
        mBinding.productPerSub.setTitle("محصولات");
        mBinding.productPerSub.setTitleTextColor(getActivity().getResources().getColor(android.R.color.black));

        ((AppCompatActivity)getActivity()).setSupportActionBar(mBinding.productPerSub);
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

    private void setupRecycle() {
        mBinding.proPerCategory.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void setUpAdapter(List<Response> items){
        if (mAdapter==null) {
            mAdapter = new ListAllProductAdapter(items, getContext());
            mBinding.proPerCategory.setAdapter(mAdapter);
        } else {
            mAdapter.setAllProductList(items);
            mAdapter.notifyDataSetChanged();
        }
    }

}
