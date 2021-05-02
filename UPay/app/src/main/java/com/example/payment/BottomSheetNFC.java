package com.example.payment;

import android.annotation.TargetApi;
import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import androidx.biometric.BiometricPrompt;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.media.MediaPlayer;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.nfc.cardemulation.CardEmulation;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.application.isradeleon.notify.Notify;
import com.example.upay.HomePage;
import com.example.upay.JavaMailAPI;
import com.example.upay.R;
import com.google.android.gms.location.LocationRequest;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.Executor;
import java.util.logging.Logger;

public class BottomSheetNFC extends BottomSheetDialogFragment {
    public NfcAdapter mNfcAdapter;
    public CardEmulation cardEmulation;
    VideoView videoView;
    TextView textView;
    //
    private FirebaseUser user;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private DatabaseReference mDatabase;

    public static final int REQUEST_CODE_DEFAULT_PAYMENT_APP = 1;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_bottom_sheet_nfc, container, false);
        //
        mNfcAdapter = NfcAdapter.getDefaultAdapter(getContext());
        //
        textView = view.findViewById(R.id.textView26);
        //
        videoView = view.findViewById(R.id.videoView);
        Uri uri = Uri.parse("android.resource://"+ getContext().getPackageName()+"/"+R.raw.nfc1);
        videoView.setZOrderOnTop(true);
        videoView.setVideoURI(uri);

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });
        videoView.start();

        Payment();
        return  view;
    }

    public void verified(){
        textView.setText("Payment Success");
        //
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(false);
            }
        });
        Uri uri = Uri.parse("android.resource://"+ getContext().getPackageName()+"/"+R.raw.nfc_check);
        videoView.setZOrderOnTop(true);
        videoView.setVideoURI(uri);
        videoView.start();
        //
        MediaPlayer mp = new MediaPlayer();
            mp = MediaPlayer.create(getContext(), R.raw.nfc_check_sound);
            mp.start();
    }


    public void Payment(){
        // biometric test;
        biometric();
        biometricPrompt.authenticate(promptInfo);
    }

    private BiometricPrompt.PromptInfo promptInfo;
    private Executor executor;
    private BiometricPrompt biometricPrompt;
    public void biometric(){
        executor = ContextCompat.getMainExecutor(getContext());
        biometricPrompt = new BiometricPrompt(BottomSheetNFC.this,
                executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode,
                                              @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                dismiss();
            }

            @Override
            public void onAuthenticationSucceeded(
                    @NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                sendData();
                verified();
                ComponentName componentName;
                if (mNfcAdapter == null) {
                    Toast.makeText(getContext(), "NFC is not available", Toast.LENGTH_LONG).show();
                }
                else {
                     cardEmulation = CardEmulation.getInstance(mNfcAdapter);
                    Toast.makeText(getContext(), "NFC availability : "+mNfcAdapter.isEnabled(), Toast.LENGTH_LONG).show();
                    //
                    componentName = new ComponentName(getContext(), MyHostApduService.class);
                    boolean isDefault = cardEmulation.isDefaultServiceForCategory(componentName, CardEmulation.CATEGORY_PAYMENT);
                    if (!isDefault) {
                        //
                        //
                        Intent intent = new Intent(CardEmulation.ACTION_CHANGE_DEFAULT);
                        intent.putExtra(CardEmulation.EXTRA_CATEGORY, CardEmulation.CATEGORY_PAYMENT);
                        intent.putExtra(CardEmulation.EXTRA_SERVICE_COMPONENT, componentName);
                        getActivity().startActivityForResult(intent, REQUEST_CODE_DEFAULT_PAYMENT_APP);
                        //
                    }
                }
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(getContext(), "Something went wrong: ",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Get near Payment Terminal")
                .setSubtitle("Place your finger to Pay")
                .setNegativeButtonText(" ")
                .build();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    public void sendData() {
        // Get location if available
        //
        user = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();
            FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                        //
                        String Location = dataSnapshot.child("Location").child("Current Country Location").getValue().toString();
                        String Place = dataSnapshot.child("Location").child("Current Place Location").getValue().toString();
                        String lon = dataSnapshot.child("Location").child("Longitude").getValue().toString();
                        String lat = dataSnapshot.child("Location").child("Latitude").getValue().toString();
                        String count = dataSnapshot.child("User Data").child("Transaction count").getValue().toString();
                        String Switch3 = dataSnapshot.child("Switches").child("Switch3").getValue().toString();
                        String Switch4 = dataSnapshot.child("Switches").child("Switch4").getValue().toString();
                        String Currency = dataSnapshot.child("Currency").child("Currency").getValue().toString();
                        //
                        String AmountFINAL = "21";
                        //
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                            LocalDate date = LocalDate.now();
                            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                            String text = date.format(dtf);
                            //
                            LocalDate parsedDate = LocalDate.parse(text, dtf);
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YYYY");
                            //
                            user = FirebaseAuth.getInstance().getCurrentUser();
                            mDatabase = FirebaseDatabase.getInstance().getReference();
                            //
                            if (Currency.equals("$")){
                            }
                            else if (Currency.equals("€")){
                              Double new_val = Double.parseDouble(AmountFINAL);
                              AmountFINAL = new DecimalFormat("##.##").format(new_val*1.20);
                            }
                            else if (Currency.equals("$CA")){
                                Double new_val = Double.parseDouble(AmountFINAL);
                                AmountFINAL = new DecimalFormat("##.##").format(new_val*0.81);
                            }

                            //
                            HashMap<String, Object> values = new HashMap<>();
                            //TODO() Certain Values are For Testing;
                            values.put("Name", Place);
                            values.put("Location", Location);
                            values.put("Amount", AmountFINAL);
                            values.put("Date", formatter.format(parsedDate));
                            values.put("Longitude", lon);
                            values.put("Latitude", lat);
                            //
                            mDatabase.child("Users").child(user.getUid()).child("Transactions").child(count).setValue(values);
                            int x = Integer.parseInt(count) + 1;
                            HashMap<String, Object> values2 = new HashMap<>();
                            values2.put("Transaction count", x);
                            mDatabase.child("Users").child(user.getUid()).child("User Data").setValue(values2);
                            //
                            if (Switch3.equals("true")) {
                                BottomSheetNFC m = new BottomSheetNFC();
                                PurchaseNotification(Place, Location, formatter.format(parsedDate), AmountFINAL,Currency);
                            }
                            if (Switch4.equals("true")) {
                                //TODO() Certain Values are For Testing;
                                BottomSheetNFC m = new BottomSheetNFC();
                                sendEmail(Place, Location, formatter.format(parsedDate), AmountFINAL,Currency);
                            }
                        }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
                //
            });
        }
    public void sendEmail(String Name,String Location,String Date,String Amount,String Currency) {
        user = FirebaseAuth.getInstance().getCurrentUser();
        String mEmail = user.getEmail();
        String mSubject = "UPay- "+Name+" Transaction";
        String mMessage = "Your Recent Transaction at "+Name+":" +
                "\nLocation: "+Location
                +"\nThe amount of "+Currency+Amount+" has been spent on " +Date;

        JavaMailAPI javaMailAPI = new JavaMailAPI(getActivity().getApplicationContext(), mEmail, mSubject, mMessage);

        javaMailAPI.execute();
    }

    public void PurchaseNotification(String Name,String Location,String Date,String Amount,String Currency){
       // String mSubject = "UPay- "+Name+" Transaction";
        String mMessage = "Your Recent Transaction at "+Name+":" +
                "\nLocation: "+Location
                +"\nThe amount of "+Currency+Amount+" has been spent on " +Date;
        Notify.build(getContext())
                .setTitle("UPay")
                .setContent(mMessage)
                .setSmallIcon(R.drawable.ic_payment)
                .setColor(R.color.color4)
                .largeCircularIcon()
                .show(); // Show notification
    }

    public void Location(){

    }
}