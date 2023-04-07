package com.example.bewithyou.ui.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.RatingBar;

import com.example.bewithyou.Callback;
import com.example.bewithyou.R;
import com.example.bewithyou.getData;
import com.example.bewithyou.model.Store;
import com.example.bewithyou.ui.cart.CartPage;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomePage extends AppCompatActivity {
    private ImageButton imageButtonCart;

    GridView gridViewStore;

    private ViewPager viewPager;
    private int[] images = {R.drawable.img1_viewpaper, R.drawable.img2_viewpaper, R.drawable.img3_viewpaper, R.drawable.img4_viewpaper};

    private int currentPage = 0;
    private Timer timer;
    private final long DELAY_MS = 500; //đặt khoảng thời gian delay cho mỗi lần chuyển trang
    private final long PERIOD_MS = 3000; //đặt khoảng thời gian chuyển trang


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        imageButtonCart = findViewById(R.id.btnCart);
        imageButtonCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomePage.this, CartPage.class));
            }
        });

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
