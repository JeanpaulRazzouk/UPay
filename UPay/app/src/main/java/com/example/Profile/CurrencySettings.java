package com.example.Profile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.payment.BottomSheetNFC;
import com.example.upay.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;

public class CurrencySettings extends Fragment {
    private FirebaseUser user;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private DatabaseReference mDatabase;
    //
    RadioButton usd;
    RadioButton eur;
    RadioButton cnd;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_currency_settings, container, false);
        //
        usd = view.findViewById(R.id.checkBox2);
        eur = view.findViewById(R.id.checkBox3);
        cnd = view.findViewById(R.id.checkBox4);
        //
        user = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        //
        FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try{
                    String USD = dataSnapshot.child("Currency Settings").child("USD").getValue().toString();
                    String EUR = dataSnapshot.child("Currency Settings").child("EUR").getValue().toString();
                    String CAN = dataSnapshot.child("Currency Settings").child("CAN").getValue().toString();
                    if (USD.equals("true")){
                        usd.setChecked(true);
                    }
                    else if (EUR.equals("true")){
                        eur.setChecked(true);
                    }
                    else if (CAN.equals("true")){
                        cnd.setChecked(true);
                    }
                }catch(Exception e){

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //
        usd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user = FirebaseAuth.getInstance().getCurrentUser();
                mDatabase = FirebaseDatabase.getInstance().getReference();
                HashMap<String, Object> values = new HashMap<>();
                values.put("Currency","$");
                mDatabase.child("Users").child(user.getUid()).child("Currency").updateChildren(values);
                //
                HashMap<String, Object> value2 = new HashMap<>();
                value2.put("USD",true);
                value2.put("EUR",false);
                value2.put("CAN",false);
                mDatabase.child("Users").child(user.getUid()).child("Currency Settings").updateChildren(value2);
            }
        });

        eur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user = FirebaseAuth.getInstance().getCurrentUser();
                mDatabase = FirebaseDatabase.getInstance().getReference();
                HashMap<String, Object> values = new HashMap<>();
                values.put("Currency","€");
                mDatabase.child("Users").child(user.getUid()).child("Currency").updateChildren(values);
                //
                HashMap<String, Object> value2 = new HashMap<>();
                value2.put("EUR",true);
                value2.put("USD",false);
                value2.put("CAN",false);
                mDatabase.child("Users").child(user.getUid()).child("Currency Settings").updateChildren(value2);
            }
        });


        cnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user = FirebaseAuth.getInstance().getCurrentUser();
                mDatabase = FirebaseDatabase.getInstance().getReference();
                HashMap<String, Object> values = new HashMap<>();
                values.put("Currency","$CA");
                mDatabase.child("Users").child(user.getUid()).child("Currency").updateChildren(values);
                //
                HashMap<String, Object> value2 = new HashMap<>();
                value2.put("CAN",true);
                value2.put("EUR",false);
                value2.put("USD",false);
                mDatabase.child("Users").child(user.getUid()).child("Currency Settings").updateChildren(value2);
            }
        });
        return view;
    }
}