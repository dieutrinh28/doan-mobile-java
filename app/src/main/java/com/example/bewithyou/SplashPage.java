package com.example.bewithyou;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.example.bewithyou.ui.auth.LoginPage;
import com.example.bewithyou.ui.home.HomePage;

public class SplashPage extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_page);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                nextActivity();
            }
        }, 1000);

    }

    private void nextActivity() {
        SharedPreferences preferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        String username = preferences.getString("username", "default_value");

        if (!username.equals("")) {
            // Email exists, skip login page and go to home page
            startActivity(new Intent(SplashPage.this, HomePage.class));
            finish();
        } else {
            // Email does not exist, go to login page
            startActivity(new Intent(SplashPage.this, LoginPage.class));
            finish();
        }

    }
}