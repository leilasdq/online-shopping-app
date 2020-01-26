package com.example.maktabproj.View.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.maktabproj.R;
import com.example.maktabproj.View.fragment.SearchResultsFragment;

public class SearchResultActivity extends SingleFragmentActivity {

    public static final String EXTRA_SEARCH_QUERY = "com.example.maktabproj.Search query";

    public static Intent newIntent(Context context, String searchQuery){
        Intent intent = new Intent(context, SearchResultActivity.class);
        intent.putExtra(EXTRA_SEARCH_QUERY, searchQuery);
        return intent;
    }

    @Override
    public Fragment createFragment() {
        return SearchResultsFragment.newInstance(getIntent().getStringExtra(EXTRA_SEARCH_QUERY));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_fragment);
    }
}
