package com.example.maktabproj.Controller.fragment;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.maktabproj.R;
import com.google.android.material.snackbar.Snackbar;
import com.novoda.merlin.Disconnectable;
import com.novoda.merlin.Merlin;
import com.novoda.merlin.MerlinsBeard;
import com.wang.avi.AVLoadingIndicatorView;

/**
 * A simple {@link Fragment} subclass.
 */
public class NetCheckerFragment extends Fragment {
    private Merlin mMerlin;
    private MerlinsBeard mMerlinsBeard;
    private Button mRetryButton;
    private AVLoadingIndicatorView mLoadingIndicatorView;

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

        mMerlin.registerDisconnectable(new Disconnectable() {
            @Override
            public void onDisconnect() {
                Toast.makeText(getContext().getApplicationContext(), "DISCONNECTED", Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_connection_checker, container, false);

        initUi(view);
        initListeners();

        return view;
    }

    private void initListeners() {
        mRetryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoadingIndicatorView.setVisibility(View.VISIBLE);
                mLoadingIndicatorView.smoothToShow();

                if (mMerlinsBeard.isConnected()){
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .remove(NetCheckerFragment.this)
                            .commit();
                    Toast.makeText(getContext().getApplicationContext(), "CONNECTED", Toast.LENGTH_LONG).show();
                    mLoadingIndicatorView.setVisibility(View.GONE);

                } else {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Snackbar.make(getView(), getActivity().getString(R.string.no_net_again), Snackbar.LENGTH_LONG).show();
                            mLoadingIndicatorView.setVisibility(View.GONE);
                        }
                    }, 3000);
                }
            }
        });
    }

    private void initUi(View view) {
        mRetryButton = view.findViewById(R.id.no_connection_btn);
        mLoadingIndicatorView = view.findViewById(R.id.loading);
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
