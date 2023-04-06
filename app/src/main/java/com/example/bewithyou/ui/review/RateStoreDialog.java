package com.example.bewithyou.ui.review;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import com.example.bewithyou.R;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class RateStoreDialog extends Dialog {


    public RateStoreDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_store_dialog);

        final Button later = findViewById(R.id.btnLater);
        final Button rate = findViewById(R.id.btnRate);
        final MaterialRatingBar ratingBar = findViewById(R.id.ratingBarRate);
        final EditText comment = findViewById(R.id.etComment);

        later.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
}