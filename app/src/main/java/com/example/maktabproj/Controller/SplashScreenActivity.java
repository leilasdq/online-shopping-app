package com.example.maktabproj.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.example.maktabproj.R;

import gr.net.maroulis.library.EasySplashScreen;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EasySplashScreen config = new EasySplashScreen(SplashScreenActivity.this)
                .withFullScreen()
                .withTargetActivity(MainActivity.class)
                .withSplashTimeOut(4000)
                .withBackgroundResource(R.color.colorPrimary)
                .withHeaderText("Shop here")
                .withFooterText("Copyright 2016")
                .withAfterLogoText("My cool boutique")
                .withLogo(R.drawable.online_shop);

        config.getHeaderTextView().setTextColor(Color.BLACK);
        config.getFooterTextView().setTextColor(Color.BLACK);
        config.getAfterLogoTextView().setTextColor(Color.BLACK);

        View easySplashScreenView = config.create();
        setContentView(easySplashScreenView);

    }
}
