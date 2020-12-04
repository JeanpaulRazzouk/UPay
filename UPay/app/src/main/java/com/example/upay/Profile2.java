package com.example.upay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class Profile2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        setContentView(R.layout.activity_profile2);

        Intent i = getIntent();
        int message = Integer.parseInt(i.getStringExtra("message"));
       if (message == 1){
           personal_info_frag frag = new personal_info_frag();
           getSupportFragmentManager().beginTransaction().replace(R.id.framec,
                   frag).commit();

           Toast.makeText(this,"HELOOO",Toast.LENGTH_SHORT).show();

       }



    }


}