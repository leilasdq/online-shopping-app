package com.example.maktabproj.Adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ListAllProductTypeAdapter extends RecyclerView.Adapter<ListAllProductTypeAdapter.ListAllProductViewHolder> {
    private List allProductList = new ArrayList();

    public void setAllProductList(List allProductList) {
        this.allProductList = allProductList;
    }

    public ListAllProductTypeAdapter(List allProductList) {
        this.allProductList = allProductList;
    }

    @NonNull
    @Override
    public ListAllProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ListAllProductViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ListAllProductViewHolder extends RecyclerView.ViewHolder{

        public ListAllProductViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
