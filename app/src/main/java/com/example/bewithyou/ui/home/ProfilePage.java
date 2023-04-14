package com.example.bewithyou.ui.home;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.bewithyou.Callback;
import com.example.bewithyou.R;
import com.example.bewithyou.getData;
import com.example.bewithyou.model.User;
import com.example.bewithyou.ui.auth.LoginPage;

public class ProfilePage extends AppCompatActivity {

    private TextView userName,phoneNum,address, email;
    private Button button;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        SharedPreferences preferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);


        button = findViewById(R.id.btnLogOut);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("username", "");
                editor.apply();
                startActivity(new Intent(ProfilePage.this , LoginPage.class));
            }
        });

        userName = findViewById(R.id.username_text);
        phoneNum= findViewById(R.id.btnPhone);
        address = findViewById(R.id.btnDeliveryAddress);
        email = findViewById(R.id.btnEmail);

        String username = preferences.getString("username", "default_value");

        getData.getUser(username, new Callback<User>() {
            @Override
            public void onSuccess(User data) {
                System.out.println("name:"+data.toString());
                userName.setText(data.getUserName());
                phoneNum.setText(data.getPhoneNum());
                address.setText(data.getAddress());
                email.setText(data.getEmail());
            }

            @Override
            public void onError(String errorMessage) {

            }
        });



    }
}