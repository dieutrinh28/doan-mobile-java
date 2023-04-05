package com.example.bewithyou;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.util.Log;
import android.widget.RatingBar;
import android.widget.Toast;


import java.util.Map;

public class MainActivity extends AppCompatActivity {

    GridView gridViewStore;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridViewStore = findViewById(R.id.gridViewStore);
        RatingBar ratingBar = findViewById(R.id.ratingBar);

        getData.getStoreData(new Callback<List<Store>>() {
            @Override
            public void onSuccess(List<Store> data) {
                StoreAdapter adapter = new StoreAdapter(data, getApplicationContext());
                gridViewStore.setAdapter(adapter);
                Log.d("TAG", "onSuccess: "+ "ngan");
            }

            @Override
            public void onError(String errorMessage) {

            }
        });




    }
}
