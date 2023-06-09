package com.example.bewithyou;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.bewithyou.model.Cart;
import com.example.bewithyou.model.Product;
import com.example.bewithyou.model.Review;

import com.example.bewithyou.model.Store;
import com.example.bewithyou.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class getData {
    private static Context context;
    public static void init(Context context) {
        getData.context = context;
    }

    public static void updateProductPrice(String storeName, String productId, String newPrice, final Callback<Boolean> callback) {
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

    public static void updateStoreRating(String storeName, String newRating, final Callback<Boolean> callback) {
        DatabaseReference productRef = FirebaseDatabase.getInstance().getReference("stores").child(storeName).child("rating");
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
    public static void deleteCart(String userName, final Callback<Cart> callback) {
        DatabaseReference cartRef = FirebaseDatabase.getInstance().getReference().child("cart");
        cartRef.child(userName).setValue(null).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    // Cart successfully deleted
                    callback.onSuccess(null);
                } else {
                    // Cart deletion failed
                    callback.onError(task.getException().toString());
                }
            }
        });
    }



    public static void getSpecificProduct(String storeName, String name, Callback<Product> callback) {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference(storeName);

        Query query = databaseRef.orderByChild("productName").equalTo(name);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    DataSnapshot firstChild = snapshot.getChildren().iterator().next();
                    Product product = firstChild.getValue(Product.class);
                    callback.onSuccess(product);
                } else {
                    callback.onError("No product found!");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onError(error.getMessage());
            }
        });
    }
    public static void searchStore(String storeName, Callback<List<Store>> callback) {
        final String TAG = "Data";
        Log.d(TAG, "Searching for stores in database");

        DatabaseReference storesRef = FirebaseDatabase.getInstance().getReference("stores");
        storesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Store> storeList = new ArrayList<>();
                for (DataSnapshot storeSnapshot : snapshot.getChildren()) {
                    Store store = storeSnapshot.getValue(Store.class);
                    if (store.getStoreName().toLowerCase().contains(storeName.toLowerCase())) {
                        storeList.add(store);
                    }
                }
                callback.onSuccess(storeList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "Error retrieving stores from database: " + error.getMessage());
                callback.onError(error.getMessage());
            }
        });
    }


    public static void updateProductProperty(String storeName ,String productId, String property, Object value, final Callback<Boolean> callback) {
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

    public static void searchProductInStore(String productName,String storeName, Callback<List<Product>> callback) {
        DatabaseReference productsRef = FirebaseDatabase.getInstance().getReference(storeName);

        productsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Product> productList = new ArrayList<>();

                for (DataSnapshot productSnapshot : snapshot.getChildren()) {
                    Product product = productSnapshot.getValue(Product.class);

                    if (product != null && product.getProductName().toLowerCase().contains(productName.toLowerCase())) {
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

    public static void searchProductInAllStores(String productName, Callback<List<Product>> callback) {
        DatabaseReference storesRef = FirebaseDatabase.getInstance().getReference();

        storesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Product> productList = new ArrayList<>();

                for (DataSnapshot storeSnapshot : snapshot.getChildren()) {
                    String storeName = storeSnapshot.getKey();

                    DatabaseReference productsRef = FirebaseDatabase.getInstance().getReference(storeName);
                    productsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot productSnapshot : snapshot.getChildren()) {
                                Product product = productSnapshot.getValue(Product.class);
                                if (product != null && product.getProductName() != null && productName != null) {
                                    if (product.getProductName().toLowerCase().contains(productName.toLowerCase())) {
                                        productList.add(product);
                                    }
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            // Handle error
                        }
                    });
                }

                if (productList.size() > 0) {
                    callback.onSuccess(productList);
                } else {
                    callback.onError("No products found");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
            }
        });
    }


    public static void getProductsInStore(String storeName, Callback<List<Product>> callback) {
        final String TAG = "Data";
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
    public static void getAllProducts(Callback<List<Product>> callback) {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Product> productList = new ArrayList<>();
                for (DataSnapshot nodeSnapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot productSnapshot : nodeSnapshot.getChildren()) {
                        Product product = productSnapshot.getValue(Product.class);
                        productList.add(product);
                    }
                }
                callback.onSuccess(productList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onError(databaseError.getMessage());
            }
        });
    }

    public static void addToCart(String productName, String price, String quantity, String imgLink,
                                 String productId, String userName, Callback<String> callback) {
        DatabaseReference cartRef = FirebaseDatabase.getInstance().getReference("cart").child(userName).child(productId);
        cartRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Product already exists in cart, update the quantity
                    Cart cartItem = snapshot.getValue(Cart.class);
                    int currentQuantity = Integer.parseInt(cartItem.getQuantity());
                    cartItem.setQuantity(String.valueOf(currentQuantity+(Integer.parseInt(quantity))));
                    cartRef.setValue(cartItem)
                            .addOnSuccessListener(aVoid -> callback.onSuccess("Updated your cart"))
                            .addOnFailureListener(e -> callback.onError(e.getMessage()));
                } else {
                    // Product does not exist in cart, add it
                    Cart cartItem = new Cart(productName, price, quantity, imgLink);
                    cartRef.setValue(cartItem)
                            .addOnSuccessListener(aVoid -> callback.onSuccess("Product added to cart"))
                            .addOnFailureListener(e -> callback.onError(e.getMessage()));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onError(error.getMessage());
            }
        });
    }

    public static void getCardData(Callback<List<Cart>> callback) {
        SharedPreferences preferences = context.getSharedPreferences("MyPreferences", MODE_PRIVATE);
        String username = preferences.getString("username", "default_value");

        final String TAG = "Data";
        Log.d(TAG, "Getting cart from database");
        DatabaseReference productsRef = FirebaseDatabase.getInstance().getReference("cart").child(username);
        productsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Cart> cardList = new ArrayList<>();
                for (DataSnapshot productSnapshot : dataSnapshot.getChildren()) {
                    Cart card = productSnapshot.getValue(Cart.class);
                    cardList.add(card);
                }
                callback.onSuccess(cardList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "Error retrieving products from database: " + databaseError.getMessage());
                callback.onError(databaseError.getMessage());
            }
        });
    }
    public static void getUser(String userName,Callback<User> callback) {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("users");

        Query query = databaseRef.orderByChild("userName").equalTo(userName);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    DataSnapshot firstChild = snapshot.getChildren().iterator().next();
                    User user = firstChild.getValue(User.class);
                    callback.onSuccess(user);
                } else {
                    callback.onError("No User found!");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onError(error.getMessage());
            }
        });
    }

    public static void getStoreData(Callback<List<Store>> callback){
        final String TAG = "Data";
        Log.d(TAG, "Getting products from database");
        DatabaseReference storesRef = FirebaseDatabase.getInstance().getReference("stores");

        storesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Store> storeList = new ArrayList<>();
                for (DataSnapshot storeSnapshot : dataSnapshot.getChildren()) {
                    Store store = storeSnapshot.getValue(Store.class);
                    storeList.add(store);
                    Log.d(TAG, "onDataChange: "+"hiii");
                }
                callback.onSuccess(storeList);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "Error retrieving products from database: " + databaseError.getMessage());
                callback.onError(databaseError.getMessage());
            }
        });
    }

    public static void getSpecificStore(String storeName, Callback<Store> callback) {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("stores").child(storeName);

        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Store store = snapshot.getValue(Store.class);
                    callback.onSuccess(store);
                } else {
                    callback.onError("No product found!");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onError(error.getMessage());
            }
        });
    }

    public static void getReviewData(Callback<List<Review>> listCallback, String storeName) {

        final String TAG = "Data";
        Log.d(TAG, "Getting reviews from database");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reviewRef = database.getReference("review").child(storeName);

        reviewRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Review> reviewList = new ArrayList<>();
                for (DataSnapshot reviewSnapshot: snapshot.getChildren()) {
                    Review review = reviewSnapshot.getValue(Review.class);
                    reviewList.add(review);
                }
                listCallback.onSuccess(reviewList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "Error 'retrieving' reviews from database: " + error.getMessage());
                listCallback.onError(error.getMessage());
            }
        });

    }

    public static void addNewReview(String userId, String rating, String comment, String date, String storeName) {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("review").child(storeName);

        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long count = dataSnapshot.getChildrenCount();
                String reviewId = "review" + (count + 1);
                DatabaseReference newReviewRef = databaseRef.child(reviewId);
                newReviewRef.child("userId").setValue(userId);
                newReviewRef.child("rating").setValue(rating);
                newReviewRef.child("comment").setValue(comment);
                newReviewRef.child("date").setValue(date);
                Log.d("TAG", "New review saved with id: " + reviewId);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("TAG", "Database error: " + databaseError.getMessage());
            }
        });
    }
}
