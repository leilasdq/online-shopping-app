package com.example.maktabproj.View.fragment;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.maktabproj.View.adapter.recycler.EndlessRecyclerView;
import com.example.maktabproj.View.adapter.recycler.recyclerViewAdapter.ListAllProductAdapter;
import com.example.maktabproj.Model.Response;
import com.example.maktabproj.Network.FetchItems;
import com.example.maktabproj.R;
import com.example.maktabproj.databinding.FragmentListAllProductBinding;
import com.example.maktabproj.viewmodel.ListAllProductsViewModel;

import java.io.IOException;
import java.util.ArrayList;
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_list_all_product, container, false);
        setupRecycle();
        setupToolbar();

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
        mBinding.allListsToolbar.setTitleTextColor(getActivity().getResources().getColor(android.R.color.white));
    }

//    private class GetAllListAsync extends AsyncTask<Void, Void, List<Response>> {
//        private FetchItems mFetchItems = FetchItems.getInstance();
//
//        @Override
//        protected List<Response> doInBackground(Void... voids) {
//
//           mList = new ArrayList<>();
//            try {
//                if (type.equalsIgnoreCase("date")) {
//                    mList = mFetchItems.getAllProductsPerPage(pageNumber);
//                } else if (type.equalsIgnoreCase("popular")) {
//                    mList = mFetchItems.getPopularProductsPerPage(pageNumber);
//                } else if (type.equalsIgnoreCase("rated")) {
//                    mList = mFetchItems.getRatedProductsPerPage(pageNumber);
//                }
//                getAllList.addAll(mList);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return getAllList;
//        }
//
//        @Override
//        protected void onPostExecute(List<Response> responses) {
//            super.onPostExecute(responses);
//            setupAdapter();
//        }
//    }

}
