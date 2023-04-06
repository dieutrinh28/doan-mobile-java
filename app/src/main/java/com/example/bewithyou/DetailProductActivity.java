package com.example.bewithyou;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailProductActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnAddtoCart, btnPlus, btnMinus;
    ImageView imvProDuct;
    TextView tvProductName, tvProductPrice, tvQuantity;
    EditText edNote;
    int quantity;
    public void assignId(Button btn, int id){
        btn = (Button) findViewById(id);
        btn.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);

        assignId(btnAddtoCart, R.id.btn_addtocart);
        assignId(btnPlus, R.id.btn_plus);
        assignId(btnMinus, R.id.btn_minus);

        edNote = findViewById(R.id.tv_note);

        tvProductPrice = findViewById(R.id.tv_product_name);
        tvQuantity = findViewById(R.id.tv_product_name);
        tvProductName = findViewById(R.id.tv_product_name);

        imvProDuct = findViewById(R.id.imv_product);

        int id = (int) getIntent().getIntExtra("id", 0);
        tvProductName.setText(String.valueOf(id));
    }

    @Override
    public void onClick(View view) {
        Button btn = (Button) view;
        quantity = Integer.parseInt(tvQuantity.getText().toString());
        switch (btn.getId()) {
            case R.id.btn_addtocart:
                break;
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