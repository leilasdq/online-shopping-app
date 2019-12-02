package com.example.maktabproj.Controller;


import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.maktabproj.Model.ImagesItem;
import com.example.maktabproj.Model.Response;
import com.example.maktabproj.Network.FetchItems;
import com.example.maktabproj.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewItemsFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private List<Response> items;
    private FetchItems fetchItems;
    private SliderLayout sliderLayout;

    private static final String TAG = "NewItemsFragment";

    public NewItemsFragment() {
        // Required empty public constructor
    }

    public static NewItemsFragment newInstance() {

        Bundle args = new Bundle();

        NewItemsFragment fragment = new NewItemsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fetchItems = new FetchItems();

        ProductsAsync async = new ProductsAsync();
        async.execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_item, container, false);

        sliderLayout = view.findViewById(R.id.slider);
        sliderSetup();
        mRecyclerView = view.findViewById(R.id.recycle);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        setupAdapter();

        return view;
    }

    private void sliderSetup() {
        HashMap<String, Integer> pics = new HashMap<>();
        pics.put("First Pic", R.drawable.one);
        pics.put("Second Pic", R.drawable.two);
        pics.put("Third Pic", R.drawable.three);
        pics.put("Fourth Pic", R.drawable.four);
        for (String name :
                pics.keySet()) {
            TextSliderView textSliderView = new TextSliderView(getContext());
            textSliderView.description(name)
                    .image(pics.get(name))
                    .setScaleType(BaseSliderView.ScaleType.CenterCrop);
            sliderLayout.addSlider(textSliderView);
        }
    }

    private void setupAdapter() {
        if (isAdded()) {
            ProductAdapter adapter = new ProductAdapter();
            adapter.setResponseList(items);
            mRecyclerView.setAdapter(adapter);
        }
    }

    private class ProductViewHolder extends RecyclerView.ViewHolder {

        ImageView productImage;
        TextView productName;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.pro_img);
            productName = itemView.findViewById(R.id.pro_name);
        }

        public void bind(Response response){
            productName.setText(response.getName());
            ImagesItem src = response.getImages().get(0);
            Picasso.with(getContext()).load(Uri.parse(src.getSrc())).placeholder(R.drawable.image_loading).error(R.drawable.image_error).into(productImage);
        }
    }

    private class ProductAdapter extends RecyclerView.Adapter<ProductViewHolder>{

        private List<Response> mResponseList;

        public void setResponseList(List<Response> responseList) {
            mResponseList = responseList;
        }

        @NonNull
        @Override
        public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.list_items_layout, parent, false);
            return new ProductViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
            holder.bind(mResponseList.get(position));
        }

        @Override
        public int getItemCount() {
            return mResponseList.size();
        }
    }

    private class ProductsAsync extends AsyncTask<Void, Void, List<Response>>{

        @Override
        protected List<Response> doInBackground(Void... voids) {
            items = new ArrayList<>();
            try {
                items = fetchItems.getAllProducts();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return items;
        }

        @Override
        protected void onPostExecute(List<Response> responses) {
            super.onPostExecute(responses);
            setupAdapter();
            Log.e(TAG, "onPostExecute: items size" + items.size());
        }
    }

}
