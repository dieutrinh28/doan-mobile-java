package com.example.bewithyou.ui.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bewithyou.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class RegisterPage extends AppCompatActivity {

    private EditText name;
    private EditText email, password, username;
    private Button register;
    private TextView loginUser;

    //    private DatabaseReference mRootRef;
    private FirebaseAuth Auth;
//
//    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

//        username = findViewById(R.id.username);
//        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        username = findViewById(R.id.username);
        register = findViewById(R.id.register);

        Auth = FirebaseAuth.getInstance();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt_email = email.getText().toString();
                String txt_password = password.getText().toString();
                String txt_name = username.getText().toString();

                if (TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)) {
                    Toast.makeText(RegisterPage.this, "Empty credentials!", Toast.LENGTH_SHORT).show();
                } else if (txt_password.length() < 6) {
                    Toast.makeText(RegisterPage.this, "Password too short!", Toast.LENGTH_SHORT).show();
                } else {
                    registerUser(txt_name, txt_email, txt_password);
                }
            }


        });
    }
//    private void registerUser(String email, String password)
//    {
//        Auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(Register.this,  new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if(task.isSuccessful()){
//                    Toast.makeText(Register.this, "Registering user successfull", Toast.LENGTH_SHORT).show();
//                    startActivity(new Intent(Register.this, MainActivity.class));
//                    finish();
//                }else {
//                    Toast.makeText(Register.this, "Registration failed!", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//    }

//        loginUser = findViewById(R.id.login_user);

//        mRootRef = FirebaseDatabase.getInstance().getReference();
//        mAuth = FirebaseAuth.getInstance();
//        pd = new ProgressDialog(this);

//        register.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String txtUsername = username.getText().toString();
//                String txtName = name.getText().toString();
//                String txtEmail = email.getText().toString();
//                String txtPassword = password.getText().toString();
//
//                if (TextUtils.isEmpty(txtUsername) || TextUtils.isEmpty(txtName)
//                        || TextUtils.isEmpty(txtEmail) || TextUtils.isEmpty(txtPassword)){
//                    Toast.makeText(Register.this, "Empty credentials!", Toast.LENGTH_SHORT).show();
//                } else if (txtPassword.length() < 6){
//                    Toast.makeText(Register.this, "Password too short!", Toast.LENGTH_SHORT).show();
//                } else {
//                    registerUser(txtUsername , txtName , txtEmail , txtPassword);
//                }
//            }
//        });
//    }

    private void registerUser(final String username, final String email, String password) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign up successful, update the user's display name with their username
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(username)
                                    .build();
                            user.updateProfile(profileUpdates);

                            // TODO: Save the user's information to a database or other storage
                        } else {
                            // Sign up failed, display a message to the user
                            Toast.makeText(getApplicationContext(), "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
