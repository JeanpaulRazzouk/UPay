package com.example.BarFragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.upay.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import us.fatehi.magnetictrack.bankcard.BankCardMagneticTrack;
import us.fatehi.magnetictrack.bankcard.Track1FormatB;

public class PaymentMethodFragment extends Fragment {
    TextView textView;
    TextView textView2;
    TextView textView3;
    TextView textView4;
    TextView textView5;
    TextView textView6;
    TextView textView7;
    TextView textView8;
    EditText editText;
    ImageButton imageButton;
    ImageButton imageButton2;
    ImageButton imageButton3;
    ImageView imageView,imageView2;
    CardView cardView;
    private FirebaseUser user;
    private DatabaseReference mDatabase;

    public PaymentMethodFragment() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payment_method, container, false);
        textView = view.findViewById(R.id.textView3);
        imageButton = view.findViewById(R.id.imageButton9);
        imageButton2 = view.findViewById(R.id.payment_card);//
        imageButton3 = view.findViewById(R.id.imageButton12);// add button ;
        cardView = view.findViewById(R.id.cardView3);
        cardView.setVisibility(View.INVISIBLE);
        //
        textView2 = view.findViewById(R.id.textView4);
        textView3 = view.findViewById(R.id.card_pan);
        textView4 = view.findViewById(R.id.card_holder_name);
        textView5 = view.findViewById(R.id.expiry_date);
        textView6 = view.findViewById(R.id.expiry_date_val);
        textView7 = view.findViewById(R.id.track_data);
        textView8 = view.findViewById(R.id.add);

        editText = view.findViewById(R.id.editTextTextPersonName2);
        //
        imageView = view.findViewById(R.id.chipset);
        imageView2 = view.findViewById(R.id.visa);

//      textView3.setVisibility(View.INVISIBLE);
//        textView4.setVisibility(View.INVISIBLE);
//        textView5.setVisibility(View.INVISIBLE);
//        textView6.setVisibility(View.INVISIBLE);
//        imageView.setVisibility(View.INVISIBLE);
//        imageView2.setVisibility(View.INVISIBLE);
//
        textView2.setText("No Cards Found");
        //
        user = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        //
        mDatabase = FirebaseDatabase.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    if (!dataSnapshot.child("User Data").child("Track Data").getValue().toString().equals(" ")) {
                        final BankCardMagneticTrack allTracks = BankCardMagneticTrack.from(dataSnapshot.child("User Data").child("Track Data").getValue().toString());
                        Track1FormatB track1Data = allTracks.getTrack1();
                        String cardNumber = track1Data.getPrimaryAccountNumber().getLastFourDigits();
                        String expDate = track1Data.getExpirationDate().toString();
                        String fullName = track1Data.hasName() ? track1Data.getName().getFullName() : "[none]";
                        textView3.setText("**** **** **** " + cardNumber);
                        textView4.setText(fullName);
                        textView6.setText(expDate);
                        //
                        imageButton.setVisibility(View.INVISIBLE);
                        imageButton3.setVisibility(View.INVISIBLE);
                        textView7.setVisibility(View.INVISIBLE);
                        editText.setVisibility(View.INVISIBLE);
                        textView8.setVisibility(View.INVISIBLE);
                        //
                        textView2.setVisibility(View.INVISIBLE);
                        //
                        textView3.setVisibility(View.VISIBLE);
                        textView4.setVisibility(View.VISIBLE);
                        textView5.setVisibility(View.VISIBLE);
                        textView6.setVisibility(View.VISIBLE);
                        imageView.setVisibility(View.VISIBLE);
                        imageView2.setVisibility(View.VISIBLE);

                        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.open_animation);
                        animation.setDuration(1000);
                        cardView.setAnimation(animation);
                        cardView.setVisibility(View.VISIBLE);

                    }else{
                        HashMap<String, Object> values = new HashMap<>();
                        values.put("Track Data", " ");
                        mDatabase.child("Users").child(user.getUid()).child("User Data").updateChildren(values);
                    }
                }catch(Exception e ){
                 }
                }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView2.setText("");
                cardView.setVisibility(View.VISIBLE);
                //
               Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.open_animation);
               animation.setDuration(1300);
               cardView.setAnimation(animation);
               imageButton.setVisibility(View.INVISIBLE);
               //
                textView3.setVisibility(View.INVISIBLE);
                textView4.setVisibility(View.INVISIBLE);
                textView5.setVisibility(View.INVISIBLE);
                textView6.setVisibility(View.INVISIBLE);
                imageView.setVisibility(View.INVISIBLE);
                imageView2.setVisibility(View.INVISIBLE);


            }
        });

        imageButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ObjectAnimator oa1 = ObjectAnimator.ofFloat(cardView, "scaleX", 1f, 0f);
                final ObjectAnimator oa2 = ObjectAnimator.ofFloat(cardView, "scaleX", 0f, 1f);
                oa1.setInterpolator(new DecelerateInterpolator());
                oa2.setInterpolator(new AccelerateDecelerateInterpolator());
                oa1.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        cardView.setCardBackgroundColor(0xFFffffff);
                        oa2.start();
                        String trackData_input = editText.getText().toString();
                        //
                        mDatabase = FirebaseDatabase.getInstance().getReference();
                        user = FirebaseAuth.getInstance().getCurrentUser();
                        HashMap<String, Object> values = new HashMap<>();
                        if (trackData_input !=" ") {
                            values.put("Track Data", trackData_input);
                            mDatabase.child("Users").child(user.getUid()).child("User Data").updateChildren(values);
                        }
                    }
                });
                oa1.start();
            }
        });

        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ObjectAnimator oa1 = ObjectAnimator.ofFloat(cardView, "scaleX", 1f, 0f);
                final ObjectAnimator oa2 = ObjectAnimator.ofFloat(cardView, "scaleX", 0f, 1f);
                oa1.setInterpolator(new DecelerateInterpolator());
                oa2.setInterpolator(new AccelerateDecelerateInterpolator());
                oa1.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        cardView.setCardBackgroundColor(0xFFffffff);
                        oa2.start();

                        textView3.setVisibility(View.INVISIBLE);
                        textView4.setVisibility(View.INVISIBLE);
                        textView5.setVisibility(View.INVISIBLE);
                        textView6.setVisibility(View.INVISIBLE);
                        imageView.setVisibility(View.INVISIBLE);
                        imageView2.setVisibility(View.INVISIBLE);
                        //
                        imageButton3.setVisibility(View.VISIBLE);
                        textView7.setVisibility(View.VISIBLE);
                        editText.setVisibility(View.VISIBLE);
                        textView8.setVisibility(View.VISIBLE);
                    }
                });
                oa1.start();
            }
        });
        return view;
    }
}