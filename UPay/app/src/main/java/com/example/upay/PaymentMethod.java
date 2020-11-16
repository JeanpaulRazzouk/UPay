package com.example.upay;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class PaymentMethod extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_method);
        BottomSheet bottomSheet = new BottomSheet();
        //
        BottomSheetPayPal bottomSheetPayPal = new BottomSheetPayPal();
    }
    public  void payment_Interface(View view){
        BottomSheet bottomSheet = new BottomSheet();
        bottomSheet.show(getSupportFragmentManager(),"TAG");
    }

    public void paypal_payment_Interface(View view){
        BottomSheetPayPal bottomSheetPayPal = new BottomSheetPayPal();
        bottomSheetPayPal.show(getSupportFragmentManager(),"TAG");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}


