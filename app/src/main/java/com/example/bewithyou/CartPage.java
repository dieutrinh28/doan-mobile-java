package com.example.bewithyou;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CartPage extends AppCompatActivity {
    private RecyclerView mRecyclerViewCart;
    private CartAdapter mCartAdapter;
    GridView gridView;
    Button btnMinus, btnAdd;
    TextView totalAmount, txtQuantity;


    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_page);

        getData.init(getApplicationContext());
      //  List<Cart> photos = PhotoData.getPhotos();

        gridView = findViewById(R.id.gridview);
        btnAdd = findViewById(R.id.btnAdd);
        btnMinus = findViewById(R.id.btnMinus);
        totalAmount = findViewById(R.id.totalAmount);
        txtQuantity =findViewById(R.id.textView_quantity);
        SharedPreferences preferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        String username = preferences.getString("username", "default_value");


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
                totalAmount.setText("Tổng thanh toán: "+String.valueOf(sum));
                txtQuantity.setText("Số lượng: "+String.valueOf(quantity));
            }

            @Override
            public void onError(String errorMessage) {

            }
        });

//        CartAdapter adapter = new CartAdapter(photos, getApplicationContext());

       // gridView.setOnItemClickListener(onitemclick);

    }

    // Function to retrieve data from Firebase and pass the data to the callback




}