package com.example.payment;

import android.annotation.TargetApi;
import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import androidx.biometric.BiometricPrompt;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

import com.example.upay.HomePage;
import com.example.upay.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.logging.Logger;

public class BottomSheetNFC extends BottomSheetDialogFragment {
    public NfcAdapter mNfcAdapter;
    public CardEmulation cardEmulation;
    VideoView videoView;
    TextView textView;

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

        if (mNfcAdapter != null) {
            cardEmulation = CardEmulation.getInstance(mNfcAdapter);
        }
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

                ComponentName componentName;
                if (mNfcAdapter == null) {
                    Toast.makeText(getContext(), "NFC is not available", Toast.LENGTH_LONG).show();
                }
                else {
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
}