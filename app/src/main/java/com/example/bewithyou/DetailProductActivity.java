package com.example.bewithyou;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bewithyou.model.Product;
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
//
        btnAddtoCart = findViewById(R.id.btn_addtocart);
        btnPlus = findViewById(R.id.btn_plus);
        btnMinus = findViewById(R.id.btn_minus);
        edNote = findViewById(R.id.tv_note);

        btnMinus.setOnClickListener(this);
        btnPlus.setOnClickListener(this);
        btnAddtoCart.setOnClickListener(this);

        tvProductPrice = findViewById(R.id.tv_product_price);
        tvQuantity = findViewById(R.id.tv_quantity);
        tvProductName = findViewById(R.id.tv_product_name);
        tvProductDescription = findViewById(R.id.tv_product_description);

        imvProDuct = findViewById(R.id.imv_product);

        product_name = (String) getIntent().getStringExtra("product_name");
        tvProductName.setText(product_name);
        getData.getSpecificProduct("Starbucks", product_name, new Callback<Product>() {
            @Override
            public void onSuccess(Product data) {
                System.out.println("here");
                System.out.print(data.toString());
                Picasso.get().load(data.getProductImg()).into(imvProDuct);
                tvProductPrice.setText(data.getPrice());
                tvProductDescription.setText(data.getProductDescription());
                btnAddtoCart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getData.addToCart(data.getProductName(), data.getPrice(), tvQuantity.getText().toString(), data.getProductImg(), data.getProductName(), "phuonganh", new Callback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                System.out.print(data.toString());
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