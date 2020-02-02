package com.example.maktabproj.View.fragment;


import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.maktabproj.R;
import com.example.maktabproj.databinding.FragmentCreateUserBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateUserFragment extends Fragment {
    private FragmentCreateUserBinding mBinding;

    public static CreateUserFragment newInstance() {
        
        Bundle args = new Bundle();
        
        CreateUserFragment fragment = new CreateUserFragment();
        fragment.setArguments(args);
        return fragment;
    }


    public CreateUserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_create_user, container, false);
        mBinding.signupBtn.setBackgroundColor(getActivity().getResources().getColor(R.color.colorUserRed));

        mBinding.backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        return mBinding.getRoot();
    }

}
