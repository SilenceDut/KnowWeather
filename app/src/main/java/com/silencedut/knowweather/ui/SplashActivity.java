package com.silencedut.knowweather.ui;

import android.content.Intent;
import android.widget.FrameLayout;

import com.silencedut.knowweather.R;
import com.silencedut.weather_core.corebase.BaseActivity;

import butterknife.BindView;

/**
 * Created by SilenceDut on 2016/11/25 .
 */

public class SplashActivity extends BaseActivity {
    @BindView(R.id.splash_fl)
    FrameLayout mSplashFl;


    @Override
    public int getContentViewId() {
        return R.layout.weather_activity_splash;
    }

    @Override
    public void initViews() {

        mSplashFl.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2000);

    }

}
