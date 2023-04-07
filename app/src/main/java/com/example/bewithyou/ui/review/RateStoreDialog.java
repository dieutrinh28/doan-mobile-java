package com.example.bewithyou.ui.review;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.bewithyou.Callback;
import com.example.bewithyou.R;
import com.example.bewithyou.SearchPage;
import com.example.bewithyou.getData;
import com.example.bewithyou.model.Review;
import com.example.bewithyou.ui.cart.CartPage;
import com.example.bewithyou.ui.cart.PaymentPage;
import com.example.bewithyou.ui.home.HomePage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class RateStoreDialog extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_store_dialog);

        final Button btnLater = findViewById(R.id.btnLater);
        final Button btnRate = findViewById(R.id.btnRate);
        final MaterialRatingBar rbRate = findViewById(R.id.ratingBarRate);
        final EditText etComment = findViewById(R.id.etComment);

        btnLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RateStoreDialog.this, HomePage.class));
            }
        });

        btnRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String comment = etComment.getText().toString();
                final String rating = String.valueOf(rbRate.getRating());

                Date currentTime = new Date(System.currentTimeMillis());
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                final String date = dateFormat.format(currentTime);

                SharedPreferences userIdRef = getSharedPreferences("MyPreferences", MODE_PRIVATE);
                String username = userIdRef.getString("username", "default_value");
                final String userId = username;

                SharedPreferences storeRef = getSharedPreferences("MyPreferences", MODE_PRIVATE);
                String storeName = storeRef.getString("storeName", "Starbucks");

                getData.addNewReview(userId, rating, comment, date, storeName);
                startActivity(new Intent(RateStoreDialog.this, HomePage.class));

            }
        });


    }
}