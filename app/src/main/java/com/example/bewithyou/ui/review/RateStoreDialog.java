package com.example.bewithyou.ui.review;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
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
import com.example.bewithyou.ui.cart.PaymentPage;

import java.text.SimpleDateFormat;
import java.util.Date;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class RateStoreDialog extends Dialog {


    public RateStoreDialog(@NonNull Context context) {
        super(context);
    }

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
                dismiss();
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
                final String userId = "1";
                final String storeName = "Starbucks";
                final String reviewId = "review3";

                getData.writeReview(userId, rating, comment, date, storeName, reviewId, new Callback<String>() {
                    @Override
                    public void onSuccess(String data) {
                    }

                    @Override
                    public void onError(String errorMessage) {

                    }
                });
            }
        });

    }
}