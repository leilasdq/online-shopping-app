package com.example.maktabproj.View.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.maktabproj.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BuyCardFragment extends Fragment {

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_buy_card, container, false);
    }

}
