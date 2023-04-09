package com.example.bewithyou.ui.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bewithyou.R;
import com.example.bewithyou.model.User;
import com.example.bewithyou.ui.home.HomePage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class RegisterPage extends AppCompatActivity {

    private EditText name;
    private EditText email, password, username,phoneNum,address;
    private Button register;
    private TextView loginUser;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
//
//    ProgressDialog pd;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

//        username = findViewById(R.id.username);
//        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        register = findViewById(R.id.register);
        phoneNum =findViewById(R.id.phone);
        address =findViewById(R.id.address);


        mAuth = FirebaseAuth.getInstance();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt_email = email.getText().toString();
                String txt_password = password.getText().toString();
                String txt_phone = phoneNum.getText().toString();
                String txt_address =address.getText().toString();
                String txt_name= txt_email.replace("@gmail.com","");

                if (TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)) {
                    Toast.makeText(RegisterPage.this, "Empty credentials!", Toast.LENGTH_SHORT).show();
                } else if (txt_password.length() < 6) {
                    Toast.makeText(RegisterPage.this, "Password too short!", Toast.LENGTH_SHORT).show();
                } else {
                    if(isValidEmail(txt_email))
                    {
                        register(txt_email,txt_password,txt_name,txt_phone,txt_address);
                    }
                    else
                        Toast.makeText(RegisterPage.this, "Register fail", Toast.LENGTH_SHORT).show();

                }
            }


        });
    }
    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pattern.matcher(email).matches();
    }


    public void register(String email, String password, String username, String phoneNum, String address) {
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // User registration successful
                        FirebaseUser user = mAuth.getCurrentUser();
                        String em = email.replace("@gmail.com","");
                        SharedPreferences preferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("username", em);
                        editor.apply();
                        saveUserData(em, email, phoneNum, address, username);
                    } else {
                        // User registration failed
                        String errorMessage = task.getException().getMessage();
                        // Handle error message
                        System.out.println(errorMessage);
                    }
                });

    }
    private void saveUserData(String userId, String email, String phoneNum, String address, String username) {
        User user = new User(email, phoneNum, address, username);
        mDatabase.child("users").child(userId).setValue(user)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // User data saved successfully
                        startActivity(new Intent(RegisterPage.this , HomePage.class));
                        Toast.makeText(RegisterPage.this, "Register Successful", Toast.LENGTH_SHORT).show();

                    } else {
                        // User data saving failed
                        String errorMessage = task.getException().getMessage();
                        // Handle error message
                        System.out.println(errorMessage);

                    }
                });
    }

}
