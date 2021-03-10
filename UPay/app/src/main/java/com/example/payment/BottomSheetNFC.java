package com.example.payment;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.nfc.cardemulation.CardEmulation;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.upay.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.io.ByteArrayInputStream;
import java.util.logging.Logger;

public class BottomSheetNFC extends BottomSheetDialogFragment {
    public NfcAdapter mNfcAdapter;
    public CardEmulation cardEmulation;
    VideoView videoView;


    public static final int REQUEST_CODE_DEFAULT_PAYMENT_APP = 1;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_bottom_sheet_nfc, container, false);
        //
        mNfcAdapter = NfcAdapter.getDefaultAdapter(getContext());
        videoView = view.findViewById(R.id.videoView);
        intro_vid();
        videoView.setVisibility(View.INVISIBLE);
        if (mNfcAdapter != null) {
            cardEmulation = CardEmulation.getInstance(mNfcAdapter);
        }
        Payment();
        return  view;
    }

    public void Payment(){
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
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();

    }


    public  void intro_vid(){
        videoView.setVisibility(View.VISIBLE);
        Uri uri = Uri.parse("android.resource://"+ getContext().getPackageName()+"/"+R.raw.vid4);
        videoView.setVideoURI(uri);
        videoView.setZOrderOnTop(true);
        videoView.start();
    }
}