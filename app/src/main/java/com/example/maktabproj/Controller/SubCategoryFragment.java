package com.example.maktabproj.Controller;


import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.maktabproj.Model.CategoriesItem;
import com.example.maktabproj.Network.FetchItems;
import com.example.maktabproj.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SubCategoryFragment extends Fragment {


    public static final String ARGS_CATEGORY_ID = "category id";
    private int mCategoryId;
    private List<CategoriesItem> mList = new ArrayList<>();

    private RecyclerView mRecyclerView;

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

        GetSubCategories async = new GetSubCategories();
        async.execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sub_category, container, false);

        mRecyclerView = view.findViewById(R.id.show_category_recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

    private void setUpAdapter(){
            SubCategoryAdapter adapter = new SubCategoryAdapter(mList);
            mRecyclerView.setAdapter(adapter);
    }

    private class SubCategoryViewHolder extends RecyclerView.ViewHolder{
        private ImageView categoryImage;
        private TextView categoryName;
        CategoriesItem mCategoriesItem;

        public SubCategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            categoryImage = itemView.findViewById(R.id.category_image);
            categoryName = itemView.findViewById(R.id.category_name);
        }

        private void bind(CategoriesItem categoriesItem){
            mCategoriesItem = categoriesItem;
            categoryName.setText(mCategoriesItem.getName());
//            Picasso.with(getContext()).load(mCategoriesItem.get)
        }
    }

    private class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryViewHolder>{
        List<CategoriesItem> mCategoriesItems = new ArrayList<>();

        public SubCategoryAdapter(List<CategoriesItem> categoriesItems) {
            mCategoriesItems = categoriesItems;
        }

        public void setCategoriesItems(List<CategoriesItem> categoriesItems) {
            mCategoriesItems = categoriesItems;
        }

        @NonNull
        @Override
        public SubCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.sub_category_list_item, parent, false);
            return new SubCategoryViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull SubCategoryViewHolder holder, int position) {
            holder.bind(mCategoriesItems.get(position));
        }

        @Override
        public int getItemCount() {
            return mCategoriesItems.size();
        }
    }

    private class GetSubCategories extends AsyncTask<Void, Void, Void>{
        private FetchItems mFetchItems = FetchItems.getInstance();

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                mList = mFetchItems.getSubCategory(mCategoryId);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            setUpAdapter();
        }
    }

}
