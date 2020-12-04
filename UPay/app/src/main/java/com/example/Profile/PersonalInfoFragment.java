package com.example.Profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

import com.example.upay.BottomSheetPayPal;
import com.example.upay.R;

public class PersonalInfoFragment extends Fragment {

ImageButton imageButton;
    public PersonalInfoFragment() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal_info_frag, container, false);
        imageButton = view.findViewById(R.id.imageButton4);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(),Profile.class);
                startActivity(i);
            }
        });
        return view;
    }

    public void onBackProfile(View view) {
        Intent i = new Intent(getContext(),Profile.class);
        startActivity(i);
    }
}