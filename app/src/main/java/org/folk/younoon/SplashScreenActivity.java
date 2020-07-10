package org.folk.younoon;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class SplashScreenActivity extends AppCompatActivity {
    private SliderPrefManager prefMan;
    @Override
    protected void onResume() {

        super.onResume();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        prefMan = new SliderPrefManager(this);
        haveNetwork();

    }

    public void haveNetwork(){
        //For 3G check
        ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

        boolean is3g = Objects.requireNonNull(manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE))
                .isConnectedOrConnecting();
        //For WiFi Check
        boolean isWifi = Objects.requireNonNull(manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI))
                .isConnectedOrConnecting();
        System.out.println(is3g + " net " + isWifi);
        if (!is3g && !isWifi){
            Refresh();
        } else {
            checkSplash();
        }
    }
    void checkSplash(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                if (!prefMan.startSlider()) {
                    intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                } else {
                    intent = new Intent(SplashScreenActivity.this, IntroSliderActivity.class);
                }
                startActivity(intent);
                finish();
            }
        }, 3000);
    }


    public void Refresh() {
        BottomSheetFragment bottomSheetDialog = BottomSheetFragment.newInstance();
        bottomSheetDialog.show(getSupportFragmentManager(), "Bottom Sheet Dialog Fragment");
        BottomSheetFragment bottomSheetFragment = new BottomSheetFragment();
        bottomSheetFragment.setCancelable(false);
        bottomSheetFragment.setListener(() -> {
            bottomSheetFragment.dismiss();
            finish();
            startActivity(getIntent());
        });
        bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());

    }

}