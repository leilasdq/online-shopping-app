package com.example.maktabproj.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.maktabproj.R;

public class SplashScreenActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 6000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = MainActivity.newIntent(SplashScreenActivity.this);
                startActivity(intent);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
