package com.example.bewithyou.ui.review;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.bewithyou.Callback;
import com.example.bewithyou.R;
import com.example.bewithyou.getData;
import com.example.bewithyou.model.Review;

import java.util.List;


public class ReviewPage extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_page);

        getData.init(getApplicationContext());

        listView = findViewById(R.id.lvReviewList);

       getData.getReviewData(new Callback<List<Review>>() {
           @Override
           public void onSuccess(List<Review> data) {
               ReviewAdapter adapter = new ReviewAdapter(data, getApplicationContext());
               listView.setAdapter(adapter);
           }

           @Override
           public void onError(String errorMessage) {

           }
       });

    }
}