package com.example.maktabproj.Controller.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import com.example.maktabproj.Controller.fragment.NetCheckerFragment;
import com.example.maktabproj.R;

public class NetworkCheckerActivity extends AppCompatActivity {
    Context mContext;
    private BroadcastReceiver netReceiver;
    private IntentFilter filter;
    private String mNetworkPermission = "check.network.available";

    public static Intent newIntent(Context context){
        return new Intent(context, NetworkCheckerActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_checker);

        mContext = this.getApplicationContext();
        checkInternet();
    }

    private void checkInternet(){
        filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        netReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int[] type = {ConnectivityManager.TYPE_MOBILE, ConnectivityManager.TYPE_WIFI};
                if (!isNetworkAvailable(type)){
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.fragment_container, NetCheckerFragment.newInstance())
                            .commit();
                }
            }
        };
    }

    private boolean isNetworkAvailable(int[] type) {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(CONNECTIVITY_SERVICE);

        for (int netType : type) {
            NetworkInfo networkInfo = cm.getNetworkInfo(netType);
            if (networkInfo!=null && networkInfo.getState() == NetworkInfo.State.CONNECTED){
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(netReceiver, filter, mNetworkPermission, null);
    }

    @Override
    protected void onStop() {
        unregisterReceiver(netReceiver);
        super.onStop();
    }
}
