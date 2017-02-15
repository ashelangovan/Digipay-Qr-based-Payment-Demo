package com.cfd.freya.digipay.starter;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.cfd.freya.digipay.CommonUtils;
import com.cfd.freya.digipay.Constants;
import com.cfd.freya.digipay.MainActivity;
import com.cfd.freya.digipay.R;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
       // if(!CommonUtils.locationPermission(getApplicationContext(),this))
            startSplashScreen();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case 23:{
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay!
                    startSplashScreen();
                }
            }
        }
    }

    public void startSplashScreen() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(SplashScreen.this, Login.class);
                SplashScreen.this.startActivity(intent);
                SplashScreen.this.finish();
                Log.d("SS","intent");
            }

        }, Constants.SPLASH_DISPLAY_TIME);
    }
}
