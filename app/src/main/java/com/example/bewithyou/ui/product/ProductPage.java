package com.example.bewithyou.ui.product;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.example.bewithyou.Callback;
import com.example.bewithyou.R;
import com.example.bewithyou.getData;
import com.example.bewithyou.model.Product;
import com.example.bewithyou.ui.review.ReviewPage;

import java.util.List;

public class ProductPage extends AppCompatActivity {

    GridView gridView;
    Button btnReviewStore;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_page);


        SharedPreferences preferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        String storeName = preferences.getString("storeName", "default_value");

        getData.init(getApplicationContext());

        gridView = findViewById(R.id.product_display_gridview);
        btnReviewStore = findViewById(R.id.btnReviewStore);


        getData.getProductsInStore(storeName, new Callback<List<Product>>() {
            @Override
            public void onSuccess(List<Product> data) {
                ProductAdapter adapter = new ProductAdapter(data, getApplicationContext());
                gridView.setAdapter(adapter);

                Log.d("TAG", "onSuccess: ");
            }

            @Override
            public void onError(String errorMessage) {

            }
        });

        btnReviewStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductPage.this, ReviewPage.class);
                startActivity(intent);
            }
        });



    }
}