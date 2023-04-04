package com.example.bewithyou.ui.home;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.bewithyou.R;
import com.example.bewithyou.ui.cart.CartPage;

public class HomePage extends AppCompatActivity {
    private ImageButton imageButtonCart;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageButtonCart = findViewById(R.id.btnCart);
        imageButtonCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomePage.this, CartPage.class));
            }
        });



    }
}
