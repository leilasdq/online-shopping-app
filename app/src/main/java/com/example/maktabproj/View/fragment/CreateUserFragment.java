package com.example.maktabproj.View.fragment;


import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.maktabproj.R;
import com.example.maktabproj.View.activity.CreateUserActivity;
import com.example.maktabproj.View.activity.LoginUserActivity;
import com.example.maktabproj.databinding.FragmentCreateUserBinding;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.regex.Pattern;

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

        setupListeners();

        return mBinding.getRoot();
    }

    private void setupListeners() {
        mBinding.backArrow.setOnClickListener(v -> getActivity().finish());

        mBinding.signupBtn.setOnClickListener(v -> {
            if (!validateMail() | ! validateUsername() | !validatePassword()){
                Snackbar.make(mBinding.getRoot(), "مقادیر خواسته شده زا پر کنید", BaseTransientBottomBar.LENGTH_LONG).show();
                return;
            } else {
                Toast.makeText(getActivity(), "email = " + mBinding.textInputEmail.getEditText().getText().toString()
                + "\n first = " + mBinding.textInputFirstName.getEditText().getText().toString()
                + "\n last = " + mBinding.textLastName.getEditText().getText().toString()
                + "\n username = " + mBinding.textInputUserName.getEditText().getText().toString()
                + "\n password = " + mBinding.textInputPassword.getEditText().getText().toString()
                , Toast.LENGTH_LONG).show();
            }
        });

        mBinding.goToLoginPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(LoginUserActivity.newIntent(getActivity()));
                getActivity().finish();
            }
        });
    }

    private boolean validateMail(){
        String emailAddress = mBinding.textInputEmail.getEditText().getText().toString().trim();

        if (emailAddress.isEmpty()) {
            mBinding.textInputEmail.setError(getActivity().getResources().getString(R.string.email_requer_txt));
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()) {
            mBinding.textInputEmail.setError(getActivity().getResources().getString(R.string.valid_email_txt));
            return false;
        } else {
            mBinding.textInputEmail.setError(null);
            return true;
        }
    }

    private boolean validateUsername(){
        String username = mBinding.textInputUserName.getEditText().getText().toString().trim();

        if (username.isEmpty()) {
            mBinding.textInputUserName.setError(getActivity().getResources().getString(R.string.username_requier_txt));
            return false;
        } else if (username.length() > 10){
            mBinding.textInputUserName.setError(getActivity().getResources().getString(R.string.long_user_name_txt));
            return false;
        } else {
            mBinding.textInputUserName.setError(null);
            return true;
        }
    }

    private boolean validatePassword(){
        String password = mBinding.textInputPassword.getEditText().getText().toString().trim();

        if (password.isEmpty()) {
            mBinding.textInputPassword.setError(getActivity().getResources().getString(R.string.pass_requiere_txt));
            return false;
        } else if (password.length() < 6){
            mBinding.textInputPassword.setError(getActivity().getResources().getString(R.string.short_pass_txt));
            return false;
        } else {
            mBinding.textInputPassword.setError(null);
            return true;
        }
    }

}
