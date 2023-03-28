package com.example.bewithyou;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import android.util.Log;


import java.util.Map;

public class MainActivity extends AppCompatActivity {
    Button btnAdd;
    EditText txtData;
    private ListView listview;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = findViewById(R.id.btnAdd);
        listview = findViewById(R.id.listview);

        ArrayList<Product> list = new ArrayList<>();
        ArrayList<Product>  products = getProductsInStore("Orchid Oasis");

        ArrayAdapter adapter = new ArrayAdapter<Product>(this, R.layout.listview, products);
        listview.setAdapter(adapter);

        getProducts("Scented Treasures",new Callback<List<Product>>() {
            @Override
            public void onSuccess(List<Product> productList) {
                for (Product product:
                     productList) {
                    System.out.println(product.toString()+"hehe\n");
                }
            }

            @Override
            public void onError(String errorMessage) {
                // Handle errors here
            }
        });


    }

    public ArrayList<Product> getProductsInStore(String storeName)
    {
        ArrayList<Product> list = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(storeName);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    Product product = snapshot1.getValue(Product.class);
                    list.add(product);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        return list;
    }
    public void getProducts(String storeName,Callback<List<Product>> callback) {
         final String TAG = "MyClass";
        Log.d(TAG, "Getting products from database");
        DatabaseReference productsRef = FirebaseDatabase.getInstance().getReference(storeName);
        productsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Product> productList = new ArrayList<>();
                for (DataSnapshot productSnapshot : dataSnapshot.getChildren()) {
                    Product product = productSnapshot.getValue(Product.class);
                    productList.add(product);
                }
                Log.d(TAG, "Retrieved " + productList.size() + " products");
                callback.onSuccess(productList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "Error retrieving products from database: " + databaseError.getMessage());
                callback.onError(databaseError.getMessage());
            }
        });
    }


}
