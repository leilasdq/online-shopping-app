package com.example.maktabproj.Controller.adapter.recycler.viewPagerAdapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.maktabproj.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageViewAdapter extends PagerAdapter {
    Context mContext;
    List<String> imageUrls;

    public ImageViewAdapter(Context context, List imageUrls) {
        mContext = context;
        this.imageUrls = imageUrls;
    }

    @Override
    public int getCount() {
        return imageUrls.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imageView = new ImageView(mContext);
        Picasso.with(mContext).load(imageUrls.get(position)).fit().centerInside().placeholder(R.drawable.image_loading).into(imageView);
        container.addView(imageView);

        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
