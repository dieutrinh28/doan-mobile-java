package com.example.bewithyou.ui.order;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bewithyou.Callback;
import com.example.bewithyou.ExpandableHeightGridView;
import com.example.bewithyou.R;
import com.example.bewithyou.getData;
import com.example.bewithyou.model.Cart;
import com.example.bewithyou.model.Store;
import com.example.bewithyou.model.User;
import com.example.bewithyou.ui.review.RateStoreDialog;

import java.util.List;

public class PaymentPage extends AppCompatActivity {


    ExpandableHeightGridView gridView;

    TextView price, ship, total, storeAddress, personal, location;
    Button order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_page);

        getData.init(getApplicationContext());

        storeAddress = findViewById(R.id.btnStoreAddress);
        location = findViewById(R.id.btnLocation);
        personal = findViewById(R.id.btnPersonal);

        SharedPreferences preferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        String username = preferences.getString("username", "default_value");
        String storeName = preferences.getString("storeName", "default_value");

        getData.getUser(username, new Callback<User>() {
            @Override
            public void onSuccess(User data) {
                location.setText(data.getAddress());
                personal.setText(data.getUserName() + " - " + data.getPhoneNum());
            }

            @Override
            public void onError(String errorMessage) {

            }
        });

        getData.getSpecificStore(storeName, new Callback<Store>() {
            @Override
            public void onSuccess(Store data) {
                storeAddress.setText(data.getStoreAddress());
            }

            @Override
            public void onError(String errorMessage) {

            }
        });


        gridView = findViewById(R.id.gvItems);
        gridView.setExpanded(true);
        price = findViewById(R.id.tvPrice);
        ship = findViewById(R.id.tvShip);
        total = findViewById(R.id.tvTotal);
        order = findViewById(R.id.btnOrder);

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(PaymentPage.this, RateStoreDialog.class);
                startActivity(intent);
            }
        });

        getData.getCardData(new Callback<List<Cart>>() {

            @Override
            public void onSuccess(List<Cart> data) {
                OrderAdapter adapter = new OrderAdapter(data, getApplicationContext());
                gridView.setAdapter(adapter);
                float sum = 0;
                for (Cart cart : data) {
                    sum += Float.parseFloat(cart.getPrice()) * Float.parseFloat(cart.getQuantity());
                }
                price.setText(sum + "00đ");
                total.setText(sum + "00đ");
            }

            @Override
            public void onError(String errorMessage) {

            }
        });
    }

}