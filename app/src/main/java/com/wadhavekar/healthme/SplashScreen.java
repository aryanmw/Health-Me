package com.wadhavekar.healthme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {
    public ImageView logo;
    public TextView name;
    private static int splashTimeOut = 1000;
    Boolean isFirstRun = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
//        logo = findViewById(R.id.iv_logo);
//        name = findViewById(R.id.tv_appName);

        isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getBoolean("isFirstRun", true);

        if (isFirstRun) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    Intent intent = new Intent(SplashScreen.this,Welcome.class);
                    startActivity(intent);
                    finish();
                }
            },splashTimeOut);



        }

        else  {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                   Intent intent1 = new Intent(SplashScreen.this,Profile.class);
                   startActivity(intent1);
                   finish();
                }
            },splashTimeOut);
        }


    }
}
