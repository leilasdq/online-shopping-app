package com.example.maktabproj.Controller;


import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.maktabproj.Model.ImagesItem;
import com.example.maktabproj.Model.Response;
import com.example.maktabproj.Network.FetchItems;
import com.example.maktabproj.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.maktabproj.Controller.FirstPageFragment.RESPONSE_ID_EXTRA;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListAllProductFragment extends Fragment {

    public static final String GET_PRODUCT_TYPE_ARGS = "get product type";
    private String type;
    private int pageNumber = 1;

    private EndlessRecyclerView scrollListener;
    private LinearLayoutManager manager;

    private RecyclerView allProductsRecycle;
    private Toolbar mToolbar;
    private List<Response> mList;
    private List<Response> getAllList = new ArrayList<>();
    private ListAllProductAdapter adapter;

    public static ListAllProductFragment newInstance(String productType) {

        Bundle args = new Bundle();
        args.putString(GET_PRODUCT_TYPE_ARGS, productType);

        ListAllProductFragment fragment = new ListAllProductFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public ListAllProductFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        type = getArguments().getString(GET_PRODUCT_TYPE_ARGS);
        GetAllListAsync getLists = new GetAllListAsync();
        getLists.execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_all_product, container, false);

        initViews(view);
        setupToolbar();

        scrollListener = new EndlessRecyclerView(manager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                pageNumber++;
                GetAllListAsync getLists = new GetAllListAsync();
                getLists.execute();

//                scrollListener.resetState();
            }
        };
        allProductsRecycle.addOnScrollListener(scrollListener);

        return view;
    }

    private void initViews(View view) {
        allProductsRecycle = view.findViewById(R.id.all_product_recycler);
        manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mToolbar = view.findViewById(R.id.all_lists_toolbar);
        allProductsRecycle.setLayoutManager(manager);
    }

    private class ListAllProductViewHolder extends RecyclerView.ViewHolder {
        private Response mResponse;
        private ImageView proImage;
        private TextView name;
        private TextView shortDes;
        private TextView realPrice;
        private TextView salePrice;

        public ListAllProductViewHolder(@NonNull View itemView) {
            super(itemView);

            proImage = itemView.findViewById(R.id.all_list_product_image);
            name = itemView.findViewById(R.id.all_list_product_name);
            shortDes = itemView.findViewById(R.id.all_list_product_short_des);
            realPrice = itemView.findViewById(R.id.all_list_product_real_price);
            salePrice = itemView.findViewById(R.id.all_list_product_sale_price);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = DetailProductActivity.newIntent(getActivity(), mResponse.getId());
                    startActivity(intent);
                }
            });
        }

        private void bind(Response response) {
            mResponse = response;

            name.setText(mResponse.getName());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                shortDes.setText(Html.fromHtml(mResponse.getShortDescription(), Html.FROM_HTML_MODE_LEGACY));
            } else  shortDes.setText(Html.fromHtml(mResponse.getShortDescription()));
            String original = response.getRegularPrice();
            String sale = response.getSalePrice();
            realPrice.setText(original.concat(getString(R.string.Tooman)));
            if (!sale.equalsIgnoreCase("")) {
                salePrice.setText(sale.concat(getString(R.string.Tooman)));
                salePrice.setVisibility(View.VISIBLE);
                realPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                realPrice.setPaintFlags(realPrice.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                salePrice.setVisibility(View.INVISIBLE);
                realPrice.setGravity(Gravity.CENTER_VERTICAL);
            }
            ImagesItem src = response.getImages().get(0);
            Picasso.with(getContext()).load(Uri.parse(src.getSrc())).placeholder(R.drawable.image_loading).error(R.drawable.image_error).into(proImage);

        }
    }

    private class ListAllProductAdapter extends RecyclerView.Adapter<ListAllProductViewHolder> {

        private List<Response> allProductList = new ArrayList<>();

        public void setAllProductList(List<Response> allProductList) {
            this.allProductList = allProductList;
        }

        @NonNull
        @Override
        public ListAllProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.all_list_items, parent, false);
            return new ListAllProductViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ListAllProductViewHolder holder, int position) {
            holder.bind(allProductList.get(position));
        }

        @Override
        public int getItemCount() {
            return allProductList.size();
        }
    }

    private void setupAdapter() {
        if (isAdded()) {
            if (adapter==null) {
                adapter = new ListAllProductAdapter();
                adapter.setAllProductList(getAllList);
                allProductsRecycle.setAdapter(adapter);
            }
            else {
                adapter.setAllProductList(getAllList);
                adapter.notifyDataSetChanged();
            }
        }
    }

    private void setupToolbar() {
        if (type.equalsIgnoreCase("date")) {
            mToolbar.setTitle(getString(R.string.all_products));
        } else if (type.equalsIgnoreCase("popular")) {
            mToolbar.setTitle(getString(R.string.all_popular));
        } else if (type.equalsIgnoreCase("rated")) {
            mToolbar.setTitle(getString(R.string.all_rated));
        }
    }

    private class GetAllListAsync extends AsyncTask<Void, Void, List<Response>> {
        private FetchItems mFetchItems = FetchItems.getInstance();

        @Override
        protected List<Response> doInBackground(Void... voids) {

           mList = new ArrayList<>();
            try {
                if (type.equalsIgnoreCase("date")) {
                    mList = mFetchItems.getAllProductsPerPage(pageNumber);
                } else if (type.equalsIgnoreCase("popular")) {
                    mList = mFetchItems.getPopularProductsPerPage(pageNumber);
                } else if (type.equalsIgnoreCase("rated")) {
                    mList = mFetchItems.getRatedProductsPerPage(pageNumber);
                }
                getAllList.addAll(mList);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return getAllList;
        }

        @Override
        protected void onPostExecute(List<Response> responses) {
            super.onPostExecute(responses);
            setupAdapter();
        }
    }

}
