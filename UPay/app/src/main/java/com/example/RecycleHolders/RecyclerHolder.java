package com.example.RecycleHolders;

import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Profile.PersonalInfoFragment;
import com.example.Profile.Profile;
import com.example.upay.BottomSheetHome;
import com.example.upay.PurchaseItems;
import com.example.upay.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RecyclerHolder extends RecyclerView.ViewHolder {

    private TextView Name,location,amount,Date;
    ImageView imageView;
    private CardView cardView;
    Animation animation;
    ImageButton imageButton;
    private FirebaseUser user;
    private DatabaseReference mDatabase;

    public RecyclerHolder(View itemView) {
        super(itemView);
        Name = itemView.findViewById(R.id.txtName);
        location = itemView.findViewById(R.id.txtLocation);
        amount = itemView.findViewById(R.id.txtAmount);
        imageView = itemView.findViewById(R.id.imageView3);
        Date = itemView.findViewById(R.id.textView22);
        //
        imageButton = itemView.findViewById(R.id.imageButton18);
        //
        cardView = itemView.findViewById(R.id.income);
        animation = AnimationUtils.loadAnimation(itemView.getContext(), R.anim.open_animation);
        animation.setDuration(1200);
        cardView.setAnimation(animation);
        //
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                // Adapter Position
                int Position = getAdapterPosition();
                user = FirebaseAuth.getInstance().getCurrentUser();
                mDatabase = FirebaseDatabase.getInstance().getReference();
                //
                HashMap<String, Object> values2 = new HashMap<>();
                values2.put("Position",Position);
                mDatabase.child("Users").child(user.getUid()).child("Home Position").updateChildren(values2);
                //
                BottomSheetHome bottomSheet = new BottomSheetHome();
                bottomSheet.show(((AppCompatActivity)itemView.getContext()).getSupportFragmentManager(),"TAG");
            }
        });
    }

    public void setDetails(PurchaseItems items) {
        Name.setText(items.getName());
        amount.setText(items.getAmount());
        location.setText(items.getLocation());
        Date.setText(items.getDate());
    }

}