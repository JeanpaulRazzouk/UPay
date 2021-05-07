package com.example.payment;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.airbnb.lottie.LottieAnimationView;
import com.example.Profile.Profile;
import com.example.upay.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class BottomSheetAmount extends BottomSheetDialogFragment {
    ImageButton imageButton;
    EditText editText;
    private DatabaseReference mDatabase;
    private FirebaseUser user;
    LottieAnimationView lottieAnimationView;
    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.bottom_sheet_amount, container, false);
        imageButton = view.findViewById(R.id.imageButton20);
        editText = view.findViewById(R.id.editTextNumberDecimal);
        lottieAnimationView = view.findViewById(R.id.animationView);
        //
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                mDatabase = FirebaseDatabase.getInstance().getReference();
                user = FirebaseAuth.getInstance().getCurrentUser();
                //
                lottieAnimationView.setAnimation(R.raw.process2);
                lottieAnimationView.setRepeatCount(9999);
                //
                HashMap<String, Object> values2 = new HashMap<>();
                values2.put("Amount Value",editText.getText().toString());
                mDatabase.child("Users").child(user.getUid()).child("Amount Value").setValue(values2);
                //
                BottomSheetNFC bottomSheet = new BottomSheetNFC();
                bottomSheet.show(getFragmentManager(), "TAG");
            }
        });
        return  view;
    }
}
