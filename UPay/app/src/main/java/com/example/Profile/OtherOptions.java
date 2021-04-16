package com.example.Profile;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.Toast;

import com.application.isradeleon.notify.Notify;
import com.example.upay.JavaMailAPI;
import com.example.upay.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class OtherOptions extends Fragment {
    public Switch Switch,Switch2,Switch3,Switch4;
    private DatabaseReference mDatabase;
    private FirebaseUser user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //
        getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View View =  inflater.inflate(R.layout.fragment_other_options, container, false);
        //
        Switch = View.findViewById(R.id.switch1);
        Switch2 = View.findViewById(R.id.switch2);
        Switch3 = View.findViewById(R.id.switch3);
        Switch4 = View.findViewById(R.id.switch4);
        //
        mDatabase = FirebaseDatabase.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();
        //
            Switch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    firstSwitch();
                }
            });
            //
            Switch2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    secondSwitch();
                }
            });
            //
            Switch3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    thirdSwitch();
                }
            });
            //
            Switch4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    fourthSwitch();
                }
            });


            // Getting Current Data
            FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    try {
                        String x1 = dataSnapshot.child("Switches").child("Switch1").getValue().toString();
                        String x2 = dataSnapshot.child("Switches").child("Switch2").getValue().toString();
                        String x3 = dataSnapshot.child("Switches").child("Switch3").getValue().toString();
                        String x4 = dataSnapshot.child("Switches").child("Switch4").getValue().toString();
                    if (x1.equals("true")) {
                        Switch.setChecked(true);
                    } else {
                        Switch.setChecked(false);
                    }

                    if (x2.equals("true")) {
                        Switch2.setChecked(true);
                    } else {
                        Switch2.setChecked(false);
                    }

                    if (x3.equals("true")) {
                        Switch3.setChecked(true);
                    } else {
                        Switch3.setChecked(false);
                    }

                    if (x4.equals("true")) {
                        Switch4.setChecked(true);
                    } else {
                        Switch4.setChecked(false);
                    }
                    }catch(Exception e){}
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }

            });
        return View;
    }
    public void firstSwitch(){
        if(Switch.isChecked()){
            HashMap<String, Object> values = new HashMap<>();
            values.put("Switch1", true);
            mDatabase.child("Users").child(user.getUid()).child("Switches").updateChildren(values);
        }else {
            HashMap<String, Object> values = new HashMap<>();
            values.put("Switch1", false);
            mDatabase.child("Users").child(user.getUid()).child("Switches").updateChildren(values);
        }
    }

    public void secondSwitch() {
        // Second Switch;
        if (Switch2.isChecked()) {
            HashMap<String, Object> values = new HashMap<>();
            values.put("Switch2", true);
            mDatabase.child("Users").child(user.getUid()).child("Switches").updateChildren(values);
        } else {
            HashMap<String, Object> values = new HashMap<>();
            values.put("Switch2", false);
            mDatabase.child("Users").child(user.getUid()).child("Switches").updateChildren(values);
        }
    }

        public void thirdSwitch(){
            // Third Switch;
            if(Switch3.isChecked()){
                HashMap<String, Object> values = new HashMap<>();
                values.put("Switch3", true);
                mDatabase.child("Users").child(user.getUid()).child("Switches").updateChildren(values);
                //
                PurchaseNotification(true);
            }else {
                HashMap<String, Object> values = new HashMap<>();
                values.put("Switch3", false);
                mDatabase.child("Users").child(user.getUid()).child("Switches").updateChildren(values);
                //
                PurchaseNotification(false);
            }
        }
       public void fourthSwitch(){
           // Fourth Switch;
           if(Switch4.isChecked()){
               HashMap<String, Object> values = new HashMap<>();
               values.put("Switch4", true);
               mDatabase.child("Users").child(user.getUid()).child("Switches").updateChildren(values);
               //
               Emails(true);
           }else {
               HashMap<String, Object> values = new HashMap<>();
               values.put("Switch4", false);
               mDatabase.child("Users").child(user.getUid()).child("Switches").updateChildren(values);
               //
               Emails(false);
           }
       }


       public void AutomaticPayment(){

        //TODO()

       }

       public void ShopAlert(){

           //TODO()

       }

    public void PurchaseNotification(Boolean input){
        if (input == true){
            Notify.build(getContext())
                    .setTitle("UPay")
                    .setContent("Purchase Notfication Activated")
                    .setSmallIcon(R.drawable.ic_payment)
                    .setColor(R.color.color4)
                    .largeCircularIcon()
                    .show(); // Show notification
        }
    }



    public void Emails(Boolean Input){
      if (Input == true){
          sendEmail();
      }
    }
    private void sendEmail() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        String mEmail = user.getEmail();
        String mSubject = "UPay";
        String mMessage = "You have activated UPay Transaction Automated Email !";

        JavaMailAPI javaMailAPI = new JavaMailAPI(getContext(), mEmail, mSubject, mMessage);

        javaMailAPI.execute();
    }



}