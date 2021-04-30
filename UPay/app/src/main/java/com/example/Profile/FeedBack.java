package com.example.Profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.upay.JavaMailAPI;
import com.example.upay.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class FeedBack extends BottomSheetDialogFragment {
    private DatabaseReference mDatabase;
    private FirebaseUser user;
    //
    EditText editText;
    ImageButton imageButton;
    RatingBar ratingBar;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_feed_back, container, false);
         editText = view.findViewById(R.id.editText5);
         imageButton = view.findViewById(R.id.imageButton14);
         ratingBar = view.findViewById(R.id.ratingBar);
         //
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmail(editText.getText().toString());
                dismiss();
                Toast.makeText(getContext(),"Feedback sent!",Toast.LENGTH_SHORT).show();
            }
        });
        return  view;
    }


    private void sendEmail(String msg) {
        user = FirebaseAuth.getInstance().getCurrentUser();
        String mEmail = "upayworldservice@gmail.com";
        String mSubject = "Feedback";
        String mMessage = msg+"\nrating:"+ratingBar.getNumStars()+"\nfrom: "+user.getEmail();

        JavaMailAPI javaMailAPI = new JavaMailAPI(getContext(), mEmail, mSubject, mMessage);

        javaMailAPI.execute();
    }
}