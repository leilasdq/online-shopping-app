package com.example.maktabproj.View.fragment;


import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.maktabproj.Model.entities.Products;
import com.example.maktabproj.View.adapter.recycler.viewPagerAdapter.ImageViewAdapter;
import com.example.maktabproj.Model.ImagesItem;
import com.example.maktabproj.Model.Response;
import com.example.maktabproj.R;
import com.example.maktabproj.databinding.FragmentDetailProductBinding;
import com.example.maktabproj.repository.BuyingCardRepository;
import com.example.maktabproj.sharedprefs.BadgePrefs;
import com.example.maktabproj.viewmodel.DetailViewModel;
import com.google.android.material.appbar.AppBarLayout;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailProductFragment extends Fragment {

    public static final String ARGS_PRODUCT_ID = "mResponse id";
    private int productId;
    private Response mResponse;
    private ImageViewAdapter mViewAdapter;
    private FragmentDetailProductBinding mBinding;
    private DetailViewModel mViewModel;

    public DetailProductFragment() {
        // Required empty public constructor
    }

    public static DetailProductFragment newInstance(int id) {
        Bundle args = new Bundle();
        args.putInt(ARGS_PRODUCT_ID, id);

        DetailProductFragment fragment = new DetailProductFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        mResponse = new Response();
        productId = getArguments().getInt(ARGS_PRODUCT_ID);

        mViewModel = ViewModelProviders.of(this).get(DetailViewModel.class);
        mViewModel.getResponseLiveData(productId).observe(this, new Observer<Response>() {
            @Override
            public void onChanged(Response response) {
                updateUI(response);
            }

            private void updateUI(Response response) {
                mResponse = response;
                mBinding.addToCartBtn.setVisibility(View.VISIBLE);
                mBinding.showProductProgress.setVisibility(View.INVISIBLE);
                mBinding.addToCartBtn.setBackgroundColor(getActivity().getResources().getColor(R.color.categoryButtonColor));

                mBinding.appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
                    boolean isShow = true;
                    int scrollRange = -1;

                    @Override
                    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                        if (scrollRange == -1) {
                            scrollRange = appBarLayout.getTotalScrollRange();
                        }
                        if (scrollRange + verticalOffset == 0) {
                            mBinding.collapsing.setTitle(mResponse.getName());
                            mBinding.collapsing.setCollapsedTitleTextColor(getResources().getColor(android.R.color.black));
                            isShow = true;
                        } else if(isShow) {
                            mBinding.collapsing.setTitle(" ");//careful there should a space between double quote otherwise it wont work
                            isShow = false;
                        }
                    }
                });

                mBinding.productName.setText(mResponse.getName());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    mBinding.productDetail.setText(Html.fromHtml(mResponse.getDescription(), Html.FROM_HTML_MODE_LEGACY));
                } else  mBinding.productDetail.setText(Html.fromHtml(mResponse.getDescription()));

                List<ImagesItem> images = mResponse.getImages();
                List<String> urls = new ArrayList<>();
                for (int i = 0; i < images.size(); i++) {
                    urls.add(images.get(i).getSrc());
                }
                mViewAdapter = new ImageViewAdapter(getActivity(), urls);
                mBinding.productImages.setAdapter(mViewAdapter);

                NumberFormat formatter = new DecimalFormat("#,###");
                String original = mResponse.getRegularPrice();
                String sale = mResponse.getSalePrice();
                String formattedOriginal = formatter.format(Long.parseLong(original));
                mBinding.productRealPrice.setText(formattedOriginal.concat(getString(R.string.Tooman)));
                if (!sale.equalsIgnoreCase("")){
                    String formattedSale = formatter.format(Long.parseLong(sale));
                    mBinding.productSalePrice.setText(formattedSale.concat(getString(R.string.Tooman)));
                    mBinding.productRealPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                } else {
                    mBinding.productRealPrice.setPaintFlags( mBinding.productRealPrice.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
                    mBinding.productSalePrice.setVisibility(View.INVISIBLE);
                    mBinding.productRealPrice.setGravity(Gravity.CENTER_VERTICAL);
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_detail_product, container, false );

        mBinding.showProductToolbar.setTitleTextColor(getResources().getColor(android.R.color.transparent));
        ((AppCompatActivity)getActivity()).setSupportActionBar(mBinding.showProductToolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mBinding.addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BadgePrefs.setBadgeCount(getContext(), BadgePrefs.getBadgeCount(getContext())+1);
                Products products = new Products(mResponse.getId(), 1);
                BuyingCardRepository.getInstance(getActivity()).addTodoItem(products);
                Toast.makeText(getContext(), "محصول به سبد خرید شما اضافه شد.", Toast.LENGTH_SHORT).show();
            }
        });

        return mBinding.getRoot();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            getActivity().invalidateOptionsMenu();
            getActivity().finish();
            return true;
        } else return super.onOptionsItemSelected(item);
    }
}
