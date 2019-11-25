package com.example.maktabproj.Controller;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.maktabproj.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MostVisitedFragment extends Fragment {


    public MostVisitedFragment() {
        // Required empty public constructor
    }

    public static MostVisitedFragment newInstance() {
        
        Bundle args = new Bundle();
        
        MostVisitedFragment fragment = new MostVisitedFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_most_visited, container, false);
    }

}
