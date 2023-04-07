package com.example.bewithyou;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
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
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    GridView gridViewStore;

    private ViewPager viewPager;
    private int[] images = {R.drawable.img1_viewpaper, R.drawable.img2_viewpaper, R.drawable.img3_viewpaper, R.drawable.img4_viewpaper};

    private int currentPage = 0;
    private Timer timer;
    private final long DELAY_MS = 500;//đặt khoảng thời gian delay cho mỗi lần chuyển trang
    private final long PERIOD_MS = 3000;//đặt khoảng thời gian chuyển trang

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


        viewPager = findViewById(R.id.viewPaper);
        ViewpaperAdapter adapter = new ViewpaperAdapter(this);
        viewPager.setAdapter(adapter);

        startAutoSlider();
    }
    //hàm khởi tạo timer để tự động chuyển trang trong ViewPager
    private void startAutoSlider() {
        final Handler handler = new Handler();
        final Runnable update = new Runnable() {
            public void run() {
                viewPager.setCurrentItem(currentPage, true);
                currentPage = (currentPage + 1) % images.length;
            }
        };

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(update);
            }
        }, DELAY_MS, PERIOD_MS);
    }

    //hàm dừng timer tự động chuyển trang trong ViewPager
    private void stopAutoSlider() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        stopAutoSlider();
    }
}
