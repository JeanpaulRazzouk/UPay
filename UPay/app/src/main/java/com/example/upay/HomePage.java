package com.example.upay;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.BarFragments.AnalyticsFrag;
import com.example.BarFragments.HomeFragment;
import com.example.BarFragments.PaymentMethodFragment;

import me.ibrahimsn.lib.SmoothBottomBar;

public class HomePage extends AppCompatActivity {
    SmoothBottomBar smoothBottomBar;
    FrameLayout frameLayout;
    Fragment selectedFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        frameLayout = findViewById(R.id.fragment_container);
        //
         selectedFragment =  new HomeFragment();;
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                selectedFragment).commit();
        //
        smoothBottomBar = findViewById(R.id.bottom_navigation);
        smoothBottomBar.setOnItemSelectedListener(i -> {
            selectedFragment = new HomeFragment();
            switch (i) {
                case 0:
                    selectedFragment = new HomeFragment();
                    break;
                case 1:
                    selectedFragment = new AnalyticsFrag();
                    break;
                case 2:
                    selectedFragment = new PaymentMethodFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    selectedFragment).commit();
        });
    }

    public void Add() {
        startActivity(new Intent(this, PaymentMethod.class));
    }

    public void PaymentMethod (View view){
        startActivity(new Intent(this, PaymentMethod.class));
    }


    @Override
    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }
}

