package com.example.upay;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class PaymentMethod extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_method);
        BottomSheet bottomSheet = new BottomSheet();
        //
        BottomSheetNFC bottomSheetNFC = new BottomSheetNFC();
    }
    public  void payment_Interface(View view){
        BottomSheet bottomSheet = new BottomSheet();
        bottomSheet.show(getSupportFragmentManager(),"TAG");
    }

    public void paypal_payment_Interface(View view){
        BottomSheetNFC bottomSheetNFC = new BottomSheetNFC();
        bottomSheetNFC.show(getSupportFragmentManager(),"TAG");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}


