package com.example.Profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.BarFragments.AnalyticsFrag;
import com.example.BarFragments.HomeFragment;
import com.example.BarFragments.PaymentMethodFragment;
import com.example.upay.R;

public class Settings extends AppCompatActivity {
    FrameLayout frameLayout;
    Fragment selectedFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        //
        frameLayout = findViewById(R.id.fragment_container_r);
        //
        Intent i = getIntent();

        String received_value = i.getStringExtra("section");
        switch (received_value){
                case "0":
                    selectedFragment = new ChangeInfo();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_r,
                            selectedFragment).commit();
                    break;
                case "1":
                    selectedFragment = new CurrencySettings();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_r,
                            selectedFragment).commit();
                    break;
                case "2":
                    selectedFragment = new SupportedCards();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_r,
                            selectedFragment).commit();
                    break;
            case "3":
                selectedFragment = new OtherOptions();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_r,
                        selectedFragment).commit();
                break;

        }

    }




}