package cn.ucai.fulicenter.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.activity.utils.MFGT;

public class SplashActivity extends AppCompatActivity {

    private final long sleepTime = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onStart() {
        super.onStart();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                MFGT.gotoMainActivity(SplashActivity.this);
                finish();
            }
        }, sleepTime);
    }
}
