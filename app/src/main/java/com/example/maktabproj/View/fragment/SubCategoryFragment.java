package com.example.maktabproj.View.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.maktabproj.View.adapter.recycler.recyclerViewAdapter.SubCategoryAdapter;
import com.example.maktabproj.Model.Category;
import com.example.maktabproj.R;
import com.example.maktabproj.databinding.FragmentSubCategoryBinding;
import com.example.maktabproj.viewmodel.SubCategoryViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SubCategoryFragment extends Fragment {


    public static final String ARGS_CATEGORY_ID = "category id";
    private int mCategoryId;
    private FragmentSubCategoryBinding mBinding;
    private SubCategoryViewModel mViewModel;
    private SubCategoryAdapter mAdapter;

    public SubCategoryFragment() {
        // Required empty public constructor
    }

    public static SubCategoryFragment newInstance(int categoryId) {

        Bundle args = new Bundle();
        args.putInt(ARGS_CATEGORY_ID, categoryId);

        SubCategoryFragment fragment = new SubCategoryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCategoryId = getArguments().getInt(ARGS_CATEGORY_ID);

        mViewModel = ViewModelProviders.of(this).get(SubCategoryViewModel.class);
        mViewModel.getCategoryLiveData(mCategoryId).observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                setUpAdapter(categories);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_sub_category, container, false);
        mBinding.showCategoryRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        return mBinding.getRoot();
    }

    private void setUpAdapter(List<Category> categories) {
        if (mAdapter==null) {
            mAdapter = new SubCategoryAdapter(categories, getContext());
            mBinding.showCategoryRecycler.setAdapter(mAdapter);
        } else {
            mAdapter.setCategoriesItems(categories);
            mAdapter.notifyDataSetChanged();
        }
    }

}
