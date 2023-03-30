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
        txtData = findViewById(R.id.txtText);
//
//        ArrayList<Product> list = new ArrayList<>();
//        ArrayList<Product>  products = getProductsInStore("Orchid Oasis");
//
//        ArrayAdapter adapter = new ArrayAdapter<Product>(this, R.layout.listview, products);
//        listview.setAdapter(adapter);
        txtData.setText("helo");
        ArrayList<Product> list = new ArrayList<>();
        updateProductRating("Orchid Oasis", "Chili", "2", new Callback<Boolean>() {
            @Override
            public void onSuccess(Boolean data) {
                
            }   

            @Override
            public void onError(String errorMessage) {

            }
        });
        searchProduct("Chili", "Orchid Oasis", new Callback<List<Product>>() {
            @Override
            public void onSuccess(List<Product> data) {
                for (Product pr :
                        data) {
                    System.out.println(pr.toString());
                }
            }

            @Override
            public void onError(String errorMessage) {

            }
        });
     
    }

    public void updateProductPrice(String storeName, String productId, String newPrice, final Callback<Boolean> callback) {
        DatabaseReference productRef = FirebaseDatabase.getInstance().getReference().child(storeName).child(productId).child("price");
        productRef.setValue(newPrice).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    callback.onSuccess(true);
                } else {
                    callback.onError(task.getException().getMessage());
                }
            }
        });
    }
    public void updateProductRating(String storeName, String productId, String newRating, final Callback<Boolean> callback) {
        DatabaseReference productRef = FirebaseDatabase.getInstance().getReference().child(storeName).child(productId).child("rating");
        productRef.setValue(newRating).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    callback.onSuccess(true);
                } else {
                    callback.onError(task.getException().getMessage());
                }
            }
        });
    }



    public void updateProductProperty(String storeName ,String productId, String property, Object value, final Callback<Boolean> callback) {
        DatabaseReference productRef = FirebaseDatabase.getInstance().getReference().child(storeName).child(productId);
        Map<String, Object> updateMap = new HashMap<>();
        updateMap.put(property, value);
        productRef.updateChildren(updateMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    callback.onSuccess(true);
                } else {
                    callback.onError(task.getException().getMessage());
                }
            }
        });
    }


    public static void searchProduct(String productName,String storeName, Callback<List<Product>> callback) {
        DatabaseReference productsRef = FirebaseDatabase.getInstance().getReference(storeName);

        productsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Product> productList = new ArrayList<>();

                for (DataSnapshot productSnapshot : snapshot.getChildren()) {
                    Product product = productSnapshot.getValue(Product.class);

                    if (product != null && product.getName().toLowerCase().contains(productName.toLowerCase())) {
                        productList.add(product);
                    }
                }

                if (productList.size() > 0) {
                    callback.onSuccess(productList);
                } else {
                    callback.onError("No products found");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onError(error.getMessage());
            }
        });
    }



    public ArrayList<Product> getProductsInStore(String storeName) {
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

    public void getProducts(String storeName, Callback<List<Product>> callback) {
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
