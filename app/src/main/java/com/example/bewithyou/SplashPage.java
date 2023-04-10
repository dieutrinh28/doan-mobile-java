package com.example.bewithyou;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.bewithyou.ui.auth.LoginPage;
import com.example.bewithyou.ui.home.HomePage;
import com.example.bewithyou.ui.review.ReviewPage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Intent intent = new Intent(this, LoginPage.class);
          startActivity(intent);
//        if (user == null) {
//            Intent intent = new Intent(this, LoginPage.class);
//            startActivity(intent);
//        }
//        else {
//            Intent intent = new Intent(this, HomePage.class);
//            startActivity(intent);
//        }
        finish();
    }
}