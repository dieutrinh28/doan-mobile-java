package com.example.bewithyou.ui.product;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bewithyou.Callback;
import com.example.bewithyou.R;
import com.example.bewithyou.getData;
import com.example.bewithyou.model.Product;
import com.example.bewithyou.ui.cart.CartPage;
import com.squareup.picasso.Picasso;

public class DetailProductActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnAddtoCart, btnPlus, btnMinus;
    ImageView imvProDuct;
    TextView tvProductName, tvProductPrice, tvQuantity, tvProductDescription;
    EditText edNote;
    int quantity;
    String product_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);

        btnAddtoCart = findViewById(R.id.btn_addtocart);
        btnPlus = findViewById(R.id.btn_plus);
        btnMinus = findViewById(R.id.btn_minus);

        btnMinus.setOnClickListener(this);
        btnPlus.setOnClickListener(this);
        btnAddtoCart.setOnClickListener(this);

        tvProductPrice = findViewById(R.id.tv_product_price);
        tvQuantity = findViewById(R.id.tv_quantity);
        tvProductName = findViewById(R.id.tv_product_name);
        tvProductDescription = findViewById(R.id.tv_product_description);

        imvProDuct = findViewById(R.id.imv_product);

        product_name = getIntent().getStringExtra("product_name");
        tvProductName.setText(product_name);

        SharedPreferences preferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        String storeName = preferences.getString("storeName", "default_value");
        String username = preferences.getString("username", "default_value");

        getData.getSpecificProduct(storeName, product_name, new Callback<Product>() {
            @Override
            public void onSuccess(Product data) {
                Picasso.get().load(data.getProductImg()).into(imvProDuct);
                tvProductPrice.setText(data.getPrice());
                tvProductDescription.setText(data.getProductDescription());
                btnAddtoCart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getData.addToCart(data.getProductName(), data.getPrice(), tvQuantity.getText().toString(), data.getProductImg(), data.getProductName(), username, new Callback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                startActivity(new Intent(DetailProductActivity.this , CartPage.class));
                            }

                            @Override
                            public void onError(String errorMessage) {

                            }
                        });
                    }
                });
            }

            @Override
            public void onError(String errorMessage) {
                tvProductDescription.setText(errorMessage);
            }
        });
    }

    @Override
    public void onClick(View view) {
        Button btn = (Button) view;
        switch (btn.getId()){
            case R.id.btn_minus:
                if(quantity > 0){
                    quantity -=1;
                }
                tvQuantity.setText(String.valueOf(quantity));
                break;
            case R.id.btn_plus:
                quantity += 1;
                tvQuantity.setText(String.valueOf(quantity));
                break;
            default: break;
        }
    }
}