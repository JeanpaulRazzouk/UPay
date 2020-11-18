package com.example.upay;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;




import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.InputStream;

public class HomePage extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    FrameLayout frameLayout;
    Fragment selectedFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
//        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
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
                            // N/A
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();
                    return true;
                }
            };

    public void log_out() {
        Intent i = new Intent(this, LoginPage.class);
        startActivity(i, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

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

