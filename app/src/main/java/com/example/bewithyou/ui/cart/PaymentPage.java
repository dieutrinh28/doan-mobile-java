package com.example.bewithyou.ui.cart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.example.bewithyou.Callback;
import com.example.bewithyou.R;
import com.example.bewithyou.getData;
import com.example.bewithyou.model.Cart;
import com.example.bewithyou.ui.review.RateStoreDialog;
import com.example.bewithyou.ui.review.ReviewPage;

import java.util.List;

public class PaymentPage extends AppCompatActivity {


    GridView gridView;

    TextView price, ship, total;
    Button order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_page);

        getData.init(getApplicationContext());

        gridView = findViewById(R.id.gvItems);
        price = findViewById(R.id.tvPrice);
        ship = findViewById(R.id.tvShip);
        total = findViewById(R.id.tvTotal);
        order = findViewById(R.id.btnOrder);

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PaymentPage.this, RateStoreDialog.class));
            }
        });

        getData.getCardData(new Callback<List<Cart>>() {

            @Override
            public void onSuccess(List<Cart> data) {
                CartAdapter adapter = new CartAdapter(data,getApplicationContext());
                gridView.setAdapter(adapter);
                float sum = 0;
                for (Cart cart  : data) {
                    sum += Float.parseFloat(cart.getPrice()) * Float.parseFloat(cart.getQuantity());
                }
                price.setText(String.valueOf(sum));
                total.setText(String.valueOf(sum));
            }

            @Override
            public void onError(String errorMessage) {

            }
        });
    }

}