package com.example.bewithyou.ui.cart;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bewithyou.Callback;
import com.example.bewithyou.R;
import com.example.bewithyou.getData;
import com.example.bewithyou.model.Cart;
import com.example.bewithyou.ui.order.PaymentPage;

import java.util.List;

public class CartPage extends AppCompatActivity {

    GridView gridView;
    Button btnMinus, btnAdd, btnPayment;
    TextView totalAmount, txtQuantity;


    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_page);

        getData.init(getApplicationContext());

        gridView = findViewById(R.id.gridview);
        btnAdd = findViewById(R.id.btnAdd);
        btnMinus = findViewById(R.id.btnMinus);
        totalAmount = findViewById(R.id.totalAmount);
        txtQuantity =findViewById(R.id.textView_quantity);
        SharedPreferences preferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        String username = preferences.getString("username", "default_value");

        btnPayment = findViewById(R.id.btnPayment);
        btnPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CartPage.this, PaymentPage.class));
                finish();
            }
        });

        getData.getCardData(new Callback<List<Cart>>() {

            @Override
            public void onSuccess(List<Cart> data) {
                CartAdapter adapter = new CartAdapter(data,getApplicationContext());
                gridView.setAdapter(adapter);
                float sum = 0;
                int quantity = 0;
                for (Cart cart  :
                        data) {
                    sum+= Float.parseFloat(cart.getPrice()) * Float.parseFloat(cart.getQuantity());
                    quantity += Integer.parseInt(cart.getQuantity());
                }
                totalAmount.setText(sum +"00đ");
                txtQuantity.setText(String.valueOf(quantity));
            }

            @Override
            public void onError(String errorMessage) {

            }
        });

    }



}