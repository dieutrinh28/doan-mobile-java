package com.example.bewithyou.ui.review;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.bewithyou.Callback;
import com.example.bewithyou.R;
import com.example.bewithyou.getData;
import com.example.bewithyou.model.Review;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;


public class ReviewPage extends AppCompatActivity {

    ListView listView;
    RatingBar rbStore;
    TextView tvRating, tvStoreName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_page);

        getData.init(getApplicationContext());


        SharedPreferences preferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        String storeName = preferences.getString("storeName", "default_value");

        listView = findViewById(R.id.lvReviewList);
        rbStore = findViewById(R.id.rbStore);
        tvRating = findViewById(R.id.tvRating);
        tvStoreName = findViewById(R.id.tvStoreName);
        tvStoreName.setText(storeName);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("stores").child(storeName).child("rating");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String rating = snapshot.getValue(String.class);
                tvRating.setText(rating);
                rbStore.setRating(Float.parseFloat(rating));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

       getData.getReviewData(new Callback<List<Review>>() {
           @Override
           public void onSuccess(List<Review> data) {
               ReviewAdapter adapter = new ReviewAdapter(data, getApplicationContext());
               listView.setAdapter(adapter);
           }

           @Override
           public void onError(String errorMessage) {

           }
       }, storeName);

    }
}