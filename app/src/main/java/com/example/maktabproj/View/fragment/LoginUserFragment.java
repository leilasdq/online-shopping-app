package com.example.maktabproj.View.fragment;


import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.maktabproj.R;
import com.example.maktabproj.databinding.FragmentCreateUserBinding;
import com.example.maktabproj.databinding.FragmentLoginUserBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginUserFragment extends Fragment {
    private FragmentLoginUserBinding mBinding;


    public LoginUserFragment() {
        // Required empty public constructor
    }

    public static LoginUserFragment newInstance() {
        
        Bundle args = new Bundle();
        
        LoginUserFragment fragment = new LoginUserFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_login_user, container, false);
        mBinding.loginBtn.setBackgroundColor(getActivity().getResources().getColor(R.color.colorUserRed));

        mBinding.backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        return mBinding.getRoot();
    }

}
