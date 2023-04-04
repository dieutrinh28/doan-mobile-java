package com.example.bewithyou.ui.product;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;

import com.example.bewithyou.Callback;
import com.example.bewithyou.R;
import com.example.bewithyou.getData;
import com.example.bewithyou.model.Product;

import java.util.List;

public class ProductPage extends AppCompatActivity {

    GridView gridView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_page);


        getData.init(getApplicationContext());

        gridView = findViewById(R.id.gridView);

        getData.getProductsInStore("Starbucks",new Callback<List<Product>>() {
            @Override
            public void onSuccess(List<Product> data) {
                ProductAdapter adapter = new ProductAdapter(data, getApplicationContext());
                gridView.setAdapter(adapter);
                for (Product hi: data
                     ) {
                    System.out.println(hi.toString());

                }
                Log.d("TAG", "onSuccess: ");
            }

            @Override
            public void onError(String errorMessage) {

            }
        });
    }
}