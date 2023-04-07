package com.example.bewithyou;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.List;

public class ProductPage extends AppCompatActivity {

    GridView gridView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_page);

        getData.init(getApplicationContext());

        gridView = findViewById(R.id.product_display_gridview);

        getData.getProductsInStore("Starbucks",new Callback<List<Product>>() {
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

    }
}