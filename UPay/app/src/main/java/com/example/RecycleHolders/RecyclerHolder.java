package com.example.RecycleHolders;

import android.graphics.Bitmap;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.upay.PurchaseItems;
import com.example.upay.R;

public class RecyclerHolder extends RecyclerView.ViewHolder {

    private TextView Name,location,amount,Date;
    ImageView imageView;
    private CardView cardView;
    Animation animation;

    public RecyclerHolder(View itemView) {
        super(itemView);
        Name = itemView.findViewById(R.id.txtName);
        location = itemView.findViewById(R.id.txtLocation);
        amount = itemView.findViewById(R.id.txtAmount);
        imageView = itemView.findViewById(R.id.imageView3);
        Date = itemView.findViewById(R.id.textView22);
        //
        cardView = itemView.findViewById(R.id.income);
        animation = AnimationUtils.loadAnimation(itemView.getContext(), R.anim.open_animation);
        animation.setDuration(1200);
        cardView.setAnimation(animation);
    }

    public void setDetails(PurchaseItems items) {
        Name.setText(items.getName());
        amount.setText(items.getAmount());
        location.setText(items.getLocation());
        Date.setText(items.getDate());
    }

}