package com.example.maktabproj.Controller;


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
import com.victor.loading.newton.NewtonCradleLoading;

/**
 * A simple {@link Fragment} subclass.
 */
public class NetCheckerFragment extends Fragment {
    private Merlin mMerlin;
    private MerlinsBeard mMerlinsBeard;
    private Button mRetryButton;
    private NewtonCradleLoading mLoading;
    private NetCheckerFragment mNetCheckerFragment;

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

        mNetCheckerFragment = NetCheckerFragment.newInstance();
        mMerlin = new Merlin.Builder().withDisconnectableCallbacks().build(getContext().getApplicationContext());
        mMerlinsBeard = MerlinsBeard.from(getContext().getApplicationContext());

//        if (!mMerlinsBeard.isConnected()){
//            getActivity().getSupportFragmentManager().beginTransaction()
//                    .add(R.id.fragment_container, NetCheckerFragment.newInstance())
//                    .commit();
//        }

        mMerlin.registerDisconnectable(new Disconnectable() {
            @Override
            public void onDisconnect() {
                Toast.makeText(getContext().getApplicationContext(), "DISCONNECTED", Toast.LENGTH_LONG).show();
//                getActivity().getSupportFragmentManager().beginTransaction()
//                        .add(R.id.fragment_container, mNetCheckerFragment)
//                        .commit();
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_connection_checker, container, false);

        mRetryButton = view.findViewById(R.id.no_connection_btn);
        mLoading = view.findViewById(R.id.loading);
        mRetryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoading.setVisibility(View.VISIBLE);
                mLoading.start();
                mLoading.setLoadingColor(R.color.red);
                if (mMerlinsBeard.isConnected()){
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .remove(NetCheckerFragment.this)
                            .commit();
                    Toast.makeText(getContext().getApplicationContext(), "CONNECTED", Toast.LENGTH_LONG).show();
                    mLoading.setVisibility(View.GONE);
                    mLoading.stop();

                } else {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Snackbar.make(getView(), getActivity().getString(R.string.no_net_again), Snackbar.LENGTH_LONG).show();
                            mLoading.setVisibility(View.GONE);
                            mLoading.stop();
                        }
                    }, 3000);
                }
            }
        });

        return view;
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
