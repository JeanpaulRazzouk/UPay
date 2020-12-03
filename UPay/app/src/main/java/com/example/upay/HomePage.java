package com.example.upay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;


import com.example.BarFragments.AnalyticsFrag;
import com.example.BarFragments.HomeFragment;
import com.example.BarFragments.PaymentMethodFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomePage extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    FrameLayout frameLayout;
    Fragment selectedFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        frameLayout = findViewById(R.id.fragment_container);
        //
         selectedFragment =  new HomeFragment();;
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                selectedFragment).commit();
        //
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
    }
    public BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                     selectedFragment =  new HomeFragment();;
                    switch (item.getItemId()) {
                        case R.id.page_1:
                                selectedFragment = new HomeFragment();
                          break;
                        case R.id.page_2:
                            selectedFragment = new AnalyticsFrag();
                            break;
                        case R.id.page_3:
                            selectedFragment = new PaymentMethodFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();
                    return true;
                }
            };

    public void Add() {
        startActivity(new Intent(this, PaymentMethod.class));
    }

    public void PaymentMethod (View view){
        startActivity(new Intent(this, PaymentMethod.class));
    }


    @Override
    public void onBackPressed() {
        // super.onBackPressed();
    }
}

