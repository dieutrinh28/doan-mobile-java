package com.example.bewithyou;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.time.temporal.Temporal;
import java.util.List;

public class SearchPage extends AppCompatActivity implements SearchView.OnQueryTextListener{
    private SearchView searchView;
    private EditText editText;
    private TextView textView;
    private Button button;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page);

        searchView =findViewById(R.id.search_bar);
        searchView.setOnQueryTextListener(this);

        textView =findViewById(R.id.result);
        editText = findViewById(R.id.search);
        button = findViewById(R.id.buttonSearch);

        RatingBar ratingBar = findViewById(R.id.ratingBar);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                // Handle the rating change here
                int myRating = (int) rating;

                        getData.updateProductRating("Orchid Oasis", "Chili", String.valueOf(myRating), new Callback<Boolean>() {
                            @Override
                            public void onSuccess(Boolean data) {
                                Toast.makeText(SearchPage.this,myRating+" sao ne",Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onError(String errorMessage) {

                            }
                        });


            }
        });

    }

    @Override
    public boolean onQueryTextSubmit(String s) {

        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }
}