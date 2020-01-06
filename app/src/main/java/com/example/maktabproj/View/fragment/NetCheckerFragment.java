package com.example.maktabproj.View.fragment;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.maktabproj.R;
import com.example.maktabproj.databinding.FragmentConnectionCheckerBinding;
import com.google.android.material.snackbar.Snackbar;
import com.novoda.merlin.Merlin;
import com.novoda.merlin.MerlinsBeard;

/**
 * A simple {@link Fragment} subclass.
 */
public class NetCheckerFragment extends Fragment {
    private Merlin mMerlin;
    private MerlinsBeard mMerlinsBeard;
    private FragmentConnectionCheckerBinding mBinding;

    public static NetCheckerFragment newInstance() {

        Bundle args = new Bundle();

        NetCheckerFragment fragment = new NetCheckerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public NetCheckerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMerlin = new Merlin.Builder().withDisconnectableCallbacks().build(getContext().getApplicationContext());
        mMerlinsBeard = MerlinsBeard.from(getContext().getApplicationContext());

        mMerlin.registerDisconnectable(() -> Toast.makeText(getContext().getApplicationContext(), "DISCONNECTED", Toast.LENGTH_LONG).show());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_connection_checker, container, false);
        initListeners();
        return mBinding.getRoot();
    }

    private void initListeners() {
        mBinding.noConnectionBtn.setOnClickListener(v -> {
            mBinding.loading.setVisibility(View.VISIBLE);
            mBinding.loading.smoothToShow();

            if (mMerlinsBeard.isConnected()){
                getActivity().getSupportFragmentManager().beginTransaction()
                        .remove(NetCheckerFragment.this)
                        .commit();
                Toast.makeText(getContext().getApplicationContext(), "CONNECTED", Toast.LENGTH_LONG).show();
                mBinding.loading.setVisibility(View.GONE);

            } else {
                new Handler().postDelayed(() -> {
                    Snackbar.make(getView(), getActivity().getString(R.string.no_net_again), Snackbar.LENGTH_LONG).show();
                    mBinding.loading.setVisibility(View.GONE);
                }, 3000);
            }
        });
    }

    @Override
    public void onResume() {
        mMerlin.bind();
        if (((AppCompatActivity)getActivity()).getSupportActionBar()!=null)
            ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        super.onResume();

    }

    @Override
    public void onDetach() {
        mMerlin.unbind();
        super.onDetach();
    }
}
