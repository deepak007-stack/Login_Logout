package com.layout.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class splash_screen extends AppCompatActivity {

    private ImageView splash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        getSupportActionBar().hide();
        splash = (ImageView) findViewById(R.id.splash);

        SharedPreferences sharedPreferences = getSharedPreferences("user_data",MODE_PRIVATE);
        Handler handler =new Handler();

        if(sharedPreferences.contains("key_name"))
        {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent =new Intent(getApplicationContext(),Home.class);
                    startActivity(intent);
                    finish();
                }
            },2000);

        }
        else
        {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent =new Intent(getApplicationContext(),Login.class);
                    startActivity(intent);
                    finish();
                }
            },2000);

        }


    }
}