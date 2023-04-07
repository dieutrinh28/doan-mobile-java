package com.example.bewithyou;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bewithyou.model.Product;
import com.example.bewithyou.model.User;

import java.util.List;

public class SearchPage extends AppCompatActivity {
    private SearchView searchView;
    private EditText editText;
    private TextView userName,phoneNum, address;
    private Button button;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page);

        userName =findViewById(R.id.username_text);
        phoneNum=findViewById(R.id.phone_num_text);
        address =findViewById(R.id.btnDeliveryAddress);

        SharedPreferences preferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        String username = preferences.getString("username", "default_value");

        getData.getUser(username, new Callback<User>() {
            @Override
            public void onSuccess(User data) {
                System.out.println("name:"+data.toString());
               userName.setText(data.getUserName().toString());
               phoneNum.setText(data.getPhoneNum());
                address.setText("      "+data.getAddress());
            }

            @Override
            public void onError(String errorMessage) {

            }
        });

        //RatingBar ratingBar = findViewById(R.id.ratingBar);

//        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
//            @Override
//            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
//                // Handle the rating change here
//                int myRating = (int) rating;
//
//                        getData.updateStoreRating("Orchid Oasis", "Chili", String.valueOf(myRating), new Callback<Boolean>() {
//                            @Override
//                            public void onSuccess(Boolean data) {
//                                Toast.makeText(SearchPage.this,myRating+" sao ne",Toast.LENGTH_SHORT).show();
//                            }
//
//                            @Override
//                            public void onError(String errorMessage) {
//
//                            }
//                        });
//
//
//            }


    }
}