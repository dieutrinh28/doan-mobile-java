package com.example.bewithyou.ui.cart;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;
import android.widget.TextView;

import com.example.bewithyou.Callback;
import com.example.bewithyou.R;
import com.example.bewithyou.getData;
import com.example.bewithyou.model.Cart;

import java.util.List;

public class PaymentPage extends AppCompatActivity {


    GridView gridView;

    TextView price, ship, total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        getData.init(getApplicationContext());

        gridView = findViewById(R.id.gvItems);
        price = findViewById(R.id.tvPrice);
        ship = findViewById(R.id.tvShip);
        total = findViewById(R.id.tvTotal);

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